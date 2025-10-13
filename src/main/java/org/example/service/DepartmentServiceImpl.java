package org.example.service;

import org.example.entities.Bookmark;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

    //
    private static DepartmentService obj;

    //поле с используемыми репозитроями

    private DepartmentServiceImpl() {
        //объявление репозиториев
    }

    //для синглтон-паттерна
    public static DepartmentService getInstance() {
        if (obj == null) {
            obj = new DepartmentServiceImpl();
        }
        return obj;
    }

    private Bookmark setInfoForNewDepartment(String[] parameters) {
        //метод обработки входных данных и создания экземпляра сущности
        return null;
    }

    @Override
    public Bookmark getById(int id) {
        //обращение к репозиторию
        return null;
    }

    @Override
    public List<Bookmark> getAll() {
        //обращение к репозиторию
        return null;
    }
}
