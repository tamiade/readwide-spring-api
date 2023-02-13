package com.capstone.readWide.repository;

import org.springframework.data.repository.CrudRepository;
import com.capstone.readWide.model.Reflection;

import java.util.Optional;

public interface ReflectionRepository extends CrudRepository<Reflection, Integer> {
    Optional<Iterable<Reflection>> findByBookId(Integer bookId);
}