package com.willianbrendo.biblioteca_avancada.dashboard.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSummary {

	// Livros
    private long totalLivros;
    private long livrosDisponiveis;
    private long livrosEmprestados;
    
    // Empréstimos
    private long totalEmprestimosAtivos;
    private long totalEmprestimosFinalizados;
    private long totalEmprestimosAtrasados;
    
    // Métricas de Risco
    private double taxaDisponibilidade; // (Disponíveis / Total) * 100
    private double taxaAtraso; // (Atrasados / Ativos) * 100
}
