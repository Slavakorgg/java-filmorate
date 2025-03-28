package ru.yandex.practicum.filmorate.storage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new ConcurrentHashMap<>();
    private int nextId = 1;

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Film createFilm(Film film) throws ValidationException {
        LocalDate date = LocalDate.of(1895, Month.DECEMBER, 28);
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription() == null || film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(date)) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.debug("Фильм успешно добавлен");
        return film;
    }

    @Override
    public Film updateFilm(Film newFilm) throws DataNotFoundException {
        if (newFilm.getId() != null && films.containsKey(newFilm.getId())) {
            films.put(newFilm.getId(), newFilm);
            log.debug("Фильм успешно изменён");
            return newFilm;
        }
        throw new DataNotFoundException("Некорректный Id");
    }

    @Override
    public Film get(int id) {
        return films.get(id);
    }

    private int getNextId() {
        return nextId++;

    }
}
