package ru.yandex.practicum.filmorate.exception;

public class DataNotFoundException extends NullPointerException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
