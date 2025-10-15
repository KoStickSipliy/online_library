package org.example.cli;

import org.example.IO.IO;
import org.example.service.BookServiceImpl;
import org.example.service.BookmarkServiceImpl;

public class DeleteBookmarkCommand implements Command{
    @Override
    public String getCommandName() {
        return "Delete bookmark";
    }

    @Override
    public void execute() {
        long bookmarkId = Long.parseLong(IO.readLine("Input bookmark ID:"));
        BookmarkServiceImpl.getInstance().deleteById(bookmarkId);
        IO.print("Bookmark was successfully deleted");
    }
}
