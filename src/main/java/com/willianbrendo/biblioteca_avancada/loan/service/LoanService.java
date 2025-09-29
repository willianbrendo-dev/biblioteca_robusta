package com.willianbrendo.biblioteca_avancada.loan.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.willianbrendo.biblioteca_avancada.book.model.Book;
import com.willianbrendo.biblioteca_avancada.book.repository.BookRepository;
import com.willianbrendo.biblioteca_avancada.loan.model.Loan;
import com.willianbrendo.biblioteca_avancada.loan.repository.LoanRepository;

@Service
public class LoanService {

	private final LoanRepository loanRepository;
	private final BookRepository bookRepository;

	public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
		this.loanRepository = loanRepository;
		this.bookRepository = bookRepository;
	}

	/**
	 * 1. Realiza um novo empréstimo. Implementa a REGRA DE NEGÓCIO: verifica se o
	 * livro está disponível.
	 */
	public Loan createLoan(Loan newLoan) {
		// A. Verifica se o Livro existe
		Book book = bookRepository.findById(newLoan.getBookId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado."));

		// B. REGRA DE NEGÓCIO: Verifica se o livro está DISPONÍVEL
		if (!book.isDisponivel()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "O Livro já está emprestado e indisponível.");
		}
		
		// 2. CORREÇÃO CRÍTICA: INICIALIZAÇÃO DE DATAS E CONTADORES
        LocalDate dataAtual = LocalDate.now();
        
        // Define a data de empréstimo (a que veio no seu JSON de retorno)
        newLoan.setDataEmprestimo(dataAtual); 
        
        // Define a data de devolução prevista (CORREÇÃO AQUI!)
        newLoan.setDataDevolucaoPrevista(dataAtual.plusDays(14)); 
        
        // Define as renovações como 0 (Padrão)
        newLoan.setRenovacoes(0);
		
		

		// C. Atualiza o status do Livro para INDISPONÍVEL
		book.setDisponivel(false);
		bookRepository.save(book);

		// D. Salva o novo Empréstimo
		return loanRepository.save(newLoan);
	}

	/**
	 * 2. Registra a devolução de um livro.
	 */
	public Loan returnBook(String bookId) {
		// A. Verifica se o Livro existe
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado."));

		// B. Encontra o Empréstimo ATIVO (dataDevolucaoReal é null)
		Loan activeLoan = loanRepository.findByBookIdAndDataDevolucaoRealIsNull(bookId);

		if (activeLoan == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Não há empréstimo ativo registrado para este livro.");
		}

		// C. Atualiza o status do Empréstimo
		activeLoan.setDataDevolucaoReal(LocalDate.now());
		Loan returnedLoan = loanRepository.save(activeLoan);

		// D. Atualiza o status do Livro para DISPONÍVEL
		book.setDisponivel(true);
		bookRepository.save(book);

		return returnedLoan;
	}

	/**
	 * 3. Retorna todos os empréstimos (Histórico completo).
	 */
	public List<Loan> findAllLoans() {
		return loanRepository.findAll();
	}

	/**
	 * 4. Retorna apenas empréstimos ativos (não devolvidos).
	 */
	public List<Loan> findActiveLoans() {
		return loanRepository.findByDataDevolucaoRealIsNull();
	}

	/**
	 * 5. Retorna empréstimos que estão atrasados.
	 */
	public List<Loan> findOverdueLoans() {
		return loanRepository.findOverdueLoans(LocalDate.now());
	}

	// --- Funcionalidade de Renovação (Do escopo robusto) ---

	/**
	 * 6. Renova um empréstimo ativo, estendendo a data prevista. REGRA DE NEGÓCIO:
	 * Limita o número de renovações.
	 */
	public Loan renewLoan(String loanId) {
		// A. Busca o empréstimo pelo ID
		Loan loan = loanRepository.findById(loanId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado."));

		// B. Verifica se o empréstimo já foi devolvido
		if (loan.getDataDevolucaoReal() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Não é possível renovar um empréstimo que já foi devolvido.");
		}

		// C. REGRA DE NEGÓCIO: Limita a 1 renovação
		if (loan.getRenovacoes() >= 1) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Limite máximo de renovações atingido para este empréstimo.");
		}

		// D. Renova
		loan.setDataDevolucaoPrevista(loan.getDataDevolucaoPrevista().plusDays(14));
		loan.setRenovacoes(loan.getRenovacoes() + 1);

		return loanRepository.save(loan);
	}

	public List<Loan> findFinishedLoans() {
		return loanRepository.findByDataDevolucaoRealIsNotNull();
	}

	public List<Loan> findActiveLoansByEmail(String email) {
		return loanRepository.findByEmailUsuarioAndDataDevolucaoRealIsNull(email);
	}
}
