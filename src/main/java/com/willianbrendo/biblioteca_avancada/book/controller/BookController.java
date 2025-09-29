package com.willianbrendo.biblioteca_avancada.book.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.willianbrendo.biblioteca_avancada.book.model.Book;
import com.willianbrendo.biblioteca_avancada.book.service.BookService;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

	private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    /**
     * POST /api/v1/books
     * Cadastra um novo livro.
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        // O método saveOrUpdateBook no Service lida com a checagem de ISBN
        Book savedBook = bookService.saveOrUpdateBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
    
    /**
     * PUT /api/v1/books/{id}
     * Atualiza os dados de um livro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book bookDetails) {
        // Garante que o ID do caminho seja usado na atualização
        bookDetails.setId(id); 
        // O Service cuida da persistência
        Book updatedBook = bookService.saveOrUpdateBook(bookDetails);
        return ResponseEntity.ok(updatedBook);
    }
    
    /**
     * GET /api/v1/books
     * Lista todos os livros.
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }
    
    /**
     * GET /api/v1/books/{id}
     * Busca um livro por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }
    
    /**
     * DELETE /api/v1/books/{id}
     * Remove um livro. O Service implementa a regra de não deletar se ativo.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
    }
    
    /**
     * GET /api/v1/books/disponiveis
     * Lista livros disponíveis para empréstimo.
     */
    @GetMapping("/disponiveis")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        return ResponseEntity.ok(bookService.findAvailableBooks());
    }
    
    /**
     * GET /api/v1/books/emprestados
     * Lista livros atualmente emprestados (indisponíveis).
     */
    @GetMapping("/emprestados")
    public ResponseEntity<List<Book>> getLoanedBooks() {
        return ResponseEntity.ok(bookService.findLoanedBooks());
    }
    
    /**
     * GET /api/v1/books/search/titulo/{titulo}
     * Busca livros por Título (Busca parcial).
     */
    @GetMapping("/search/titulo/{titulo}")
    public ResponseEntity<List<Book>> searchByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(bookService.findByTitulo(titulo));
    }
    
    /**
     * GET /api/v1/books/search/autor/{autor}
     * Busca livros por Autor (Busca parcial).
     */
    @GetMapping("/search/autor/{autor}")
    public ResponseEntity<List<Book>> searchByAutor(@PathVariable String autor) {
        return ResponseEntity.ok(bookService.findByAutor(autor));
    }
    
    /**
     * GET /api/v1/books/search/categoria/{categoria}
     * Busca livros por Categoria (Busca exata).
     */
    @GetMapping("/search/categoria/{categoria}")
    public ResponseEntity<List<Book>> searchByCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(bookService.findByCategoria(categoria));
    }
    
    /**
     * GET /api/v1/books/search/isbn/{isbn}
     * Busca um livro por ISBN (Busca exata).
     */
    @GetMapping("/search/isbn/{isbn}")
    public ResponseEntity<Book> searchByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findByIsbn(isbn));
    }
}
