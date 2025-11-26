package org.example.repository;

import lombok.Getter;
import org.example.DBManager.PostgreSQLManager;
import org.example.Exception.NoEntityException;
import org.example.IO.IO;
import org.example.entities.Bookmark;

import java.sql.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookmarkDBRepository implements BookmarkRepository {
    @Getter(lazy = true)
    private static final BookmarkDBRepository instance = new BookmarkDBRepository();

    private Statement statement;
    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement findLastBookmarkInBookStatement;
    private PreparedStatement findLastPageInBookStatement;
    private PreparedStatement deleteAllPreviousBookmarksStatement;
    private PreparedStatement findAllBookmarksInBookStatement;

    private BookmarkDBRepository () {
        Connection connection = PostgreSQLManager.getInstance().getConnection();

        try {
            statement = connection.createStatement();
            createStatement = connection.prepareStatement("INSERT INTO bookmark (book_id, page, date) VALUES (?, ?, ?)");
            deleteByIdStatement = connection.prepareStatement("DELETE FROM bookmark WHERE id = ?");
            updateStatement = connection.prepareStatement("UPDATE bookmark SET book_id = ?, page = ?, date = ? WHERE id = ?");
            getByIdStatement = connection.prepareStatement("SELECT * FROM bookmark WHERE id = ?");

        findLastBookmarkInBookStatement = connection.prepareStatement(
            "SELECT * FROM bookmark WHERE book_id = ? ORDER BY date DESC LIMIT 1"
        );

        findLastPageInBookStatement = connection.prepareStatement(
            "SELECT page FROM bookmark WHERE book_id = ? ORDER BY date DESC LIMIT 1"
        );

        deleteAllPreviousBookmarksStatement = connection.prepareStatement(
            "DELETE FROM bookmark WHERE book_id = ? AND id NOT IN (" +
                "SELECT id FROM bookmark WHERE book_id = ? ORDER BY page DESC LIMIT 1)"
        );
        findAllBookmarksInBookStatement = connection.prepareStatement("SELECT * FROM bookmark WHERE book_id = ? ORDER BY date DESC");
        } catch (SQLException e) {
            IO.printError("Exception while preparing statements: " + e.getMessage());
            throw new RuntimeException("Failed to prepare BookmarkDBRepository statements", e);
        }
    }

    @Override
    public Bookmark findLastBookmarkInBook(long bookId) {
        try {
            findLastBookmarkInBookStatement.setLong(1, bookId);
            try (ResultSet result = findLastBookmarkInBookStatement.executeQuery()) {
                List<Bookmark> bookmarks = extractBookmark(result);
                return bookmarks.isEmpty() ? null : bookmarks.get(0);
            }
        } catch (SQLException e) {
            IO.printError("Error finding last bookmark in book " + bookId + ": " + e.getMessage());
            throw new RuntimeException("Failed to find last bookmark in book " + bookId, e);
        }
    }

    @Override
    public int findLastPageInBook(long bookId) {
        try {
            findLastPageInBookStatement.setLong(1, bookId);
            try (ResultSet result = findLastPageInBookStatement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("page");
                }
                return 0;
            }
        } catch (SQLException e) {
            IO.printError("Error finding last page in book " + bookId + ": " + e.getMessage());
            throw new RuntimeException("Failed to find last page in book " + bookId, e);
        }
    }

    @Override
    public long findLastReadBookId() {
        try (ResultSet result = statement.executeQuery("SELECT bookId FROM bookmark ORDER BY date DESC LIMIT 1")) {
            if (result.next()) {
                return result.getLong("bookId");
            }
            return 0;
        } catch (SQLException e) {
            IO.printError("Error finding last read book ID: " + e.getMessage());
            throw new RuntimeException("Failed to find last read book id", e);
        }
    }

    @Override
    public List<Long> findAllStartedBookIds() {
        List<Long> bookIds = new ArrayList<>();
        try (ResultSet result = statement.executeQuery("SELECT DISTINCT bookId FROM bookmark")) {
            while (result.next()) {
                bookIds.add(result.getLong("bookId"));
            }
            return bookIds;
        } catch (SQLException e) {
            IO.printError("Error finding all started book IDs: " + e.getMessage());
            throw new RuntimeException("Failed to find all started book ids", e);
        }
    }

    @Override
    public List<Bookmark> findAllBookmarksInBook(long bookId) {
        try {
            findAllBookmarksInBookStatement.setLong(1, bookId);
            try (ResultSet result = findAllBookmarksInBookStatement.executeQuery()) {
                return extractBookmark(result);
            }
        } catch (SQLException e) {
            IO.printError("Error finding all bookmarks in book " + bookId + ": " + e.getMessage());
            throw new RuntimeException("Failed to find all bookmarks in book " + bookId, e);
        }
    }

    @Override
    public void deleteAllPreviousBookmarks(long bookId) {
        try {
            deleteAllPreviousBookmarksStatement.setLong(1, bookId);
            deleteAllPreviousBookmarksStatement.setLong(2, bookId);
            deleteAllPreviousBookmarksStatement.executeUpdate();
        } catch (SQLException e) {
            IO.printError("Error deleting previous bookmarks for book " + bookId + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete previous bookmarks for book " + bookId, e);
        }
    }

//    @Override
//    public List<Bookmark> findAllById(Collection<Long> ids) {
//        String stringIds = String.join(",", ids.stream().map(String::valueOf).toArray(String[]::new));
//        if (stringIds.isEmpty()) {
//            return List.of();
//        }
//
//        try (ResultSet result = statement.executeQuery(
//                ("SELECT * FROM bookmark where id IN (%s)").formatted(stringIds)
//        )) {
//            return extractBookmark(result);
//        } catch (SQLException e) {
//            IO.printError("Error finding bookmarks by IDs: " + e.getMessage());
//            return List.of();
//        }
//    }

    @Override
    public void create(Bookmark object) {
        try {
            createStatement.setLong(1, object.getBookId());
            createStatement.setInt(2, object.getPage());
            createStatement.setDate(3, Date.valueOf(object.getDate()));
            createStatement.executeUpdate();
        } catch (SQLException e) {
            IO.printError("Error creating bookmark: " + e.getMessage());
            throw new RuntimeException("Failed to create bookmark", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            statement.executeUpdate("DELETE FROM bookmark");
        } catch (SQLException e) {
            IO.printError("Error deleting all bookmarks: " + e.getMessage());
            throw new RuntimeException("Failed to delete all bookmarks", e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            if (deletedRows == 0) {
                throw new NoEntityException("Bookmark", String.valueOf(id));
            }
        } catch (SQLException e) {
            IO.printError("Error deleting bookmark by ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete bookmark by id " + id, e);
        }
    }

    @Override
    public void update(long id, Bookmark newObject) {
        try {
            updateStatement.setLong(1, newObject.getBookId());
            updateStatement.setInt(2, newObject.getPage());
            updateStatement.setDate(3, Date.valueOf(newObject.getDate()));
            updateStatement.setLong(4, id);
            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                throw new NoEntityException("Bookmark", String.valueOf(id));
            }
        } catch (SQLException e) {
            IO.printError("Error updating bookmark " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to update bookmark " + id, e);
        }
    }

    @Override
    public Bookmark getById(long id) {
        try {
            getByIdStatement.setLong(1, id);
            try (ResultSet result = getByIdStatement.executeQuery()) {
                List<Bookmark> bookmarks = extractBookmark(result);
                if (bookmarks.isEmpty()) {
                    throw new NoEntityException("Bookmark", String.valueOf(id));
                }
                return bookmarks.get(0);
            }
        } catch (SQLException e) {
            IO.printError("Error getting bookmark by ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to get bookmark by id " + id, e);
        }
    }

    @Override
    public List<Bookmark> getAll() {
        try (ResultSet result = statement.executeQuery("SELECT * FROM bookmark")) {
            return extractBookmark(result);
        } catch (SQLException e) {
            IO.printError("Error getting all bookmarks: " + e.getMessage());
            throw new RuntimeException("Failed to get all bookmarks", e);
        }
    }

    private List<Bookmark> extractBookmark(ResultSet result) {
        List<Bookmark> bookmarkList = new ArrayList<>();
        try {
            while (result.next()) {
        java.sql.Date sqlDate = result.getDate("date");
        java.time.LocalDate localDate = sqlDate == null ? null : sqlDate.toLocalDate();
        bookmarkList.add(new Bookmark(
            result.getLong("id"),
            result.getLong("book_id"),
            result.getInt("page"),
            localDate
        ));
            }
        } catch (SQLException e) {
            IO.printError("Exception while extracting bookmarks: " + e.getMessage());
            throw new RuntimeException("Failed to extract bookmarks", e);
        }
        return bookmarkList;
    }
}
