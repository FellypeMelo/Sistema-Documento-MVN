package classes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StatusHistoryDAO {

    /**
     * Inserts a new status history record into the status_historico table.
     * 
     * @param history The StatusHistory object containing the data to save.
     * @return true if the insertion was successful, false otherwise.
     */
    public boolean saveHistory(StatusHistory history) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        // SQL to insert a new record into status_historico
        String sql = "INSERT INTO status_historico (id_solicitacao, status, data_hora, responsavel) VALUES (?, ?, ?, ?)";

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return false; // Connection failure
            }

            stmt = conn.prepareStatement(sql);
            
            // 1. id_solicitacao
            stmt.setInt(1, history.getIdSolicitacao());
            
            // 2. status
            stmt.setString(2, history.getStatus());
            
            // 3. data_hora (using java.sql.Timestamp for MySQL compatibility, setting current time)
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            
            // 4. responsavel
            stmt.setString(4, history.getResponsavel());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (SQLException e) {
            System.err.println("SQL Error saving status history: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Ensure resources are closed properly
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return success;
    }

    /**
     * Retrieves all status history records associated with a specific solicitation ID 
     * from the status_historico table.
     * 
     * @param solicitationId The ID of the solicitation.
     * @return A list of StatusHistory objects, or an empty list if an error occurs.
     */
    public List<StatusHistory> getHistoryBySolicitationId(int solicitationId) {
        List<StatusHistory> historyList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        // Assuming the table name is 'status_historico' and the foreign key column is 'id_solicitacao'
        String sql = "SELECT id, id_solicitacao, status, data_hora, responsavel FROM status_historico WHERE id_solicitacao = ? ORDER BY data_hora ASC";

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return historyList; // Return empty list on connection failure
            }

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, solicitationId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idSolicitacao = rs.getInt("id_solicitacao");
                String status = rs.getString("status");
                String dataHora = rs.getString("data_hora");
                String responsavel = rs.getString("responsavel");
                
                StatusHistory history = new StatusHistory(id, idSolicitacao, status, dataHora, responsavel);
                historyList.add(history);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving status history by solicitation ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Ensure resources are closed properly
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return historyList;
    }
}