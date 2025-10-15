package org.example.cli;

import org.example.IO.IO;
import org.example.entities.Book;
import org.example.entities.Bookmark;
import org.example.service.BookServiceImpl;
import org.example.service.BookmarkServiceImpl;

import java.util.List;

public class GetAllBookmarksCommand implements Command{
    @Override
    public String getCommandName() {
        return "Get all bookmarks";
    }

    @Override
    public void execute() {
        List<Bookmark> bms = BookmarkServiceImpl.getInstance().getAll();
        if (bms.isEmpty()) {
            IO.print("No books found");
            return;
        }
        bms.forEach(bm -> {
            IO.print("%d. book: %d, %d page, %s".formatted(
                    bm.getId(),
                    bm.getBookId(),
                    bm.getPage(),
                    bm.getDate().toString()
            ));
        });
    }
}
