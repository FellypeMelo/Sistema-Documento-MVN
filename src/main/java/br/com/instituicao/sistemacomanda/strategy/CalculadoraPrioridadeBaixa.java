package br.com.instituicao.sistemacomanda.strategy;

import br.com.instituicao.sistemacomanda.model.ConfigPrioridade;
import br.com.instituicao.sistemacomanda.model.Solicitacao;
import br.com.instituicao.sistemacomanda.model.UrgenciaSolicitacao;

import java.sql.SQLException;

/**
 * Low priority calculation strategy.
 * Provides minimum weight and longer processing time.
 */
public class CalculadoraPrioridadeBaixa extends CalculadoraPrioridadeBase {
    
    @Override
    public int calcularPrioridade(Solicitacao solicitacao) {
        try {
            ConfigPrioridade config = getConfiguracao(UrgenciaSolicitacao.BAIXA);
            int pesoPrioridade = getPesoPrioridade(UrgenciaSolicitacao.BAIXA);
            int fatorTempo = calcularFatorTempo(solicitacao, config);
            
            // Baixa prioridade considera apenas peso base + fator tempo
            return pesoPrioridade + fatorTempo;
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating low priority", e);
        }
    }
    
    @Override
    public int getTempoProcessamentoEstimado(Solicitacao solicitacao) {
        // Baixa prioridade tem tempo de processamento máximo
        return 5;
    }
}