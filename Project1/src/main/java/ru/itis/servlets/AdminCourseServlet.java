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

@WebServlet({"/admin/add", "/admin/update", "/admin/delete"})
public class AdminCourseServlet extends HttpServlet {

    private final CourseDao dao = new CourseDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        try {
            if ("/admin/add".equals(path)) {

                CourseEntity course = new CourseEntity(
                        null,
                        req.getParameter("title"),
                        req.getParameter("description"),
                        req.getParameter("details") != null ? req.getParameter("details") : "",
                        req.getParameter("image"),
                        Double.parseDouble(req.getParameter("price"))
                );
                dao.saveNewCourse(course);

            } else if ("/admin/update".equals(path)) {
                Long id = Long.parseLong(req.getParameter("id"));
                CourseEntity course = dao.getCourseById(id);

                if (course != null) {
                    course.setTitle(req.getParameter("title"));
                    course.setDescription(req.getParameter("description"));
                    course.setDetails(req.getParameter("details"));
                    course.setImage(req.getParameter("image"));
                    course.setPrice(Double.parseDouble(req.getParameter("price")));
                    dao.updateCourse(course);
                }

            } else if ("/admin/delete".equals(path)) {
                Long id = Long.parseLong(req.getParameter("id"));
                dao.deleteCourse(id);
            }

        } catch (SQLException e) {
            throw new ServletException("Ошибка базы данных", e);
        } catch (NumberFormatException e) {
            throw new ServletException("Неверный формат числа (цена)", e);
        } catch (Exception e) {
            throw new ServletException("Неизвестная ошибка", e);
        }

        resp.sendRedirect(req.getContextPath() + "/admin");
    }
}