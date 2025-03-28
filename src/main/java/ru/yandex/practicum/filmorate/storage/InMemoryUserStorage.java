package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private int nextId = 1;

    @Override
    public Collection<User> getUsers() {
        return users.values();
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
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.debug("Пользователь успешно добавлен");
        return user;
    }

    @Override
    public User updateUser(User newUser) throws DataNotFoundException {
        if (newUser.getId() != null && users.containsKey(newUser.getId())) {
            users.put(newUser.getId(), newUser);
            log.debug("Пользователь успешно изменён");
            return newUser;
        }

        throw new DataNotFoundException("Некорректный Id");

    }

    @Override
    public User get(int id) {
        return users.get(id);
    }

    private int getNextId() {
        return nextId++;

    }
}
