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
import classes.UserDAO;

// Assuming User, Solicitation, DocumentType, UserDAO, SolicitationDAO, and DocumentTypeDAO are in the default package, no explicit import needed for them.

@WebServlet("/admin_dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SolicitationDAO solicitationDAO = new SolicitationDAO();
    private UserDAO userDAO = new UserDAO();
    private DocumentTypeDAO documentTypeDAO = new DocumentTypeDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            // Not logged in, redirect to login page
            response.sendRedirect("login");
            return;
        }

        // Fetch all solicitations
        List<Solicitation> solicitations = solicitationDAO.getAllSolicitations();
        
        // Set the list as a request attribute
        request.setAttribute("solicitations", solicitations);

        // Fetch all users
        List<User> allUsers = userDAO.getAllUsers();
        request.setAttribute("allUsers", allUsers);

        // Fetch all document types
        List<DocumentType> allDocumentTypes = documentTypeDAO.getAllDocumentTypes();
        request.setAttribute("allDocumentTypes", allDocumentTypes);

        // Forward to the admin dashboard JSP
        request.getRequestDispatcher("/admin_dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // For simplicity, redirect POST requests to doGet
        doGet(request, response);
    }
}