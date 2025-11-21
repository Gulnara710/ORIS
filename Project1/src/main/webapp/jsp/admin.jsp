<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Админ-панель</title>
    <link rel="icon" href="${pageContext.request.contextPath}/images/icon.png" type="image/png">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

</head>
<body class="light-theme">

<header>
    <nav>
        <a href="${pageContext.request.contextPath}/index">
            <img src="${pageContext.request.contextPath}/images/logo.png" height="100" alt="Логотип">
        </a>
        <ul id="navigation">
            <li><a href="${pageContext.request.contextPath}/index">Главная</a></li>
            <li><a href="${pageContext.request.contextPath}/courses">Курсы</a></li>
            <li><a href="${pageContext.request.contextPath}/reviews">Отзывы</a></li>
            <li><a href="${pageContext.request.contextPath}/contacts">Контакты</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Выйти (${sessionScope.user.username})</a></li>
        </ul>
        <button id="currentTheme" class="button">Переключить тему</button>
        <a href="${pageContext.request.contextPath}/cart">
            <img src="${pageContext.request.contextPath}/images/cart.jpeg" height="100" alt="Корзина">
        </a>
    </nav>
</header>

<div class="admin-container">
    <h1>Админ-панель</h1>

    <button class="button add-course-btn" onclick="openAddModal()">Добавить новый курс</button>
    <section id="courses">
        <h1 id="education-title">Все курсы</h1>

        <div class="cards-conteiner">
            <c:forEach var="course" items="${courses}">
                <div class="card">
                    <img src="${pageContext.request.contextPath}${course.image}" width="200" height="200"  alt="${course.title}">
                    <h3>${course.title}</h3>
                    <p><i>${course.description}</i></p>
                    <p>${course.details}</p>
                    <p><strong>Цена: ${course.price} ₽</strong></p>

                    <div class="card-actions">
                        <button class="button"
                                onclick='openEditModal(${course.id}, "${course.title}", "${course.description}", "${course.details}", "${course.image}", ${course.price})'>
                            Изменить
                        </button>

                        <form action="${pageContext.request.contextPath}/admin/delete" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${course.id}">
                            <button type="submit" class="button"
                                    onclick="return confirm('Удалить курс «${course.title}»?')">
                                Удалить
                            </button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>
</div>

<div id="courseModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2 id="modalTitle">Добавить курс</h2>
            <span class="close" onclick="closeModal()">×</span>
        </div>

        <div class="modal-body">
            <form id="courseForm" action="${pageContext.request.contextPath}/admin/add" method="post">
                <input type="hidden" name="id" id="courseId">

                <div class="form-group">
                    <label>Название курса</label>
                    <input type="text" name="title" id="title" required>
                </div>

                <div class="form-group">
                    <label>Краткое описание</label>
                    <textarea name="description" id="description" required></textarea>
                </div>

                <div class="form-group">
                    <label>Подробное описание</label>
                    <textarea name="details" id="details"></textarea>
                </div>

                <div class="form-group">
                    <label>Путь к изображению</label>
                    <input type="text" name="image" id="image" placeholder="/images/course.jpg" required>
                </div>

                <div class="form-group">
                    <label>Цена (₽)</label>
                    <input type="number" step="0.01" name="price" id="price" required>
                </div>

                <div class="modal-footer">
                    <button type="submit" class="button" style="width:100%; padding:14px; font-size:18px;">
                        Сохранить курс
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>