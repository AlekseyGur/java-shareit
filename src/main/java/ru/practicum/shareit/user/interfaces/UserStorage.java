package ru.practicum.shareit.user.interfaces;

import java.util.Optional;

import ru.practicum.shareit.user.model.User;

public interface UserStorage {
    Optional<User> add(User user);

    Optional<User> get(Long id);

    Optional<User> update(User user);

    void delete(Long id);

    boolean checkIdExist(Long id);

    boolean checkEmailExist(String email);
}