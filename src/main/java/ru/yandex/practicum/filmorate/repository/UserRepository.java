package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    User update(User user);

    Optional<User> getById(int userId);

    Collection<User> getUsers();

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    List<User> getUserFriends(int userId);
}
