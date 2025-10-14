package org.example.repository;

import org.example.entities.Book;
import org.example.entities.Bookmark;
import org.example.utils.Autoincrement;

import java.util.ArrayList;
import java.util.List;

public class BookmarkInMemoryRepository implements BookmarkRepository{
    private static final BookmarkInMemoryRepository repo = new BookmarkInMemoryRepository();
    private final List<Bookmark> storage = new ArrayList<>();
    private final Autoincrement idGenerator = new Autoincrement();

    public BookmarkInMemoryRepository() {}

    @Override
    public Book findLastBook() {
        return null;
    }

    @Override
    public int findLastPage(Book book) {
        return 0;
    }

    @Override
    public Book findAllStartedBooks() {
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
        if (object.getId() == 0) {
            object.setId(idGenerator.increment());
        }
        storage.add(object);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public void deleteById(int id) {
        storage.remove(this.getById(id));
    }

    @Override
    public void update(int id, Bookmark newObject) {
        for (int i =0; i<storage.size(); i++) {
            if (storage.get(i).getId() == newObject.getId()) {
                storage.set(i, newObject);
                return;
            }
        }
    }

    @Override
    public Bookmark getById(int id) {
        Bookmark found = null;
        for (Bookmark bm : storage) {
            if (bm.getId() == id) {
                found = bm;
                break;
            }
        }
        return found;
    }

    @Override
    public List<Bookmark> getAll() {
        return List.copyOf(storage);
    }
}
