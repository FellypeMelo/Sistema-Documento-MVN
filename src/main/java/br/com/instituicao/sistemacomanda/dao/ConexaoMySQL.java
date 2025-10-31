package br.com.instituicao.sistemacomanda.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Database connection manager using HikariCP connection pool.
 * Implements Singleton pattern to ensure single pool instance.
 */
public class ConexaoMySQL {
    private static ConexaoMySQL instance;
    private final HikariDataSource dataSource;

    private ConexaoMySQL() {
        Properties props = loadProperties();
        HikariConfig config = new HikariConfig();
        
        config.setJdbcUrl(props.getProperty("db.url"));
        config.setUsername(props.getProperty("db.username"));
        config.setPassword(props.getProperty("db.password"));
        config.setDriverClassName(props.getProperty("db.driver"));
        
        // Connection pool settings
        config.setMinimumIdle(Integer.parseInt(props.getProperty("db.pool.min")));
        config.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.pool.max")));
        config.setConnectionTimeout(30000); // 30 seconds
        config.setIdleTimeout(600000); // 10 minutes
        config.setMaxLifetime(1800000); // 30 minutes
        
        // Enable auto-commit (can be disabled if needed)
        config.setAutoCommit(true);
        
        // Set pool name for easier monitoring
        config.setPoolName("SistemaDocumentosPool");
        
        dataSource = new HikariDataSource(config);
    }

    /**
     * Gets the singleton instance of the connection manager.
     * @return ConexaoMySQL instance
     */
    public static synchronized ConexaoMySQL getInstance() {
        if (instance == null) {
            instance = new ConexaoMySQL();
        }
        return instance;
    }

    /**
     * Gets a connection from the pool.
     * @return Connection object
     * @throws SQLException if connection cannot be established
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Loads database properties from file.
     * @return Properties object with database configuration
     */
    private Properties loadProperties() {
        Properties props = new Properties();
        
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new IOException("database.properties not found");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        }
        
        return props;
    }

    /**
     * Closes the connection pool.
     * Should be called when the application shuts down.
     */
    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}