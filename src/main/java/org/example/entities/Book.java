package org.example.entities;

//пример сущности
public class Book {
    private int id;
    private String name;
    private String path;
    public Book() {
    }
    public Book(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "\n Book{" +
                "id=" + id +
                ", name=" + name +
                ", path=" + path + '\'' +
                "} \n";
    }
}
