package classes;
public class Solicitation {
    private int id;
    private int idAluno;
    private int idDocumentoTipo;
    private String dataSolicitacao;
    private String urgencia;
    private String status;
    private int prioridade;
    private String observacoes;

    public Solicitation(int id, int idAluno, int idDocumentoTipo, String dataSolicitacao, String urgencia, String status, int prioridade) {
        this.id = id;
        this.idAluno = idAluno;
        this.idDocumentoTipo = idDocumentoTipo;
        this.dataSolicitacao = dataSolicitacao;
        this.urgencia = urgencia;
        this.status = status;
        this.prioridade = prioridade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getIdDocumentoTipo() {
        return idDocumentoTipo;
    }

    public void setIdDocumentoTipo(int idDocumentoTipo) {
        this.idDocumentoTipo = idDocumentoTipo;
    }

    public String getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(String dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(String urgencia) {
        this.urgencia = urgencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
}