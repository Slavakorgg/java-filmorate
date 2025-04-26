package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.JdbcMpaRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final JdbcMpaRepository jdbcMpaRepository;

    @Override
    public Mpa getById(int id) throws DataNotFoundException {
        if (!jdbcMpaRepository.mpaExist(id)) {
            throw new DataNotFoundException("Некорректный id");
        }
        return jdbcMpaRepository.getById(id);
    }

    @Override
    public Collection<Mpa> getAllMpa() {
        return jdbcMpaRepository.getAll();
    }
}
