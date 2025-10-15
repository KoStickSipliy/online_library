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
    public Book (String name, String path){
        this.name = name;
        this.path = path;
    }
}
