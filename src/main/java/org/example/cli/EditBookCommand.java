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
        Long bookID = IO.readLongSafe("Input book ID:");
        if (bookID == null) {
            IO.printError("Invalid book ID");
            return;
        }
        Book book = BookServiceImpl.getInstance().getById(bookID);
        if (book == null) {
            IO.print("This book does not exist");
            return;
        }
        String title = IO.readLine("Input new title or skip:");
        String path = IO.readLine("Input new path or skip:");
        try {
            BookServiceImpl.getInstance().update(bookID, new Book(
                    title.isBlank() ? book.getName() : title,
                    path.isBlank() ? book.getPath() : path
            ));
            IO.print("Book updated");
        } catch (RuntimeException e) {
            IO.printError("Failed to update book: " + e.getMessage());
        }
    }
}
