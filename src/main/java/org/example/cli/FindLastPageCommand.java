package org.example.cli;

import org.example.IO.IO;
import org.example.entities.Book;
import org.example.service.BookServiceImpl;
import org.example.service.BookmarkServiceImpl;

public class FindLastPageCommand implements Command {
    @Override
    public String getCommandName() {
        return "Find last page in book";
    }

    @Override
    public void execute() {
        String bookIdInput = IO.readLine("Input book ID:");

        long bookId;
        try {
            bookId = Long.parseLong(bookIdInput.trim());
        } catch (Exception e) {
            IO.printError("Invalid book ID");
            return;
        }

        try {
            Book book = BookServiceImpl.getInstance().getById(bookId);
            if (book == null) {
                IO.print("This book does not exist");
                return;
            }

            try {
                int lastPage = BookmarkServiceImpl.getInstance().findLastPage(book);
                IO.print("Last page with bookmark: " + lastPage);
            } catch (RuntimeException ex) {
                IO.printError("Failed to find last page: " + ex.getMessage());
            }
        } catch (RuntimeException e) {
            IO.printError("Error while checking book existence: " + e.getMessage());
        }
    }
}
