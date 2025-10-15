package org.example.cli;

import org.example.IO.IO;
import org.example.entities.Bookmark;
import org.example.service.BookmarkServiceImpl;

public class CreateBookmarkCommand implements Command{
    @Override
    public String getCommandName() {
        return "Add new bookmark";
    }

    @Override
    public void execute() {
        long bookId = Long.parseLong(IO.readLine("Input book ID:"));
        int page = Integer.parseInt(IO.readLine("Input page:"));

        BookmarkServiceImpl.getInstance().create(new Bookmark(bookId, page));
        IO.print("Bookmark was successfully added");

    }
}
