package org.example.repository;

import org.example.entities.Author;

public interface DepartmentRepository extends Repository<Author, Integer>{
    Author getDepartmentByName(String name);

}
