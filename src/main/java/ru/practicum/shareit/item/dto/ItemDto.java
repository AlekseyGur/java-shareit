package ru.practicum.shareit.item.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.comment.dto.CommentDto;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long ownerId;
    private Long requestId;

    private List<CommentDto> comments;
    private Long lastBooking;
    private Long nextBooking;
}