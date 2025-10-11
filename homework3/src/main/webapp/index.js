document.addEventListener('DOMContentLoaded', function() {

    // тема светлая/темная
    const currentTheme = document.getElementById('currentTheme');

    if (currentTheme) {
        currentTheme.addEventListener('click', function () {
            const body = document.body;
            if (body.classList.contains('light-theme')){
                body.classList.replace('light-theme', 'dark-theme');
            } else {
                body.classList.replace('dark-theme', 'light-theme');
            }
        });
    }
    
    // добавить новости
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


    // слайдер
    const slider = document.querySelector('.slider');
    if (slider) {

        const slides = slider.querySelector('.slides');
        const slideList = slider.querySelectorAll('.slide');
        const prevBtn = slider.querySelector('.prev');
        const nextBtn = slider.querySelector('.next');

        const slideCount = slideList.length;
        let currentIndex = 0;

        function goToSlide(index) {
            if (index < 0) index = slideCount - 1;
            if (index >= slideCount) index = 0;
            currentIndex = index;
            slides.style.transform = `translateX(${-currentIndex * 100}%)`;
        }

        prevBtn.addEventListener('click', () => goToSlide(currentIndex - 1));
        nextBtn.addEventListener('click', () => goToSlide(currentIndex + 1));
    }


    // таймер
    const countdown = () => {
        const countDate = new Date('Oct 17, 2025 18:00:00').getTime();
        const now = new Date().getTime();
        const gap = countDate - now;

        if (gap < 0) {
            document.getElementById('timer').innerHTML = '<h3>Вебинар начался!</h3>';
            return;
       }

        const second = 1000;
        const minute = second * 60;
        const hour = minute * 60;
        const day = hour * 24;

        const days = Math.floor(gap / day);
        const hours = Math.floor((gap % day) / hour);
        const minutes = Math.floor((gap % hour) / minute);
        const seconds = Math.floor((gap % minute) / second);

        document.getElementById('days').innerText = days;
        document.getElementById('hours').innerText = hours;
        document.getElementById('minutes').innerText = minutes;
        document.getElementById('seconds').innerText = seconds;
    };

    if (document.getElementById('timer')) {
        countdown();
        setInterval(countdown, 1000);
    }

});



