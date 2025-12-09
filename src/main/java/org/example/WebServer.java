package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.example.servlets.BookServlet;
import org.example.servlets.BookmarkServlet;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Jetty веб-сервер для веб-приложения
 */
public class WebServer {
    private static final int PORT = 8081;
    private static Server server;

    /**
     * Запуск веб-сервера
     */
    public static void start() throws Exception {
        server = new Server(PORT);

        // Создаем контекст приложения
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Path staticDir = Paths.get("webapp").toAbsolutePath();
        context.setBaseResource(Resource.newResource(staticDir));
        context.setWelcomeFiles(new String[]{"index.html"});
        server.setHandler(context);

        // Регистрируем сервлеты
        context.addServlet(new ServletHolder(new BookServlet()), "/api/books/*");
        context.addServlet(new ServletHolder(new BookmarkServlet()), "/api/bookmarks/*");
        context.addServlet(DefaultServlet.class, "/");

        // Запускаем сервер
        server.start();
        System.out.println("✓ Веб-сервер запущен на http://localhost:" + PORT);
        System.out.println("✓ API доступен по адресу http://localhost:" + PORT + "/api/");
        server.join();
    }

    /**
     * Остановка веб-сервера
     */
    public static void stop() throws Exception {
        if (server != null) {
            server.stop();
            System.out.println("✓ Веб-сервер остановлен");
        }
    }

    /**
     * Проверка, работает ли сервер
     */
    public static boolean isRunning() {
        return server != null && server.isRunning();
    }
}
