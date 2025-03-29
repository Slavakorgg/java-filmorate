package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Set<Integer> friendsList = new HashSet<>();
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

}
