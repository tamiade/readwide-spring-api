package com.capstone.readWide.controller;

import java.lang.Iterable;
import java.util.Optional;

import com.capstone.readWide.model.Book;
import com.capstone.readWide.repository.BookRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.util.ObjectUtils;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(final BookRepository bookRepository) {
    this.bookRepository = bookRepository;
    }

    @GetMapping
    public Iterable<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/search")
    public Optional<Book> searchBooks(@RequestParam(name="title", required=false) String title, @RequestParam(name="author", required=false) String author) {
        if (title != null && author != null) {
            return this.bookRepository.findByTitleAndAuthor(title, author);
        } else if (title != null) {
            return this.bookRepository.findByTitle(title);
        } else if (author != null) {
            return this.bookRepository.findByAuthor(author);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, you must enter a valid title or author, or the Book you entered does not exist on ReadWide!");
        }
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Book was successfully added!")
    public Book addBook(@RequestBody Book book) {
        if (ObjectUtils.isEmpty(book.getTitle())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must input a title to add a book to ReadWide!");
        }
        Optional<Book> existingBook = bookRepository
                .findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (existingBook.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                    "This Book already exists on ReadWide! You can add a reflection instead!");
        }
        Book newBook = this.bookRepository.save(book);
        return newBook;
    }
}
