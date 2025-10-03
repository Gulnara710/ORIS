package ru.itis;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/registration")
public class Registration extends HttpServlet {
    private static final String dataFile = "registrations.txt";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String error = (String) request.getAttribute("error");

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"ru\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Registration</title></head>");
        out.println("<body>");
        out.println("<h1>Форма регистрации</h1>");
        if (error != null) {
            out.println("<p style=\"color: red\">" + error + "</p>");
        }
        out.println("<form method=\"post\" action=\"/registration\">");
        out.println("<label>Логин:<br><input type=\"text\" name=\"login\" required></label><br>");
        out.println("<label>Email:<br><input type=\"text\" name=\"email\" required></label><br>");
        out.println("<label>Пароль:<br><input type=\"password\" name=\"password\" required></label><br>");
        out.println("<input type=\"submit\" value=\"Зарегистрироваться\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        // out.println("");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String error = null;

        if (login == null || login.trim().isEmpty()) {
            error = "Логин не может быть пустым";
        } else if (email == null || email.trim().isEmpty()) {
            error = "email не может быть пустым";
        } else if (!email.contains("@")) {
            error = "email должен содержать @";
        } else if (password == null || password.trim().isEmpty()) {
            error = "пароль не может быть пустым";
        }

        if (error != null && error.length() > 0) {
            request.setAttribute("error", error);
            doGet(request, response);
            return;
        }

        save(login, email, password);
        response.sendRedirect("thank");
    }

    private void save(String login, String email, String password) throws IOException{
        try (FileWriter fw = new FileWriter(dataFile, true)) {
            fw.write("login: " + login + "\n");
            fw.write("email" + email + "\n");
            fw.write("password" + password + "\n");
        }
    }
}