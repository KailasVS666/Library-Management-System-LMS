package com.lms.service;

import com.lms.entity.Book;
import com.lms.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List; // Make sure this import is present
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Finds a paginated list of books, optionally filtered by a keyword.
     */
    public Page<Book> findPaginated(int pageNo, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        if (keyword != null && !keyword.trim().isEmpty()) {
            return bookRepository.findByTitleContaining(keyword, pageable);
        }
        return bookRepository.findAll(pageable);
    }

    /**
     * Returns a simple list of all books.
     * This is needed for dropdowns, like in the create_loan form.
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Counts the total number of books in the database.
     * Used for the dashboard.
     */
    public long countBooks() {
        return bookRepository.count();
    }

    /**
     * Saves or updates a book.
     */
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Retrieves a book by its ID.
     */
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Deletes a book by its ID.
     */
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}