package br.com.instituicao.sistemacomanda.api;

public class ErrorDTO {
    private final String mensagem;

    public ErrorDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}