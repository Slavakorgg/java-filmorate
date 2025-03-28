package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getUsers();

    User createUser(User user) throws ValidationException;

    User updateUser(User newUser) throws DataNotFoundException;

    User get(int id);
}
