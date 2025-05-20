package ru.practicum.shareit.comment.interfaces;

import java.util.List;

import ru.practicum.shareit.comment.dto.CommentDto;

public interface CommentService {

    List<CommentDto> getAll();

    CommentDto get(Long id);

    CommentDto add(CommentDto comment);

    CommentDto update(CommentDto comment);

    void delete(Long id);

    List<CommentDto> findByItemId(Long itemId);

    List<CommentDto> findByItemId(List<Long> itemIds);

    List<CommentDto> findByAuthorId(Long authorId);
}