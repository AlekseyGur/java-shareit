package ru.practicum.shareit.comment.interfaces;

import java.util.List;
import java.util.Optional;

import ru.practicum.shareit.comment.dto.CommentDto;

public interface CommentService {

    List<CommentDto> getAll();

    Optional<CommentDto> getById(Long id);

    CommentDto add(CommentDto comment);

    CommentDto update(CommentDto comment);

    void delete(Long id);

    List<CommentDto> findByItemId(Long postId);

    List<CommentDto> findByAuthorId(Long author);
}