package com.dangers.libreria.controller;

import com.dangers.libreria.dto.BookDTO;
import com.dangers.libreria.dto.errorDTO.BookErrorDTO;
import com.dangers.libreria.entities.BookEntity;
import com.dangers.libreria.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v5/book")
@CrossOrigin(originPatterns = "*")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<?> add(@RequestBody BookEntity dataReceived){
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(this.bookService.save(dataReceived));
        } catch (Exception e) {
            BookErrorDTO errorPersonalized = new BookErrorDTO();
            errorPersonalized.setMensajeError(e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorPersonalized.getMensajeError());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> seachById(@PathVariable Integer id){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.bookService.seachById(id));
        }catch (Exception e){
            BookErrorDTO errorPersonalized = new BookErrorDTO();
            errorPersonalized.setMensajeError(e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorPersonalized.getMensajeError());
        }
    }
    @GetMapping("/seach")
    public ResponseEntity<?> seachAllBooks(){
        try {
            List<BookDTO> bookDTOList = this.bookService.seachAllBooks();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookDTOList);
        } catch (Exception e){
            BookErrorDTO errorPersonalized = new BookErrorDTO();
            errorPersonalized.setMensajeError(e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorPersonalized.getMensajeError());
        }
    }
    @GetMapping("/title")
    public ResponseEntity<?> searchByTitle(@RequestParam String title){
        try {
            List<BookDTO> bookDTOList = this.bookService.searchByTitle(title);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(bookDTOList);
        } catch (Exception e){
            BookErrorDTO errorPersonalized = new BookErrorDTO();
            errorPersonalized.setMensajeError(e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorPersonalized.getMensajeError());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyBook(@RequestBody BookEntity dataReceived, @PathVariable Integer id){
        try {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(this.bookService.modifyBook(id, dataReceived));
        }catch (Exception e){
            BookErrorDTO errorPersonalized = new BookErrorDTO();
            errorPersonalized.setMensajeError(e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorPersonalized.getMensajeError());
        }
    }
    @DeleteMapping(value = "/delete/{id}")
    public Optional<ResponseEntity<?>> delete(@PathVariable int id){

        Boolean result = bookService.deleteBook(id);

        if (result){
            return Optional.of(ResponseEntity.noContent().build());
        }
        return Optional.of(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
