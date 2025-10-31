package br.com.instituicao.sistemacomanda.strategy;

import br.com.instituicao.sistemacomanda.model.Solicitacao;

/**
 * Interface for priority calculation strategies.
 * Implements the Strategy pattern to allow different priority calculation algorithms.
 */
public interface CalculadoraPrioridade {
    /**
     * Calculates the priority score for a given request.
     * 
     * @param solicitacao The document request to calculate priority for
     * @return The calculated priority score
     */
    int calcularPrioridade(Solicitacao solicitacao);
    
    /**
     * Gets the estimated processing time in days.
     * 
     * @param solicitacao The document request to estimate time for
     * @return Estimated processing time in days
     */
    int getTempoProcessamentoEstimado(Solicitacao solicitacao);
}