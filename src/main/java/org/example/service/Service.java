package org.example.service;

import java.util.List;

public interface Service<T, ID> {
    void create(T object);
    T getById(long id);
    List<T> getAll();
    void update(long id, T newObject);
    void deleteAll();
    void deleteById(long id);
}
