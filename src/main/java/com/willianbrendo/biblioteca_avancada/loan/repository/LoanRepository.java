package com.willianbrendo.biblioteca_avancada.loan.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.willianbrendo.biblioteca_avancada.loan.model.Loan;

public interface LoanRepository extends MongoRepository<Loan, String>{

	// 1. Encontra o empréstimo ATIVO de um livro específico
    // Critério: bookId é igual E dataDevolucaoReal é NULL (não devolvido).
    Loan findByBookIdAndDataDevolucaoRealIsNull(String bookId);

    // 2. Lista todos os empréstimos ATIVOS (Ainda não devolvidos)
    List<Loan> findByDataDevolucaoRealIsNull();

    // 3. Lista todos os empréstimos FINALIZADOS (Devolvidos)
    List<Loan> findByDataDevolucaoRealIsNotNull();
    
    // 4. Lista empréstimos ATIVOS de um usuário por e-mail
    List<Loan> findByEmailUsuarioAndDataDevolucaoRealIsNull(String emailUsuario);

    // 5. Consulta Avançada: Lista empréstimos ATRASADOS
    // Usamos a anotação @Query para consultas MongoDB mais complexas
    // Critério: dataDevolucaoReal é NULL E dataDevolucaoPrevista é ANTES (less than) da data atual.
    @Query("{ 'dataDevolucaoReal' : null, 'dataDevolucaoPrevista' : { $lt: ?0 } }")
    List<Loan> findOverdueLoans(LocalDate dataAtual);
}
