package org.example.service;

import org.example.entities.Book;
import org.example.entities.Bookmark;

import java.util.List;

public interface BookmarkService extends Service<Bookmark, Long>{
    List<Bookmark> findAllBookmarksInBook(Book book);
    Bookmark findLastBookmarkInBook(Book book);
    int findLastPage(Book book);
    void deleteAllPreviousBookmarks(Book book);
}
