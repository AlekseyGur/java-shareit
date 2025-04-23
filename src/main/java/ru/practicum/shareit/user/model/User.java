package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.UniqueConstraint;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "email" })
public class User {
    private Long id;
    private String name;

    @UniqueConstraint
    private String email;
}
