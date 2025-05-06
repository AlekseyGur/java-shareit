package ru.practicum.shareit.comment.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.interfaces.CommentService;
import ru.practicum.shareit.comment.repository.CommentRepository;
import ru.practicum.shareit.system.exception.NotFoundException;
import ru.practicum.shareit.system.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.comment.mapper.CommentMapper;
import ru.practicum.shareit.comment.model.Comment;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Autowired
    @Lazy
    private BookingService bookingService;

    @Override
    public List<CommentDto> getAll() {
        List<CommentDto> comments = commentRepository.findAll().stream()
                .map(CommentMapper::toDto)
                .toList();
        return addUserInfo(comments);
    }

    @Override
    public CommentDto get(Long id) {
        CommentDto comment = commentRepository.findById(id)
                .map(CommentMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Комментарий с таким id не найден"));
        return addUserInfo(comment);
    }

    @Override
    public CommentDto add(CommentDto commentDto) {
        checkItemBookedAndApproved(commentDto.getAuthorId(), commentDto.getItemId());
        return save(commentDto);
    }

    @Override
    public CommentDto update(CommentDto commentDto) {
        return save(commentDto);
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> findByItemId(Long itemId) {
        List<CommentDto> commentsDto = CommentMapper.toDto(commentRepository.findByItemId(itemId));
        return addUserInfo(commentsDto);
    }

    @Override
    public List<CommentDto> findByItemId(List<Long> itemIds) {
        List<CommentDto> commentsDto = CommentMapper.toDto(commentRepository.findByItemIdIn(itemIds));
        return addUserInfo(commentsDto);
    }

    @Override
    public List<CommentDto> findByAuthorId(Long authorId) {
        List<CommentDto> commentsDto = CommentMapper.toDto(commentRepository.findByAuthorId(authorId));
        return addUserInfo(commentsDto);
    }

    private List<CommentDto> addUserInfo(List<CommentDto> comments) {
        List<Long> authorsIds = comments.stream().map(CommentDto::getAuthorId).toList();
        List<UserDto> users = userService.get(authorsIds);
        Map<Long, String> usersNames = users.stream()
                .collect(Collectors.toMap(
                        UserDto::getId,
                        UserDto::getName));

        return comments.stream()
                .map(x -> {
                    String name = usersNames.getOrDefault(x.getAuthorId(), "no name");
                    x.setAuthorName(name);
                    return x;
                }).toList();
    }

    private CommentDto addUserInfo(CommentDto comment) {
        return addUserInfo(List.of(comment)).get(0);
    }

    private CommentDto save(CommentDto commentDto) {
        Comment comment = CommentMapper.toComment(commentDto);
        CommentDto commentSaved = CommentMapper.toDto(commentRepository.save(comment));
        return addUserInfo(commentSaved);
    }

    private void checkItemBookedAndApproved(Long bookerId, Long itemId) {
        List<BookingDto> bookings = bookingService.getByBookerAndItemAndStatus(bookerId, itemId,
                BookingStatus.APPROVED);
        if (bookings.isEmpty()) {
            throw new ValidationException(
                    "Чтобы оставить комментарий, нужно взять вещь в аренду");
        }
        if (!bookings.get(0).getStatus().equals(BookingStatus.PAST)) {
            throw new ValidationException(
                    "Чтобы оставить комментарий, нужно дождаться, пока закончится срок аренды");
        }
    }
}
