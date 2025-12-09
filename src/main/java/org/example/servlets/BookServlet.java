package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Book;
import org.example.service.BookServiceImpl;

import java.io.IOException;
import java.util.List;

/**
 * Сервлет для управления книгами
 */
@WebServlet("/api/books/*")
public class BookServlet extends HttpServlet {
    private final BookServiceImpl bookService = (BookServiceImpl) BookServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/books - получить все книги
                List<Book> books = bookService.getAll();
                StringBuilder sb = new StringBuilder();
                for (Book b : books) {
                    sb.append("id=").append(b.getId())
                            .append(", name=").append(b.getName())
                            .append(", path=").append(b.getPath())
                            .append("\n");
                }
                resp.getWriter().write(sb.toString());
            } else if (pathInfo.startsWith("/")) {
                // GET /api/books/{id} - получить книгу по ID
                long id = Long.parseLong(pathInfo.substring(1));
                Book book = bookService.getById(id);
                if (book != null) {
                    resp.getWriter().write("id=" + book.getId() + ", name=" + book.getName() + ", path=" + book.getPath());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Book not found");
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid book ID format");
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
            String name = req.getParameter("name");
            String path = req.getParameter("path");
            Book book = new Book(name, path);

            // Валидация
            if (book.getName() == null || book.getName().isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Book name cannot be empty");
                return;
            }

            if (book.getPath() == null || book.getPath().isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Book path cannot be empty");
                return;
            }

            bookService.create(book);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Book created successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error creating book: " + e.getMessage());
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
                resp.getWriter().write("Book ID required");
                return;
            }

            long id = Long.parseLong(pathInfo.substring(1));
            String name = req.getParameter("name");
            String path = req.getParameter("path");
            Book book = new Book(name, path);

            // Валидация
            if (book.getName() == null || book.getName().isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Book name cannot be empty");
                return;
            }

            if (book.getPath() == null || book.getPath().isBlank()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Book path cannot be empty");
                return;
            }

            bookService.update(id, book);
            resp.getWriter().write("Book updated successfully");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid book ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error updating book: " + e.getMessage());
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
                resp.getWriter().write("Book ID required");
                return;
            }

            long id = Long.parseLong(pathInfo.substring(1));
            bookService.deleteById(id);
            resp.getWriter().write("Book deleted successfully");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid book ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error deleting book: " + e.getMessage());
        }
    }
}
