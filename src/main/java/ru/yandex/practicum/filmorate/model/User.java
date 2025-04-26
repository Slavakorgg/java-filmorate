package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private Set<Integer> friendsList = new HashSet<>();
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

}