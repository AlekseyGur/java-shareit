package ru.practicum.shareit.user.interfaces;

import java.util.Optional;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public interface UserStorage {
    Optional<UserDto> add(User user);

    Optional<UserDto> get(Long id);

    Optional<UserDto> patch(User user);

    void delete(Long id);

    boolean checkIdExist(Long id);

    boolean checkEmailExist(String email);
}