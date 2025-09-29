package com.willianbrendo.biblioteca_avancada;

import java.util.Arrays;
import java.util.List;
import com.willianbrendo.biblioteca_avancada.loan.repository.LoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.willianbrendo.biblioteca_avancada.book.model.Book;
import com.willianbrendo.biblioteca_avancada.book.repository.BookRepository;

@Component
public class DataInitializer implements CommandLineRunner{

    private final LoanRepository loanRepository;
	
	private final BookRepository bookRepository;

    // Injeta o repositório para salvar os dados
    public DataInitializer(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

	@Override
	public void run(String... args) throws Exception {
		

		// Limpar a coleção de livros antes de popular para evitar duplicatas em cada reinício
        // Atenção: Remova esta linha em ambiente de produção!
        bookRepository.deleteAll();
        loanRepository.deleteAll();
        
        System.out.println("⚠️ MongoDB: Iniciando a população da coleção 'books'...");

        // Criação de objetos Book com status inicial 'disponivel = true' (default)
        List<Book> initialBooks = Arrays.asList(
            createBook("Código Limpo", "Robert C. Martin", "978-0132350884", "Alta Books", 2008, "Programação"),
            createBook("Padrões de Projeto", "Gang of Four", "978-0201633610", "Bookman", 1994, "Arquitetura"),
            createBook("A Bússola de Ouro", "Philip Pullman", "978-0439992015", "Rocco", 1995, "Fantasia"),
            createBook("Fahrenheit 451", "Ray Bradbury", "978-0345342966", "Globo", 1953, "Ficção Científica"),
            createBook("O Senhor dos Anéis", "J.R.R. Tolkien", "978-8578278072", "HarperCollins", 1954, "Fantasia")
        );

        // Salva todos os livros no MongoDB
        bookRepository.saveAll(initialBooks);
        
        System.out.println("✅ MongoDB: " + bookRepository.count() + " livros iniciais populados com sucesso!");
    }

    /**
     * Método auxiliar para simplificar a criação dos objetos Book.
     */
    private Book createBook(String titulo, String autor, String isbn, String editora, Integer ano, String categoria) {
        Book book = new Book();
        book.setTitulo(titulo);
        book.setAutor(autor);
        book.setIsbn(isbn);
        book.setEditora(editora);
        book.setAnoPublicacao(ano);
        book.setCategoria(categoria);
        // O status 'disponivel' é true por padrão no modelo
        return book;
		
	}

}
