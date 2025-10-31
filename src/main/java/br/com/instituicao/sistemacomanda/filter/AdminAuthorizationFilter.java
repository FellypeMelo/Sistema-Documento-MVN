package br.com.instituicao.sistemacomanda.filter;

import br.com.instituicao.sistemacomanda.model.Administrador;
import br.com.instituicao.sistemacomanda.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/api/admin/*"})
public class AdminAuthorizationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        // Check if user is authenticated and is an admin
        if (session != null && session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario instanceof Administrador) {
                chain.doFilter(request, response);
                return;
            }
        }
        
        // For API requests, return 403 Forbidden
        if (httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/api/")) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        // For web pages, redirect to access denied page
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/acesso-negado");
    }
    
    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}