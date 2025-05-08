package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private Long id;

    @NotNull
    @Size(min = 2, max = 1000)
    private String description;

    @NotNull
    @Positive
    private Long requestorId;
    private LocalDateTime createdAt;
}
