package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Collection<Film> getFilms();

    Film getFilmById(int id);

    Film createFilm(Film film) throws ValidationException, DataNotFoundException;

    Film updateFilm(Film newFilm) throws DataNotFoundException;

    void addLike(int filmId, int userId) throws DataNotFoundException;

    void deleteLike(int filmId, int userId) throws DataNotFoundException;

    Collection<Film> getPopularFilms(int count);

    Film get(int filmId) throws DataNotFoundException;
}
