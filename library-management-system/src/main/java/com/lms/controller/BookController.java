package com.lms.controller;

import com.lms.entity.Book;
import com.lms.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
@Tag(name = "Book Management", description = "APIs for managing books in the library")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get all books", description = "Returns a list of all books in the system.")
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @Operation(summary = "Show form to create a new book")
    @GetMapping("/new")
    public String createBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "create_book";
    }

    @Operation(summary = "Save a new book")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "302", description = "Successfully created the book and redirected"),
        @ApiResponse(responseCode = "400", description = "Invalid book data provided")
    })
    @PostMapping
    public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "create_book";
        }
        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute("success", "Book added successfully!");
        return "redirect:/books";
    }

    @Operation(summary = "Show form to edit an existing book")
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "edit_book";
    }

    @Operation(summary = "Update an existing book")
    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id, @Valid @ModelAttribute("book") Book bookDetails, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit_book";
        }
        Book existingBook = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));

        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        
        bookService.saveBook(existingBook);
        redirectAttributes.addFlashAttribute("success", "Book updated successfully!");
        return "redirect:/books";
    }

    @Operation(summary = "Delete a book by ID")
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("success", "Book deleted successfully!");
        return "redirect:/books";
    }
}