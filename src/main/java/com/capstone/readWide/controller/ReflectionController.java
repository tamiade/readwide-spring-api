package com.capstone.readWide.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import com.capstone.readWide.model.Book;
import com.capstone.readWide.model.Reflection;
import com.capstone.readWide.model.ReflectionInput;
import com.capstone.readWide.repository.BookRepository;
import com.capstone.readWide.repository.ReflectionRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.util.ObjectUtils;

@CrossOrigin
@RestController
@RequestMapping("/reflections")
public class ReflectionController {

    private final ReflectionRepository reflectionRepository;
    private final BookRepository bookRepository;

    public ReflectionController(final ReflectionRepository reflectionRepository, final BookRepository bookRepository) {
    this.reflectionRepository = reflectionRepository;
    this.bookRepository = bookRepository;
    }

    @GetMapping("/get")
    public Iterable<Reflection> getAllReflections() {
        return this.reflectionRepository.findAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addUserReflection(@RequestBody ReflectionInput reflectionInput) {
        if (ObjectUtils.isEmpty(reflectionInput.submittedBy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<Book> book = bookRepository.findById(reflectionInput.bookId);
        if (!book.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Reflection reflection = new Reflection();
        reflection.setBook(book.get());
        reflection.setReflection(reflectionInput.reflection);
        reflection.setSubmittedBy(reflectionInput.submittedBy);
        reflection.setPostedTime(Timestamp.valueOf(LocalDateTime.now()));
        reflectionRepository.save(reflection);
    }
}
