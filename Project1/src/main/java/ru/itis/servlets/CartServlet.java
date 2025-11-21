package ru.itis.servlets;

import ru.itis.dao.CourseDao;
import ru.itis.entity.CourseEntity;
import ru.itis.entity.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final CourseDao courseDao = new CourseDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity user = (UserEntity) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        String courseIdParam = req.getParameter("courseId");

        if (courseIdParam == null || courseIdParam.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        Long courseId = Long.parseLong(courseIdParam);

        try {
            if ("add".equals(action)) {
                String insertSql = "INSERT INTO users_courses (user_id, course_id) VALUES (?, ?)";
                try (Connection conn = courseDao.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                    stmt.setLong(1, user.getId());
                    stmt.setLong(2, courseId);
                    stmt.executeUpdate();
                }
            } else if ("remove".equals(action)) {
                String deleteSql = "DELETE FROM users_courses WHERE user_id = ? AND course_id = ?";
                try (Connection conn = courseDao.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                    stmt.setLong(1, user.getId());
                    stmt.setLong(2, courseId);
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity user = (UserEntity) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        CourseDao courseDao = new CourseDao();
        try {
            List<CourseEntity> userCourses = courseDao.getUserCourses(user.getId());
            double totalPrice = userCourses.stream().mapToDouble(CourseEntity::getPrice).sum();

            req.setAttribute("userCourses", userCourses);
            req.setAttribute("totalPrice", String.format("%.2f", totalPrice));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/jsp/cart.jsp").forward(req, resp);
    }
}