<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>ЧИСТЫЙ ХОЛСТ — Курсы</title>
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
            <li class="menu"><a href="${pageContext.request.contextPath}/courses">Курсы</a>
                <div class="menu-content">
                    <a href="${pageContext.request.contextPath}/courses#beginner">От Каракуля к Шедевру</a>
                    <a href="${pageContext.request.contextPath}/courses#watercolor">Акварельная Магия</a>
                    <a href="${pageContext.request.contextPath}/courses#comics">Комиксы с Нуля</a>
                    <a href="${pageContext.request.contextPath}/courses#portrait">Искусство портрета</a>
                </div>
            </li>
            <li><a href="${pageContext.request.contextPath}/reviews">Отзывы</a></li>
            <li><a href="${pageContext.request.contextPath}/contacts">Контакты</a></li>
            <c:if test="${sessionScope.user != null}">
                <li><a href="${pageContext.request.contextPath}/logout">Выйти</a></li>
            </c:if>
        </ul>
        <button id="currentTheme" class="button">Переключить тему</button>
        <a href="${pageContext.request.contextPath}/cart">
            <img src="${pageContext.request.contextPath}/images/cart.jpeg" height="100" alt="Корзина">
        </a>
    </nav>
</header>

<section id="courses">
    <h1 id="education-title">Обучение у нас – это удобно и результативно</h1>
    <div class="cards-conteiner">
        <c:forEach var="course" items="${courses}">
            <div class="card" id="${course.id == 1 ? 'beginner' : course.id == 2 ? 'watercolor' : course.id == 3 ? 'comics' : 'portrait'}">
                <img src="${pageContext.request.contextPath}${course.image}" width="200" height="200" alt="${course.title}">
                <h3>${course.title}</h3>
                <p><i>${course.description}</i></p>
                <p>${course.details}</p>
                <p><strong>Цена: ${course.price} ₽</strong></p>

                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="courseId" value="${course.id}">
                            <input type="hidden" name="action" value="add">
                            <button class="button" type="submit">В корзину</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <button class="button" onclick="window.location.href='${pageContext.request.contextPath}/login'">Войдите, чтобы купить</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </div>
</section>

<script src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>