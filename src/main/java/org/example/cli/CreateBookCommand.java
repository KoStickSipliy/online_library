package org.example.cli;

import org.example.IO.IO;
import org.example.entities.Book;
import org.example.service.BookServiceImpl;

public class CreateBookCommand implements Command{
    @Override
    public String getCommandName() {
        return "Add new book";
    }

    @Override
    public void execute() {
        String name = IO.readLine("Input title:");
        String path = IO.readLine("Input path:");
        if (name == null || name.isBlank()) {
            IO.printError("Title cannot be empty");
            return;
        }
        if (path == null || path.isBlank()) {
            IO.printError("Path cannot be empty");
            return;
        }

        try {
            BookServiceImpl.getInstance().create(new Book(name, path));
            IO.print("Book was successfully added");
        } catch (RuntimeException e) {
            IO.printError("Failed to create book: " + e.getMessage());
        }
    }
}
