package com.willianbrendo.biblioteca_avancada.book.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.willianbrendo.biblioteca_avancada.book.model.Book;

public interface BookRepository extends MongoRepository<Book, String>{

	// 1. Busca por ISBN (String única)
    Book findByIsbn(String isbn);
    
    // 2. Busca por parte do Título, ignorando case (Ex: Like %titulo%)
    List<Book> findByTituloContainingIgnoreCase(String titulo);

    // 3. Busca por parte do Autor, ignorando case (Ex: Like %autor%)
    List<Book> findByAutorContainingIgnoreCase(String autor);

    // 4. Busca por Categoria (correspondência exata)
    List<Book> findByCategoria(String categoria);

    // 5. Busca por status de disponibilidade (true para disponíveis, false para emprestados)
    List<Book> findByDisponivel(boolean disponivel);
}
