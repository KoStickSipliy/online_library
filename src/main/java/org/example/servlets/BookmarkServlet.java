package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Book;
import org.example.entities.Bookmark;
import org.example.service.BookmarkServiceImpl;
import org.example.service.BookServiceImpl;
import org.example.web.ApiResponse;

import java.io.IOException;
import java.util.List;

/**
 * Сервлет для управления закладками
 */
@WebServlet("/api/bookmarks/*")
public class BookmarkServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final BookmarkServiceImpl bookmarkService = (BookmarkServiceImpl) BookmarkServiceImpl.getInstance();
    private final BookServiceImpl bookService = (BookServiceImpl) BookServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        String bookIdParam = req.getParameter("bookId");

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/bookmarks?bookId={id} - получить все закладки для книги
                if (bookIdParam == null || bookIdParam.isBlank()) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("bookId parameter is required")));
                    return;
                }

                long bookId = Long.parseLong(bookIdParam);
                Book book = bookService.getById(bookId);
                if (book == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Book not found")));
                    return;
                }

                List<Bookmark> bookmarks = bookmarkService.findAllBookmarksInBook(book);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.success(bookmarks, "Bookmarks retrieved successfully")));
            } else if (pathInfo.startsWith("/")) {
                // GET /api/bookmarks/{id} - получить закладку по ID
                long id = Long.parseLong(pathInfo.substring(1));
                Bookmark bookmark = bookmarkService.getById(id);
                if (bookmark != null) {
                    resp.getWriter().write(mapper.writeValueAsString(ApiResponse.success(bookmark, "Bookmark retrieved successfully")));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Bookmark not found")));
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Invalid ID format")));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Error: " + e.getMessage())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            Bookmark bookmark = mapper.readValue(req.getInputStream(), Bookmark.class);

            // Валидация
            if (bookmark == null || bookmark.getBookId() <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Valid bookId is required")));
                return;
            }

            if (bookmark.getPage() < 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("page cannot be negative")));
                return;
            }

            bookmarkService.create(bookmark);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.success("Bookmark created successfully")));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Error creating bookmark: " + e.getMessage())));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Bookmark ID required")));
                return;
            }

            long id = Long.parseLong(pathInfo.substring(1));
            Bookmark bookmark = mapper.readValue(req.getInputStream(), Bookmark.class);

            // Валидация
            if (bookmark == null || bookmark.getBookId() <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Valid bookId is required")));
                return;
            }

            if (bookmark.getPage() < 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("page cannot be negative")));
                return;
            }

            bookmarkService.update(id, bookmark);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.success("Bookmark updated successfully")));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Invalid bookmark ID format")));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Error updating bookmark: " + e.getMessage())));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Bookmark ID required")));
                return;
            }

            long id = Long.parseLong(pathInfo.substring(1));
            bookmarkService.deleteById(id);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.success("Bookmark deleted successfully")));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Invalid bookmark ID format")));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Error deleting bookmark: " + e.getMessage())));
        }
    }
}
