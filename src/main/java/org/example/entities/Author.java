package org.example.entities;

//пример сущности
public class Author {
    private int id;
    private String firstName;
    private String lastName;
    public Author() {
    }
    public Author(int id, String name) {
        this.id = id;
        this.firstName = name;
        this.lastName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "\n Author{" +
                "id=" + id +
                ", first name=" + firstName +
                ", last name=" + lastName + '\'' +
                "} \n";
    }
}
