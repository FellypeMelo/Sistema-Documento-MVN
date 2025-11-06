package br.com.instituicao.sistemacomanda.controller;

import br.com.instituicao.sistemacomanda.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Servlet controller for handling user authentication.
 */
public class LoginServlet extends BaseController {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            redirectToUserDashboard(request, response, (Usuario) session.getAttribute("usuario"));
            return;
        }
        
        // Show login page
        forward(request, response, "login");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        try {
            Optional<Usuario> usuarioOpt = facade.autenticarUsuario(email, senha);
            
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                // Create new session and store user
                HttpSession session = request.getSession(true);
                session.setAttribute("usuario", usuario);
                
                // Redirect to appropriate dashboard
                redirectToUserDashboard(request, response, usuario);
            } else {
                setErrorMessage(request, "Email ou senha inválidos");
                forward(request, response, "login");
            }
        } catch (SQLException e) {
            setErrorMessage(request, "Erro ao realizar login. Tente novamente.");
            forward(request, response, "login");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle logout
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }
    
    private void redirectToUserDashboard(HttpServletRequest request, HttpServletResponse response, 
            Usuario usuario) throws IOException {
        switch (usuario.getTipo()) {
            case ALUNO:
                response.sendRedirect(request.getContextPath() + "/aluno/dashboard");
                break;
            case ADMIN:
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
            default:
                throw new IllegalStateException("Tipo de usuário inválido: " + usuario.getTipo());
        }
    }
}