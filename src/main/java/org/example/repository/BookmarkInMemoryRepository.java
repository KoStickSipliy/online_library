package org.example.repository;

import org.example.IO.IO;
import org.example.entities.Bookmark;
import org.example.utils.Autoincrement;

import java.time.Year;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Bookmark findLastBookmarkInBook(long bookId) {
        LocalDate lastDate = LocalDate.of(Year.MIN_VALUE, 1, 1);
        int iMax = -1;
        for (int  i = 0; i<storage.size(); i++) {
            if (storage.get(i).getBookId() == bookId) {
                if (storage.get(i).getDate().isAfter(lastDate)){
                    lastDate = storage.get(i).getDate();
                    iMax = i;
                }
            }
        } return storage.get(iMax);
    }

    @Override
    public int findLastPageInBook(long bookId) {
        return findLastBookmarkInBook(bookId).getPage();
    }

    @Override
    public long findLastReadBookId() {
        LocalDate lastDate = LocalDate.of(Year.MIN_VALUE, 1, 1);
        int iMax = -1;
        for (int  i = 0; i<storage.size(); i++) {
            if (storage.get(i).getDate().isAfter(lastDate)){
                lastDate = storage.get(i).getDate();
                iMax = i;
            }
        } return storage.get(iMax).getBookId();
    }

    @Override
    public List<Long> findAllStartedBookIds() {
        Set<Long> bookIds = new HashSet<>();
        for (Bookmark bm : storage) {
            bookIds.add(bm.getBookId());
        }
        return bookIds.stream().toList();
    }

    @Override
    public List<Bookmark> findAllBookmarksInBook(long bookId) {
        List<Bookmark> bms = new ArrayList<>();
        for (Bookmark bm : storage) {
            if (bm.getBookId() == bookId) {
                bms.add(bm);
            }
        }
        return bms;
    }

    @Override
    public void deleteAllPreviousBookmarks(long bookId) {
        IO.printError("deleteAllPreviousBookmarks is not supported");
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
