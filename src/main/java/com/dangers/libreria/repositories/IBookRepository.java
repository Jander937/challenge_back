package com.dangers.libreria.repositories;

import com.dangers.libreria.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookRepository extends JpaRepository <BookEntity, Integer> {
    List<BookEntity> findByTitleContainingIgnoreCase(String title);
}
