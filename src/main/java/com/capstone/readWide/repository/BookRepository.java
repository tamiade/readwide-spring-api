package com.capstone.readWide.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.capstone.readWide.model.Book;


public interface BookRepository extends CrudRepository<Book, Integer> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByAuthor(String author);
    Optional<Book> findByTitleAndAuthor(String title, String author);
}

