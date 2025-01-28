package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new ConcurrentHashMap<>();
    private int nextId = 1;

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }


    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException {
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

    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) throws ValidationException {

        if (newFilm.getId() != null && films.containsKey(newFilm.getId())) {
            films.put(newFilm.getId(), newFilm);
            log.debug("Фильм успешно изменён");
            return newFilm;
        }
        throw new ValidationException("Некорректный Id");
    }

    private int getNextId() {
        return nextId++;

    }
}
