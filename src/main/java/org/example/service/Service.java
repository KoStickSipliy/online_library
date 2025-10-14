package org.example.service;

import java.util.List;

public interface Service<T, Integer> {
    void create(T object);
    T getById(int id);
    List<T> getAll();
    void update(int id, T newObject);
    void deleteAll();
    void deleteById(int id);
}
