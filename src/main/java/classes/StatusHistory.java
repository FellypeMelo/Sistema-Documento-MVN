package classes;
public class StatusHistory {
    private int id;
    private int idSolicitacao;
    private String status;
    private String dataHora;
    private String responsavel;

    public StatusHistory(int id, int idSolicitacao, String status, String dataHora, String responsavel) {
        this.id = id;
        this.idSolicitacao = idSolicitacao;
        this.status = status;
        this.dataHora = dataHora;
        this.responsavel = responsavel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(int idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}