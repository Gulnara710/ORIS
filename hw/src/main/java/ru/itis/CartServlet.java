package ru.itis;

import ru.itis.dao.CourseDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        InputStream is = getClass().getClassLoader().getResourceAsStream("cart.html");

        String s;
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        while ((s = br.readLine()) != null) {
            out.println(s);
        }
        br.close();

        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        List<Long> cartIds = (List<Long>) session.getAttribute("cart");
        if (cartIds == null) cartIds = new ArrayList<>();

        if (cartIds.isEmpty()) {
            out.println("<p>Ваша корзина пуста(</p>");
            out.println("<a href=\"courses.html\" class=\"button\">Перейти к курсам</a>");
        } else {
            CourseDao dao = new CourseDao();
            for (Long id : cartIds) {
                try {
                    Course c = dao.getCourseById(id);
                    if (c != null) {
                        out.println("<div>");
                        out.println("<img src=\"" + c.getImage() + "\" width=\"200\">");
                        out.println("<h3>" + c.getTitle() + "</h3>");
                        out.println("<p><i>" + c.getDescription() + "</i></p>");
                        out.println("<p>" + c.getDetails() + "</p>");
                        out.println("<p>Цена: " + c.getPrice() + " руб.</p>");
                        out.println("</div><hr>");
                    }
                } catch (SQLException e) {
                    out.println("<p style='color:red;'>Ошибка с ID=" + id + "</p>");
                    e.printStackTrace();
                }
            }
        }
        out.println("</section>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        @SuppressWarnings("unchecked")
        List<Long> cartIds = (List<Long>) session.getAttribute("cart");
        if (cartIds == null) {
            cartIds = new ArrayList<>();
            session.setAttribute("cart", cartIds);
        }

        String courseIdStr = req.getParameter("courseId");
        if (courseIdStr != null) {
            try {
                Long courseId = Long.parseLong(courseIdStr);
                CourseDao dao = new CourseDao();
                try {
                    if (dao.getCourseById(courseId) != null) {
                        cartIds.add(courseId);
                        session.setAttribute("cart", cartIds);
                    }
                } catch (SQLException e) {
                    System.err.println("Ошибка с ID: " + courseId);
                    e.printStackTrace();
                }
            } catch (NumberFormatException ignored) {
            }
            resp.sendRedirect("/cart");
            return;
        }

        if (!cartIds.isEmpty()) {
            CourseDao dao = new CourseDao();
            try (FileWriter fw = new FileWriter("orders.txt", true)) {
                for (Long id : cartIds) {
                    try {
                        Course c = dao.getCourseById(id);
                        if (c != null) {
                            fw.write("Курс: " + c.getTitle() + "\n");
                            fw.write("Описание: " + c.getDescription() + "\n");
                            fw.write("Цена: " + c.getPrice() + " руб\n");
                        }
                    } catch (SQLException e) {
                        fw.write("Ошибка с ID " + id + "\n");
                        e.printStackTrace();
                    }
                }
                fw.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            cartIds.clear();
            session.setAttribute("cart", cartIds);
        }

        resp.sendRedirect("/cart");
    }
}