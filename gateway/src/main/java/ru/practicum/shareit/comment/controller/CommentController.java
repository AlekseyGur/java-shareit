package ru.practicum.shareit.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.comment.CommentClient;
import ru.practicum.shareit.comment.dto.CommentDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Validated
public class CommentController {
    private final CommentClient commentClient;

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommentDto> addComment(
            @PathVariable @Positive Long itemId,
                    @RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId,
            @Valid @RequestBody CommentDto comment) {
        comment.setAuthorId(userId);
        comment.setItemId(itemId);
        return commentClient.add(userId, itemId, comment);
    }
}
