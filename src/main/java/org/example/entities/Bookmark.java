//COMPLETED

package org.example.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

//пример сущности
@Data
@Builder
public class Bookmark {
    private long id;
    private int bookId;
    private int page;
    private LocalDate date;
}
