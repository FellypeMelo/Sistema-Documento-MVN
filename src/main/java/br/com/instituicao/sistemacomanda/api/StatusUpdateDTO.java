package br.com.instituicao.sistemacomanda.api;

import br.com.instituicao.sistemacomanda.model.StatusSolicitacao;

public class StatusUpdateDTO {
    private Long idAdmin;
    private StatusSolicitacao status;
    private String observacao;

    // Getters and Setters
    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}