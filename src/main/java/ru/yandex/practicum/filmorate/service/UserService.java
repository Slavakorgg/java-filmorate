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

        User user = get(id);
        User friend = get(friendId);

        user.getFriendsList().add(friendId);
        friend.getFriendsList().add(id);
    }

    public void deleteFriend(Integer id, int friendId) throws DataNotFoundException {

        User user = get(id);
        User friend = get(friendId);

        user.getFriendsList().remove(friendId);
        friend.getFriendsList().remove(id);
    }

    public List<User> getUserFriends(int id) throws DataNotFoundException {

        User user = get(id);

        List<User> friends = new ArrayList<>();
        for (Integer friend : user.getFriendsList()) {
            friends.add(get(friend));
        }
        return friends;

    }

    public List<User> getCommonFriends(int id, int otherId) throws DataNotFoundException {
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


    public User get(int userId) throws DataNotFoundException {

        return userStorage.get(userId);
    }
}
