package org.example.repository;

import org.example.entities.Author;

import java.util.List;

public class DepartmentRepositoryInMemImpl implements DepartmentRepository {
    //для синглтон-паттерна
    private static DepartmentRepositoryInMemImpl obj;

    // место для хранения ваших сущностей (список, сет, мапа)

    private DepartmentRepositoryInMemImpl() {
        
    }

    //для синглтон-паттерна
    public static DepartmentRepository getInstance() {
        if (obj == null) {
            obj = new DepartmentRepositoryInMemImpl();
        }
        return obj;
    }

    @Override
    public Author getDepartmentByName(String name) {
        //логика обращения к полю, хранящему ваши объекты
        return null;
    }

    @Override
    public void add(Author object) {
        //логика обращения к полю, хранящему ваши объекты
    }

    @Override
    public void removeAll() {
        //логика обращения к полю, хранящему ваши объекты
    }
    
    @Override
    public void update(int id, Author newObject) {
        //логика обращения к полю, хранящему ваши объекты
    }

    @Override
    public Author getById(int id) {
        //логика обращения к полю, хранящему ваши объекты
        return null;
    }


    @Override
    public List<Author> getAll() {
        //логика обращения к полю, хранящему ваши объекты
        return null;
    }
}

