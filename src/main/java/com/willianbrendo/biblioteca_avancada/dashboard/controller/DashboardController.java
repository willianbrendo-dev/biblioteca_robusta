package com.willianbrendo.biblioteca_avancada.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.willianbrendo.biblioteca_avancada.dashboard.model.DashboardSummary;
import com.willianbrendo.biblioteca_avancada.dashboard.service.DashboardService;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    
    /**
     * GET /api/v1/dashboard/resumo
     * Retorna um resumo conciso das principais métricas para widgets.
     */
    @GetMapping("/resumo")
    public ResponseEntity<DashboardSummary> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }
    
 // O endpoint /api/v1/dashboard (sem /resumo) pode ser reservado
    // para um endpoint mais complexo, com gráficos e dados históricos,
    // mas por hora ele chamará o mesmo resumo.
    @GetMapping
    public ResponseEntity<DashboardSummary> getFullDashboard() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }
	
}
