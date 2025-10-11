package ru.itis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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
        List<Course> cart = (List<Course>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        if (cart.isEmpty()) {
            out.println("<p>Ваша корзина пуста(</p>");
            out.println("<a href=\"courses.html\" class=\"button\">Перейти к курсам</a>");
        } else {
            for (Course c : cart) {
                out.println("<div>");
                out.println("<h3>" + c.getTitle() + "</h3>");
                out.println("<p>Описание: " + c.getDescription() + "</p>");
                out.println("<p>Цена: " + c.getPrice() + " руб.</p>");
                out.println("</div>");
                out.println("<hr>");
            }

            out.println("<form action=\"/cart\" method=\"post\">");
            out.println("<button type=\"submit\" class=\"button\">Оформить заказ)</button>");
            out.println("</form>");
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
        List<Course> cart = (List<Course>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        String title = req.getParameter("title");
        if (title != null) {
            String description = req.getParameter("description");
            String priceStr = req.getParameter("price");
            try {
                double price = Double.parseDouble(priceStr);
                cart.add(new Course(title, description, price));
                session.setAttribute("cart", cart);
            } catch (NumberFormatException ignored) {}
            resp.sendRedirect("/cart");
            return;
        }

        if (!cart.isEmpty()) {
            try (FileWriter fw = new FileWriter("orders.txt", true)) {

                for (Course c : cart) {
                    fw.write("Курс: " + c.getTitle() + "\n");
                    fw.write("Описание: " + c.getDescription() + "\n");
                    fw.write("Цена: " + c.getPrice() + " руб\n");
                }
                fw.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            cart.clear();
            session.setAttribute("cart", cart);
        }

        resp.sendRedirect("/cart");
    }
}