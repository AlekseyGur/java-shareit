package ru.practicum.shareit.item;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.practicum.shareit.ShareItApp;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.model.User;

@SpringBootTest(classes = ShareItApp.class)
public class ItemControllerTest {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    @Test
    void add() {
        User user = new User();
        user.setName("name");
        user.setEmail(genEmail());

        User userSaved = userService.add(user);

        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);
        item.setOwner(userSaved.getId());

        Item itemSaved = itemService.add(item);
        assertTrue(itemSaved.getId() > 0, "Вещь должна добавиться");
    }

    @Test
    void addNoUser() {
        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);

        assertThrows(Exception.class, () -> itemService.add(item),
                "Надо указывать пользователя");
    }

    @Test
    void delete() {
        User user = new User();
        user.setName("name");
        user.setEmail(genEmail());

        User userSaved = userService.add(user);

        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);
        item.setOwner(userSaved.getId());

        Item itemSaved = itemService.add(item);
        assertTrue(itemSaved.getId() > 0, "Вещь должна добавиться");

        itemService.delete(itemSaved.getId());
        assertThrows(Exception.class, () -> itemService.get(item.getId()),
                "Вещь должна удалиться");
    }

    @Test
    void getByUserId() {
        User user = new User();
        user.setName("name");
        user.setEmail(genEmail());

        User userSaved = userService.add(user);

        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);
        item.setOwner(userSaved.getId());

        itemService.add(item);

        List<Item> itemSaved = itemService.getByUserId(userSaved.getId());
        assertTrue(itemSaved.size() == 1, "Вещь должна добавиться");
    }

    @Test
    void patch() {
        User user = new User();
        user.setName("name");
        user.setEmail(genEmail());

        User userSaved = userService.add(user);

        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);
        item.setOwner(userSaved.getId());

        Item itemSaved = itemService.add(item);

        Item item2 = new Item();
        item2.setId(itemSaved.getId());
        item2.setName("No name");
        item2.setDescription("New");
        item2.setAvailable(true);
        item2.setOwner(userSaved.getId());

        itemService.patch(item2);

        Item itemUpdated = itemService.get(itemSaved.getId());
        assertTrue(itemUpdated.getName().equals(item2.getName()), "Имя должно измениться");
        assertTrue(itemUpdated.getDescription().equals(item2.getDescription()), "Описание должно измениться");
    }

    @Test
    void search() {
        User user = new User();
        user.setName("name");
        user.setEmail(genEmail());

        User userSaved = userService.add(user);

        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);
        item.setOwner(userSaved.getId());

        itemService.add(item);

        Item item2 = new Item();
        item2.setName("No name");
        item2.setDescription("New");
        item2.setAvailable(true);
        item2.setOwner(userSaved.getId());

        itemService.add(item2);

        List<Item> items = itemService.find("crIpTi");
        assertTrue(items.size() == 1, "Вещь должна найтись");
    }

    private String genEmail() {
        String emailPrefix = "itemcontrollertest";
        String randomChars = "";
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            randomChars += (char) (random.nextInt(26) + 'a');
        }
        return emailPrefix + randomChars + "@test.test";
    }
}
