package org.example.repository;

import org.example.entities.Book;

import java.util.Collection;
import java.util.List;

public class BookDBRepository implements BookRepository {
    @Override
    public List<Book> findAllById(Collection<Long> ids) {
        return null;
    }

    @Override
    public void create(Book object) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void update(long id, Book newObject) {

    }

    @Override
    public Book getById(long id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }
}
