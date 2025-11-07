package classes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String URL = "jdbc:mariadb://localhost:3306/sistema_documento";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the JDBC driver
            Class.forName(DRIVER);
            
            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MariaDB JDBC Driver not found.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection.");
            e.printStackTrace();
            return null;
        }
        return connection;
    }
}