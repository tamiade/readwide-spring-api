package com.capstone.readWide.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;


@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "title")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "reflection")
    private String reflection;
}
