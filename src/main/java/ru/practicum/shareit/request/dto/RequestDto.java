package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private Long id;
    private String description;
    private Long requestorId;
    private LocalDateTime createdAt;
}
