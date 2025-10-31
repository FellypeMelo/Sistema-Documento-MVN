package br.com.instituicao.sistemacomanda.model;

import java.time.LocalDateTime;

/**
 * Class representing a document request in the system.
 */
public class Solicitacao {
    private Long id;
    private Long idAluno;
    private Long idDocumentoTipo;
    private LocalDateTime dataSolicitacao;
    private UrgenciaSolicitacao urgencia;
    private StatusSolicitacao status;
    private int prioridade;
    private String observacoes;
    private LocalDateTime dataConclusao;
    private Usuario usuario;
    private DocumentoTipo tipoDocumento;
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