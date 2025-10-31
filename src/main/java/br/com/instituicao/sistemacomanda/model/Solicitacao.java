package br.com.instituicao.sistemacomanda.model;

import java.time.LocalDateTime;

/**
 * Represents a document request in the system. This class handles all the information
 * related to a document request, including its status, priority, and relationships
 * with other entities.
 *
 * @author Sistema de Documentos Team
 * @version 1.0
 * @since 2025-10-31
 */
public class Solicitacao {
    /** Unique identifier for the request */
    private Long id;
    
    /** ID of the student who made the request */
    private Long idAluno;
    
    /** ID of the document type being requested */
    private Long idDocumentoTipo;
    
    /** Timestamp when the request was created */
    private LocalDateTime dataSolicitacao;
    
    /** Urgency level of the request (ALTA, MEDIA, BAIXA) */
    private UrgenciaSolicitacao urgencia;
    
    /** Current status of the request (PENDENTE, EM_ANDAMENTO, CONCLUIDO, CANCELADO) */
    private StatusSolicitacao status;
    
    /** Calculated priority score based on various factors */
    private int prioridade;
    
    /** Additional notes or comments about the request */
    private String observacoes;
    
    /** Timestamp when the request was completed (if applicable) */
    private LocalDateTime dataConclusao;
    
    /** Reference to the user (student) who made the request */
    private Usuario usuario;
    
    /** Reference to the type of document being requested */
    private DocumentoTipo tipoDocumento;
    
    /** Estimated processing time in days */
    private int tempoEstimado;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public Long getIdDocumentoTipo() {
        return idDocumentoTipo;
    }

    public void setIdDocumentoTipo(Long idDocumentoTipo) {
        this.idDocumentoTipo = idDocumentoTipo;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public UrgenciaSolicitacao getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(UrgenciaSolicitacao urgencia) {
        this.urgencia = urgencia;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            this.idAluno = usuario.getId();
        }
    }

    public DocumentoTipo getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(DocumentoTipo tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        if (tipoDocumento != null) {
            this.idDocumentoTipo = tipoDocumento.getId();
        }
    }

    public int getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(int tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }
}