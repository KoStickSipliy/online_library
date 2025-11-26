package org.example.repository;

import java.util.List;

public interface Repository<T, ID> {
    void create(T object);
    void deleteAll();
    void deleteById(long id);
    void update(long id, T newObject);
    T getById(long id);
    List<T> getAll();
}
