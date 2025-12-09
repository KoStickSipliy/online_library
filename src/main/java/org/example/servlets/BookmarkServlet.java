package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Book;
import org.example.entities.Bookmark;
import org.example.service.BookmarkServiceImpl;
import org.example.service.BookServiceImpl;
import org.example.utils.DateUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Сервлет для управления закладками
 */
@WebServlet("/api/bookmarks/*")
public class BookmarkServlet extends HttpServlet {
    private final BookmarkServiceImpl bookmarkService = (BookmarkServiceImpl) BookmarkServiceImpl.getInstance();
    private final BookServiceImpl bookService = (BookServiceImpl) BookServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        String bookIdParam = req.getParameter("bookId");

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/bookmarks - получить все закладки
                // GET /api/bookmarks?bookId={id} - получить все закладки для конкретной книги
                if (bookIdParam == null || bookIdParam.isBlank()) {
                    // Получить все закладки
                    List<Bookmark> allBookmarks = bookmarkService.getAll();
                    StringBuilder sb = new StringBuilder();
                    for (Bookmark bm : allBookmarks) {
                        sb.append("id=").append(bm.getId())
                                .append(", bookId=").append(bm.getBookId())
                                .append(", page=").append(bm.getPage())
                                .append(", date=").append(bm.getDate() == null ? "" : bm.getDate().format(DateUtils.formatter))
                                .append("\n");
                    }
                    resp.getWriter().write(sb.toString());
                } else {
                    // Получить закладки для конкретной книги
                    long bookId = Long.parseLong(bookIdParam);
                    Book book = bookService.getById(bookId);
                    if (book == null) {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        resp.getWriter().write("Book not found");
                        return;
                    }

                    List<Bookmark> bookmarks = bookmarkService.findAllBookmarksInBook(book);
                    StringBuilder sb = new StringBuilder();
                    for (Bookmark bm : bookmarks) {
                        sb.append("id=").append(bm.getId())
                                .append(", bookId=").append(bm.getBookId())
                                .append(", page=").append(bm.getPage())
                                .append(", date=").append(bm.getDate() == null ? "" : bm.getDate().format(DateUtils.formatter))
                                .append("\n");
                    }
                    resp.getWriter().write(sb.toString());
                }
            } else if (pathInfo.startsWith("/")) {
                // GET /api/bookmarks/{id} - получить закладку по ID
                long id = Long.parseLong(pathInfo.substring(1));
                Bookmark bookmark = bookmarkService.getById(id);
                if (bookmark != null) {
                    resp.getWriter().write("id=" + bookmark.getId() + ", bookId=" + bookmark.getBookId() + ", page=" + bookmark.getPage() + ", date=" + (bookmark.getDate() == null ? "" : bookmark.getDate().format(DateUtils.formatter)));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Bookmark not found");
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            long bookId = Long.parseLong(req.getParameter("bookId"));
            int page = Integer.parseInt(req.getParameter("page"));
            String dateStr = req.getParameter("date");
            LocalDate date = (dateStr == null || dateStr.isBlank()) ? LocalDate.now() : LocalDate.parse(dateStr, DateUtils.formatter);
            Bookmark bookmark = new Bookmark(bookId, page, date);

            // Валидация
            if (bookmark.getBookId() <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Valid bookId is required");
                return;
            }

            if (bookmark.getPage() < 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("page cannot be negative");
                return;
            }

            bookmarkService.create(bookmark);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Bookmark created successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error creating bookmark: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Bookmark ID required");
                return;
            }

            long id = Long.parseLong(pathInfo.substring(1));
            long bookId = Long.parseLong(req.getParameter("bookId"));
            int page = Integer.parseInt(req.getParameter("page"));
            String dateStr = req.getParameter("date");
            LocalDate date = (dateStr == null || dateStr.isBlank()) ? LocalDate.now() : LocalDate.parse(dateStr, DateUtils.formatter);
            Bookmark bookmark = new Bookmark(bookId, page, date);

            // Валидация
            if (bookmark.getBookId() <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Valid bookId is required");
                return;
            }

            if (bookmark.getPage() < 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("page cannot be negative");
                return;
            }

            bookmarkService.update(id, bookmark);
            resp.getWriter().write("Bookmark updated successfully");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid bookmark ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error updating bookmark: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Bookmark ID required");
                return;
            }

            long id = Long.parseLong(pathInfo.substring(1));
            bookmarkService.deleteById(id);
            resp.getWriter().write("Bookmark deleted successfully");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid bookmark ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error deleting bookmark: " + e.getMessage());
        }
    }
}
