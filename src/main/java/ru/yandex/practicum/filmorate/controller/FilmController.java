package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final List<Film> films = new ArrayList<>();
    private int nextId = 1;

    @GetMapping
    public List<Film> getFilms() {
        return films;
    }


    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        LocalDate date = LocalDate.of(1895, Month.DECEMBER, 28);
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(date)) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        film.setId(getNextId());
        films.add(film);
        log.debug("Фильм успешно добавлен");
        return film;

    }

    @PutMapping
    public Film updateFilm(@RequestBody Film newFilm) {
        for (Film film : films) {
            if (newFilm != null && film.getId() == newFilm.getId()) {
                films.remove(film);
                films.add(newFilm);
                log.debug("Фильм успешно изменён");
                return newFilm;
            }
        }
        throw new ValidationException("Некорректный Id");
    }

    private int getNextId() {
        return nextId++;

    }
}
