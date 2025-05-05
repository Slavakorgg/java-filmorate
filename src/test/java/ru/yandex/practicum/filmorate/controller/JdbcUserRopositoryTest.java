package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.JdbcUserRepository;
import ru.yandex.practicum.filmorate.repository.mappers.UserRowMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcUserRepository.class, UserRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JdbcUserRopositoryTest {
    public static final int TEST_USER_ID = 1;
    private final JdbcUserRepository jdbcUserRepository;
    private final UserRowMapper mapper;

    static User getTestUser() {
        User user = new User();
        user.setId(TEST_USER_ID);
        user.setLogin("userLogin");
        user.setEmail("email@email.com");
        user.setName("user");
        user.setBirthday(LocalDate.of(2000, 3, 22));
        return user;
    }

    @Test
    public void find_by_id() {
        Optional<User> userOptional = jdbcUserRepository.getById(TEST_USER_ID);

        assertThat(userOptional)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(getTestUser());
    }
}
