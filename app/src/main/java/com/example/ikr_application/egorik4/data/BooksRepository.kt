package com.example.ikr_application.egorik4.data

import com.example.ikr_application.egorik4.data.models.BookDto

class BooksRepository {
    fun getBooks(): List<BookDto> {
        return listOf(
            BookDto(
                title = "1984",
                author = "Джордж Оруэлл",
                year = 1949,
                rating = 4.8,
                genre = "Антиутопия"
            ),
            BookDto(
                title = "Преступление и наказание",
                author = "Фёдор Достоевский",
                year = 1866,
                rating = 4.9,
                genre = "Роман"
            ),
            BookDto(
                title = "Гарри Поттер и философский камень",
                author = "Джоан Роулинг",
                year = 1997,
                rating = 4.7,
                genre = "Фантастика"
            ),
            BookDto(
                title = "Мастер и Маргарита",
                author = "Михаил Булгаков",
                year = 1967,
                rating = 4.8,
                genre = "Роман"
            ),
            BookDto(
                title = "Убийство в Восточном экспрессе",
                author = "Агата Кристи",
                year = 1934,
                rating = 4.6,
                genre = "Детектив"
            ),
            BookDto(
                title = "Война и мир",
                author = "Лев Толстой",
                year = 1869,
                rating = 4.9,
                genre = "Роман"
            ),
            BookDto(
                title = "Дюна",
                author = "Фрэнк Герберт",
                year = 1965,
                rating = 4.7,
                genre = "Научная фантастика"
            ),
            BookDto(
                title = "Анна Каренина",
                author = "Лев Толстой",
                year = 1877,
                rating = 4.8,
                genre = "Роман"
            ),
            BookDto(
                title = "Шерлок Холмс",
                author = "Артур Конан Дойл",
                year = 1887,
                rating = 4.6,
                genre = "Детектив"
            ),
            BookDto(
                title = "Властелин колец",
                author = "Джон Р. Р. Толкин",
                year = 1954,
                rating = 4.9,
                genre = "Фэнтези"
            ),
            BookDto(
                title = "Собачье сердце",
                author = "Михаил Булгаков",
                year = 1925,
                rating = 4.5,
                genre = "Сатира"
            ),
            BookDto(
                title = "Идиот",
                author = "Фёдор Достоевский",
                year = 1869,
                rating = 4.7,
                genre = "Роман"
            ),
            BookDto(
                title = "О дивный новый мир",
                author = "Олдос Хаксли",
                year = 1932,
                rating = 4.6,
                genre = "Антиутопия"
            ),
            BookDto(
                title = "Три мушкетёра",
                author = "Александр Дюма",
                year = 1844,
                rating = 4.5,
                genre = "Приключения"
            ),
            BookDto(
                title = "Граф Монте-Кристо",
                author = "Александр Дюма",
                year = 1844,
                rating = 4.8,
                genre = "Приключения"
            )
        )
    }
}

