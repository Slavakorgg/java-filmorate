package ru.yandex.practicum.filmorate.controller;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserControllerTest {


   /* @Autowired
    private UserController userController;

    @Test
    public void returnsUsersTest() throws ValidationException {
        User user1 = new User();
        user1.setEmail("user1@yandex.ru");
        user1.setLogin("user1");
        user1.setName("User1");
        user1.setBirthday(LocalDate.of(1999, 8, 6));

        User user2 = new User();
        user2.setEmail("user2@yandex.ru");
        user2.setLogin("user2");
        user2.setName("User2");
        user2.setBirthday(LocalDate.of(1991, 3, 20));

        userController.createUser(user1);
        userController.createUser(user2);

        Collection<User> users = userController.getUsers();

        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    void userValidTest() throws ValidationException {
        User user = new User();
        user.setEmail("user1@yandex.ru");
        user.setLogin("user1");
        user.setName("user1");
        user.setBirthday(LocalDate.of(1999, 8, 6));

        User savedUser1 = userController.createUser(user);

        assertNotNull(savedUser1.getId());
        assertEquals("user1@yandex.ru", savedUser1.getEmail());
        assertEquals("user1", savedUser1.getLogin());
        assertEquals("user1", savedUser1.getName());
        assertEquals(LocalDate.of(1999, 8, 6), savedUser1.getBirthday());

        user.setEmail("12345");
        assertThrows(ValidationException.class, () -> userController.createUser(user));

        user.setEmail("user3.com");
        assertThrows(ValidationException.class, () -> userController.createUser(user));

        user.setEmail("user1@yandex.ru");
        user.setLogin("");
        assertThrows(ValidationException.class, () -> userController.createUser(user));

        user.setLogin(null);
        assertThrows(ValidationException.class, () -> userController.createUser(user));

        user.setLogin("user 1");
        assertThrows(ValidationException.class, () -> userController.createUser(user));

        user.setBirthday(LocalDate.now().plusMonths(5));
        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
    void updateUserTest() throws ValidationException {
        User user1 = new User();
        user1.setEmail("user1@yandex.ru");
        user1.setLogin("user1");
        user1.setName(null);
        user1.setBirthday(LocalDate.of(1999, 8, 6));

        userController.createUser(user1);

        User userUpdate = new User();
        userUpdate.setEmail("user1@yandex.ru");
        userUpdate.setLogin("user1");
        userUpdate.setName(null);
        userUpdate.setBirthday(LocalDate.of(1999, 8, 6));
        userUpdate.setId(8);
        assertThrows(NotFoundException.class, () -> userController.updateUser(userUpdate));
    }*/
}
