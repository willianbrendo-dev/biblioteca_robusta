package com.willianbrendo.biblioteca_avancada.book.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Gera Getters, Setters, equals(), hashCode() e toString()
@NoArgsConstructor // Construtor sem argumentos para o Spring/Jackson
@Document(collection = "books")
public class Book {

	@Id
	private String id;
	
	// Campos para Busca Avançada
    private String titulo;
    private String autor;
    private String isbn; 
    private String editora;
    private Integer anoPublicacao;
    private String categoria;
    
 // Status do Livro (Regra de Negócio)
    private boolean disponivel = true;
    
    public Book(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponivel = true;
    }
}
