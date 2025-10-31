package br.com.instituicao.sistemacomanda.model;

/**
 * Represents a student user in the system.
 * Extends the base Usuario class with student-specific attributes.
 */
public class Aluno extends Usuario {
    
    public Aluno() {
        super();
        setTipo(TipoUsuario.ALUNO);
    }
    
    /**
     * Creates a new student with basic information.
     * 
     * @param nome Student's name
     * @param email Student's email
     * @param matricula Student's registration number
     * @param curso Student's course
     */
    public Aluno(String nome, String email, String matricula, String curso) {
        super();
        setTipo(TipoUsuario.ALUNO);
        setNome(nome);
        setEmail(email);
        setMatricula(matricula);
        setCurso(curso);
    }
    
    /**
     * Validates if the student has all required information.
     * 
     * @return true if all required fields are present
     */
    public boolean isValid() {
        return getNome() != null && !getNome().trim().isEmpty() &&
               getEmail() != null && !getEmail().trim().isEmpty() &&
               getMatricula() != null && !getMatricula().trim().isEmpty() &&
               getCurso() != null && !getCurso().trim().isEmpty();
    }
}