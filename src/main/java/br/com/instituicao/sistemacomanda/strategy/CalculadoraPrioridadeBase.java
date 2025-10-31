package br.com.instituicao.sistemacomanda.strategy;

import br.com.instituicao.sistemacomanda.dao.ConfigPrioridadeDAO;
import br.com.instituicao.sistemacomanda.model.ConfigPrioridade;
import br.com.instituicao.sistemacomanda.model.Solicitacao;
import br.com.instituicao.sistemacomanda.model.UrgenciaSolicitacao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Abstract base class for priority calculators.
 * Provides common functionality for all priority calculation strategies.
 */
public abstract class CalculadoraPrioridadeBase implements CalculadoraPrioridade {
    
    protected ConfigPrioridadeDAO configPrioridadeDAO;
    
    public CalculadoraPrioridadeBase() {
        this.configPrioridadeDAO = new ConfigPrioridadeDAO();
    }
    
    /**
     * Gets the configuration for a specific urgency level.
     */
    protected ConfigPrioridade getConfiguracao(UrgenciaSolicitacao urgencia) throws SQLException {
        return configPrioridadeDAO.findByUrgencia(urgencia)
            .orElseThrow(() -> new IllegalStateException("Configuration not found for urgency: " + urgencia));
    }
    
    /**
     * Calculates the time factor based on how long the request has been waiting.
     */
    protected int calcularFatorTempo(Solicitacao solicitacao, ConfigPrioridade config) {
        long diasEspera = ChronoUnit.DAYS.between(solicitacao.getDataSolicitacao(), LocalDateTime.now());
        return (int) (diasEspera * config.getMultiplicadorTempo());
    }
    
    /**
     * Gets the base priority weight for the urgency level.
     */
    protected int getPesoPrioridade(UrgenciaSolicitacao urgencia) throws SQLException {
        ConfigPrioridade config = getConfiguracao(urgencia);
        return config.getPesoBase();
    }
}