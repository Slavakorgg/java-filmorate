package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreRepository {
    Genre getById(int id);

    Collection<Genre> getAll();

    boolean genreExist(int id);
}
