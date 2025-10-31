package br.com.instituicao.sistemacomanda.controller;

import br.com.instituicao.sistemacomanda.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet controller for handling document requests.
 */
@WebServlet("/solicitacao/*")
public class SolicitacaoServlet extends BaseController {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!checkAuthentication(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            // List requests
            listarSolicitacoes(request, response);
        } else if (pathInfo.equals("/nova")) {
            // Show new request form
            mostrarFormularioNovaSolicitacao(request, response);
        } else if (pathInfo.startsWith("/detalhe/")) {
            // Show request details
            mostrarDetalhesSolicitacao(request, response, pathInfo);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!checkAuthentication(request, response)) {
            return;
        }
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            // Create new request
            criarNovaSolicitacao(request, response);
        } else if (pathInfo.equals("/atualizar-status")) {
            // Update request status
            atualizarStatusSolicitacao(request, response);
        }
    }
    
    private void listarSolicitacoes(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Usuario usuario = getLoggedInUser(request);
            List<Solicitacao> solicitacoes;
            
            if (usuario.getTipo() == TipoUsuario.ALUNO) {
                solicitacoes = facade.listarSolicitacoesAluno((Aluno) usuario);
            } else {
                solicitacoes = facade.listarSolicitacoesPendentes();
            }
            
            request.setAttribute("solicitacoes", solicitacoes);
            forward(request, response, "solicitacao/lista");
        } catch (SQLException e) {
            setErrorMessage(request, "Erro ao listar solicitações");
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
    
    private void mostrarFormularioNovaSolicitacao(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<DocumentoTipo> tiposDocumento = facade.listarTiposDocumento();
            request.setAttribute("tiposDocumento", tiposDocumento);
            forward(request, response, "solicitacao/formulario");
        } catch (SQLException e) {
            setErrorMessage(request, "Erro ao carregar formulário");
            response.sendRedirect(request.getContextPath() + "/solicitacao");
        }
    }
    
    private void mostrarDetalhesSolicitacao(HttpServletRequest request, HttpServletResponse response, 
            String pathInfo) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(pathInfo.substring(9));
            Solicitacao solicitacao = facade.getSolicitacaoDAO().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));
            
            List<StatusHistorico> historico = facade.listarHistoricoSolicitacao(solicitacao);
            
            request.setAttribute("solicitacao", solicitacao);
            request.setAttribute("historico", historico);
            forward(request, response, "solicitacao/detalhe");
        } catch (Exception e) {
            setErrorMessage(request, "Erro ao carregar detalhes da solicitação");
            response.sendRedirect(request.getContextPath() + "/solicitacao");
        }
    }
    
    private void criarNovaSolicitacao(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Usuario usuario = getLoggedInUser(request);
            if (!(usuario instanceof Aluno)) {
                setErrorMessage(request, "Apenas alunos podem criar solicitações");
                response.sendRedirect(request.getContextPath() + "/solicitacao");
                return;
            }
            
            Long tipoDocumentoId = Long.parseLong(request.getParameter("tipoDocumento"));
            DocumentoTipo tipoDocumento = facade.getDocumentoTipoDAO().findById(tipoDocumentoId)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de documento inválido"));
            
            UrgenciaSolicitacao urgencia = UrgenciaSolicitacao.valueOf(request.getParameter("urgencia"));
            String observacoes = request.getParameter("observacoes");
            
            facade.criarSolicitacao((Aluno) usuario, tipoDocumento, urgencia, observacoes);
            
            setSuccessMessage(request, "Solicitação criada com sucesso");
            response.sendRedirect(request.getContextPath() + "/solicitacao");
        } catch (Exception e) {
            setErrorMessage(request, "Erro ao criar solicitação");
            response.sendRedirect(request.getContextPath() + "/solicitacao/nova");
        }
    }
    
    private void atualizarStatusSolicitacao(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Usuario usuario = getLoggedInUser(request);
            if (!(usuario instanceof Administrador)) {
                setErrorMessage(request, "Apenas administradores podem atualizar status");
                response.sendRedirect(request.getContextPath() + "/solicitacao");
                return;
            }
            
            Long solicitacaoId = Long.parseLong(request.getParameter("solicitacaoId"));
            Solicitacao solicitacao = facade.getSolicitacaoDAO().findById(solicitacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));
            
            StatusSolicitacao novoStatus = StatusSolicitacao.valueOf(request.getParameter("status"));
            String observacao = request.getParameter("observacao");
            
            facade.atualizarStatusSolicitacao(solicitacao, novoStatus, observacao, (Administrador) usuario);
            
            setSuccessMessage(request, "Status atualizado com sucesso");
            response.sendRedirect(request.getContextPath() + "/solicitacao/detalhe/" + solicitacaoId);
        } catch (Exception e) {
            setErrorMessage(request, "Erro ao atualizar status");
            response.sendRedirect(request.getContextPath() + "/solicitacao");
        }
    }
}