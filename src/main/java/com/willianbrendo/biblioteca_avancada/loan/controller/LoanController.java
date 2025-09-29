package com.willianbrendo.biblioteca_avancada.loan.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.willianbrendo.biblioteca_avancada.loan.model.Loan;
import com.willianbrendo.biblioteca_avancada.loan.repository.LoanRepository;
import com.willianbrendo.biblioteca_avancada.loan.service.LoanService;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

	private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
        
    }
    
    /**
     * POST /api/v1/loans
     * Realiza um novo empréstimo. O Service verifica a disponibilidade do livro.
     * Exemplo de Body: { "bookId": "...", "nomeUsuario": "...", "emailUsuario": "..." }
     */
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan newLoan) {
        Loan savedLoan = loanService.createLoan(newLoan);
        return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
    }
    
    /**
     * PUT /api/v1/loans/{bookId}/devolucao
     * Registra a devolução do livro e o torna disponível novamente.
     * O bookId é usado para encontrar o empréstimo ATIVO.
     */
    @PutMapping("/{bookId}/devolucao")
    public ResponseEntity<Loan> returnBook(@PathVariable String bookId) {
        Loan returnedLoan = loanService.returnBook(bookId);
        return ResponseEntity.ok(returnedLoan);
    }
    
    /**
     * PUT /api/v1/loans/{loanId}/renovar
     * Renova um empréstimo ativo.
     */
    @PutMapping("/{loanId}/renovar")
    public ResponseEntity<Loan> renewLoan(@PathVariable String loanId) {
        Loan renewedLoan = loanService.renewLoan(loanId);
        return ResponseEntity.ok(renewedLoan);
    }
    
    /**
     * GET /api/v1/loans
     * Lista todos os empréstimos (histórico completo).
     */
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.findAllLoans());
    }
    
    /**
     * GET /api/v1/loans/ativos
     * Lista apenas os empréstimos ativos (em mãos do usuário).
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        return ResponseEntity.ok(loanService.findActiveLoans());
    }
    
    /**
     * GET /api/v1/loans/finalizados
     * Lista apenas os empréstimos já devolvidos.
     * OBS: Este filtro precisa ser implementado no LoanService, usando o LoanRepository.
     */
    @GetMapping("/finalizados")
    public ResponseEntity<List<Loan>> getFinishedLoans() {
        // Implementação temporária no Repositório: findByDataDevolucaoRealIsNotNull()
        // Por hora, apenas chamaremos o findAll e faremos um filtro simples, mas é melhor otimizar no Service.
        // Vamos adicionar um método no LoanService para ser mais consistente.
        //
        // **Ação: Precisamos adicionar o método findFinishedLoans() no LoanService.**
        // Por enquanto, usaremos a consulta completa do repositório para evitar quebrar o fluxo.
        return ResponseEntity.ok(loanService.findFinishedLoans());
    }
    
    /**
     * GET /api/v1/loans/atrasados
     * Lista todos os empréstimos que estão atrasados.
     */
    @GetMapping("/atrasados")
    public ResponseEntity<List<Loan>> getOverdueLoans() {
        return ResponseEntity.ok(loanService.findOverdueLoans());
    }
    
    /**
     * GET /api/v1/loans/usuario/{email}/ativos
     * Lista todos os empréstimos ativos de um usuário específico.
     * OBS: Este filtro precisa ser adicionado ao LoanService.
     */
    @GetMapping("/usuario/{email}/ativos")
    public ResponseEntity<List<Loan>> getActiveLoansByEmail(@PathVariable String email) {
        // **Ação: Precisamos adicionar o método findActiveLoansByEmail() no LoanService.**
        // Por enquanto, usaremos a consulta completa do repositório para evitar quebrar o fluxo.
        return ResponseEntity.ok(loanService.findActiveLoansByEmail(email));
    }
}
