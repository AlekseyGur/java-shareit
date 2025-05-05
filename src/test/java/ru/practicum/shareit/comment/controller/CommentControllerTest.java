package ru.practicum.shareit.comment.controller;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.interfaces.CommentService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.system.config.ShareItApp;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ShareItApp.class)
class CommentControllerTest {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void add() {
        UserDto userDto = new UserDto();
        userDto.setEmail(genEmail());
        userDto.setName("username");
        UserDto userSaved = userService.save(userDto);

        ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(true);
        itemDto.setName("Item name");
        itemDto.setDescription("Item descritption");
        itemDto.setOwner(userSaved);
        ItemDto itemSaved = itemService.add(itemDto, userSaved.getId());

        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorId(userSaved.getId());
        commentDto.setItemId(itemSaved.getId());
        commentDto.setText("Comment text");

        CommentDto commentSaved = commentService.add(commentDto);
        List<CommentDto> comments = commentService.getAll();

        assertTrue(comments.contains(commentSaved));
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void findByItemId() {
    }

    @Test
    void findByAuthorId() {
    }

    private String genEmail() {
        String emailPrefix = "comment.service";
        String randomChars = "";
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            randomChars += (char) (random.nextInt(26) + 'a');
        }
        return emailPrefix + randomChars + "@test.test";
    }
}