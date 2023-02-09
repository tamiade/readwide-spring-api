package com.capstone.readWide.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reflections")
public class Reflection {
    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(name = "submittedBy")
    private String submittedBy;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="book_id")
    private Book book;

    @Column(name = "reflection")
    private String reflection;

    @Column(name = "posted_time")
    private java.sql.Timestamp postedTime;
}
