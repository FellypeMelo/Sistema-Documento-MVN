package br.com.instituicao.sistemacomanda.dao;

import br.com.instituicao.sistemacomanda.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension of SolicitacaoDAO with additional query methods.
 */
public class SolicitacaoDAOExt extends SolicitacaoDAO {
    private static final String SELECT_BY_STATUS = 
        "SELECT s.* FROM solicitacoes s WHERE s.status = ? ORDER BY s.prioridade DESC";
    
    private static final String SELECT_BY_USUARIO = 
        "SELECT s.* FROM solicitacoes s WHERE s.id_aluno = ? ORDER BY s.data_solicitacao DESC";

    /**
     * Find all requests with a specific status.
     * @param status Status to search for
     * @return List of matching requests
     */
    public List<Solicitacao> findByStatus(StatusSolicitacao status) throws SQLException {
        List<Solicitacao> solicitacoes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_BY_STATUS);
            stmt.setString(1, status.name());
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                solicitacoes.add(resultSetToEntity(rs));
            }
            
            return solicitacoes;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    /**
     * Find all requests for a specific student.
     * @param aluno Student to find requests for
     * @return List of matching requests
     */
    public List<Solicitacao> findByUsuario(Aluno aluno) throws SQLException {
        List<Solicitacao> solicitacoes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_BY_USUARIO);
            stmt.setLong(1, aluno.getId());
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                solicitacoes.add(resultSetToEntity(rs));
            }
            
            return solicitacoes;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }
}