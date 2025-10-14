package org.example.repository;

import org.example.entities.Book;
import org.example.entities.Bookmark;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface BookRepository extends Repository<Book, Integer>{
    List<Book> findAllById(Collection<Long> ids);
}

//COMPLETED

