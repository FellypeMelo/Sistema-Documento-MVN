package br.com.instituicao.sistemacomanda.strategy;

import br.com.instituicao.sistemacomanda.model.ConfigPrioridade;
import br.com.instituicao.sistemacomanda.model.Solicitacao;
import br.com.instituicao.sistemacomanda.model.UrgenciaSolicitacao;

import java.sql.SQLException;

/**
 * High priority calculation strategy.
 * Provides maximum weight and shortest processing time.
 */
public class CalculadoraPrioridadeAlta extends CalculadoraPrioridadeBase {
    
    public CalculadoraPrioridadeAlta(br.com.instituicao.sistemacomanda.dao.ConfigPrioridadeDAO configPrioridadeDAO) {
        super(configPrioridadeDAO);
    }

    @Override
    public int calcularPrioridade(Solicitacao solicitacao) {
        try {
            ConfigPrioridade config = getConfiguracao(UrgenciaSolicitacao.ALTA);
            int pesoPrioridade = getPesoPrioridade(UrgenciaSolicitacao.ALTA);
            int fatorTempo = calcularFatorTempo(solicitacao, config);
            
            // Alta prioridade considera peso base + fator tempo + bônus de urgência
            return pesoPrioridade + fatorTempo + 50;
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating high priority", e);
        }
    }
    
    @Override
    public int getTempoProcessamentoEstimado(Solicitacao solicitacao) {
        // Alta prioridade tem tempo de processamento mínimo
        return 1;
    }
}