package org.example.repository;

import lombok.Getter;
import org.example.DBManager.PostgreSQLManager;
import org.example.Exception.NoEntityException;
import org.example.IO.IO;
import org.example.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookDBRepository implements BookRepository {
    @Getter(lazy = true)
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
            throw new RuntimeException("Failed to prepare BookDBRepository statements", e);
        }
    }
    @Override
    public List<Book> findAllById(Collection<Long> ids) {
        String stringIds = String.join(",", ids.stream().map(String::valueOf).toArray(String[]::new));
        if (stringIds.isEmpty()) {return List.of();}

        try (ResultSet result = statement.executeQuery(
                ("SELECT * FROM book where id IN (%s)").formatted(stringIds)
        )) { return extractBook(result);
        } catch (SQLException e) {
            IO.printError(e.getMessage());
            throw new RuntimeException("Failed to find books by ids", e);
        }
    }

    @Override
    public void create(Book object) {
        try {
            createStatement.setString(1, object.getName());
            createStatement.setString(2, object.getPath());
            createStatement.executeUpdate();
        } catch (SQLException e) {
            IO.printError(e.getMessage());
            throw new RuntimeException("Failed to create book", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            statement.executeUpdate("DELETE FROM book");
        } catch (SQLException e) {
            IO.printError(e.getMessage());
            throw new RuntimeException("Failed to delete all books", e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            if (deletedRows == 0) {
//                IO.printError(("Book delete exception: no entity (id: %d)").formatted(id));
                throw new NoEntityException("Book", String.valueOf(id));
            }
        } catch (SQLException e) {
            IO.printError(e.getMessage());
            throw new RuntimeException("Failed to delete book by id " + id, e);
        }
    }

    @Override
    public void update(long id, Book newObject) {
        try {
            updateStatement.setString(1, newObject.getName());
            updateStatement.setString(2, newObject.getPath());
            updateStatement.setLong(3, id);
            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                throw new NoEntityException("Book", String.valueOf(id));
            }
        } catch (SQLException e) {
            IO.printError(e.getMessage());
            throw new RuntimeException("Failed to update book " + id, e);
        }
    }

    @Override
    public Book getById(long id) {
        try {
            getByIdStatement.setLong(1, id);
            try (ResultSet result = getByIdStatement.executeQuery()) {
                List<Book> bookResultSet = extractBook(result);
                if (bookResultSet.isEmpty()) {
                    throw new NoEntityException("Book", String.valueOf(id));
                }
                return bookResultSet.iterator().next();
            }
        } catch (SQLException e) {
            IO.printError("Error getting book by id " + id + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> getAll() {
        try (ResultSet result = statement.executeQuery("SELECT * FROM book")) {
            return extractBook(result);
        } catch (SQLException e) {
            IO.printError(e.getMessage());
            throw new RuntimeException("Failed to get all books", e);
        }
    }

    private List<Book> extractBook(ResultSet result) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        while (result.next()) {
            bookList.add(new Book(
                    result.getLong("id"),
                    result.getString("name"),
                    result.getString("path")
            ));
        }
        return bookList;
    }
}
