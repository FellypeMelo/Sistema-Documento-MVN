package br.com.instituicao.sistemacomanda.dao;

import br.com.instituicao.sistemacomanda.model.ConfigPrioridade;
import br.com.instituicao.sistemacomanda.model.UrgenciaSolicitacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for ConfigPrioridade entity.
 */
public class ConfigPrioridadeDAO extends AbstractBaseDAO<ConfigPrioridade, Long> {
    private static final String INSERT_SQL = 
        "INSERT INTO config_prioridades (tipo_urgencia, peso_base, multiplicador_tempo) " +
        "VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = 
        "UPDATE config_prioridades SET peso_base=?, multiplicador_tempo=? " +
        "WHERE id=?";
    private static final String SELECT_BY_ID = 
        "SELECT * FROM config_prioridades WHERE id=?";
    private static final String SELECT_ALL = 
        "SELECT * FROM config_prioridades";
    private static final String SELECT_BY_URGENCIA = 
        "SELECT * FROM config_prioridades WHERE tipo_urgencia=?";
    private static final String EXISTS_SQL = 
        "SELECT 1 FROM config_prioridades WHERE id=?";

    @Override
    public ConfigPrioridade save(ConfigPrioridade config) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            setInsertParameters(stmt, config);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating priority config failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                config.setId(rs.getLong(1));
            } else {
                throw new SQLException("Creating priority config failed, no ID obtained.");
            }
            
            return config;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public ConfigPrioridade update(ConfigPrioridade config) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            setUpdateParameters(stmt, config);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating priority config failed, no rows affected.");
            }
            
            return config;
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    @Override
    public Optional<ConfigPrioridade> findById(Long id) throws SQLException {
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
    public List<ConfigPrioridade> findAll() throws SQLException {
        List<ConfigPrioridade> configs = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                configs.add(resultSetToEntity(rs));
            }
            
            return configs;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    /**
     * Finds configuration for a specific urgency level.
     * @param urgencia Urgency level
     * @return Optional containing configuration if found
     * @throws SQLException if database error occurs
     */
    public Optional<ConfigPrioridade> findByUrgencia(UrgenciaSolicitacao urgencia) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_BY_URGENCIA);
            stmt.setString(1, urgencia.name());
            
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
    public void delete(Long id) throws SQLException {
        throw new UnsupportedOperationException("Deletion of priority configurations is not allowed");
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
    protected ConfigPrioridade resultSetToEntity(ResultSet rs) throws SQLException {
        ConfigPrioridade config = new ConfigPrioridade();
        config.setId(rs.getLong("id"));
        config.setTipoUrgencia(UrgenciaSolicitacao.valueOf(rs.getString("tipo_urgencia")));
        config.setPesoBase(rs.getInt("peso_base"));
        config.setMultiplicadorTempo(rs.getInt("multiplicador_tempo"));
        return config;
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, ConfigPrioridade config) throws SQLException {
        stmt.setString(1, config.getTipoUrgencia().name());
        stmt.setInt(2, config.getPesoBase());
        stmt.setInt(3, config.getMultiplicadorTempo());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, ConfigPrioridade config) throws SQLException {
        stmt.setInt(1, config.getPesoBase());
        stmt.setInt(2, config.getMultiplicadorTempo());
        stmt.setLong(3, config.getId());
    }
}