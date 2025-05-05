package ru.practicum.shareit.comment.controller;

import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.interfaces.CommentService;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.system.config.ShareItApp;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.model.User;

import com.sun.jdi.InternalException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.*;

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
    void addNewComment() {
        User user = new User();
        user.setEmail(genEmail());
        user.setName("username");

        Item item = new Item();
        item.setDescription("Item descritption");
        item.setOwner(user);

        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorId(user.getId());
        commentDto.setItemId(item.getId());
        commentDto.setText("Comment text");
    }

    @Test
    void deleteComment() {
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