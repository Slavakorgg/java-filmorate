package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film createFilm(Film film) throws ValidationException {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film newFilm) throws DataNotFoundException {

        return filmStorage.updateFilm(newFilm);
    }

    public void addLike(int id, int userId) throws DataNotFoundException {
        Film film = get(id);
        User user = userService.get(userId);

        film.getLikes().add(userId);
        log.info("add like: {}", film);
    }

    public void deleteLike(int id, int userId) throws DataNotFoundException {
        Film film = get(id);
        User user = userService.get(userId);

        film.getLikes().remove(userId);
        log.info("delete like: {}", film);
    }

    public List<Film> getPopularFilms(int count) {
        List<Film> popularFilms = new ArrayList<>(filmStorage.getFilms());
        popularFilms.sort(FILM_COMPARATOR.reversed());
        return popularFilms.stream().limit(count).collect(Collectors.toList());

    }

    public static final Comparator<Film> FILM_COMPARATOR = new Comparator<Film>() {
        @Override
        public int compare(Film o1, Film o2) {
            return o1.getLikes().size() - o2.getLikes().size();
        }

    };

    public Film get(int filmId) throws DataNotFoundException {
        return filmStorage.get(filmId);
    }
}
