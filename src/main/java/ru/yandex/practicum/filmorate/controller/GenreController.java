package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping(path = "/genres/{id}")
    public Genre getGenreById(@PathVariable("id") int id) throws DataNotFoundException {
        return genreService.getGenreById(id);

    }

    @GetMapping(path = "/genres")
    public Collection<Genre> getGenres() {
        return genreService.getGenres();

    }
}
