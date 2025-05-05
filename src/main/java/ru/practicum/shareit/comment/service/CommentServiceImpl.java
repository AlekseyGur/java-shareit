package ru.practicum.shareit.comment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.interfaces.CommentService;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.comment.mapper.CommentMapper;
import ru.practicum.shareit.comment.model.Comment;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDto> getAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    @Override
    public Optional<CommentDto> getById(Long id) {
        return commentRepository.findById(id)
                .map(CommentMapper::toDto);
    }

    @Override
    public CommentDto add(CommentDto commentDto) {
        Comment comment = CommentMapper.toComment(commentDto);
        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto update(CommentDto commentDto) {
        Comment comment = CommentMapper.toComment(commentDto);
        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> findByItemId(Long postId) {
        List<Comment> comments = commentRepository.findByItemId(postId);
        return comments.stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    @Override
    public List<CommentDto> findByAuthorId(Long author) {
        List<Comment> comments = commentRepository.findByAuthorId(author);
        return comments.stream()
                .map(CommentMapper::toDto)
                .toList();
    }
}
