package classes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /**
     * Authenticates a user against the 'usuarios' table.
     * 
     * @param email The user's email.
     * @param senha The user's password.
     * @return A fully populated User object if authentication succeeds, otherwise null.
     */
    public User authenticate(String email, String senha) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        // SQL query to find user by email and password
        String sql = "SELECT id, nome, email, senha, tipo, matricula, curso FROM usuarios WHERE email = ? AND senha = ?";

        try {
            // 1. Get a database connection
            conn = DBUtil.getConnection();
            
            if (conn == null) {
                // DBUtil already prints error, return null as required
                return null;
            }

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            // 2. Execute the query
            rs = stmt.executeQuery();

            // 3. If a user is found, create and return a fully populated User object
            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String userEmail = rs.getString("email");
                String userSenha = rs.getString("senha");
                String tipo = rs.getString("tipo");
                String matricula = rs.getString("matricula");
                String curso = rs.getString("curso");

                user = new User(id, nome, userEmail, userSenha, tipo, matricula, curso);
            }
            
            // If rs.next() is false, user remains null, satisfying requirement 4.

        } catch (SQLException e) {
            // 4. If no user is found or an exception occurs, return null
            System.err.println("SQL Error during authentication: " + e.getMessage());
            return null;
        } finally {
            // 5. Ensure resources are closed
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return user;
    }

    /**
     * Retrieves all users from the 'usuarios' table.
     * 
     * @return A List<User> containing all users, or an empty list on failure.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // SQL query to select all users
        String sql = "SELECT id, nome, email, senha, tipo, matricula, curso FROM usuarios";

        try {
            conn = DBUtil.getConnection();
            
            if (conn == null) {
                return users; // returns empty list
            }

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String userEmail = rs.getString("email");
                String userSenha = rs.getString("senha");
                String tipo = rs.getString("tipo");
                String matricula = rs.getString("matricula");
                String curso = rs.getString("curso");

                User user = new User(id, nome, userEmail, userSenha, tipo, matricula, curso);
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving all users: " + e.getMessage());
            // Return empty list on failure as required
            return new ArrayList<>(); 
        } finally {
            // Ensure resources are closed
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return users;
    }

    /**
     * Retrieves a single user from the 'usuarios' table based on the provided id.
     * 
     * @param id The ID of the user to retrieve.
     * @return A fully populated User object if found, otherwise null.
     */
    public User getUserById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        // SQL query to find user by ID
        String sql = "SELECT id, nome, email, senha, tipo, matricula, curso FROM usuarios WHERE id = ?";

        try {
            // 1. Get a database connection
            conn = DBUtil.getConnection();
            
            if (conn == null) {
                return null;
            }

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            // 2. Execute the query
            rs = stmt.executeQuery();

            // 3. If a user is found, create and return a fully populated User object
            if (rs.next()) {
                int userId = rs.getInt("id");
                String nome = rs.getString("nome");
                String userEmail = rs.getString("email");
                String userSenha = rs.getString("senha");
                String tipo = rs.getString("tipo");
                String matricula = rs.getString("matricula");
                String curso = rs.getString("curso");

                user = new User(userId, nome, userEmail, userSenha, tipo, matricula, curso);
            }
            
        } catch (SQLException e) {
            // 4. If no user is found or an exception occurs, return null
            System.err.println("SQL Error retrieving user by ID: " + e.getMessage());
            return null;
        } finally {
            // 5. Ensure resources are closed
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return user;
    }
}