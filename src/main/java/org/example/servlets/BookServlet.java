package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Book;
import org.example.service.BookServiceImpl;
import org.example.web.ApiResponse;

import java.io.IOException;
import java.util.List;

/**
 * Сервлет для управления книгами
 */
@WebServlet("/api/books/*")
public class BookServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final BookServiceImpl bookService = (BookServiceImpl) BookServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/books - получить все книги
                List<Book> books = bookService.getAll();
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.success(books, "Books retrieved successfully")));
            } else if (pathInfo.startsWith("/")) {
                // GET /api/books/{id} - получить книгу по ID
                long id = Long.parseLong(pathInfo.substring(1));
                Book book = bookService.getById(id);
                if (book != null) {
                    resp.getWriter().write(mapper.writeValueAsString(ApiResponse.success(book, "Book retrieved successfully")));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Book not found")));
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Invalid book ID format")));
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
            // Читаем JSON из body запроса
            Book book = mapper.readValue(req.getInputStream(), Book.class);

            // Валидация
            if (book == null || book.getName() == null || book.getName().isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Book name cannot be empty")));
                return;
            }

            if (book.getPath() == null || book.getPath().isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Book path cannot be empty")));
                return;
            }

            bookService.create(book);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.success("Book created successfully")));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Error creating book: " + e.getMessage())));
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
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Book ID required")));
                return;
            }

            long id = Long.parseLong(pathInfo.substring(1));
            Book book = mapper.readValue(req.getInputStream(), Book.class);

            // Валидация
            if (book == null || book.getName() == null || book.getName().isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Book name cannot be empty")));
                return;
            }

            if (book.getPath() == null || book.getPath().isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Book path cannot be empty")));
                return;
            }

            bookService.update(id, book);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.success("Book updated successfully")));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Invalid book ID format")));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Error updating book: " + e.getMessage())));
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
                resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Book ID required")));
                return;
            }

            long id = Long.parseLong(pathInfo.substring(1));
            bookService.deleteById(id);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.success("Book deleted successfully")));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Invalid book ID format")));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(mapper.writeValueAsString(ApiResponse.error("Error deleting book: " + e.getMessage())));
        }
    }
}
