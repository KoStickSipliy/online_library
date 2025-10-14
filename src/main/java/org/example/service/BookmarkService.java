package org.example.service;

import org.example.entities.Book;
import org.example.entities.Bookmark;

import java.util.List;

public interface BookmarkService extends Service<Bookmark, Long>{
    List<Bookmark> findBookmarksInBook(Book book);
    int findLastPage(Book book);
    Book findLastBook();
    List<Book> findAllStartedBooks();

    void deleteAllPreviousBookmarks(Book book);
    void deleteAllBookmarks(Book book);
}
