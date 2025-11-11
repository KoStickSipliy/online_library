package org.example.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T, Integer> {
    void create(T object);
    void deleteAll();
    void deleteById(long id);
    void update(long id, T newObject);
    T getById(long id) throws SQLException;
    List<T> getAll();
}
