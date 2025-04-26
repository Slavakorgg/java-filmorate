package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;


import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping(path = "/users")
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(path = "/users")
    public User createUser(@RequestBody User user) throws ValidationException {

        return userService.createUser(user);
    }

    @PutMapping(path = "/users")
    public User updateUser(@RequestBody User newUser) throws DataNotFoundException {
        return userService.updateUser(newUser);

    }


    @PutMapping(path = "/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") int id, @PathVariable("friendId") int friendId) throws DataNotFoundException {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(path = "/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") int id, @PathVariable("friendId") int friendId) throws DataNotFoundException {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping(path = "/users/{id}/friends")
    public List<User> getUserFriends(@PathVariable("id") int id) throws DataNotFoundException {
        return userService.getUserFriends(id);
    }

    @GetMapping(path = "/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") int id, @PathVariable("otherId") int otherId) throws DataNotFoundException {
        return userService.getCommonFriends(id, otherId);
    }
}