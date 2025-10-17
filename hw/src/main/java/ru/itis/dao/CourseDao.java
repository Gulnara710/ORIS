package ru.itis.dao;

import ru.itis.Course;

import java.sql.*;
import java.util.*;

public class CourseDao {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/test",
                "postgres", "Uekz2005");
    }

    public void createCoursesTable() throws SQLException {
        getStatement().executeUpdate("""
            create table if not exists courses (
                id bigint primary key,
                title varchar(255) not null,
                description TEXT,
                details TEXT,
                image TEXT,
                price NUMERIC(10,2) not null
            );""");
    }

    public void createOrdersTable() throws SQLException {
        getStatement().executeUpdate("""
            create table if not exists orders (
                id bigint primary key,
                course_id bigint not null references courses(id),
                created_at timestamp default CURRENT_TIMESTAMP
            );""");
    }

    public void saveNewCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses (id, title, description, details, image, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, course.getId());
            stmt.setString(2, course.getTitle());
            stmt.setString(3, course.getDescription());
            stmt.setString(4, course.getDetails());
            stmt.setString(5, course.getImage());
            stmt.setDouble(6, course.getPrice());
            stmt.executeUpdate();
        }
    }

    public void initCourses() throws SQLException {
        createCoursesTable();
        createOrdersTable();
        if (getAllCourses().isEmpty()) {
            saveNewCourse(new Course(
                    1L,
                    "От Каракуля к Шедевру",
                    "Курс для начинающих художников (6–12 лет)",
                    "Погружаемся в волшебный мир рисования! Ребята научатся уверенно держать карандаш, смешивать краски и создавать первые настоящие картины — от забавных зверушек до фантастических миров.",
                    "./images/card1.jpeg",
                    2000.0
            ));
            saveNewCourse(new Course(
                    2L,
                    "Акварельная Магия",
                    "Онлайн-курс по акварели для подростков и взрослых",
                    "Освойте технику акварели без страха перед «непослушной» краской. Учимся работать с водой, слоями и светом — создавая нежные пейзажи, цветы и абстракции, которые хочется дарить.",
                    "./images/card2.jpeg",
                    2000.0
            ));
            saveNewCourse(new Course(
                    3L,
                    "Комиксы с Нуля",
                    "Курс по созданию комиксов и манги",
                    "Пишем сюжет, придумываем героев, рисуем панели и оформляем страницы. Даже если вы не профессионал — к концу курса у вас будет готовый мини-комикс, которым можно гордиться!",
                    "./images/card3.jpeg",
                    10000.0
            ));
            saveNewCourse(new Course(
                    4L,
                    "Искусство портрета",
                    "Курс по портрету для начинающих",
                    "Приглашаем вас на курс по портрету в нашей онлайн школе живописи! Здесь вы научитесь создавать выразительные и живые портреты, освоив ключевые техники и методы работы с формой, светом и тенью.",
                    "https://i.pinimg.com/originals/24/9d/d2/249dd24a94d10945d0ff4a04db509a63.png",
                    5000.0
            ));
        }
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "select * from courses";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                courses.add(convert(rs));
            }
        }
        return courses;
    }

    public Course getCourseById(Long id) throws SQLException {
        String sql = "select * FROM courses where id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return convert(rs);
                }
            }
        }
        return null;
    }

    private Course convert(ResultSet rs) throws SQLException {
        return new Course(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("details"),
                rs.getString("image"),
                rs.getDouble("price")
        );
    }

    public void updateCourse(Course course) throws SQLException {
        String sql = "update courses set title=?, description=?, details=?, image=?, price=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getTitle());
            stmt.setString(2, course.getDescription());
            stmt.setString(3, course.getDetails());
            stmt.setString(4, course.getImage());
            stmt.setDouble(5, course.getPrice());
            stmt.setLong(6, course.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteCourse(Long id) throws SQLException {
        String sql = "delete from courses where id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("ваще всё поломалось, Наташа, мы ваще всё уронили");
            throw new IllegalStateException(e);
        }
    }
}