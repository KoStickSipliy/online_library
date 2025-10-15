package org.example.repository;

import org.example.entities.Book;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends Repository<Book, Integer>{
    List<Book> findAllById(Collection<Long> ids);
}

//COMPLETED

