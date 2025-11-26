package org.example.cli;

import org.example.IO.IO;
import org.example.entities.Bookmark;
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
        if (bm == null) {
            IO.printError("Bookmark not found");
            return;
        }

        String pageInput = IO.readLine("Input new page or skip:");
        Integer page = null;
        if (pageInput != null && !pageInput.isBlank()) {
            try {
                page = Integer.parseInt(pageInput.trim());
            } catch (NumberFormatException e) {
                IO.printError("Invalid page number");
                return;
            }
        }

        String dateString = IO.readLine("Input new date or skip:");
        LocalDate newDate = null;
        if (dateString != null && !dateString.isBlank()) {
            try {
                newDate = LocalDate.parse(dateString.trim(), DateUtils.formatter);
            } catch (Exception e) {
                IO.printError("Invalid date format. Use: " + DateUtils.formatter.toString());
                return;
            }
        }

        Bookmark updated = new Bookmark(
                bm.getBookId(),
                page == null ? bm.getPage() : page,
                newDate == null ? bm.getDate() : newDate
        );
        updated.setId(bm.getId());

        BookmarkServiceImpl.getInstance().update(bookmarkID, updated);
    }
}
