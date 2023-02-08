package com.capstone.readWide.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.capstone.readWide.model.Book;


public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
}

