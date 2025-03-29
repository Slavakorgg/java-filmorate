package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;


import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService filmService;

    @GetMapping(path = "/films")
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }


    @PostMapping(path = "/films")
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.createFilm(film);

    }

    @PutMapping(path = "/films")
    public Film updateFilm(@RequestBody Film newFilm) throws DataNotFoundException {

        return filmService.updateFilm(newFilm);
    }

    @PutMapping(path = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable("id") int id, @PathVariable("userId") int userId) throws DataNotFoundException {

        filmService.addLike(id, userId);
    }

    @DeleteMapping(path = "/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") int id, @PathVariable("userId") int userId) throws DataNotFoundException {
        filmService.deleteLike(id, userId);
    }

    @GetMapping(path = "/films/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);

    }

}
