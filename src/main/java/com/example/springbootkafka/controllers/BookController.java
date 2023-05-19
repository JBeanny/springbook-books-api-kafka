package com.example.springbootkafka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springbootkafka.domain.Book;
import com.example.springbootkafka.services.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping("{isbn}")
    public ResponseEntity<Book> createUpdateBook(@PathVariable final String isbn,@RequestBody final Book book) {
         book.setIsbn(isbn);
         final Book savedBook = bookService.save(book);

         if(bookService.isBookExists(book)) {
            return new ResponseEntity<Book>(savedBook,HttpStatus.OK);
         } else {
             return new ResponseEntity<Book>(savedBook,HttpStatus.CREATED);
         }
    }

    @GetMapping("{isbn}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final String isbn) {
        final Optional<Book> foundBook = bookService.findById(isbn);
        return foundBook.map(book -> new ResponseEntity<Book>(book,HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
    }

    @GetMapping()
    public ResponseEntity<List<Book>> retrieveBooks() {
        return new ResponseEntity<List<Book>>(bookService.listBooks(),HttpStatus.OK);
    }

    @DeleteMapping("{isbn}")
    public ResponseEntity<Object> deleteBook(@PathVariable final String isbn) {
        bookService.deleteBookById(isbn);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
