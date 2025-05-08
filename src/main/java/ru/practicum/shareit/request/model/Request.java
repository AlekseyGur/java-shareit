package ru.practicum.shareit.request.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false, length = 1000)
    @NotNull
    @Size(min = 2, max = 1000)
    private String description;

    @Column(name = "requestor_id", nullable = false)
    @NotNull
    @Positive
    private Long requestorId;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;
}
