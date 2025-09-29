package com.willianbrendo.biblioteca_avancada.dashboard.service;

import org.springframework.stereotype.Service;

import com.willianbrendo.biblioteca_avancada.book.service.BookService;
import com.willianbrendo.biblioteca_avancada.dashboard.model.DashboardSummary;
import com.willianbrendo.biblioteca_avancada.loan.service.LoanService;

@Service
public class DashboardService {

	private final BookService bookService;
    private final LoanService loanService;

    // Injeção dos Serviços necessários
    public DashboardService(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }
    
    /**
     * Reúne todas as estatísticas para o dashboard de resumo.
     */
    public DashboardSummary getSummary() {
        
        // 1. Coleta de Dados Brutos
        long totalLivros = bookService.findAll().size();
        long livrosDisponiveis = bookService.findAvailableBooks().size();
        long totalEmprestimosAtivos = loanService.findActiveLoans().size();
        long totalEmprestimosAtrasados = loanService.findOverdueLoans().size();
        
        // Usamos o método corrigido do LoanService para finalizados
        long totalEmprestimosFinalizados = loanService.findFinishedLoans().size();
        
        long livrosEmprestados = totalLivros - livrosDisponiveis; // Deve ser igual a totalEmprestimosAtivos se a lógica estiver perfeita

        // 2. Cálculos de Métricas
        double taxaDisponibilidade = totalLivros > 0 ? (double) livrosDisponiveis / totalLivros * 100 : 0;
        double taxaAtraso = totalEmprestimosAtivos > 0 ? (double) totalEmprestimosAtrasados / totalEmprestimosAtivos * 100 : 0;

        // 3. Constrói e Retorna o DTO (usando @Builder do Lombok)
        return DashboardSummary.builder()
                .totalLivros(totalLivros)
                .livrosDisponiveis(livrosDisponiveis)
                .livrosEmprestados(livrosEmprestados)
                .totalEmprestimosAtivos(totalEmprestimosAtivos)
                .totalEmprestimosFinalizados(totalEmprestimosFinalizados)
                .totalEmprestimosAtrasados(totalEmprestimosAtrasados)
                .taxaDisponibilidade(Math.round(taxaDisponibilidade * 100.0) / 100.0) // Arredonda para 2 casas
                .taxaAtraso(Math.round(taxaAtraso * 100.0) / 100.0)
                .build();
    }
}
