package br.com.instituicao.sistemacomanda.dao;

import br.com.instituicao.sistemacomanda.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension of StatusHistoricoDAO with additional query methods.
 */
public class StatusHistoricoDAOExt extends StatusHistoricoDAO {
    private static final String SELECT_BY_SOLICITACAO = 
        "SELECT * FROM status_historico WHERE id_solicitacao = ? ORDER BY data_mudanca DESC";

    /**
     * Find all status history entries for a specific request.
     * @param solicitacao Request to find history for
     * @return List of status history entries
     */
    public List<StatusHistorico> findBySolicitacao(Solicitacao solicitacao) throws SQLException {
        List<StatusHistorico> historicos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_BY_SOLICITACAO);
            stmt.setLong(1, solicitacao.getId());
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                historicos.add(resultSetToEntity(rs));
            }
            
            return historicos;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }
}