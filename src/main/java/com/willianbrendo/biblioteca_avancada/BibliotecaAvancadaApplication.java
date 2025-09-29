package com.willianbrendo.biblioteca_avancada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaAvancadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaAvancadaApplication.class, args);
		
		final String BASE = "/api/v1";

        // --- Exibição dos Endpoints no Console ---
        System.out.println("==========================================================");
        System.out.println("🚀 API de Biblioteca Robusta iniciada com sucesso!");
        System.out.println("📚 Acesse a porta padrão: http://localhost:8080");
        System.out.println("==========================================================");
        System.out.println();
        System.out.println("📋 Endpoints disponíveis:");
        System.out.println();
        
        System.out.println("📖 LIVROS (" + BASE + "/books):");
        System.out.println("GET    " + BASE + "/books - Lista todos os livros");
        System.out.println("POST   " + BASE + "/books - Cadastra novo livro (Body: Book)");
        System.out.println("GET    " + BASE + "/books/{id} - Busca livro por ID");
        System.out.println("PUT    " + BASE + "/books/{id} - Atualiza livro");
        System.out.println("DELETE " + BASE + "/books/{id} - Remove livro (Regra: se não estiver emprestado)");
        System.out.println("GET    " + BASE + "/books/disponiveis - Livros disponíveis");
        System.out.println("GET    " + BASE + "/books/emprestados - Livros emprestados");
        System.out.println("GET    " + BASE + "/books/search/titulo/{titulo} - Busca por título (parcial)");
        System.out.println("GET    " + BASE + "/books/search/autor/{autor} - Busca por autor (parcial)");
        System.out.println("GET    " + BASE + "/books/search/categoria/{categoria} - Busca por categoria");
        System.out.println("GET    " + BASE + "/books/search/isbn/{isbn} - Busca por ISBN");
        System.out.println();
        
        System.out.println("📋 EMPRÉSTIMOS (" + BASE + "/loans):");
        System.out.println("GET    " + BASE + "/loans - Lista todos os empréstimos (Histórico)");
        System.out.println("POST   " + BASE + "/loans - Realiza novo empréstimo (Body: Loan)");
        System.out.println("PUT    " + BASE + "/loans/{bookId}/devolucao - Realiza devolução");
        System.out.println("PUT    " + BASE + "/loans/{loanId}/renovar - Renova empréstimo");
        System.out.println("GET    " + BASE + "/loans/ativos - Empréstimos ativos");
        System.out.println("GET    " + BASE + "/loans/finalizados - Empréstimos finalizados (devolvidos)");
        System.out.println("GET    " + BASE + "/loans/atrasados - Empréstimos atrasados");
        System.out.println("GET    " + BASE + "/loans/usuario/{email}/ativos - Empréstimos ativos do usuário");
        System.out.println();
        
        System.out.println("📊 DASHBOARD (" + BASE + "/dashboard):");
        System.out.println("GET    " + BASE + "/dashboard - Estatísticas e Resumo completo");
        System.out.println("GET    " + BASE + "/dashboard/resumo - Resumo para widgets");
        System.out.println("==========================================================");
	}

}
