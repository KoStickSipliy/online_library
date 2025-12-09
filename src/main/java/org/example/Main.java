package org.example;

/**
 * Точка входа приложения
 * Запускает веб-сервер на http://localhost:8080
 */
public class Main {
    public static void main(String[] args) {
        try {
            WebServer.start();
        } catch (Exception e) {
            System.err.println("✗ Ошибка при запуске веб-сервера:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
// java -jar .\target\online_library-1.0-SNAPSHOT.jar