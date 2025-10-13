package org.example.repository;

import org.example.entities.Book;
import org.example.entities.Bookmark;

import java.util.Collection;
import java.util.Set;

public interface BookmarkRepository extends Repository<Bookmark, Integer>{
    Book findLastBook();
    int findLastPage(Book book);
    Book findAllStartedBooks();
    void deleteAllPreviousBookmarks(Book book);
    void deleteAllBookmarks(Book book);
}

//COMPLETED

