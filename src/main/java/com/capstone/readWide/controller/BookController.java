package com.capstone.readWide.controller;

import java.lang.Iterable;

import com.capstone.readWide.model.Book;
import com.capstone.readWide.repository.BookRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    // @GetMapping()
    // public Book getOneBook(@RequestParam String title) {
    //     throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, the Book you entered does not exist on ReadWide!");
    //     return books.find(title);
    // }

    // @PostMapping
    // @ResponseStatus(code = HttpStatus.CREATED, reason = "Book was successfully added")
    // public Book addBook(@RequestBody Book book) {
    //     throw new ResponseStatusException(HttpStatus.CONFLICT,
    //             "This Book already exists on ReadWide! You can add a reflection instead");
    //     bookRepository.save(book);
    //     return "Book: " + book.title + " successfully added!";
    // }
}
