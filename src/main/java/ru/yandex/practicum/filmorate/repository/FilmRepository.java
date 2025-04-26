package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmRepository {
    Film save(Film film);

    Film update(Film film);

    Film getById(int id);

    Collection<Film> getFilms();

    void setGenre(Film film);


    void getGenre(Film film);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    Collection<Film> getPopularFilms(int count);
}
