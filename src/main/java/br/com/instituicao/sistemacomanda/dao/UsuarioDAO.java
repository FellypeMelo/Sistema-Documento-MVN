package br.com.instituicao.sistemacomanda.dao;

import br.com.instituicao.sistemacomanda.model.Usuario;
import br.com.instituicao.sistemacomanda.model.TipoUsuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for Usuario entity.
 * Handles database operations for users (both students and administrators).
 */
public class UsuarioDAO extends AbstractBaseDAO<Usuario, Long> {
    private static final String INSERT_SQL = 
        "INSERT INTO usuarios (nome, email, senha, tipo, matricula, curso) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = 
        "UPDATE usuarios SET nome=?, email=?, senha=?, tipo=?, matricula=?, curso=?, ativo=? WHERE id=?";
    private static final String SELECT_BY_ID = 
        "SELECT * FROM usuarios WHERE id=?";
    private static final String SELECT_ALL = 
        "SELECT * FROM usuarios";
    private static final String DELETE_SQL = 
        "UPDATE usuarios SET ativo=false WHERE id=?";
    private static final String SELECT_BY_EMAIL = 
        "SELECT * FROM usuarios WHERE email=? AND ativo=true";
    private static final String EXISTS_SQL = 
        "SELECT 1 FROM usuarios WHERE id=?";

    @Override
    public Usuario save(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            setInsertParameters(stmt, usuario);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getLong(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            
            return usuario;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public Usuario update(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(UPDATE_SQL);
            setUpdateParameters(stmt, usuario);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
            
            return usuario;
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) throws SQLException {
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
    public List<Usuario> findAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                usuarios.add(resultSetToEntity(rs));
            }
            
            return usuarios;
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
                throw new SQLException("Deleting user failed, no rows affected.");
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

    /**
     * Finds a user by email.
     * @param email Email to search for
     * @return Optional containing user if found
     * @throws SQLException if database error occurs
     */
    public Optional<Usuario> findByEmail(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SELECT_BY_EMAIL);
            stmt.setString(1, email);
            
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
    protected Usuario resultSetToEntity(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setTipo(TipoUsuario.valueOf(rs.getString("tipo")));
        usuario.setAtivo(rs.getBoolean("ativo"));
        usuario.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
        usuario.setMatricula(rs.getString("matricula"));
        usuario.setCurso(rs.getString("curso"));
        return usuario;
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Usuario usuario) throws SQLException {
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getSenha());
        stmt.setString(4, usuario.getTipo().name());
        stmt.setString(5, usuario.getMatricula());
        stmt.setString(6, usuario.getCurso());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Usuario usuario) throws SQLException {
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getSenha());
        stmt.setString(4, usuario.getTipo().name());
        stmt.setString(5, usuario.getMatricula());
        stmt.setString(6, usuario.getCurso());
        stmt.setBoolean(7, usuario.isAtivo());
        stmt.setLong(8, usuario.getId());
    }
}