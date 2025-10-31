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

/**
 * WebSocket endpoint for real-time notifications in the document request system.
 * This class handles WebSocket connections for both students and administrators,
 * providing real-time updates about document request status changes.
 * 
 * <p>The endpoint path is "/websocket/notificacoes/{userId}" where userId is the
 * user's identifier. For administrators, special handling is implemented using
 * userId = 0 for broadcasting notifications to all admin sessions.</p>
 * 
 * <p>This implementation uses thread-safe collections (ConcurrentHashMap and
 * CopyOnWriteArraySet) to handle concurrent connections and notifications.</p>
 * 
 * @author Sistema de Documentos Team
 * @version 1.0
 * @since 2025-10-31
 */
@ServerEndpoint("/websocket/notificacoes/{userId}")
public class NotificacaoWebSocket {
    private static final Map<Long, Set<Session>> userSessions = new ConcurrentHashMap<>();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    
    /**
     * Handles new WebSocket connections.
     * When a user connects, their session is stored in a thread-safe collection
     * mapped to their user ID.
     *
     * @param session the WebSocket session being opened
     * @param userId the ID of the user establishing the connection
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        userSessions.computeIfAbsent(userId, k -> new CopyOnWriteArraySet<>()).add(session);
    }
    
    /**
     * Handles WebSocket connection closures.
     * Removes the session from the user's session set and cleans up empty sets.
     *
     * @param session the WebSocket session being closed
     * @param userId the ID of the user whose connection is closing
     */
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
    
    /**
     * Handles WebSocket errors.
     * Logs the error and closes the connection to maintain system stability.
     *
     * @param session the WebSocket session where the error occurred
     * @param userId the ID of the user whose connection encountered an error
     * @param throwable the error that occurred
     */
    @OnError
    public void onError(Session session, @PathParam("userId") Long userId, Throwable throwable) {
        System.err.println("Error on WebSocket connection for user " + userId + ": " + throwable.getMessage());
        onClose(session, userId);
    }
    
    /**
     * Notifies relevant users about a status change in a document request.
     * Creates a notification DTO, converts it to JSON, and sends it to both
     * the student who made the request and all administrators.
     *
     * @param solicitacao the document request that was updated
     * @param historico the history record containing the status change details
     */
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
    
    /**
     * Sends a notification to a specific user.
     * Retrieves all active sessions for the user and broadcasts the message.
     *
     * @param userId the ID of the user to notify
     * @param mensagem the JSON-formatted message to send
     */
    private static void notificarUsuario(Long userId, String mensagem) {
        Set<Session> sessions = userSessions.get(userId);
        if (sessions != null) {
            broadcast(sessions, mensagem);
        }
    }
    
    /**
     * Broadcasts a message to a set of WebSocket sessions.
     * Handles each session individually and manages potential IO exceptions.
     *
     * @param sessions the set of WebSocket sessions to send the message to
     * @param mensagem the message to broadcast
     */
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