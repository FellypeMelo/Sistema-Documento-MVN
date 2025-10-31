package br.com.instituicao.sistemacomanda.strategy;

import br.com.instituicao.sistemacomanda.model.UrgenciaSolicitacao;

/**
 * Factory class for creating priority calculation strategies.
 */
public class CalculadoraPrioridadeFactory {
    
    /**
     * Creates the appropriate priority calculator based on urgency level.
     * 
     * @param urgencia The urgency level of the request
     * @return The appropriate calculator strategy
     */
    public static CalculadoraPrioridade criarCalculadora(UrgenciaSolicitacao urgencia) {
        switch (urgencia) {
            case ALTA:
                return new CalculadoraPrioridadeAlta();
            case MEDIA:
                return new CalculadoraPrioridadeMedia();
            case BAIXA:
                return new CalculadoraPrioridadeBaixa();
            default:
                throw new IllegalArgumentException("Invalid urgency level: " + urgencia);
        }
    }
}