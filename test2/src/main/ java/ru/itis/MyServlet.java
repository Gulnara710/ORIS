package ru.itis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>Привет, это мой первый сервлет!</h1>");
        out.println("<p>Текущее время: " + new java.util.Date() + "</p>");
        out.println("<form method=\"post\" action=\"/hello\">");
        out.println("<input id=\"email\" name=\"email\" type=\"email\">");
        out.println("<input type=\"submit\">");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        System.out.println(email);
        response.sendRedirect("success");
    }
}
