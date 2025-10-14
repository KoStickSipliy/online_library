package org.example.repository;

import org.example.entities.Book;
import org.example.entities.Bookmark;
import org.example.utils.Autoincrement;

import java.util.ArrayList;
import java.util.List;

// LIMITED FUNCTIONALITY
public class BookmarkInMemoryRepository implements BookmarkRepository{
    private static final BookmarkInMemoryRepository repo = new BookmarkInMemoryRepository();
    private final List<Bookmark> storage = new ArrayList<>();
    private final Autoincrement idGenerator = new Autoincrement();

    public BookmarkInMemoryRepository() {}

    public static BookmarkRepository getInstance() {
        return repo;
    }

    @Override
    public int findLastPage(long bookId) {
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
    public void deleteAllBookmarks(long bookId) {

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
    public void deleteById(long id) {
        storage.remove(this.getById(id));
    }

    @Override
    public void update(long id, Bookmark newObject) {
        for (int i =0; i<storage.size(); i++) {
            if (storage.get(i).getId() == newObject.getId()) {
                storage.set(i, newObject);
                return;
            }
        }
    }

    @Override
    public Bookmark getById(long id) {
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
