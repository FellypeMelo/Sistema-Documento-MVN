package classes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentTypeDAO {

    private DocumentType extractDocumentTypeFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String descricao = rs.getString("descricao");
        int prazoBase = rs.getInt("prazo_base"); // Assuming column name is 'prazo_base'

        return new DocumentType(id, nome, descricao, prazoBase);
    }

    /**
     * Retrieves all document types from the documentos_tipo table.
     * 
     * @return A list of DocumentType objects, or an empty list if an error occurs.
     */
    public List<DocumentType> getAllDocumentTypes() {
        List<DocumentType> documentTypes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT id, nome, descricao, prazo_base FROM documentos_tipo";

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return documentTypes; // Return empty list on connection failure
            }

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                documentTypes.add(extractDocumentTypeFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving all document types: " + e.getMessage());
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

        return documentTypes;
    }

    /**
     * Retrieves a single document type from the documentos_tipo table based on the provided id.
     * 
     * @param id The ID of the document type to retrieve.
     * @return A DocumentType object if found, or null otherwise.
     */
    public DocumentType getDocumentTypeById(int id) {
        DocumentType documentType = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT id, nome, descricao, prazo_base FROM documentos_tipo WHERE id = ?";

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                return null; // Return null on connection failure
            }

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                documentType = extractDocumentTypeFromResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error retrieving document type by ID: " + e.getMessage());
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

        return documentType;
    }
}