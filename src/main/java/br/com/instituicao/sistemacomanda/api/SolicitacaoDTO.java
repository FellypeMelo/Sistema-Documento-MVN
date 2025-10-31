package br.com.instituicao.sistemacomanda.api;

import br.com.instituicao.sistemacomanda.model.UrgenciaSolicitacao;

public class SolicitacaoDTO {
    private Long idAluno;
    private Long idDocumentoTipo;
    private UrgenciaSolicitacao urgencia;
    private String observacoes;

    // Getters and Setters
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

    public UrgenciaSolicitacao getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(UrgenciaSolicitacao urgencia) {
        this.urgencia = urgencia;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}