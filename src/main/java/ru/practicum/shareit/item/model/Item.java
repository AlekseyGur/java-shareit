package ru.practicum.shareit.item.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(name = "is_available", nullable = false)
    private Boolean available;

    @NotNull
    @Positive
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Positive
    @Column(name = "request_id")
    private Long requestId;
}
