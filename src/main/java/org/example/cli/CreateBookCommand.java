package org.example.cli;

import org.example.IO.IO;
import org.example.entities.Book;
import org.example.service.BookServiceImpl;

import java.time.LocalDate;

public class CreateBookCommand implements Command{
    @Override
    public String getCommandName() {
        return "Add new book";
    }

    @Override
    public void execute() {
        String name = IO.readLine("Input title:");
        String path = IO.readLine("Input path:");

        BookServiceImpl.getInstance().create(new Book(name, path));
        IO.print("Book was successfully added");
    }
}
