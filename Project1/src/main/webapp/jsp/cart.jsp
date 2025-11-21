<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Корзина - ЧИСТЫЙ ХОЛСТ</title>
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

<main>
    <section id="cart">
        <h1>🛒 Ваша корзина</h1>

        <c:choose>
            <c:when test="${empty userCourses}">
                <p style="text-align: center; font-size: 1.2em; color: #666;">
                    Корзина пуста. <a href="${pageContext.request.contextPath}/courses">Перейти к курсам →</a>
                </p>
            </c:when>
            <c:otherwise>
                <div class="cards-conteiner">
                    <c:forEach var="course" items="${userCourses}">
                        <div class="card">
                            <img src="${pageContext.request.contextPath}${course.image}"
                                 width="200" height="200" alt="${course.title}">
                            <h3>${course.title}</h3>
                            <p><i>${course.description}</i></p>
                            <p><strong>Цена: ${course.price} ₽</strong></p>

                            <form action="${pageContext.request.contextPath}/cart" method="post">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="courseId" value="${course.id}">
                                <button type="submit" сlass="button">Убрать</button>
                            </form>
                        </div>
                    </c:forEach>
                </div>

                <div>
                    <p><strong>Итого: ${totalPrice} ₽</strong></p>
                    <button class="button">Оплатить</button>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</main>

<script src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>