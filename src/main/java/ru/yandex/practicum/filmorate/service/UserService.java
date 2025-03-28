package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User createUser(User user) throws ValidationException {

        return userStorage.createUser(user);
    }

    public User updateUser(User newUser) throws DataNotFoundException {
        return userStorage.updateUser(newUser);

    }

    public void addFriend(int id, int friendId) throws DataNotFoundException {

        User user = userStorage.get(id);
        User friend = userStorage.get(friendId);
        if (user == null) {
            throw new DataNotFoundException("Искомый пользователь не найден");

        }
        if (friend == null) {
            throw new DataNotFoundException("Искомый друг не найден");

        }
        user.getFriendsList().add(friendId);
        friend.getFriendsList().add(id);
    }

    public void deleteFriend(Integer id, int friendId) throws DataNotFoundException {

        User user = userStorage.get(id);
        User friend = userStorage.get(friendId);
        if (user == null) {
            throw new DataNotFoundException("Искомый пользователь не найден");

        }
        if (friend == null) {
            throw new DataNotFoundException("Искомый друг не найден");

        }
        user.getFriendsList().remove(friendId);
        friend.getFriendsList().remove(id);
    }

    public List<User> getUserFriends(int id) throws DataNotFoundException {

        User user = userStorage.get(id);
        if (user == null) {
            throw new DataNotFoundException("Искомый пользователь не найден");

        }
        List<User> friends = new ArrayList<>();
        for (Integer friend : user.getFriendsList()) {
            friends.add(userStorage.get(friend));
        }
        return friends;

    }

    public List<User> getCommonFriends(int id, int otherId) {
        List<User> userList = getUserFriends(id);
        List<User> otherUserList = getUserFriends(otherId);
        List<User> commonFriends = new ArrayList<>();
        for (User user : userList) {
            if (otherUserList.contains(user)) {
                commonFriends.add(user);
            }
        }
        return commonFriends;


    }


}
