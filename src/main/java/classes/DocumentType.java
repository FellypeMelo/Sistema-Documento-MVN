package classes;
public class DocumentType {
    private int id;
    private String nome;
    private String descricao;
    private int prazoBase; // hours

    public DocumentType(int id, String nome, String descricao, int prazoBase) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.prazoBase = prazoBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}