package ru.practicum.shareit.user.interfaces;

import ru.practicum.shareit.user.model.User;

public interface UserService {
    User get(Long id);

    User add(User user);

    void delete(Long id);

    boolean checkIdExist(Long id);

    boolean checkEmailExist(String email);

    User patch(User user);
}