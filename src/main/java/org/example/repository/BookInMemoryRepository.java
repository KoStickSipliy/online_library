package org.example.repository;

import org.example.entities.Book;
import org.example.utils.Autoincrement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BookInMemoryRepository implements BookRepository {
    private static final BookInMemoryRepository repo = new BookInMemoryRepository();
    private final List<Book> storage = new ArrayList<>();
    private final Autoincrement idGenerator = new Autoincrement();

    private BookInMemoryRepository() {}

    public static BookRepository getInstance() {
        return repo;
    }

    @Override
    public void create(Book object) {
        if (object.getId() == 0) {
            object.setId(idGenerator.increment());
        }
        storage.add(object);
    } //*

    @Override
    public void deleteAll() {
        storage.clear();
    } //*

    @Override
    public void deleteById(long id) {
        storage.remove(this.getById(id));
    }

    @Override
    public void update(long id, Book newObject) {
        for (int i =0; i<storage.size(); i++) {
            if (storage.get(i).getId() == newObject.getId()) {
                storage.set(i, newObject);
                return;
            }
        }
    }//*

    @Override
    public Book getById(long id) {
        Book found = null;
        for (Book book : storage) {
            if (book.getId() == id) {
                found = book;
                break;
            }
        }
        return found;
    }//*

    @Override
    public List<Book> getAll() {
        return List.copyOf(storage);
    } //*

    @Override
    public List<Book> findAllById(Collection<Long> ids) {
        List<Book> booklist = null;
        for (Book book : storage) {
            if (ids.contains(book.getId())){
                booklist.add(book);
            }
        }
        return booklist;
    } //*

}
