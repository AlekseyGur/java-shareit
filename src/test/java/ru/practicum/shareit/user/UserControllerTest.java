package ru.practicum.shareit.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.practicum.shareit.ShareItApp;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.model.User;

@SpringBootTest(classes = ShareItApp.class)
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private UserService userService;

    @Test
    void add() {
        User user = new User();
        user.setName("name");
        user.setEmail(genEmail());

        User userSaved = userController.add(user);
        assertTrue(userSaved.getId() > 0, "Пользователь должен добавиться");
    }

    @Test
    void addBadEmail() {
        User user = new User();
        user.setName("name");
        user.setEmail("test_test.test");

        assertThrows(Exception.class, () -> userController.add(user),
                "Пользователь НЕ должен добавиться");
    }

    @Test
    void addBadName() {
        User user = new User();
        user.setName("");
        user.setEmail(genEmail());

        User userSaved = userController.add(user);
        assertTrue(userSaved.getId() > 0, "Пользователь должен добавиться");
    }

    @Test
    void patchName() {
        User user = new User();
        user.setName("name");
        user.setEmail(genEmail());

        User userCreated = userController.add(user);

        User user2 = new User();
        user2.setId(userCreated.getId());
        user2.setName("New");
        userService.patch(user2);

        User userSaved = userController.get(userCreated.getId());
        assertTrue(userSaved.getName().equals("New"), "Имя должно измениться");
    }

    @Test
    void patchEmail() {
        User user = new User();
        user.setName("name");
        user.setEmail(genEmail());

        User userCreated = userController.add(user);

        User user2 = new User();
        user2.setId(userCreated.getId());
        user2.setEmail(genEmail());
        userService.patch(user2);

        User userSaved = userController.get(userCreated.getId());
        assertTrue(userSaved.getEmail().equals(user2.getEmail()), "Email должно измениться");
    }

    @Test
    void patchUnknownUser() {
        User user = new User();
        user.setId(999999999999L);
        user.setName("name");
        user.setEmail(genEmail());

        assertThrows(Exception.class, () -> userService.patch(user),
                "Нельзя изменить несуществующего пользователя");
    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setName("name");
        user.setEmail(genEmail());

        User userSaved = userController.add(user);
        assertTrue(userSaved.getId() > 0, "Пользователь должен добавиться");

        userController.delete(userSaved.getId());
        assertThrows(Exception.class, () -> userController.get(userSaved.getId()),
                "Пользователь должен удалиться");
    }

    private String genEmail() {
        String emailPrefix = "usercontrollertest";
        String randomChars = "";
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            randomChars += (char) (random.nextInt(26) + 'a');
        }
        return emailPrefix + randomChars + "@test.test";
    }
}
