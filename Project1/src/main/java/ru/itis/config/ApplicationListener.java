package ru.itis.config;

import ru.itis.dao.CourseDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            CourseDao courseDao = new CourseDao();
            courseDao.initCourses();
            System.out.println("курсы инициализированы");

        } catch (Exception e) {
            System.err.println("Ошибка при инициализации(");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}