package com.dangers.libreria.service.impl;

import com.dangers.libreria.dto.BookDTO;
import com.dangers.libreria.entities.BookEntity;
import com.dangers.libreria.mapper.IBookMapper;
import com.dangers.libreria.repositories.IBookRepository;
import com.dangers.libreria.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    IBookMapper iBookMapper;

    @Autowired
    IBookRepository iBookRepository;

    @Override
    @Transactional
    public BookDTO save(BookEntity dataBook) throws Exception{
        try {
            return this.iBookMapper.bookToDTO(this.iBookRepository.save(dataBook));
        }catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }
    @Override
    @Transactional(readOnly = true)
    public BookDTO seachById(Integer id) throws Exception{
        Optional<BookEntity> bookEntityOptional = this.iBookRepository.findById(id);
        if (bookEntityOptional.isPresent()){
            return this.iBookMapper.bookToDTO(bookEntityOptional.get());
        }else {
            throw new Exception("book not found with the given ID" + id);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> seachAllBooks() throws Exception {
        List<BookEntity> bookEntityList = iBookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookEntity bookEntity : bookEntityList) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId_book(bookEntity.getId_book());
            bookDTO.setImage(bookEntity.getImage());
            bookDTO.setTitle(bookEntity.getTitle());
            bookDTO.setIsbn(bookEntity.getIsbn());
            bookDTO.setAuthors(bookEntity.getAuthors());
            bookDTO.setPublication_year(bookEntity.getPublication_year());
            bookDTO.setCustomers(bookEntity.getCustomers());
            bookDTO.setGenders(bookEntity.getGenders());
            bookDTO.setLoans(bookEntity.getLoans());
            // Establece otras propiedades del libro que deseas incluir en el JSON

            bookDTOList.add(bookDTO);
        }
        return bookDTOList;
    }
    @Override
    @Transactional
    public BookDTO modifyBook(Integer id, BookEntity book) throws Exception{
        try {
            Optional<BookEntity> optionalBook = iBookRepository.findById(id);
            if (optionalBook.isPresent()){
                BookEntity existingBook = optionalBook.get();
                existingBook.setTitle(book.getTitle());
                existingBook.setImage(book.getImage());
                existingBook.setPublication_year(book.getPublication_year());
                existingBook.setIsbn(book.getIsbn());
                // Aquí establece los demás atributos que deseas modificar

                return iBookMapper.bookToDTO(iBookRepository.save(existingBook));
            } else {
                throw new Exception("Book not found with ID: " + id);
            }
        } catch (Exception error){
            throw new Exception("Failed to modify book: " + error.getMessage());
        }
    }
    @Override
    @Transactional
    public List<BookDTO> searchByTitle(String title) throws Exception {
        List<BookEntity> bookEntities = iBookRepository.findByTitleContainingIgnoreCase(title);
        if (bookEntities.isEmpty()) {
            throw new Exception("No se encontraron libros con el título proporcionado.");
        }
        return bookEntities.stream()
                .map(iBookMapper::bookToDTO)
                .collect(Collectors.toList());
    }

    // Los demás métodos ya implementados aquí
    @Override
    @Transactional
    public Boolean deleteBook(Integer id){
        Optional<BookEntity> deleteBook = iBookRepository.findById(id);

        if (deleteBook.isEmpty())
            return false;
        iBookRepository.deleteById(id);

        return true;
    }
}
