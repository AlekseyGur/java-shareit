package ru.practicum.shareit.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.practicum.shareit.request.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
        List<Request> getByRequestorId(Long userId);
}