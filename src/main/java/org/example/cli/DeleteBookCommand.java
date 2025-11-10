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
        long bookId = Long.parseLong(IO.readLine("Input book ID:"));
        BookServiceImpl.getInstance().deleteById(bookId);
        IO.print("Book was successfully deleted");
    }
}
