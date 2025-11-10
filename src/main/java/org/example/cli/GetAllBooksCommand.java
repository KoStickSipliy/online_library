package org.example.cli;

import org.example.IO.IO;
import org.example.entities.Book;
import org.example.service.BookServiceImpl;

import java.util.List;

public class GetAllBooksCommand implements Command{
    @Override
    public String getCommandName() {
        return "Get all books";
    }

    @Override
    public void execute() {
        List<Book> books = BookServiceImpl.getInstance().getAll();
        if (books.isEmpty()) {
            IO.print("No books found");
            return;
        }
        books.forEach(book -> {
            IO.print("%d. %s (%s)".formatted(
                    book.getId(),
                    book.getName(),
                    book.getPath()
            ));
        });
    }
}
