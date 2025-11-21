<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>ЧИСТЫЙ ХОЛСТ</title>
    <link rel="icon" href="${pageContext.request.contextPath}/images/icon.png" type="image/png">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
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
        <h1 id="contacts-title">КОНТАКТЫ</h1>
        <section id="contacts">
            <p>Вы можете задать все вопросы или оплатить приглянувшийся курс, позвонив на этот номер <u>+79172877777</u></p>
            <p>Также вы можете перейти в наш канал <a href="https://t.me/+AFy-YGReZiI1NzYy">Telegram</a></p>
            <p>Ещё вы можете отправить свои данные ниже и мы свяжемся с вами</p>

            <form>
                <label for="name">Имя:</label>
                <input type="text" id="name" name="name" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>

                <label for="phone">Номер телефона:</label>
                <input type="tel" id="phone" name="phone" mpattern="[\+]\d{1}\d{3}\d{3}\d{2}\d{2}" placeholder="+79172877777" required>

                <input type="submit" value="Отправить">
            </form>
        </section>
    </main>
    <script src="/js/index.js"></script>
</body>
</html>
