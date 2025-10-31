package br.com.instituicao.sistemacomanda.model;

/**
 * Enum representing request status.
 */
public enum StatusSolicitacao {
    PENDENTE("Pendente"),
    EM_ANALISE("Em Análise"),
    EM_PROCESSAMENTO("Em Processamento"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado"),
    REJEITADO("Rejeitado");

    private final String descricao;

    StatusSolicitacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}