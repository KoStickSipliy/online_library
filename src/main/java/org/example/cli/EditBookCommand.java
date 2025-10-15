package org.example.cli;

import org.example.IO.IO;
import org.example.entities.Book;
import org.example.service.BookServiceImpl;

public class EditBookCommand implements Command{
    @Override
    public String getCommandName() {
        return "Edit book";
    }

    @Override
    public void execute() {
        long bookID = Long.parseLong(IO.readLine("Input book ID:"));
        Book book = BookServiceImpl.getInstance().getById(bookID);
        String title = IO.readLine("Input new title or skip:");
        String path = IO.readLine("Input new path or skip:");
        BookServiceImpl.getInstance().update(bookID, new Book(
                title.isBlank() ? book.getName() : title,
                path.isBlank() ? book.getPath() : title
                )
        );
    }
}
