# java-filmorate
Template repository for Filmorate project.

# Документация по структуре базы данных
Ниже описывана схема базы данных, предназначенной для хранения информации о фильмах и пользователях.

Визуальная схема таблицы
Filmorate Database 
![FilmorateDatabase.png](..%2FFilmorateDatabase.png)

## Описание таблиц.

## Таблица film.

### Хранит информацию о фильмах.

film_id integer (Первичный ключ): Уникальный идентификатор фильма.

name varchar: Название фильма.

description varchar: Описание фильма.

release_date date: Дата выхода фильма.

duration integer: Продолжительность фильма в минутах.

mpa_id integer: Идентификатор рейтинга Ассоциации кинокомпаний (англ. Motion Picture Association, сокращённо МРА)
