package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final List<User> users = new ArrayList<>();
    private int nextId = 1;

    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        user.setId(getNextId());
        users.add(user);
        log.debug("Пользователь успешно добавлен");
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        for (User user : users) {
            if (newUser != null && user.getId() == newUser.getId()) {
                users.remove(user);
                users.add(newUser);
                log.debug("Пользователь успешно изменён");
                return newUser;
            }
        }
        throw new ValidationException("Некорректный Id");

    }


    private int getNextId() {
        return nextId++;

    }
}
