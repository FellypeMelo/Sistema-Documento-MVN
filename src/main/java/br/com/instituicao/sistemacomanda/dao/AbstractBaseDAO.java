package br.com.instituicao.sistemacomanda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract base implementation of BaseDAO with common functionality.
 * @param <T> Entity type
 * @param <ID> ID type
 */
public abstract class AbstractBaseDAO<T, ID> implements BaseDAO<T, ID> {
    protected final ConexaoMySQL conexaoMySQL;
    
    protected AbstractBaseDAO() {
        this.conexaoMySQL = ConexaoMySQL.getInstance();
    }
    
    /**
     * Gets a new database connection.
     * @return Connection object
     * @throws SQLException if connection cannot be established
     */
    protected Connection getConnection() throws SQLException {
        return conexaoMySQL.getConnection();
    }
    
    /**
     * Closes database resources safely.
     * @param rs ResultSet to close
     * @param stmt PreparedStatement to close
     * @param conn Connection to close
     */
    protected void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            // Log the exception but don't rethrow as these are cleanup operations
            e.printStackTrace();
        }
    }
    
    /**
     * Converts a ResultSet row to an entity.
     * Must be implemented by concrete DAOs.
     * @param rs ResultSet positioned at the row to convert
     * @return Entity object
     * @throws SQLException if database error occurs
     */
    protected abstract T resultSetToEntity(ResultSet rs) throws SQLException;
    
    /**
     * Sets parameters for an insert PreparedStatement.
     * Must be implemented by concrete DAOs.
     * @param stmt PreparedStatement to set parameters on
     * @param entity Entity to get parameters from
     * @throws SQLException if database error occurs
     */
    protected abstract void setInsertParameters(PreparedStatement stmt, T entity) throws SQLException;
    
    /**
     * Sets parameters for an update PreparedStatement.
     * Must be implemented by concrete DAOs.
     * @param stmt PreparedStatement to set parameters on
     * @param entity Entity to get parameters from
     * @throws SQLException if database error occurs
     */
    protected abstract void setUpdateParameters(PreparedStatement stmt, T entity) throws SQLException;
}