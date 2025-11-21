package ru.itis.servlets;

import ru.itis.dao.CourseDao;
import ru.itis.entity.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final CourseDao courseDao = new CourseDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        UserEntity user = (UserEntity) req.getSession().getAttribute("user");


        if (user == null || !"admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            req.setAttribute("courses", courseDao.getAllCourses());
            req.getRequestDispatcher("/jsp/admin.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка при получении курсов", e);
        }
    }
}