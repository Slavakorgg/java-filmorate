package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<User> mapper;

    @Override
    public User save(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", user.getLogin());
        params.addValue("email", user.getEmail());
        params.addValue("user_name", user.getName());
        params.addValue("birthday", user.getBirthday());
        jdbc.update("INSERT INTO USERS (LOGIN, EMAIL, USER_NAME, BIRTHDAY) VALUES(:login,:email ,:user_name ,:birthday )", params, keyHolder);
        user.setId(keyHolder.getKeyAs(Integer.class));
        return user;
    }

    @Override
    public User update(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", user.getLogin());
        params.addValue("email", user.getEmail());
        params.addValue("user_name", user.getName());
        params.addValue("birthday", user.getBirthday());
        params.addValue("user_id", user.getId());
        jdbc.update("UPDATE PUBLIC.USERS SET LOGIN=:login, EMAIL=:email, USER_NAME=:user_name, BIRTHDAY=:birthday WHERE USER_ID=:user_id", params, keyHolder);
        return user;


    }

    @Override
    public Optional<User> getById(int userId) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        return Optional.ofNullable(jdbc.queryForObject("SELECT * FROM USERS WHERE USER_ID = :user_id", params, mapper));
    }

    @Override
    public Collection<User> getUsers() {
        final String query = "select * FROM USERS";
        return jdbc.query(query, mapper);

    }

    @Override
    public void addFriend(int userId, int friendId) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        params.addValue("friend_id", friendId);
        jdbc.update("INSERT INTO FRIENDS(USER_ID, FRIEND_ID) VALUES(:user_id,:friend_id)", params, keyHolder);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        params.addValue("friend_id", friendId);
        jdbc.update("DELETE FROM FRIENDS WHERE USER_ID=:user_id AND FRIEND_ID=:friend_id", params, keyHolder);
    }

    @Override
    public List<User> getUserFriends(int userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        return jdbc.query("SELECT * FROM USERS where USER_ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = :user_id) ", params, mapper);

    }

}


