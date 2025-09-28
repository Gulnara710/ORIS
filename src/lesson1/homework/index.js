document.addEventListener('DOMContentLoaded', function() {
    let newsCount = 0;
    const news = [
        "🎉 Новый курс «Комиксы с Нуля: От Идеи до Страницы» стартует уже в следующем месяце!",
        "🎨 Мы запустили конкурс среди учеников — лучшие работы попадут в онлайн-галерею!",
        "📚 Бесплатный мастер-класс по композиции пройдёт в субботу в 18:00 по МСК",
        "🖌️ Теперь все курсы доступны с пожизненным доступом к материалам!"

    ];
    const addNewsButton = document.getElementById('add-news');
    const container = document.getElementById('news-container');

    addNewsButton.addEventListener('click', function() {
        if (newsCount < news.length) {
            const newsCard = document.createElement('div');
            newsCard.classList.add('news-card');
            newsCard.textContent =news[newsCount];
            container.appendChild(newsCard);
            newsCount++;

            if (newsCount >= news.length) {
                this.textContent = 'Больше новостей нет';
                this.disabled = true;
            }
        }
    });
});

