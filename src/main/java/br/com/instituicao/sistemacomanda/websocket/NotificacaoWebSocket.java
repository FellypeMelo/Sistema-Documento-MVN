package br.com.instituicao.sistemacomanda.websocket;

import br.com.instituicao.sistemacomanda.api.LocalDateTimeAdapter;
import br.com.instituicao.sistemacomanda.model.Solicitacao;
import br.com.instituicao.sistemacomanda.model.StatusHistorico;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/notificacoes/{userId}")
public class NotificacaoWebSocket {
    private static final Map<Long, Set<Session>> userSessions = new ConcurrentHashMap<>();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        userSessions.computeIfAbsent(userId, k -> new CopyOnWriteArraySet<>()).add(session);
    }
    
    @OnClose
    public void onClose(Session session, @PathParam("userId") Long userId) {
        Set<Session> sessions = userSessions.get(userId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                userSessions.remove(userId);
            }
        }
    }
    
    @OnError
    public void onError(Session session, @PathParam("userId") Long userId, Throwable throwable) {
        System.err.println("Error on WebSocket connection for user " + userId + ": " + throwable.getMessage());
        onClose(session, userId);
    }
    
    public static void notificarMudancaStatus(Solicitacao solicitacao, StatusHistorico historico) {
        NotificacaoDTO notificacao = new NotificacaoDTO(
            "STATUS_ATUALIZADO",
            solicitacao.getId(),
            historico.getStatus(),
            historico.getObservacao(),
            historico.getDataMudanca()
        );
        
        String mensagem = gson.toJson(notificacao);
        
        // Notify the student
        notificarUsuario(solicitacao.getUsuario().getId(), mensagem);
        
        // Notify all administrators
        Set<Session> adminSessions = userSessions.get(0L); // Assuming 0 is used for broadcasting to all admins
        if (adminSessions != null) {
            broadcast(adminSessions, mensagem);
        }
    }
    
    private static void notificarUsuario(Long userId, String mensagem) {
        Set<Session> sessions = userSessions.get(userId);
        if (sessions != null) {
            broadcast(sessions, mensagem);
        }
    }
    
    private static void broadcast(Set<Session> sessions, String mensagem) {
        for (Session session : sessions) {
            try {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(mensagem);
                }
            } catch (IOException e) {
                System.err.println("Error sending WebSocket message: " + e.getMessage());
            }
        }
    }
}