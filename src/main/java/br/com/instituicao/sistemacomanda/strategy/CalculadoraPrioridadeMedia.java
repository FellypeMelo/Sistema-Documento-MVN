package br.com.instituicao.sistemacomanda.strategy;

import br.com.instituicao.sistemacomanda.model.ConfigPrioridade;
import br.com.instituicao.sistemacomanda.model.Solicitacao;
import br.com.instituicao.sistemacomanda.model.UrgenciaSolicitacao;

import java.sql.SQLException;

/**
 * Medium priority calculation strategy.
 * Provides standard weight and processing time.
 */
public class CalculadoraPrioridadeMedia extends CalculadoraPrioridadeBase {
    
    @Override
    public int calcularPrioridade(Solicitacao solicitacao) {
        try {
            ConfigPrioridade config = getConfiguracao(UrgenciaSolicitacao.MEDIA);
            int pesoPrioridade = getPesoPrioridade(UrgenciaSolicitacao.MEDIA);
            int fatorTempo = calcularFatorTempo(solicitacao, config);
            
            // Média prioridade considera peso base + fator tempo + bônus moderado
            return pesoPrioridade + fatorTempo + 25;
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating medium priority", e);
        }
    }
    
    @Override
    public int getTempoProcessamentoEstimado(Solicitacao solicitacao) {
        // Média prioridade tem tempo de processamento padrão
        return 3;
    }
}