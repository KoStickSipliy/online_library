package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.servlets.BookServlet;
import org.example.servlets.BookmarkServlet;


// Jetty веб-сервер для веб-приложения
public class WebServer {
    private static final int PORT = 8081;
    private static Server server;

    public static void start() throws Exception {
        server = new Server(PORT);

        // Создаем контекст приложения
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Настраиваем обслуживание статических файлов из папки webapp
        context.setResourceBase("webapp");
        context.setWelcomeFiles(new String[]{"index.html"});
        
        // Регистрируем DefaultServlet для статических файлов
        ServletHolder defaultHolder = new ServletHolder("default", DefaultServlet.class);
        defaultHolder.setInitParameter("dirAllowed", "false");
        context.addServlet(defaultHolder, "/");

        // Регистрируем сервлеты API
        context.addServlet(new ServletHolder(new BookServlet()), "/api/books/*");
        context.addServlet(new ServletHolder(new BookmarkServlet()), "/api/bookmarks/*");

        // Запускаем сервер
        server.start();
        System.out.println("Веб-сервер запущен на http://localhost:" + PORT);
        System.out.println("API доступен по адресу http://localhost:" + PORT + "/api/");
        server.join();
    }

   public static void stop() throws Exception {
       if (server != null) {
           server.stop();
           System.out.println("Веб-сервер остановлен");
       }
   }

    public static boolean isRunning() {
        return server != null && server.isRunning();
    }
}
