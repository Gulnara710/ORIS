package ru.itis;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/thank")
public class Thank extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"ru\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Спасибо</title></head>");
        out.println("<body>");
        out.println("<h1>Спасибо за регистрацию!!</h1>");
        out.println("<h2>ваши данные сохранены</h2>");
        out.println("</body>");
        out.println("</html>");
    }
}
