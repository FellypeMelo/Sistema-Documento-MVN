package br.com.instituicao.sistemacomanda.dao;

import br.com.instituicao.sistemacomanda.model.Solicitacao;
import br.com.instituicao.sistemacomanda.model.StatusSolicitacao;
import br.com.instituicao.sistemacomanda.model.UrgenciaSolicitacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for Solicitacao entity.
 */
public class SolicitacaoDAO extends AbstractBaseDAO<Solicitacao, Long> {
    private static final String INSERT_SQL = 
        "INSERT INTO solicitacoes (id_aluno, id_documento_tipo, urgencia, prioridade) " +
        "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = 
        "UPDATE solicitacoes SET status=?, prioridade=?, observacoes=?, " +
        "data_conclusao=? WHERE id=?";
    private static final String SELECT_BY_ID = 
        "SELECT * FROM solicitacoes WHERE id=?";
    private static final String SELECT_ALL = 
        "SELECT * FROM solicitacoes";
    private static final String SELECT_BY_ALUNO = 
        "SELECT * FROM solicitacoes WHERE id_aluno=? ORDER BY data_solicitacao DESC";
    private static final String SELECT_PENDENTES = 
        "SELECT * FROM solicitacoes WHERE status != 'CONCLUIDO' ORDER BY prioridade DESC";
    private static final String EXISTS_SQL = 
        "SELECT 1 FROM solicitacoes WHERE id=?";
    private static final String COUNT_ATIVAS_ALUNO = 
        "SELECT COUNT(*) FROM solicitacoes WHERE id_aluno=? AND status != 'CONCLUIDO'";

    @Override
    public Solicitacao save(Solicitacao solicitacao) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            setInsertParameters(stmt, solicitacao);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating request failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                solicitacao.setId(rs.getLong(1));
            } else {
                throw new SQLException("Creating request failed, no ID obtained.");
            }
            
            return solicitacao;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public Solicitacao update(Solicitacao solicitacao) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            setUpdateParameters(stmt, solicitacao);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating request failed, no rows affected.");
            }
            
            return solicitacao;
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    @Override
    public Optional<Solicitacao> findById(Long id) throws SQLException {
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
    public List<Solicitacao> findAll() throws SQLException {
        List<Solicitacao> solicitacoes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_ALL);
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
     * Lists all requests for a specific student.
     * @param idAluno Student ID
     * @return List of requests
     * @throws SQLException if database error occurs
     */
    public List<Solicitacao> findByAluno(Long idAluno) throws SQLException {
        List<Solicitacao> solicitacoes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_BY_ALUNO);
            stmt.setLong(1, idAluno);
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
     * Lists all pending requests ordered by priority.
     * @return List of pending requests
     * @throws SQLException if database error occurs
     */
    public List<Solicitacao> findPendentes() throws SQLException {
        List<Solicitacao> solicitacoes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_PENDENTES);
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
     * Counts active requests for a student.
     * @param idAluno Student ID
     * @return Number of active requests
     * @throws SQLException if database error occurs
     */
    public int countSolicitacoesAtivas(Long idAluno) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(COUNT_ATIVAS_ALUNO);
            stmt.setLong(1, idAluno);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
            return 0;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        throw new UnsupportedOperationException("Deletion of requests is not allowed");
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
    protected Solicitacao resultSetToEntity(ResultSet rs) throws SQLException {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(rs.getLong("id"));
        solicitacao.setIdAluno(rs.getLong("id_aluno"));
        solicitacao.setIdDocumentoTipo(rs.getLong("id_documento_tipo"));
        solicitacao.setDataSolicitacao(rs.getTimestamp("data_solicitacao").toLocalDateTime());
        solicitacao.setUrgencia(UrgenciaSolicitacao.valueOf(rs.getString("urgencia")));
        solicitacao.setStatus(StatusSolicitacao.valueOf(rs.getString("status")));
        solicitacao.setPrioridade(rs.getInt("prioridade"));
        solicitacao.setObservacoes(rs.getString("observacoes"));
        
        Timestamp dataConclusao = rs.getTimestamp("data_conclusao");
        if (dataConclusao != null) {
            solicitacao.setDataConclusao(dataConclusao.toLocalDateTime());
        }
        
        return solicitacao;
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Solicitacao solicitacao) throws SQLException {
        stmt.setLong(1, solicitacao.getIdAluno());
        stmt.setLong(2, solicitacao.getIdDocumentoTipo());
        stmt.setString(3, solicitacao.getUrgencia().name());
        stmt.setInt(4, solicitacao.getPrioridade());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Solicitacao solicitacao) throws SQLException {
        stmt.setString(1, solicitacao.getStatus().name());
        stmt.setInt(2, solicitacao.getPrioridade());
        stmt.setString(3, solicitacao.getObservacoes());
        
        if (solicitacao.getDataConclusao() != null) {
            stmt.setTimestamp(4, Timestamp.valueOf(solicitacao.getDataConclusao()));
        } else {
            stmt.setNull(4, Types.TIMESTAMP);
        }
        
        stmt.setLong(5, solicitacao.getId());
    }
}