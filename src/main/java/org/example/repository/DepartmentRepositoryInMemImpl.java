//package org.example.repository;
//
//import org.example.entities.Bookmark;
//
//import java.util.List;
//
//public class DepartmentRepositoryInMemImpl implements BookRepository {
//    //для синглтон-паттерна
//    private static DepartmentRepositoryInMemImpl obj;
//
//    // место для хранения ваших сущностей (список, сет, мапа)
//
//    private DepartmentRepositoryInMemImpl() {
//
//    }
//
//    //для синглтон-паттерна
//    public static BookRepository getInstance() {
//        if (obj == null) {
//            obj = new DepartmentRepositoryInMemImpl();
//        }
//        return obj;
//    }
//
//    @Override
//    public Bookmark getDepartmentByName(String name) {
//        //логика обращения к полю, хранящему ваши объекты
//        return null;
//    }
//
//    @Override
//    public void add(Bookmark object) {
//        //логика обращения к полю, хранящему ваши объекты
//    }
//
//    @Override
//    public void removeAll() {
//        //логика обращения к полю, хранящему ваши объекты
//    }
//
//    @Override
//    public void update(int id, Bookmark newObject) {
//        //логика обращения к полю, хранящему ваши объекты
//    }
//
//    @Override
//    public Bookmark getById(int id) {
//        //логика обращения к полю, хранящему ваши объекты
//        return null;
//    }
//
//
//    @Override
//    public List<Bookmark> getAll() {
//        //логика обращения к полю, хранящему ваши объекты
//        return null;
//    }
//}
//
