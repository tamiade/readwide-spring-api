package com.capstone.readWide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {
    
    @GetMapping("/books")
    public String getAllBooks() {
        return "Testing!";
    }
}
