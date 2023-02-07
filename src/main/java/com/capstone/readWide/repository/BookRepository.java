package com.capstone.readWide.repository;

import org.springframework.data.repository.CrudRepository;
import com.capstone.readWide.model.Book;


public interface BookRepository extends CrudRepository<Book, Integer> {

}

