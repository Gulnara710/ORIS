<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="light-theme">
<header>
    <nav>
        <a href="/index">
            <img src="${pageContext.request.contextPath}/images/logo.png" height="100">
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
        </ul>
        <button id="currentTheme" class="button">Переключить тему</button>
        <a href="/cart">
            <img src="${pageContext.request.contextPath}/images/cart.jpeg" height="100">
        </a>
    </nav>
</header>
<main>
    <h1>Регистрация</h1>
    <section id="contacts">
        <form method="post" action="${pageContext.request.contextPath}/register">
            <label for="username">Логин:</label><br>
            <input type="text" id="username" name="username" required><br>
            <label for="password">Пароль:</label><br>
            <input type="password" id="password" name="password" required><br><br>
            <button type="submit" class="button">Зарегистрироваться</button>
        </form>
        <p><a href="${pageContext.request.contextPath}/login">Уже есть аккаунт? Войти</a></p>
        <% if (request.getParameter("error") != null) { %>
            <p style="color: #ff6a2a;"><%= request.getParameter("error") %></p>
        <% } %>
    </section>
</main>
<script src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>