package br.com.instituicao.sistemacomanda.api;

import br.com.instituicao.sistemacomanda.dao.ConfigPrioridadeDAO;
import br.com.instituicao.sistemacomanda.dao.DocumentoTipoDAO;
import br.com.instituicao.sistemacomanda.dao.SolicitacaoDAOExt;
import br.com.instituicao.sistemacomanda.dao.StatusHistoricoDAOExt;
import br.com.instituicao.sistemacomanda.dao.UsuarioDAO;
import br.com.instituicao.sistemacomanda.facade.SistemaDocumentosFacade;
import br.com.instituicao.sistemacomanda.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/api/solicitacoes/*")
public class SolicitacaoAPI extends HttpServlet {
    private final SistemaDocumentosFacade facade;
    private final Gson gson;

    public SolicitacaoAPI() {
        this.facade = new SistemaDocumentosFacade(
            new UsuarioDAO(),
            new DocumentoTipoDAO(),
            new SolicitacaoDAOExt(),
            new StatusHistoricoDAOExt(),
            new ConfigPrioridadeDAO()
        );
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List all requests
                listarSolicitacoes(request, response);
            } else if (pathInfo.matches("/\\d+")) {
                // Get specific request
                Long id = Long.parseLong(pathInfo.substring(1));
                buscarSolicitacao(id, response);
            } else if (pathInfo.equals("/pendentes")) {
                // List pending requests
                listarSolicitacoesPendentes(response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao acessar banco de dados");
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            SolicitacaoDTO dto = gson.fromJson(request.getReader(), SolicitacaoDTO.class);
            
            // Validate request
            if (dto == null || dto.getIdAluno() == null || dto.getIdDocumentoTipo() == null || 
                dto.getUrgencia() == null || dto.getObservacoes() == null) {
                enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos");
                return;
            }

            // Get required entities
            Usuario usuario = facade.getUsuarioDAO().findById(dto.getIdAluno())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
            if (!(usuario instanceof Aluno)) {
                throw new IllegalArgumentException("Usuário não é um aluno");
            }

            DocumentoTipo tipoDocumento = facade.getDocumentoTipoDAO().findById(dto.getIdDocumentoTipo())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de documento não encontrado"));

            // Create request
            Solicitacao solicitacao = facade.criarSolicitacao(
                (Aluno) usuario, 
                tipoDocumento, 
                dto.getUrgencia(), 
                dto.getObservacoes()
            );

            // Return created request
            response.setStatus(HttpServletResponse.SC_CREATED);
            enviarResposta(response, solicitacao);

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (SQLException e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao acessar banco de dados");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || !pathInfo.matches("/\\d+/status")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1, pathInfo.indexOf("/status")));
            StatusUpdateDTO dto = gson.fromJson(request.getReader(), StatusUpdateDTO.class);

            // Validate request
            if (dto == null || dto.getStatus() == null || dto.getIdAdmin() == null) {
                enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos");
                return;
            }

            // Get required entities
            Solicitacao solicitacao = facade.getSolicitacaoDAO().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));

            Usuario admin = facade.getUsuarioDAO().findById(dto.getIdAdmin())
                .orElseThrow(() -> new IllegalArgumentException("Administrador não encontrado"));
            if (!(admin instanceof Administrador)) {
                throw new IllegalArgumentException("Usuário não é um administrador");
            }

            // Update status
            facade.atualizarStatusSolicitacao(solicitacao, dto.getStatus(), dto.getObservacao(), (Administrador) admin);
            
            // Return updated request
            enviarResposta(response, solicitacao);

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (SQLException e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao acessar banco de dados");
        }
    }

    private void listarSolicitacoes(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        String alunoId = request.getParameter("aluno");
        List<Solicitacao> solicitacoes;

        if (alunoId != null) {
            Usuario aluno = facade.getUsuarioDAO().findById(Long.parseLong(alunoId))
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
            if (!(aluno instanceof Aluno)) {
                throw new IllegalArgumentException("Usuário não é um aluno");
            }
            solicitacoes = facade.listarSolicitacoesAluno((Aluno) aluno);
        } else {
            solicitacoes = facade.getSolicitacaoDAO().findAll();
        }

        enviarResposta(response, solicitacoes);
    }

    private void buscarSolicitacao(Long id, HttpServletResponse response) 
            throws SQLException, IOException {
        Solicitacao solicitacao = facade.getSolicitacaoDAO().findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));
        enviarResposta(response, solicitacao);
    }

    private void listarSolicitacoesPendentes(HttpServletResponse response) 
            throws SQLException, IOException {
        List<Solicitacao> solicitacoes = facade.listarSolicitacoesPendentes();
        enviarResposta(response, solicitacoes);
    }

    private void enviarResposta(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(data));
        }
    }

    private void enviarErro(HttpServletResponse response, int status, String mensagem) 
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(new ErrorDTO(mensagem)));
        }
    }
}