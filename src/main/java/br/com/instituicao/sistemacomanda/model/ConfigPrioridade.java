package br.com.instituicao.sistemacomanda.model;

/**
 * Class representing priority configuration for urgency levels.
 */
public class ConfigPrioridade {
    private Long id;
    private UrgenciaSolicitacao tipoUrgencia;
    private int pesoBase;
    private int multiplicadorTempo;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UrgenciaSolicitacao getTipoUrgencia() {
        return tipoUrgencia;
    }

    public void setTipoUrgencia(UrgenciaSolicitacao tipoUrgencia) {
        this.tipoUrgencia = tipoUrgencia;
    }

    public int getPesoBase() {
        return pesoBase;
    }

    public void setPesoBase(int pesoBase) {
        this.pesoBase = pesoBase;
    }

    public int getMultiplicadorTempo() {
        return multiplicadorTempo;
    }

    public void setMultiplicadorTempo(int multiplicadorTempo) {
        this.multiplicadorTempo = multiplicadorTempo;
    }
}