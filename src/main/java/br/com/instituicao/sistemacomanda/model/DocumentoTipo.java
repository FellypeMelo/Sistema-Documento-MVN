package br.com.instituicao.sistemacomanda.model;

/**
 * Class representing a document type in the system.
 */
public class DocumentoTipo {
    private Long id;
    private String nome;
    private String descricao;
    private int prazoBase;
    private boolean ativo;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPrazoBase() {
        return prazoBase;
    }

    public void setPrazoBase(int prazoBase) {
        this.prazoBase = prazoBase;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}