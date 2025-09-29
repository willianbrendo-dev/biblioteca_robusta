package com.willianbrendo.biblioteca_avancada.loan.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "loans")
public class Loan {

	@Id
    private String id;

    // Referência ao livro emprestado
    private String bookId;

    // Detalhes do Leitor (para busca por usuário)
    private String nomeUsuario;
    private String emailUsuario; 
    
    // Datas de Controle
    private LocalDate dataEmprestimo = LocalDate.now();
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal; // Null se ainda ativo
    
    // Contagem para renovação
    private int renovacoes = 0;
    
 // Construtor principal para iniciar um novo empréstimo
    public Loan(String bookId, String nomeUsuario, String emailUsuario) {
        this.bookId = bookId;
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.dataEmprestimo = LocalDate.now();
        // Regra de negócio: Devolução padrão em 14 dias
        this.dataDevolucaoPrevista = this.dataEmprestimo.plusDays(14);
        this.renovacoes = 0;
    }
    
    /**
     * Verifica se o empréstimo está em atraso.
     * Um empréstimo está atrasado se não foi devolvido (dataDevolucaoReal é null)
     * E a dataDevolucaoPrevista já passou.
     */
    @JsonIgnore
    public boolean isAtrasado() {
        return this.dataDevolucaoReal == null && this.dataDevolucaoPrevista.isBefore(LocalDate.now());
    }
}
