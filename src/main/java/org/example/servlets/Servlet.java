package org.example.servlets;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

public interface Servlet {
    void init() throws ServletException;
    void service(ServletRequest request, ServletResponse response) throws ServletException, IOException;
    void destroy();
}
