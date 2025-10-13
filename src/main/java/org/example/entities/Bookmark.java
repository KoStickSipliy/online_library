//COMPLETED

package org.example.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

//пример сущности
@Data
@Builder
public class Bookmark {
    private int id;
    private int bookId;
    private int page;
    private LocalDate date;
}
