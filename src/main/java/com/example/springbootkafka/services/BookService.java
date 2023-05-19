package com.example.springbootkafka.services;

import java.util.List;
import java.util.Optional;

import com.example.springbootkafka.domain.Book;

public interface BookService {

    boolean isBookExists(Book book);
    
    Book save(Book book);

    Optional<Book> findById(String isbn);

    List<Book> listBooks();

    void deleteBookById(String isbn);

}
