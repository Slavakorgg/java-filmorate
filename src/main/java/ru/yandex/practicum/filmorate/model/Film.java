package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

import java.util.LinkedHashSet;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Film {
    private Integer id;
    private String name;
    private String description;
    @NotNull
    private LocalDate releaseDate;
    private Long duration;
    @NotNull
    private Mpa mpa;
    private LinkedHashSet<Genre> genres;


}