package org.example.repository;

import org.example.entities.Book;
import org.example.entities.Bookmark;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BookmarkRepository extends Repository<Bookmark, Integer>{
    int findLastPage(long bookId);
    long findLastBookId();
    List<Long> findAllStartedBookIds();
    void deleteAllPreviousBookmarks(long bookId);
    void deleteAllBookmarks(long bookId);
}

//COMPLETED

