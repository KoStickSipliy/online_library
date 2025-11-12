//COMPLETED

package org.example.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

//пример сущности
@Data
public class Bookmark {
    private long id;
    private long bookId;
    private int page;
    private LocalDate date;
    public Bookmark (long id, long bookId, int page) {
        this.id = id;
        this.bookId = bookId;
        this.page = page;
        this.date = LocalDate.now();
    }
    public Bookmark (long id, long bookId, int page, LocalDate date) {
        this.id = id;
        this.bookId = bookId;
        this.page = page;
        this.date = date;
    }
    public Bookmark (long bookId, int page) {
        this.bookId = bookId;
        this.page = page;
        this.date = LocalDate.now();
    }
    public Bookmark (long bookId, int page, LocalDate date) {
        this.bookId = bookId;
        this.page = page;
        this.date = date;
    }
}
