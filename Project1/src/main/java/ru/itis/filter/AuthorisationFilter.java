package ru.itis.filter;

import ru.itis.entity.UserEntity;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthorisationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        boolean isPublic = uri.endsWith(".css") ||
                uri.endsWith(".js") ||
                uri.endsWith(".png") ||
                uri.endsWith(".jpg") ||
                uri.endsWith(".jpeg") ||
                uri.contains("/login") ||
                uri.contains("/register") ||
                uri.equals(contextPath + "/") ||
                uri.endsWith("/index") ||
                uri.endsWith("/courses") && "GET".equals(request.getMethod()) ||
                uri.endsWith("/contacts") ||
                uri.endsWith("/reviews");

        if (isPublic) {
            chain.doFilter(request, response);
            return;
        }

        if (user == null) {
            response.sendRedirect(contextPath + "/login");
            return;
        }

        if (uri.contains("/admin")) {
            String role = user.getRole();
            if (role == null || !"admin".equalsIgnoreCase(role.trim())) {
                System.out.println("Я тебя дальше пущю! Пользователь: " + user.getUsername() +
                        ", role: [" + role + "]");
                response.sendRedirect(contextPath + "/index");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}