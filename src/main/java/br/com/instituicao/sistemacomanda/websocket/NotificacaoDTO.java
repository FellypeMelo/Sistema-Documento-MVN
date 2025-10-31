package br.com.instituicao.sistemacomanda.websocket;

import br.com.instituicao.sistemacomanda.model.StatusSolicitacao;
import java.time.LocalDateTime;

public class NotificacaoDTO {
    private final String tipo;
    private final Long solicitacaoId;
    private final StatusSolicitacao status;
    private final String observacao;
    private final LocalDateTime dataHora;

    public NotificacaoDTO(String tipo, Long solicitacaoId, StatusSolicitacao status, 
            String observacao, LocalDateTime dataHora) {
        this.tipo = tipo;
        this.solicitacaoId = solicitacaoId;
        this.status = status;
        this.observacao = observacao;
        this.dataHora = dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    public Long getSolicitacaoId() {
        return solicitacaoId;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public String getObservacao() {
        return observacao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}