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

    public static BookmarkService getInstance() {
        if (obj == null) {
            obj = new BookmarkServiceImpl();
        }
        return obj;
    }
    @Override
    public List<Bookmark> findAllBookmarksInBook(Book book) {
        return bookmarkRepo.findAllBookmarksInBook(book.getId());
    }

    @Override
    public Bookmark findLastBookmarkInBook(Book book) {
        return bookmarkRepo.findLastBookmarkInBook(book.getId());
    }

    @Override
    public int findLastPage(Book book) {
        return bookmarkRepo.findLastPageInBook(book.getId());
    }

    @Override
    public void deleteAllPreviousBookmarks(Book book) {
        bookmarkRepo.deleteAllPreviousBookmarks(book.getId());
    }

    @Override
    public void create(Bookmark object) {
        bookmarkRepo.create(object);
    }

    @Override
    public Bookmark getById(long id) {
        return bookmarkRepo.getById(id);
    }

    @Override
    public List<Bookmark> getAll() {
        return bookmarkRepo.getAll();
    }

    @Override
    public void update(long id, Bookmark newObject) {
        bookmarkRepo.update(id, newObject);
    }

    @Override
    public void deleteAll() {
        bookmarkRepo.deleteAll();
    }

    @Override
    public void deleteById(long id) {
        bookmarkRepo.deleteById(id);
    }
}
