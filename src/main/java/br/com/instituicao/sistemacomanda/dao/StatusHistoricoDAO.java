package br.com.instituicao.sistemacomanda.dao;

import br.com.instituicao.sistemacomanda.model.StatusHistorico;
import br.com.instituicao.sistemacomanda.model.StatusSolicitacao;
import br.com.instituicao.sistemacomanda.model.Administrador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for StatusHistorico entity.
 */
public class StatusHistoricoDAO extends AbstractBaseDAO<StatusHistorico, Long> {
    private static final String INSERT_SQL = 
        "INSERT INTO status_historico (id_solicitacao, status, data_mudanca, observacao, id_administrador) " +
        "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = 
        "UPDATE status_historico SET observacao=? WHERE id=?";
    private static final String SELECT_BY_ID = 
        "SELECT * FROM status_historico WHERE id=?";
    private static final String SELECT_ALL = 
        "SELECT * FROM status_historico";
    private static final String SELECT_BY_SOLICITACAO = 
        "SELECT * FROM status_historico WHERE id_solicitacao=? ORDER BY data_mudanca DESC";
    private static final String EXISTS_SQL = 
        "SELECT 1 FROM status_historico WHERE id=?";

    @Override
    public StatusHistorico save(StatusHistorico historico) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            setInsertParameters(stmt, historico);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating status history failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                historico.setId(rs.getLong(1));
            } else {
                throw new SQLException("Creating status history failed, no ID obtained.");
            }
            
            return historico;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public StatusHistorico update(StatusHistorico historico) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            setUpdateParameters(stmt, historico);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating status history failed, no rows affected.");
            }
            
            return historico;
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    @Override
    public Optional<StatusHistorico> findById(Long id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_BY_ID);
            stmt.setLong(1, id);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(resultSetToEntity(rs));
            }
            
            return Optional.empty();
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public List<StatusHistorico> findAll() throws SQLException {
        List<StatusHistorico> historicos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                historicos.add(resultSetToEntity(rs));
            }
            
            return historicos;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    /**
     * Lists all status history entries for a specific request.
     * @param idSolicitacao Request ID
     * @return List of status history entries
     * @throws SQLException if database error occurs
     */
    public List<StatusHistorico> findBySolicitacao(Long idSolicitacao) throws SQLException {
        List<StatusHistorico> historicos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_BY_SOLICITACAO);
            stmt.setLong(1, idSolicitacao);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                historicos.add(resultSetToEntity(rs));
            }
            
            return historicos;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        throw new UnsupportedOperationException("Deletion of status history is not allowed");
    }

    @Override
    public boolean exists(Long id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(EXISTS_SQL);
            stmt.setLong(1, id);
            
            rs = stmt.executeQuery();
            return rs.next();
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    protected StatusHistorico resultSetToEntity(ResultSet rs) throws SQLException {
        StatusHistorico historico = new StatusHistorico();
        historico.setId(rs.getLong("id"));
        historico.setIdSolicitacao(rs.getLong("id_solicitacao"));
        historico.setStatus(StatusSolicitacao.valueOf(rs.getString("status")));
        historico.setDataMudanca(rs.getTimestamp("data_mudanca").toLocalDateTime());
        historico.setObservacao(rs.getString("observacao"));
        Long idAdmin = rs.getLong("id_administrador");
        if (!rs.wasNull()) {
            new UsuarioDAO().findById(idAdmin).ifPresent(admin -> {
                if (admin instanceof Administrador) {
                    historico.setAdministrador((Administrador) admin);
                }
            });
        }
        return historico;
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, StatusHistorico historico) throws SQLException {
        stmt.setLong(1, historico.getIdSolicitacao());
        stmt.setString(2, historico.getStatus().name());
        stmt.setTimestamp(3, Timestamp.valueOf(historico.getDataMudanca()));
        stmt.setString(4, historico.getObservacao());
        if (historico.getAdministrador() != null) {
            stmt.setLong(5, historico.getAdministrador().getId());
        } else {
            stmt.setNull(5, Types.BIGINT);
        }
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, StatusHistorico historico) throws SQLException {
        stmt.setString(1, historico.getObservacao());
        stmt.setLong(2, historico.getId());
    }
}