package ru.practicum.shareit.user.mapper;

import java.util.List;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static UserDto userToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    public static User dtoToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail());
    }

    public static List<User> dtoToUser(List<UserDto> usersDto) {
        return usersDto.stream().map(UserMapper::dtoToUser).toList();
    }

    public static List<UserDto> userToDto(List<User> users) {
        return users.stream().map(UserMapper::userToDto).toList();
    }
}