package org.example.repository;

import org.example.entities.Bookmark;

import java.util.List;

public class BookmarkDBRepository implements BookmarkRepository {
    @Override
    public Bookmark findLastBookmarkInBook(long bookId) {
        return null;
    }

    @Override
    public int findLastPageInBook(long bookId) {
        return 0;
    }

    @Override
    public long findLastBookId() {
        return 0;
    }

    @Override
    public List<Long> findAllStartedBookIds() {
        return null;
    }

    @Override
    public List<Bookmark> findAllBookmarksInBook(long bookId) {
        return null;
    }

    @Override
    public void deleteAllPreviousBookmarks(long bookId) {

    }

    @Override
    public void create(Bookmark object) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void update(long id, Bookmark newObject) {

    }

    @Override
    public Bookmark getById(long id) {
        return null;
    }

    @Override
    public List<Bookmark> getAll() {
        return null;
    }
}
