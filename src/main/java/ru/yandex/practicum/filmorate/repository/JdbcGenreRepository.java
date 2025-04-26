package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.mappers.GenreRowMapper;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcOperations jdbc;
    private final GenreRowMapper mapper;

    @Override
    public Genre getById(int id) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genre_id", id);
        return jdbc.queryForObject("SELECT * FROM GENRES WHERE GENRE_ID = :genre_id", params, mapper);
    }

    @Override
    public Collection<Genre> getAll() {
        final String query = "select * FROM GENRES ORDER BY GENRE_ID ";
        return jdbc.query(query, mapper);
    }

    @Override
    public boolean genreExist(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genre_id", id);
        return jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM FILM_GENRES WHERE genre_id =:genre_id)", params, boolean.class);
    }
}
