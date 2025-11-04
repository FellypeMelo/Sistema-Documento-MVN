package br.com.instituicao.sistemacomanda.facade;

import br.com.instituicao.sistemacomanda.dao.*;
import br.com.instituicao.sistemacomanda.model.*;
import br.com.instituicao.sistemacomanda.strategy.CalculadoraPrioridade;
import br.com.instituicao.sistemacomanda.strategy.CalculadoraPrioridadeFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Facade class that encapsulates all business logic for the document request system.
 * This class coordinates the interaction between different components and provides
 * a simplified interface for the client code.
 */
public class SistemaDocumentosFacade {
    protected final UsuarioDAO usuarioDAO;
    protected final DocumentoTipoDAO documentoTipoDAO;
    protected final SolicitacaoDAOExt solicitacaoDAO;
    protected final StatusHistoricoDAOExt statusHistoricoDAO;
    protected final ConfigPrioridadeDAO configPrioridadeDAO;

    // Getter methods for DAOs
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public DocumentoTipoDAO getDocumentoTipoDAO() {
        return documentoTipoDAO;
    }

    public SolicitacaoDAOExt getSolicitacaoDAO() {
        return solicitacaoDAO;
    }

    public StatusHistoricoDAOExt getStatusHistoricoDAO() {
        return statusHistoricoDAO;
    }

    public ConfigPrioridadeDAO getConfigPrioridadeDAO() {
        return configPrioridadeDAO;
    }

    public SistemaDocumentosFacade(UsuarioDAO usuarioDAO, DocumentoTipoDAO documentoTipoDAO,
            SolicitacaoDAOExt solicitacaoDAO, StatusHistoricoDAOExt statusHistoricoDAO,
            ConfigPrioridadeDAO configPrioridadeDAO) {
        this.usuarioDAO = usuarioDAO;
        this.documentoTipoDAO = documentoTipoDAO;
        this.solicitacaoDAO = solicitacaoDAO;
        this.statusHistoricoDAO = statusHistoricoDAO;
        this.configPrioridadeDAO = configPrioridadeDAO;
    }

    /**
     * Authenticates a user with email and password.
     * 
     * @param email User's email
     * @param senha User's password
     * @return Optional containing the authenticated user if successful
     * @throws SQLException if database error occurs
     */
    public Optional<Usuario> autenticarUsuario(String email, String senha) throws SQLException {
        Optional<Usuario> usuarioOpt = usuarioDAO.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.isAtivo() && org.mindrot.jbcrypt.BCrypt.checkpw(senha, usuario.getSenha())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    /**
     * Creates a new document request.
     * 
     * @param aluno Student requesting the document
     * @param tipoDocumento Type of document being requested
     * @param urgencia Urgency level of the request
     * @param observacoes Additional observations
     * @return Created request
     * @throws SQLException if database error occurs
     */
    public Solicitacao criarSolicitacao(Aluno aluno, DocumentoTipo tipoDocumento, 
            UrgenciaSolicitacao urgencia, String observacoes) throws SQLException {
        // Create new request
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setUsuario(aluno);
        solicitacao.setTipoDocumento(tipoDocumento);
        solicitacao.setUrgencia(urgencia);
        solicitacao.setObservacoes(observacoes);
        solicitacao.setStatus(StatusSolicitacao.PENDENTE);
        solicitacao.setDataSolicitacao(LocalDateTime.now());

        // Calculate priority
        CalculadoraPrioridade calculadora = CalculadoraPrioridadeFactory.criarCalculadora(urgencia);
        int prioridade = calculadora.calcularPrioridade(solicitacao);
        solicitacao.setPrioridade(prioridade);
        solicitacao.setTempoEstimado(calculadora.getTempoProcessamentoEstimado(solicitacao));

        // Save request
        solicitacao = solicitacaoDAO.save(solicitacao);

        // Create initial status history
        StatusHistorico historico = new StatusHistorico();
        historico.setSolicitacao(solicitacao);
        historico.setStatus(StatusSolicitacao.PENDENTE);
        historico.setDataMudanca(LocalDateTime.now());
        historico.setObservacao("Solicitação criada");
        statusHistoricoDAO.save(historico);

        return solicitacao;
    }

    /**
     * Updates the status of a document request.
     * 
     * @param solicitacao Request to update
     * @param novoStatus New status
     * @param observacao Observation about the status change
     * @param admin Administrator making the change
     * @throws SQLException if database error occurs
     */
    public void atualizarStatusSolicitacao(Solicitacao solicitacao, StatusSolicitacao novoStatus, 
            String observacao, Administrador admin) throws SQLException {
        // Update request status
        solicitacao.setStatus(novoStatus);
        solicitacaoDAO.update(solicitacao);

        // Create status history entry
        StatusHistorico historico = new StatusHistorico();
        historico.setSolicitacao(solicitacao);
        historico.setStatus(novoStatus);
        historico.setDataMudanca(LocalDateTime.now());
        historico.setObservacao(observacao);
        historico.setAdministrador(admin);
        statusHistoricoDAO.save(historico);
    }

    /**
     * Gets all pending requests ordered by priority.
     * 
     * @return List of pending requests
     * @throws SQLException if database error occurs
     */
    public List<Solicitacao> listarSolicitacoesPendentes() throws SQLException {
        return solicitacaoDAO.findByStatus(StatusSolicitacao.PENDENTE);
    }

    /**
     * Gets all requests for a specific student.
     * 
     * @param aluno Student to get requests for
     * @return List of requests
     * @throws SQLException if database error occurs
     */
    public List<Solicitacao> listarSolicitacoesAluno(Aluno aluno) throws SQLException {
        return solicitacaoDAO.findByUsuario(aluno);
    }

    /**
     * Gets all available document types.
     * 
     * @return List of document types
     * @throws SQLException if database error occurs
     */
    public List<DocumentoTipo> listarTiposDocumento() throws SQLException {
        return documentoTipoDAO.findAll();
    }

    /**
     * Gets status history for a request.
     * 
     * @param solicitacao Request to get history for
     * @return List of status changes
     * @throws SQLException if database error occurs
     */
    public List<StatusHistorico> listarHistoricoSolicitacao(Solicitacao solicitacao) throws SQLException {
        return statusHistoricoDAO.findBySolicitacao(solicitacao);
    }

    /**
     * Creates a new user account.
     * 
     * @param usuario User to create
     * @return Created user
     * @throws SQLException if database error occurs
     */
    public Usuario criarUsuario(Usuario usuario) throws SQLException {
        usuario.setAtivo(true);
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setSenha(org.mindrot.jbcrypt.BCrypt.hashpw(usuario.getSenha(), org.mindrot.jbcrypt.BCrypt.gensalt()));
        return usuarioDAO.save(usuario);
    }

    /**
     * Deactivates a user account.
     * 
     * @param usuario User to deactivate
     * @throws SQLException if database error occurs
     */
    public void desativarUsuario(Usuario usuario) throws SQLException {
        usuario.setAtivo(false);
        usuarioDAO.update(usuario);
    }
}