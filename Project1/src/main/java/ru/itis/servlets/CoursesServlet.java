package ru.itis.servlets;

import ru.itis.dao.CourseDao;
import ru.itis.entity.CourseEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/courses")
public class CoursesServlet extends HttpServlet {
    private final CourseDao courseDao = new CourseDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            List<CourseEntity> courses = courseDao.getAllCourses();
            req.setAttribute("courses", courses);
            req.getRequestDispatcher("/jsp/courses.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка при загрузке курсов", e);
        }
    }
}