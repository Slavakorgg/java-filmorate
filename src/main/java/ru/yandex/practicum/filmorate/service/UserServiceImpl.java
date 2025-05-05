package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.JdbcUserRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JdbcUserRepository jdbcUserRepository;

    @Override
    public Collection<User> getUsers() {
        return jdbcUserRepository.getUsers();
    }

    @Override
    public User createUser(User user) throws ValidationException {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        return jdbcUserRepository.save(user);

    }

    @Override
    public User updateUser(User newUser) throws DataNotFoundException {
        jdbcUserRepository.getById(newUser.getId()); //если id не правильный, то после этого метода вернётся нужная ошибка

        return jdbcUserRepository.update(newUser);


    }

    @Override
    public void addFriend(int id, int friendId) throws DataNotFoundException {
        jdbcUserRepository.getById(id);
        jdbcUserRepository.getById(friendId);

        jdbcUserRepository.addFriend(id, friendId);
    }

    @Override
    public void deleteFriend(int id, int friendId) throws DataNotFoundException {
        jdbcUserRepository.getById(id);
        jdbcUserRepository.getById(friendId);
        jdbcUserRepository.deleteFriend(id, friendId);
    }

    @Override
    public List<User> getUserFriends(int id) throws DataNotFoundException {
        jdbcUserRepository.getById(id);
        return jdbcUserRepository.getUserFriends(id);

    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) throws DataNotFoundException {
        List<User> userList = getUserFriends(id);
        return getUserFriends(otherId).stream()
                .filter(userList::contains)
                .collect(Collectors.toList());


    }


}