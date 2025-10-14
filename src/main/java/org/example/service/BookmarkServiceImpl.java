package org.example.service;

import org.example.entities.Book;
import org.example.entities.Bookmark;
import org.example.repository.BookInMemoryRepository;
import org.example.repository.BookRepository;
import org.example.repository.BookmarkInMemoryRepository;
import org.example.repository.BookmarkRepository;

import java.util.List;

public class BookmarkServiceImpl implements BookmarkService {
    private static BookmarkService obj;
    private final BookRepository bookRepo = BookInMemoryRepository.getInstance();;
    private final BookmarkRepository bookmarkRepo = BookmarkInMemoryRepository.getInstance();

    @Override
    public List<Bookmark> findBookmarksInBook(Book book) {
        return null;
    }

    @Override
    public int findLastPage(Book book) {
        return 0;
    }

    @Override
    public Book findLastBook() {
        return null;
    }

    @Override
    public List<Book> findAllStartedBooks() {
        return null;
    }

    @Override
    public void deleteAllPreviousBookmarks(Book book) {

    }

    @Override
    public void deleteAllBookmarks(Book book) {

    }

    @Override
    public void create(Bookmark object) {
        bookmarkRepo.create(object);
    }

    @Override
    public Bookmark getById(long id) {
        return null;
    }

    @Override
    public List<Bookmark> getAll() {
        return null;
    }

    @Override
    public void update(long id, Bookmark newObject) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(long id) {

    }
}
