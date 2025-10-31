package br.com.instituicao.sistemacomanda.dao;

import br.com.instituicao.sistemacomanda.model.DocumentoTipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for DocumentoTipo entity.
 */
public class DocumentoTipoDAO extends AbstractBaseDAO<DocumentoTipo, Long> {
    private static final String INSERT_SQL = 
        "INSERT INTO documentos_tipo (nome, descricao, prazo_base) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = 
        "UPDATE documentos_tipo SET nome=?, descricao=?, prazo_base=?, ativo=? WHERE id=?";
    private static final String SELECT_BY_ID = 
        "SELECT * FROM documentos_tipo WHERE id=?";
    private static final String SELECT_ALL = 
        "SELECT * FROM documentos_tipo";
    private static final String DELETE_SQL = 
        "UPDATE documentos_tipo SET ativo=false WHERE id=?";
    private static final String SELECT_ACTIVE = 
        "SELECT * FROM documentos_tipo WHERE ativo=true";
    private static final String EXISTS_SQL = 
        "SELECT 1 FROM documentos_tipo WHERE id=?";

    @Override
    public DocumentoTipo save(DocumentoTipo documento) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            setInsertParameters(stmt, documento);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating document type failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                documento.setId(rs.getLong(1));
            } else {
                throw new SQLException("Creating document type failed, no ID obtained.");
            }
            
            return documento;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public DocumentoTipo update(DocumentoTipo documento) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            setUpdateParameters(stmt, documento);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating document type failed, no rows affected.");
            }
            
            return documento;
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    @Override
    public Optional<DocumentoTipo> findById(Long id) throws SQLException {
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
    public List<DocumentoTipo> findAll() throws SQLException {
        List<DocumentoTipo> documentos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                documentos.add(resultSetToEntity(rs));
            }
            
            return documentos;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    /**
     * Lists all active document types.
     * @return List of active document types
     * @throws SQLException if database error occurs
     */
    public List<DocumentoTipo> findAllActive() throws SQLException {
        List<DocumentoTipo> documentos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_ACTIVE);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                documentos.add(resultSetToEntity(rs));
            }
            
            return documentos;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(DELETE_SQL);
            stmt.setLong(1, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting document type failed, no rows affected.");
            }
        } finally {
            closeResources(null, stmt, conn);
        }
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
    protected DocumentoTipo resultSetToEntity(ResultSet rs) throws SQLException {
        DocumentoTipo documento = new DocumentoTipo();
        documento.setId(rs.getLong("id"));
        documento.setNome(rs.getString("nome"));
        documento.setDescricao(rs.getString("descricao"));
        documento.setPrazoBase(rs.getInt("prazo_base"));
        documento.setAtivo(rs.getBoolean("ativo"));
        return documento;
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, DocumentoTipo documento) throws SQLException {
        stmt.setString(1, documento.getNome());
        stmt.setString(2, documento.getDescricao());
        stmt.setInt(3, documento.getPrazoBase());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, DocumentoTipo documento) throws SQLException {
        stmt.setString(1, documento.getNome());
        stmt.setString(2, documento.getDescricao());
        stmt.setInt(3, documento.getPrazoBase());
        stmt.setBoolean(4, documento.isAtivo());
        stmt.setLong(5, documento.getId());
    }
}