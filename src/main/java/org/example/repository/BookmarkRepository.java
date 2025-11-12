package org.example.repository;

import org.example.entities.Bookmark;

import java.util.List;

public interface BookmarkRepository extends Repository<Bookmark, Integer>{
    Bookmark findLastBookmarkInBook(long bookId);
    int findLastPageInBook(long bookId);
    long findLastReadBookId();
    List<Long> findAllStartedBookIds();
    List<Bookmark> findAllBookmarksInBook(long bookId);

    void deleteAllPreviousBookmarks(long bookId);
}

//COMPLETED

