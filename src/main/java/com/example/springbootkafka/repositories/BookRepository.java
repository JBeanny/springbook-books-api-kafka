package com.example.springbootkafka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootkafka.domain.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,String> {
    
}
