package ru.yandex.practicum.filmorate.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setLogin(rs.getString("login"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("user_name"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        return user;
    }
}
