package org.example.cli;

import org.example.IO.IO;
import org.example.service.BookServiceImpl;

public class DeleteBookCommand implements Command{
    @Override
    public String getCommandName() {
        return "Delete book";
    }

    @Override
    public void execute() {
        Long bookId = IO.readLongSafe("Input book ID:");
        if (bookId == null) {
            IO.printError("Invalid book ID");
            return;
        }
        try {
            BookServiceImpl.getInstance().deleteById(bookId);
            IO.print("Book was successfully deleted");
        } catch (RuntimeException e) {
            IO.printError("Failed to delete book: " + e.getMessage());
        }
    }
}
