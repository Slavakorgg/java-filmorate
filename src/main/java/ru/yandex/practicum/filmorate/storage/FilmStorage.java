package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> getFilms();

    Film createFilm(Film film) throws ValidationException;


    Film updateFilm(Film newFilm) throws DataNotFoundException;

    Film get(int filmId);
}
