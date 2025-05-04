package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

@Data
public class RequestDto {
    private Long id;
    private String description;
    private User requestor;
    private LocalDateTime createdAt;
}
