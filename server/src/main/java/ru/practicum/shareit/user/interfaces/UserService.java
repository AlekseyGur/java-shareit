package ru.practicum.shareit.user.interfaces;

import java.util.List;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto get(Long id);

    List<UserDto> get(List<Long> id);

    UserDto save(UserDto user);

    void delete(Long id);

    boolean checkIdExist(Long id);

    boolean checkEmailExist(String email);

    UserDto patch(UserDto user);
}