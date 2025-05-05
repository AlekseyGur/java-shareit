package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RequestDto {
    private Long id;
    private String description;
    private Long requestorId;
    private LocalDateTime createdAt;
}
