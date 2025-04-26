package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.mappers.MpaRowMapper;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class JdbcMpaRepository implements MpaRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final MpaRowMapper mapper;

    @Override
    public Mpa getById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mpa_id", id);
        return jdbc.queryForObject("SELECT * FROM MPA WHERE MPA_ID = :mpa_id", params, mapper);
    }

    @Override
    public Collection<Mpa> getAll() {
        final String query = "select * FROM MPA ORDER BY MPA_ID";
        return jdbc.query(query, mapper);
    }

    @Override
    public boolean mpaExist(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mpa_id", id);
        return jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM MPA WHERE mpa_id =:mpa_id)", params, boolean.class);
    }
}
