package org.example.repository;

import lombok.Getter;
import org.example.DBManager.PostgreSQLManager;
import org.example.IO.IO;
import org.example.entities.Book;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class BookDBRepository implements BookRepository {
    @Getter
    private static final BookDBRepository instance = new BookDBRepository();

    private Statement statement;
    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement getByIdStatement;

    private BookDBRepository () {
        Connection connection = PostgreSQLManager.getInstance().getConnection();

        try {
            statement = connection.createStatement();
            createStatement = connection.prepareStatement("INSERT INTO book (name, path) VALUES (?, ?)");
            deleteByIdStatement = connection.prepareStatement("DELETE FROM book WHERE id = ?");
            updateStatement = connection.prepareStatement("UPDATE book SET name = ?, path = ? WHERE id = ?");
            getByIdStatement = connection.prepareStatement("SELECT * FROM book WHERE id = ?");
        } catch (SQLException e) {
            IO.printError("Exception while preparing statements: " + e.getMessage());
        }
    }
    @Override
    public List<Book> findAllById(Collection<Long> ids) {
        return null;
    }

    @Override
    public void create(Book object) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void update(long id, Book newObject) {

    }

    @Override
    public Book getById(long id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }
}
