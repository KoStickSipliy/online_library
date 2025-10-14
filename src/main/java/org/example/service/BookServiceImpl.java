//package org.example.service;
//
//import org.example.entities.Bookmark;
//
//import java.util.List;
//
//public class BookServiceImpl implements BookService {
//
//    //
//    private static BookService obj;
//
//    //поле с используемыми репозитроями
//
//    private BookServiceImpl() {
//        //объявление репозиториев
//    }
//
//    //для синглтон-паттерна
//    public static BookService getInstance() {
//        if (obj == null) {
//            obj = new BookServiceImpl();
//        }
//        return obj;
//    }
//
//    private Bookmark setInfoForNewDepartment(String[] parameters) {
//        //метод обработки входных данных и создания экземпляра сущности
//        return null;
//    }
//
//    @Override
//    public Bookmark getById(int id) {
//        //обращение к репозиторию
//        return null;
//    }
//
//    @Override
//    public List<Bookmark> getAll() {
//        //обращение к репозиторию
//        return null;
//    }
//}
