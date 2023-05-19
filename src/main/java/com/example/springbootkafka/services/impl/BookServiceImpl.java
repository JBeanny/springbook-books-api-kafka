package com.example.springbootkafka.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.springbootkafka.domain.Book;
import com.example.springbootkafka.domain.BookEntity;
import com.example.springbootkafka.repositories.BookRepository;
import com.example.springbootkafka.services.BookService;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(final Book book) {
        // convert book to be a type of BookEntity
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
        // convert a type of BookEntity to Book
        return bookEntityToBook(savedBookEntity);
    }

    @Override
    public void deleteBookById(String isbn) {
        try{
            bookRepository.deleteById(isbn);
        }catch(final EmptyResultDataAccessException ex) {
            log.debug("Attempting to delete a book :",ex);
        }
    }

    // convert Book to BookEntity
    private BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder()
        .isbn(book.getIsbn())
        .author(book.getAuthor())
        .title(book.getTitle())
        .build();
    }

    // convert BookEntity to Book
    private Book bookEntityToBook(BookEntity bookEntity) {
        return Book.builder()
        .isbn(bookEntity.getIsbn())
        .author(bookEntity.getAuthor())
        .title(bookEntity.getTitle())
        .build();
    }

    @Override
    public Optional<Book> findById(String isbn) {
        final Optional<BookEntity> foundBook = bookRepository.findById(isbn);
        return foundBook.map(book -> bookEntityToBook(book));
    }

    @Override
    public List<Book> listBooks() {
        final List<BookEntity> foundBooks = bookRepository.findAll();
        return foundBooks.stream()
                .map(book -> bookEntityToBook(book)).collect(Collectors.toList());
    }

    @Override
    public boolean isBookExists(Book book) {
        return bookRepository.existsById(book.getIsbn());
    }

}
