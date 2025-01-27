package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmControllerTest {

    @Autowired
    private FilmController filmController;

    @Test
    public void returnFilmsTest() {
        Film film1 = new Film();
        film1.setName("filmName1");
        film1.setDescription("Descr1");
        film1.setReleaseDate(LocalDate.of(2023, 12, 15));
        film1.setDuration(168L);

        Film film2 = new Film();
        film2.setName("filmName2");
        film2.setDescription("Descr2");
        film2.setReleaseDate(LocalDate.of(2023, 12, 15));
        film2.setDuration(168L);

        filmController.createFilm(film1);
        filmController.createFilm(film2);

        Collection<Film> films = filmController.getFilms();

        assertTrue(films.contains(film1));
        assertTrue(films.contains(film2));
    }

    @Test
    void filmsValidTest() {
        Film film = new Film();
        film.setName("filmName1");
        film.setDescription("Descr1");
        film.setReleaseDate(LocalDate.of(2023, 12, 15));
        film.setDuration(168L);

        Film savedFilm = filmController.createFilm(film);

        assertNotNull(savedFilm.getId());
        assertEquals("filmName1", savedFilm.getName());
        assertEquals("Descr1", savedFilm.getDescription());
        assertEquals(LocalDate.of(2023, 12, 15), savedFilm.getReleaseDate());
        assertEquals(168L, savedFilm.getDuration());

        film.setName(null);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));

        film.setName("filmName1");
        film.setDescription("1111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));

        film.setDescription("Descr1");
        film.setReleaseDate(LocalDate.of(1894, 12, 15));
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));

        film.setReleaseDate(LocalDate.of(2023, 12, 15));
        film.setDuration(-1L);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    void updateFilmTest() {
        Film film = new Film();
        film.setName("filmName1");
        film.setDescription("Descr1");
        film.setReleaseDate(LocalDate.of(2023, 12, 15));
        film.setDuration(168L);

        filmController.createFilm(film);

        Film filmUpdate = new Film();
        filmUpdate.setId(8);
        assertThrows(ValidationException.class, () -> filmController.updateFilm(filmUpdate));
    }
}
