package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<User> getUsers();

    User createUser(User user) throws ValidationException;

    User updateUser(User newUser) throws DataNotFoundException;

    void addFriend(int id, int friendId) throws DataNotFoundException;

    void deleteFriend(int id, int friendId) throws DataNotFoundException;

    List<User> getUserFriends(int id) throws DataNotFoundException;

    List<User> getCommonFriends(int id, int otherId) throws DataNotFoundException;
}
