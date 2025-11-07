package classes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class SolicitationDAO {

    private Solicitation extractSolicitationFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idAluno = rs.getInt("id_aluno");
        int idDocumentoTipo = rs.getInt("id_documento_tipo");
        String dataSolicitacao = rs.getString("data_solicitacao");
        String urgencia = rs.getString("urgencia");
        String status = rs.getString("status");
        int prioridade = rs.getInt("prioridade");

        return new Solicitation(id, idAluno, idDocumentoTipo, dataSolicitacao, urgencia, status, prioridade);
    }

    /**
     * Retrieves all solicitations associated with a specific student (id_aluno).
     * 
     * @param userId The ID of the student (id_aluno).
     * @return A list of Solicitation objects, or an empty list if an error occurs.
     */
    public List<Solicitation> getSolicitationsByUserId(int userId) {
        List<Solicitation> solicitations = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT id, id_aluno, id_documento_tipo, data_solicitacao, urgencia, status, prioridade FROM solicitacoes WHERE id_aluno = ?";

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return solicitations; // Return empty list on connection failure
            }

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                solicitations.add(extractSolicitationFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving solicitations by user ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return solicitations;
    }

    /**
     * Retrieves all solicitations in the system (for the admin view).
     * 
     * @return A list of all Solicitation objects, or an empty list if an error occurs.
     */
    public List<Solicitation> getAllSolicitations() {
        List<Solicitation> solicitations = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT id, id_aluno, id_documento_tipo, data_solicitacao, urgencia, status, prioridade FROM solicitacoes ORDER BY data_solicitacao DESC";

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return solicitations; // Return empty list on connection failure
            }

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                solicitations.add(extractSolicitationFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving all solicitations: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return solicitations;
    }

    /**
     * Retrieves a single solicitation by its ID.
     * 
     * @param id The ID of the solicitation.
     * @return A Solicitation object if found, otherwise null.
     */
    public Solicitation getSolicitationById(int id) {
        Solicitation solicitation = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT id, id_aluno, id_documento_tipo, data_solicitacao, urgencia, status, prioridade FROM solicitacoes WHERE id = ?";

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return null; // Return null on connection failure
            }

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                solicitation = extractSolicitationFromResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving solicitation by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return solicitation;
    }
    /**
     * Updates the status of a solicitation.
     * 
     * @param solicitationId The ID of the solicitation to update.
     * @param newStatus The new status value.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateStatus(int solicitationId, String newStatus) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        String sql = "UPDATE solicitacoes SET status = ? WHERE id = ?";

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return false; // Connection failure
            }

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, solicitationId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            System.err.println("SQL Error updating solicitation status: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources after updating solicitation status: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    /**
     * Inserts a new solicitation record into the database.
     * 
     * @param solicitation The Solicitation object containing student ID, document type ID, and urgency.
     * @return true if the insertion was successful, false otherwise.
     */
    public boolean saveSolicitation(Solicitation solicitation) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        // Note: id is auto-generated, data_solicitacao, status, and prioridade are set here.
        String sql = "INSERT INTO solicitacoes (id_aluno, id_documento_tipo, data_solicitacao, urgencia, status, prioridade) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return false; // Connection failure
            }

            stmt = conn.prepareStatement(sql);
            
            // 1. id_aluno
            stmt.setInt(1, solicitation.getIdAluno());
            // 2. id_documento_tipo
            stmt.setInt(2, solicitation.getIdDocumentoTipo());
            // 3. data_solicitacao (using java.sql.Timestamp for MySQL compatibility)
            stmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
            // 4. urgencia
            stmt.setString(4, solicitation.getUrgencia());
            // 5. status (hardcoded to "SOLICITADO")
            stmt.setString(5, "SOLICITADO");
            // 6. prioridade (hardcoded to 1)
            stmt.setInt(6, 1);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            System.err.println("SQL Error saving new solicitation: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources after saving solicitation: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}