package ru.itis.dao;

import ru.itis.entity.CourseEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/project1",
                "postgres", "Uekz2005");
    }

    public void saveNewCourse(CourseEntity course) throws SQLException {
//        String sql = "INSERT INTO courses (id, title, description, details, image, price) VALUES (?, ?, ?, ?, ?, ?)";
        String sql = "INSERT INTO courses (title, description, details, image, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getTitle());
            stmt.setString(2, course.getDescription());
            stmt.setString(3, course.getDetails());
            stmt.setString(4, course.getImage());
            stmt.setDouble(5, course.getPrice());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setId(generatedKeys.getLong(1));
                }
            }
        }

    }

    public void initCourses() throws SQLException {
        if (getAllCourses().isEmpty()) {
            saveNewCourse(new CourseEntity(
                    1L,
                    "От Каракуля к Шедевру",
                    "Курс для начинающих художников (6–12 лет)",
                    "Погружаемся в волшебный мир рисования! Ребята научатся уверенно держать карандаш, смешивать краски и создавать первые настоящие картины — от забавных зверушек до фантастических миров.",
                    "./images/card1.jpeg",
                    2000.0
            ));
            saveNewCourse(new CourseEntity(
                    2L,
                    "Акварельная Магия",
                    "Онлайн-курс по акварели для подростков и взрослых",
                    "Освойте технику акварели без страха перед «непослушной» краской. Учимся работать с водой, слоями и светом — создавая нежные пейзажи, цветы и абстракции, которые хочется дарить.",
                    "./images/card2.jpeg",
                    2000.0
            ));
            saveNewCourse(new CourseEntity(
                    3L,
                    "Комиксы с Нуля",
                    "Курс по созданию комиксов и манги",
                    "Пишем сюжет, придумываем героев, рисуем панели и оформляем страницы. Даже если вы не профессионал — к концу курса у вас будет готовый мини-комикс, которым можно гордиться!",
                    "/images/card3.jpeg",
                    10000.0
            ));
            saveNewCourse(new CourseEntity(
                    4L,
                    "Искусство портрета",
                    "Курс по портрету для начинающих",
                    "Приглашаем вас на курс по портрету в нашей онлайн школе живописи! Здесь вы научитесь создавать выразительные и живые портреты, освоив ключевые техники и методы работы с формой, светом и тенью.",
                    "https://i.pinimg.com/originals/24/9d/d2/249dd24a94d10945d0ff4a04db509a63.png",
                    5000.0
            ));
        }
    }

    public List<CourseEntity> getAllCourses() throws SQLException {
        List<CourseEntity> courses = new ArrayList<>();
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

    public CourseEntity getCourseById(Long id) throws SQLException {
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

    private CourseEntity convert(ResultSet rs) throws SQLException {
        return new CourseEntity(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("details"),
                rs.getString("image"),
                rs.getDouble("price")
        );
    }

    public void updateCourse(CourseEntity course) throws SQLException {
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

    public void addUserCourse(Long userId, Long courseId) throws SQLException {
        String sql = "insert into users_courses (user_id, course_id) values (?, ?) on conflict";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, courseId);
            stmt.executeUpdate();
        }
    }

    public List<CourseEntity> getUserCourses(Long userId) throws SQLException {
        List<CourseEntity> courses = new ArrayList<>();
        String sql = """
        SELECT c.* FROM courses c
        JOIN users_courses uc ON c.id = uc.course_id
        WHERE uc.user_id = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(convert(rs));
                }
            }
        }
        return courses;
    }

}