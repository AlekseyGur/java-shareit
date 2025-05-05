package ru.practicum.shareit.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.interfaces.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto addComment(
            @PathVariable Long itemId,
            @RequestHeader(value = "X-Sharer-User-Id", required = true) Long userId,
            @RequestBody CommentDto comment) {
        comment.setAuthorId(userId);
        comment.setItemId(itemId);
        return commentService.add(comment);
    }
}
