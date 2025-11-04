package br.com.instituicao.sistemacomanda.controller;

import br.com.instituicao.sistemacomanda.dao.ConfigPrioridadeDAO;
import br.com.instituicao.sistemacomanda.dao.DocumentoTipoDAO;
import br.com.instituicao.sistemacomanda.dao.SolicitacaoDAOExt;
import br.com.instituicao.sistemacomanda.dao.StatusHistoricoDAOExt;
import br.com.instituicao.sistemacomanda.dao.UsuarioDAO;
import br.com.instituicao.sistemacomanda.facade.SistemaDocumentosFacade;
import br.com.instituicao.sistemacomanda.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Base controller class that provides common functionality for all servlets.
 */
public abstract class BaseController extends HttpServlet {
    protected final SistemaDocumentosFacade facade;
    
    public BaseController() {
        this.facade = new SistemaDocumentosFacade(
            new UsuarioDAO(),
            new DocumentoTipoDAO(),
            new SolicitacaoDAOExt(),
            new StatusHistoricoDAOExt(),
            new ConfigPrioridadeDAO()
        );
    }
    
    /**
     * Gets the currently logged in user from the session.
     * 
     * @param request HTTP request
     * @return Usuario object or null if not logged in
     */
    protected Usuario getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Usuario) session.getAttribute("usuario");
        }
        return null;
    }
    
    /**
     * Checks if a user is logged in and redirects to login if not.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @return true if user is logged in, false otherwise
     * @throws IOException if an I/O error occurs
     */
    protected boolean checkAuthentication(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        if (getLoggedInUser(request) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
    
    /**
     * Sets an error message in the session.
     * 
     * @param request HTTP request
     * @param message Error message
     */
    protected void setErrorMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("errorMessage", message);
    }
    
    /**
     * Sets a success message in the session.
     * 
     * @param request HTTP request
     * @param message Success message
     */
    protected void setSuccessMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("successMessage", message);
    }
    
    /**
     * Forward the request to a JSP view.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @param view Path to the JSP view
     * @throws ServletException if the target resource throws this exception
     * @throws IOException if the target resource throws this exception
     */
    protected void forward(HttpServletRequest request, HttpServletResponse response, String view) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(request, response);
    }

    protected boolean isNullOrEmpty(String... values) {
        for (String value : values) {
            if (value == null || value.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}