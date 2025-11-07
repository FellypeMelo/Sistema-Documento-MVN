import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import classes.Solicitation;
import classes.SolicitationDAO;
import classes.StatusHistory;
import classes.StatusHistoryDAO;
import classes.User;
import classes.UserDAO;
import classes.DocumentType;
import classes.DocumentTypeDAO;

// User, Solicitation, StatusHistory, SolicitationDAO, StatusHistoryDAO are assumed to be in the default package and visible.

@WebServlet("/solicitation_details")
public class SolicitationDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private SolicitationDAO solicitationDAO = new SolicitationDAO();
    private StatusHistoryDAO historyDAO = new StatusHistoryDAO();
    private UserDAO userDAO = new UserDAO();
    private DocumentTypeDAO documentTypeDAO = new DocumentTypeDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Check for a logged-in User in the session. If null, redirect to /login.
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2. Retrieve the id parameter (solicitation ID) from the request.
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            // Redirect to a safe place if ID is missing, e.g., admin or student dashboard
            response.sendRedirect(request.getContextPath() + "/"); 
            return;
        }

        try {
            int solicitationId = Integer.parseInt(idParam);
            
            // 3. Use SolicitationDAO.getSolicitationById(id) to fetch the Solicitation. Set it as request attribute "solicitation".
            Solicitation solicitation = solicitationDAO.getSolicitationById(solicitationId);
            
            if (solicitation == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Solicitation details not found for ID: " + solicitationId);
                return;
            }

            // Fetch related User (Solicitante) details
            User student = userDAO.getUserById(solicitation.getIdAluno());
            String studentName = (student != null) ? student.getNome() : "N/A";
            String studentMatricula = (student != null) ? student.getMatricula() : "N/A";
            
            request.setAttribute("studentName", studentName);
            request.setAttribute("studentMatricula", studentMatricula);

            // Fetch related DocumentType details
            DocumentType documentType = documentTypeDAO.getDocumentTypeById(solicitation.getIdDocumentoTipo());
            String documentTypeName = (documentType != null) ? documentType.getNome() : "N/A";
            
            request.setAttribute("documentTypeName", documentTypeName);

            // 4. Use StatusHistoryDAO.getHistoryBySolicitationId(id) to fetch the history. Set it as request attribute "history".
            List<StatusHistory> history = historyDAO.getHistoryBySolicitationId(solicitationId);

            request.setAttribute("solicitation", solicitation);
            request.setAttribute("history", history);

            // 5. Forward the request to solicitation_details.jsp.
            request.getRequestDispatcher("/solicitation_details.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Solicitation ID format.");
        } catch (Exception e) {
            // In a real application, we would log this error properly.
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving solicitation details.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Check for a logged-in User in the session. If null, redirect to /login.
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2. Retrieve solicitationId and newStatus parameters.
        String solicitationIdParam = request.getParameter("solicitationId");
        String newStatus = request.getParameter("newStatus");

        if (solicitationIdParam == null || solicitationIdParam.isEmpty() || newStatus == null || newStatus.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing solicitationId or newStatus parameter.");
            return;
        }

        try {
            int solicitationId = Integer.parseInt(solicitationIdParam);
            
            // 3. Use SolicitationDAO.updateStatus(solicitationId, newStatus) to update the status.
            boolean statusUpdated = solicitationDAO.updateStatus(solicitationId, newStatus);

            if (statusUpdated) {
                // Retrieve the optional observations parameter
                String observations = request.getParameter("observations");
                String responsavel;

                if (observations != null && !observations.trim().isEmpty()) {
                    // Use observations as responsavel if provided
                    responsavel = observations.trim();
                } else {
                    // Otherwise, use the logged-in user's name
                    responsavel = user.getNome();
                }

                // Create a new StatusHistory object (id=0 and dataHora=null are placeholders, DAO handles them)
                StatusHistory history = new StatusHistory(0, solicitationId, newStatus, null, responsavel);
                
                // Save history record
                historyDAO.saveHistory(history);
            }

            // 4. Redirect the user back to /solicitation_details?id={solicitationId} (GET request).
            response.sendRedirect(request.getContextPath() + "/solicitation_details?id=" + solicitationId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Solicitation ID format.");
        } catch (Exception e) {
            // In a real application, we would log this error properly.
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating solicitation status.");
        }
    }
}