package org.example.service;

import org.example.IO.IO;
import org.example.entities.Book;
import org.example.entities.Bookmark;
import org.example.repository.BookInMemoryRepository;
import org.example.repository.BookRepository;
import org.example.repository.BookmarkInMemoryRepository;
import org.example.repository.BookmarkRepository;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static BookService obj;
    private final BookRepository bookRepo = BookInMemoryRepository.getInstance();;
    private final BookmarkRepository bookmarkRepo = BookmarkInMemoryRepository.getInstance();

    @Override
    public List<Book> findAllById(List<Long> ids) {
        return bookRepo.findAllById(ids);
    }

    @Override
    public List<Book> findByName(String name) {
        List<Book> books = null;
        for (Book book : books) {
            if (book.getName().equals(name)){
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public List<Book> findByPath(String path) {
        IO.printError("findByBath is not supported");
        return null;
    }

    @Override
    public List<Book> findAllStartedBooks() {
        IO.printError("findAllSupportedBooks is not supported");
        return null;
    }

    @Override
    public Book findLastBook() {
        IO.printError("findLastBook is not supported");
        return null;
    }

    @Override
    public Book findByBookmark(Bookmark bookmark) {
        return bookRepo.getById(bookmark.getBookId());
    }

    @Override
    public void create(Book object) {
        bookRepo.create(object);
    }

    @Override
    public Book getById(long id) {
        return bookRepo.getById(id);
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
