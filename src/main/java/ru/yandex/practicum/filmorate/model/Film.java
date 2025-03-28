package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Film {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private Set<Integer> likes = new HashSet<>();


}
