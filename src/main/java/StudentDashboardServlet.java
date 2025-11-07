import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import classes.DocumentType;
import classes.DocumentTypeDAO;
import classes.Solicitation;
import classes.SolicitationDAO;
import classes.User;

@WebServlet("/student_dashboard")
public class StudentDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final SolicitationDAO solicitationDAO = new SolicitationDAO();
    private final DocumentTypeDAO documentTypeDAO = new DocumentTypeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Check for a logged-in User in the session. If null, redirect to /login.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        
        // If logged in, retrieve the user's ID.
        int userId = user.getId();

        // Use SolicitationDAO to fetch the user's list of Solicitations.
        List<Solicitation> solicitations = solicitationDAO.getSolicitationsByUserId(userId);
        // Set this list as a request attribute named "solicitations".
        request.setAttribute("solicitations", solicitations);

        // Use DocumentTypeDAO to fetch all available DocumentTypes.
        List<DocumentType> documentTypes = documentTypeDAO.getAllDocumentTypes();
        // Set this list as a request attribute named "documentTypes".
        request.setAttribute("documentTypes", documentTypes);

        // Forward the request to student_dashboard.jsp.
        request.getRequestDispatcher("student_dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Check for a logged-in User in the session. If null, redirect to /login.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Retrieve the logged-in User object.
        User user = (User) session.getAttribute("user");

        // Retrieve documentTypeId (as String, parse to int) and urgency (String) from request parameters.
        String documentTypeIdStr = request.getParameter("documentTypeId");
        String urgency = request.getParameter("urgency");

        if (documentTypeIdStr == null || urgency == null || documentTypeIdStr.isEmpty() || urgency.isEmpty()) {
            // Handle missing parameters (e.g., show error or redirect back)
            // For simplicity, we redirect back to the dashboard.
            response.sendRedirect(request.getContextPath() + "/student_dashboard");
            return;
        }

        try {
            int documentTypeId = Integer.parseInt(documentTypeIdStr);
            int userId = user.getId();

            // Create a new Solicitation object using the user's ID, document type ID, and urgency.
            // Note: Solicitation constructor requires id, idAluno, idDocumentoTipo, dataSolicitacao, urgencia, status, prioridade.
            // Since this is a new solicitation, we can use placeholder/default values for id, dataSolicitacao, status, and prioridade,
            // as SolicitationDAO.saveSolicitation handles setting dataSolicitacao, status, and prioridade internally (lines 182, 186, 188 of SolicitationDAO.java).
            
            // Using 0 for ID, null for dataSolicitacao, null for status, and 0 for prioridade as placeholders for the constructor.
            Solicitation newSolicitation = new Solicitation(0, userId, documentTypeId, null, urgency, null, 0);

            // Use SolicitationDAO.saveSolicitation() to persist the new request.
            solicitationDAO.saveSolicitation(newSolicitation);

        } catch (NumberFormatException e) {
            System.err.println("Invalid documentTypeId format: " + documentTypeIdStr);
            // Handle error if documentTypeId is not an integer
        } catch (Exception e) {
            System.err.println("Error processing new solicitation: " + e.getMessage());
            e.printStackTrace();
        }

        // Redirect the user to /student_dashboard (GET request) to display the updated list.
        response.sendRedirect(request.getContextPath() + "/student_dashboard");
    }
}