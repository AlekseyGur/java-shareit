package ru.practicum.shareit.user.interfaces;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto get(Long id);

    UserDto add(UserDto user);

    void delete(Long id);

    boolean checkIdExist(Long id);

    boolean checkEmailExist(String email);

    UserDto patch(UserDto user);
}