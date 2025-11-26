package org.example.service;

import org.example.Exception.NoEntityException;
import org.example.IO.IO;
import org.example.entities.Book;
import org.example.repository.*;
import org.example.entities.Bookmark;

import java.util.List;

public class BookmarkServiceImpl implements BookmarkService {
    private static BookmarkService obj;
    private final BookRepository bookRepo = BookDBRepository.getInstance();
    private final BookmarkRepository bookmarkRepo = BookmarkDBRepository.getInstance();

    public static BookmarkService getInstance() {
        if (obj == null) {
            obj = new BookmarkServiceImpl();
        }
        return obj;
    }

    @Override
    public List<Bookmark> findAllBookmarksInBook(Book book) {
        try {
            return bookmarkRepo.findAllBookmarksInBook(book.getId());
        } catch (Exception e) {
            IO.printError("Error finding bookmarks in book " + book.getId() + ": " + e.getMessage());
            throw new RuntimeException("Failed to find bookmarks in book", e);
        }
    }

    @Override
    public Bookmark findLastBookmarkInBook(Book book) {
        try {
            Bookmark bookmark = bookmarkRepo.findLastBookmarkInBook(book.getId());
            if (bookmark == null) {
                throw new NoEntityException("Bookmark for book", String.valueOf(book.getId()));
            }
            return bookmark;
        } catch (Exception e) {
            IO.printError("Error finding last bookmark in book " + book.getId() + ": " + e.getMessage());
            throw new RuntimeException("Failed to find last bookmark in book", e);
        }
    }

    @Override
    public int findLastPage(Book book) {
        try {
            int lastPage = bookmarkRepo.findLastPageInBook(book.getId());
            if (lastPage == 0) {
                throw new NoEntityException("Bookmark for book", String.valueOf(book.getId()));
            }
            return lastPage;
        } catch (Exception e) {
            IO.printError("Error finding last page in book " + book.getId() + ": " + e.getMessage());
            throw new RuntimeException("Failed to find last page in book", e);
        }
    }

    @Override
    public void deleteAllPreviousBookmarks(Book book) {
        try {
            bookmarkRepo.deleteAllPreviousBookmarks(book.getId());
        } catch (Exception e) {
            IO.printError("Error deleting previous bookmarks for book " + book.getId() + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete previous bookmarks", e);
        }
    }

    @Override
    public void create(Bookmark object) {
        try {
            bookRepo.getById(object.getBookId());
            bookmarkRepo.create(object);
        } catch (NoEntityException e) {
            String errorMsg = "Cannot create bookmark: book with id " + object.getBookId() + " does not exist";
            IO.printError(errorMsg);
            throw new IllegalArgumentException(errorMsg, e);
        } catch (RuntimeException e) {
            String errorMsg = "Error checking book existence: " + e.getMessage();
            IO.printError(errorMsg);
            throw new RuntimeException("Database error while checking book existence", e);
        } catch (Exception e) {
            IO.printError("Error creating bookmark: " + e.getMessage());
            throw new RuntimeException("Failed to create bookmark", e);
        }
    }

    @Override
    public Bookmark getById(long id) {
        try {
            return bookmarkRepo.getById(id);
        } catch (NoEntityException e) {
            String errorMsg = "Bookmark with id " + id + " not found";
            IO.printError(errorMsg);
            throw new NoEntityException("Bookmark", String.valueOf(id), e.getMessage());
        } catch (Exception e) {
            IO.printError("Error getting bookmark by id " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to get bookmark by id", e);
        }
    }

    @Override
    public List<Bookmark> getAll() {
        try {
            return bookmarkRepo.getAll();
        } catch (Exception e) {
            IO.printError("Error getting all bookmarks: " + e.getMessage());
            throw new RuntimeException("Failed to get all bookmarks", e);
        }
    }

    @Override
    public void update(long id, Bookmark newObject) {
        try {
            Bookmark existingBookmark = bookmarkRepo.getById(id);

            if (existingBookmark.getBookId() != newObject.getBookId()) {
                try {
                    bookRepo.getById(newObject.getBookId());
                } catch (NoEntityException e) {
                    String errorMsg = "Cannot update bookmark: book with id " + newObject.getBookId() + " does not exist";
                    IO.printError(errorMsg);
                    throw new IllegalArgumentException(errorMsg, e);
                }
            }

            bookmarkRepo.update(id, newObject);
        } catch (NoEntityException e) {
            String errorMsg = "Cannot update bookmark: bookmark with id " + id + " does not exist";
            IO.printError(errorMsg);
            throw new NoEntityException("Bookmark", String.valueOf(id), e.getMessage());
        } catch (IllegalArgumentException e) {
            throw e; // Пробрасываем уже обработанные исключения дальше
        } catch (Exception e) {
            IO.printError("Error updating bookmark " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to update bookmark", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            bookmarkRepo.deleteAll();
        } catch (Exception e) {
            IO.printError("Error deleting all bookmarks: " + e.getMessage());
            throw new RuntimeException("Failed to delete all bookmarks", e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            bookmarkRepo.deleteById(id);
        } catch (NoEntityException e) {
            String errorMsg = "Cannot delete bookmark: bookmark with id " + id + " does not exist";
            IO.printError(errorMsg);
            throw new NoEntityException("Bookmark", String.valueOf(id), e.getMessage());
        } catch (Exception e) {
            IO.printError("Error deleting bookmark " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete bookmark", e);
        }
    }
}