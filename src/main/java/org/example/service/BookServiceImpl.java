package org.example.service;

import org.example.IO.IO;
import org.example.entities.Book;
import org.example.repository.*;
import org.example.entities.Bookmark;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static BookService obj;
    private final BookRepository bookRepo = BookDBRepository.getInstance();
    private final BookmarkRepository bookmarkRepo = BookmarkDBRepository.getInstance();

    public static BookService getInstance() {
        if (obj == null) {
            obj = new BookServiceImpl();
        }
        return obj;
    }
    @Override
    public List<Book> findAllById(List<Long> ids) {
        return bookRepo.findAllById(ids);
    }

    @Override
    public List<Book> findByName(String name) {
        List<Book> all = bookRepo.getAll();
        List<Book> found = new java.util.ArrayList<>();
        for (Book book : all) {
            if (book.getName() != null && book.getName().equals(name)){
                found.add(book);
            }
        }
        return found;
    }

    @Override
    public List<Book> findByPath(String path) {
        IO.printError("findByPath is not supported");
        return List.of();
    }

    @Override
    public List<Book> findAllStartedBooks() {
        List<Long> bookIds = bookmarkRepo.findAllStartedBookIds();
        return bookRepo.findAllById(bookIds);
    }

    @Override
    public Book findLastBook() {
        try {
            long lastId = bookmarkRepo.findLastReadBookId();
            return bookRepo.getById(lastId);
        } catch (RuntimeException e) {
            IO.printError(e.getMessage());
            return null;
        }

    }

    @Override
    public Book findByBookmark(Bookmark bookmark) {
        try {
            return bookRepo.getById(bookmark.getBookId());
        } catch (RuntimeException e) {
            IO.printError(e.getMessage());
            return null;
        }
    }

    @Override
    public void create(Book object) {
        bookRepo.create(object);
    }

    @Override
    public Book getById(long id) {
        try {
            return bookRepo.getById(id);
        } catch (RuntimeException e) {
            IO.printError(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        return bookRepo.getAll();
    }

    @Override
    public void update(long id, Book newObject) {
        bookRepo.update(id, newObject);
    }

    @Override
    public void deleteAll() {
        bookRepo.deleteAll();
    }

    @Override
    public void deleteById(long id) {
        bookRepo.deleteById(id);
    }
}
