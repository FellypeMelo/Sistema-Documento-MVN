package br.com.instituicao.sistemacomanda.controller;

import br.com.instituicao.sistemacomanda.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet controller for handling administrative tasks.
 */
public class AdminServlet extends BaseController {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!checkAuthentication(request, response)) {
            return;
        }
        
        Usuario usuario = getLoggedInUser(request);
        if (!(usuario instanceof Administrador)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/dashboard")) {
            mostrarDashboard(request, response);
        } else if (pathInfo.equals("/usuarios")) {
            listarUsuarios(request, response);
        } else if (pathInfo.equals("/tipos-documento")) {
            listarTiposDocumento(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!checkAuthentication(request, response)) {
            return;
        }
        
        Usuario usuario = getLoggedInUser(request);
        if (!(usuario instanceof Administrador)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        try {
            switch (pathInfo) {
                case "/usuario/criar":
                    criarUsuario(request, response);
                    break;
                case "/usuario/desativar":
                    desativarUsuario(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            setErrorMessage(request, "Erro ao processar a requisição: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        }
    }
    
    private void mostrarDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get pending requests for dashboard
            List<Solicitacao> solicitacoesPendentes = facade.listarSolicitacoesPendentes();
            request.setAttribute("solicitacoesPendentes", solicitacoesPendentes);
            
            forward(request, response, "admin/dashboard");
        } catch (SQLException e) {
            setErrorMessage(request, "Erro ao carregar dashboard");
            response.sendRedirect(request.getContextPath() + "/admin");
        }
    }
    
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Usuario> usuarios = facade.getUsuarioDAO().findAll();
            request.setAttribute("usuarios", usuarios);
            forward(request, response, "admin/usuarios");
        } catch (SQLException e) {
            setErrorMessage(request, "Erro ao listar usuários");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        }
    }
    
    private void listarTiposDocumento(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<DocumentoTipo> tiposDocumento = facade.listarTiposDocumento();
            request.setAttribute("tiposDocumento", tiposDocumento);
            forward(request, response, "admin/tipos-documento");
        } catch (SQLException e) {
            setErrorMessage(request, "Erro ao listar tipos de documento");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        }
    }
    
    private void criarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            TipoUsuario tipo = TipoUsuario.valueOf(request.getParameter("tipo"));
            
            Usuario novoUsuario;
            if (tipo == TipoUsuario.ALUNO) {
                String matricula = request.getParameter("matricula");
                String curso = request.getParameter("curso");
                novoUsuario = new Aluno(nome, email, matricula, curso);
            } else {
                novoUsuario = new Administrador(nome, email);
            }
            novoUsuario.setSenha(senha);
            
            facade.criarUsuario(novoUsuario);
            setSuccessMessage(request, "Usuário criado com sucesso");
        } catch (Exception e) {
            setErrorMessage(request, "Erro ao criar usuário: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/admin/usuarios");
    }
    
    private void desativarUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Long userId = Long.parseLong(request.getParameter("id"));
            Usuario usuario = facade.getUsuarioDAO().findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            
            facade.desativarUsuario(usuario);
            setSuccessMessage(request, "Usuário desativado com sucesso");
        } catch (Exception e) {
            setErrorMessage(request, "Erro ao desativar usuário: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/admin/usuarios");
    }
}