package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.repository.mappers.GenreRowMapper;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final FilmRowMapper mapper;
    private final GenreRowMapper genreRowMapper;


    @Override
    public Film save(Film film) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", film.getName());
        params.addValue("description", film.getDescription());
        params.addValue("release_date", film.getReleaseDate());
        params.addValue("duration", film.getDuration());
        params.addValue("mpa_id", film.getMpa().getId());

        jdbc.update("INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) VALUES(:name,:description ,:release_date ,:duration ,:mpa_id)", params, keyHolder);

        film.setId(keyHolder.getKeyAs(Integer.class));
        if (film.getGenres() != null) {
            setGenre(film);
        }
        return film;
    }

    @Override
    public Film update(Film film) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", film.getName());
        params.addValue("description", film.getDescription());
        params.addValue("release_date", film.getReleaseDate());
        params.addValue("duration", film.getDuration());
        params.addValue("mpa_id", film.getMpa().getId());
        params.addValue("film_id", film.getId());
        jdbc.update("DELETE FROM FILM_GENRES WHERE FILM_ID=:film_id", params, keyHolder);

        if (film.getGenres() != null) {
            setGenre(film);
        }
        film.setGenres(new LinkedHashSet<>());
        jdbc.update("UPDATE FILMS SET NAME=:name, DESCRIPTION=:description, RELEASE_DATE=:release_date, DURATION=:duration, MPA_ID=:mpa_id WHERE FILM_ID=:film_id", params, keyHolder);


        return film;
    }


    @Override
    public Film getById(int id) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("film_id", id);
        Film film = jdbc.queryForObject("SELECT f.*, m.MPA_NAME FROM FILMS f JOIN MPA m ON f.MPA_ID = m.MPA_ID WHERE f.FILM_ID = :film_id", params, mapper);
        if (film.getGenres() != null) {
            getGenre(film);
        }
        return film;


    }

    @Override
    public Collection<Film> getFilms() {
        final String query = "SELECT f.*, m.MPA_NAME FROM FILMS f JOIN MPA m ON f.MPA_ID = m.MPA_ID";
        Collection<Film> films = jdbc.query(query, mapper);


        for (Film film : films) {

            getGenre(film);

        }
        return films;

    }

    @Override
    public void setGenre(Film film) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("film_id", film.getId());
        Set<Genre> duplicateGenres = new HashSet<>();

        if (film.getGenres() != null) {
            Collection<Genre> genres = film.getGenres();
            for (Genre genre : genres) {
                if (!duplicateGenres.contains(genre)) {
                    params.addValue("genre_id", genre.getId());
                    jdbc.update("INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES(:film_id, :genre_id);", params, keyHolder);
                    duplicateGenres.add(genre);
                }
            }
        }

    }

    @Override
    public void getGenre(Film film) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("film_id", film.getId());

        Collection<Genre> genres = jdbc.query("SELECT g.GENRE_ID AS id ,g.GENRE_NAME AS name FROM FILM_GENRES fg JOIN GENRES g ON fg.GENRE_ID = g.GENRE_ID WHERE fg.FILM_ID = :film_id", params, genreRowMapper);
        LinkedHashSet<Genre> filmGenres = new LinkedHashSet<>(genres);
        film.setGenres(filmGenres);
    }

    @Override
    public void addLike(int filmId, int userId) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        params.addValue("film_id", filmId);
        jdbc.update("INSERT INTO FILM_LIKES (USER_ID, FILM_ID) VALUES(:user_id, :film_id)", params, keyHolder);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        params.addValue("film_id", filmId);
        jdbc.update("DELETE FROM FILM_LIKES WHERE FILM_ID=:film_id AND USER_ID=:user_id;", params, keyHolder);
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("limit", count);
        Collection<Film> popularFilms = jdbc.query("SELECT f.*, m.MPA_NAME , COUNT(fl.USER_ID) AS likes  FROM FILMS f JOIN FILM_LIKES fl ON fl.FILM_ID = f.FILM_ID JOIN MPA m ON m.MPA_ID = f.MPA_ID GROUP BY f.FILM_ID ORDER BY COUNT(fl.USER_ID) DESC LIMIT :limit", params, mapper);
        return popularFilms;

    }
}
