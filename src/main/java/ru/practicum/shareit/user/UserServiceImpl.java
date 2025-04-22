package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.exception.ConstraintViolationException;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
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
    public User get(Long id) {
        return userStorage.get(id)
                .map(UserMapper::dtoToUser)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));
    }

    @Override
    public User add(User user) {
        if (user.getEmail() == null) {
            throw new ConstraintViolationException("Нужно указать email пользователя");
        }
        if (checkEmailExist(user.getEmail())) {
            throw new DuplicatedDataException("Пользователь с таким email уже существует");
        }
        User u = UserMapper.dtoToUser(userStorage.add(user).orElse(null));
        return u;
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
    public User patch(User user) {
        if (!userStorage.checkIdExist(user.getId())) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        if (user.getEmail() != null && checkEmailExist(user.getEmail())) {
            throw new DuplicatedDataException("Пользователь с таким email уже существует");
        }

        Long id = user.getId();
        User userSaved = userStorage.get(id).map(UserMapper::dtoToUser).get();

        if (user.getEmail() != null) {
            userSaved.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            userSaved.setName(user.getName());
        }

        return UserMapper.dtoToUser(userStorage.update(userSaved).orElse(null));
    }
}