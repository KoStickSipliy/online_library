package org.example.cli;

import org.example.IO.IO;
import org.example.entities.Book;
import org.example.entities.Bookmark;
import org.example.service.BookServiceImpl;
import org.example.service.BookmarkServiceImpl;
import org.example.utils.DateUtils;

import java.time.LocalDate;

public class EditBookmarkCommand implements Command{
    @Override
    public String getCommandName() {
        return "Edit bookmark";
    }

    @Override
    public void execute() {
        long bookmarkID = Long.parseLong(IO.readLine("Input bookmark ID:"));
        Bookmark bm = BookmarkServiceImpl.getInstance().getById(bookmarkID);

        int page = -1;
        page = Integer.parseInt(IO.readLine("Input new page or skip:"));
        String dateString = IO.readLine("Input new date or skip:");

        BookmarkServiceImpl.getInstance().update(bookmarkID, new Bookmark(
                        bm.getBookId(),
                        page == -1 ? bm.getPage() : page,
                        dateString.isBlank() ? bm.getDate() : LocalDate.parse(dateString, DateUtils.formatter)
                )
        );
    }
}
