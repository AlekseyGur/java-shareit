package ru.practicum.shareit.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.practicum.shareit.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<User> getByIdIn(List<Long> userId);
}