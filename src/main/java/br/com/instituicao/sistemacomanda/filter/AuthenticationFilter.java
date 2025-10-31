package br.com.instituicao.sistemacomanda.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/app/*", "/api/*"})
public class AuthenticationFilter implements Filter {
    
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
        
        String requestURI = httpRequest.getRequestURI();
        
        // Allow access to login page and resources
        if (isPublicResource(requestURI)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Check if user is authenticated
        if (session == null || session.getAttribute("usuario") == null) {
            // For API requests, return 401 Unauthorized
            if (requestURI.startsWith(httpRequest.getContextPath() + "/api/")) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            
            // For web pages, redirect to login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        
        // Continue with the request
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Cleanup code if needed
    }
    
    private boolean isPublicResource(String uri) {
        return uri.endsWith("/login") ||
               uri.endsWith(".css") ||
               uri.endsWith(".js") ||
               uri.endsWith(".ico") ||
               uri.contains("/resources/");
    }
}