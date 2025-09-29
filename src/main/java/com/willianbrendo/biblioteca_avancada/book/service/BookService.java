package com.willianbrendo.biblioteca_avancada.book.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.willianbrendo.biblioteca_avancada.book.model.Book;
import com.willianbrendo.biblioteca_avancada.book.repository.BookRepository;
import com.willianbrendo.biblioteca_avancada.loan.repository.LoanRepository;

@Service
public class BookService {

	private final BookRepository bookRepository;
    private final LoanRepository loanRepository; // Necessário para a regra de deleção

    // Injeção de Dependências
    public BookService(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }
    
    /**
     * 1. Salva um novo livro ou atualiza um existente.
     */
    public Book saveOrUpdateBook(Book book) {
        // REGRA DE NEGÓCIO (Implícita): Evitar duplicidade de ISBN se for um novo livro.
        if (book.getId() == null && bookRepository.findByIsbn(book.getIsbn()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um livro cadastrado com este ISBN.");
        }
        return bookRepository.save(book);
    }
    
    /**
     * 2. Busca um livro por ID.
     */
    public Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado."));
    }
    
    /**
     * 3. Retorna todos os livros.
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    
    /**
     * 4. Remove um livro.
     * Implementa a REGRA DE NEGÓCIO: Não permite remoção se houver empréstimo ativo.
     */
    public void deleteBook(String id) {
        // A. Verifica se o livro existe
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado."));

        // B. REGRA DE NEGÓCIO: Verifica se há um empréstimo ativo para este livro
        if (loanRepository.findByBookIdAndDataDevolucaoRealIsNull(id) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível remover. O livro possui um empréstimo ativo.");
        }

        // C. Remove o livro
        bookRepository.delete(book);
    }
    
    /**
     * 5. Lista livros disponíveis.
     */
    public List<Book> findAvailableBooks() {
        return bookRepository.findByDisponivel(true);
    }
    
    /**
     * 6. Lista livros emprestados.
     */
    public List<Book> findLoanedBooks() {
        return bookRepository.findByDisponivel(false);
    }
    
    /**
     * 7. Busca por Título (parcial e case-insensitive).
     */
    public List<Book> findByTitulo(String titulo) {
        return bookRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    /**
     * 8. Busca por Autor (parcial e case-insensitive).
     */
    public List<Book> findByAutor(String autor) {
        return bookRepository.findByAutorContainingIgnoreCase(autor);
    }
    
    /**
     * 9. Busca por Categoria (correspondência exata).
     */
    public List<Book> findByCategoria(String categoria) {
        return bookRepository.findByCategoria(categoria);
    }
    
    /**
     * 10. Busca por ISBN (correspondência exata).
     */
    public Book findByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro com ISBN " + isbn + " não encontrado.");
        }
        return book;
    }
}
