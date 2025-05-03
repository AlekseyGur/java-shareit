package ru.practicum.shareit.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.system.exception.ConstraintViolationException;
import ru.practicum.shareit.system.exception.DuplicatedDataException;
import ru.practicum.shareit.system.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.interfaces.UserStorage;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public UserDto get(Long id) {
        return userStorage.get(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));
    }

    @Override
    public UserDto add(UserDto userDto) {
        User user = UserMapper.toUser(userDto);

        if (user.getEmail() == null) {
            throw new ConstraintViolationException("Нужно указать email пользователя");
        }
        if (checkEmailExist(user.getEmail())) {
            throw new DuplicatedDataException("Пользователь с таким email уже существует");
        }

        return UserMapper.toDto(userStorage.add(user).orElse(null));
    }

    @Override
    public void delete(Long id) {
        userStorage.delete(id);
    }

    @Override
    public boolean checkIdExist(Long id) {
        return userStorage.checkIdExist(id);
    }

    @Override
    public boolean checkEmailExist(String email) {
        return userStorage.checkEmailExist(email);
    }

    @Override
    public UserDto patch(UserDto userDto) {
        User user = UserMapper.toUser(userDto);

        if (!userStorage.checkIdExist(user.getId())) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        if (user.getEmail() != null && checkEmailExist(user.getEmail())) {
            throw new DuplicatedDataException("Пользователь с таким email уже существует");
        }

        Long id = user.getId();
        User userSaved = userStorage.get(id).get();

        if (user.getEmail() != null) {
            userSaved.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            userSaved.setName(user.getName());
        }

        return UserMapper.toDto(userStorage.update(userSaved).orElse(null));
    }
}