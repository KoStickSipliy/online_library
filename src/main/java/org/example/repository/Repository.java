package org.example.repository;

import java.util.List;

public interface Repository<T, Integer> {
    void create(T object);
    void deleteAll();
    void deleteById(int id);
    void update(int id, T newObject);
    T getById(int id);
    List<T> getAll();
}
