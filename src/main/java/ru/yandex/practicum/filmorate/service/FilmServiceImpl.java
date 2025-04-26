package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.JdbcFilmRepository;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final JdbcFilmRepository jdbcFilmRepository;
    private final UserServiceImpl userService;

    @Override
    public Collection<Film> getFilms() {
        return jdbcFilmRepository.getFilms();
    }

    @Override
    public Film getFilmById(int id) {
        return jdbcFilmRepository.getById(id);
    }

    @Override
    public Film createFilm(Film film) throws ValidationException, DataNotFoundException {
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
        if (film.getMpa().getId() > 6 || film.getMpa().getId() < 0) {
            throw new DataNotFoundException("Некорректный возрастной рейтинг");
        }
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                if (genre.getId() > 6) {
                    throw new DataNotFoundException("Некорректный жанр");
                }
            }
        }
        return jdbcFilmRepository.save(film);
    }

    @Override
    public Film updateFilm(Film newFilm) throws DataNotFoundException {
        jdbcFilmRepository.getById(newFilm.getId());
        return jdbcFilmRepository.update(newFilm);
    }

    @Override
    public void addLike(int filmId, int userId) throws DataNotFoundException {
        jdbcFilmRepository.addLike(filmId, userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) throws DataNotFoundException {
        jdbcFilmRepository.deleteLike(filmId, userId);
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        return jdbcFilmRepository.getPopularFilms(count);

    }

    @Override
    public Film get(int filmId) throws DataNotFoundException {
        return filmStorage.get(filmId);
    }
}