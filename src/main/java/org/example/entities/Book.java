//COMPLETED

package org.example.entities;

import lombok.Builder;
import lombok.Data;

//пример сущности
@Data
@Builder
public class Book {
    private long id;
    private String name;
    private String path;
}
