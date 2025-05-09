package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.JdbcGenreRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final JdbcGenreRepository jdbcGenreRepository;

    @Override
    public Genre getGenreById(int id) throws DataNotFoundException {
        if (!jdbcGenreRepository.genreExist(id)) {
            throw new DataNotFoundException("Некорректный id");
        }
        return jdbcGenreRepository.getById(id);
    }

    @Override
    public Collection<Genre> getGenres() {
        return jdbcGenreRepository.getAll();
    }
}
