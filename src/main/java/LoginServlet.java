import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import classes.User;
import classes.UserDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Requirement 2: Forward the request to login.jsp
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve email and senha parameters
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Use UserDAO.authenticate(email, senha)
        User user = userDAO.authenticate(email, senha);

        if (user != null) {
            // Authentication succeeds
            
            // Store the User object in the session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect based on user type
            if ("ALUNO".equals(user.getTipo())) {
                response.sendRedirect(request.getContextPath() + "/student_dashboard");
            } else if ("ADMIN".equals(user.getTipo())) {
                response.sendRedirect(request.getContextPath() + "/admin_dashboard");
            } else {
                // Handle unexpected user type or default redirect
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else {
            // Authentication fails
            
            // Set an error message in the request attribute
            request.setAttribute("error", "Invalid credentials");
            
            // Forward the request back to login.jsp
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}