package com.dangers.libreria.service;

import com.dangers.libreria.dto.BookDTO;
import com.dangers.libreria.entities.BookEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {
    BookDTO save(BookEntity dataBook) throws Exception;

    BookDTO seachById(Integer id) throws Exception;

    List<BookDTO> seachAllBooks()throws Exception;

    BookDTO modifyBook(Integer id, BookEntity book) throws Exception;

    @Transactional
    List<BookDTO> searchByTitle(String title) throws Exception;

    Boolean deleteBook(Integer id);
}
