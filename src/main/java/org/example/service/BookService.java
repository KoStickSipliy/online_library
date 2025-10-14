package org.example.service;

import org.example.entities.Book;
import org.example.entities.Bookmark;

import java.util.List;

public interface BookService extends Service<Bookmark, Integer> {
    List<Book> findByName (String name);
    List<Book> findByPath (String path);
    List<Book> findAllStartedBooks ();
    Book findLastBook ();
    Book findByBookmark(Bookmark bookmark);

}
