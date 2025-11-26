package org.example.cli;

import org.example.IO.IO;
import org.example.service.BookmarkServiceImpl;

public class DeleteBookmarkCommand implements Command{
    @Override
    public String getCommandName() {
        return "Delete bookmark";
    }

    @Override
    public void execute() {
        Long bookmarkId = IO.readLongSafe("Input bookmark ID:");
        if (bookmarkId == null) {
            IO.printError("Invalid bookmark ID");
            return;
        }
        try {
            BookmarkServiceImpl.getInstance().deleteById(bookmarkId);
            IO.print("Bookmark was successfully deleted");
        } catch (RuntimeException e) {
            IO.printError("Failed to delete bookmark: " + e.getMessage());
        }
    }
}
