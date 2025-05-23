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

mpa_id integer (Внешний ключ): Идентификатор рейтинга Ассоциации кинокомпаний.

## Таблица user.

### Хранит информацию о пользователях.

user_id integer (Первичный ключ): Уникальный идентификатор пользователя.

login varchar: Логин пользователя.

email varchar: Электронная почта пользователя.

user_name varchar: Имя пользователя.

birthday date: Дата рождения пользователя.

## Таблица friends.

### Хранит информацию о дружбе пользователей.

user_id integer (Первичный ключ): Идентификатор пользователя.

friend_id integer (Внешний ключ): Идентификатор другого пользователя(потенциального друга). 

## Таблица film_likes.

### Хранит информацию о лайках, которые пользователи поставили фильмам.

user_id integer (Первичный ключ): Идентификатор пользователя.

film_id integer (Внешний ключ): Идентификатор фильма.

## Таблица film_genres.

### Хранит информацию о жанре фильма.

film_id integer (Первичный ключ): Идентификатор фильма.

genre_id integer (Внешний ключ): Идентификатор жанра.

## Таблица genre.

### Хранит информацию о жанрах.

genre_id integer (Первичный ключ): Идентификатор жанра.

genre_name varchar: Наименование жанра.

## Таблица mpa.

### Хранит информацию о рейтинге фильма.

mpa_id (Первичный ключ): Идентификатор рейтинга Ассоциации кинокомпаний.

mpa_name: Наименование рейтинга.
