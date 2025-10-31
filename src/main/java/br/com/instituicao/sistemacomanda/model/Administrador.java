package br.com.instituicao.sistemacomanda.model;

/**
 * Represents an administrator user in the system.
 * Extends the base Usuario class with administrator-specific attributes.
 */
public class Administrador extends Usuario {
    
    public Administrador() {
        super();
        setTipo(TipoUsuario.ADMIN);
    }
    
    /**
     * Creates a new administrator with basic information.
     * 
     * @param nome Administrator's name
     * @param email Administrator's email
     */
    public Administrador(String nome, String email) {
        super();
        setTipo(TipoUsuario.ADMIN);
        setNome(nome);
        setEmail(email);
    }
    
    /**
     * Validates if the administrator has all required information.
     * 
     * @return true if all required fields are present
     */
    public boolean isValid() {
        return getNome() != null && !getNome().trim().isEmpty() &&
               getEmail() != null && !getEmail().trim().isEmpty();
    }
    
    /**
     * Override to ensure course and matricula are not required for administrators
     */
    @Override
    public void setMatricula(String matricula) {
        // Administrators don't need a matricula
    }
    
    /**
     * Override to ensure course and matricula are not required for administrators
     */
    @Override
    public void setCurso(String curso) {
        // Administrators don't need a course
    }
}