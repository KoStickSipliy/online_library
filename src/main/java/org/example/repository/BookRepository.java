package org.example.repository;

import org.example.entities.Book;
import org.example.entities.Bookmark;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public interface BookRepository extends Repository<Bookmark, Integer>{
    Set<Book> findAllById(Collection<Integer> ids);
}

//COMPLETED

