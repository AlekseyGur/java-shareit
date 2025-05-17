package ru.practicum.shareit.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.request.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
        List<Request> getByRequestorId(Long userId);

        @Query("INSERT INTO requests (user_id, text) VALUES (:requestorId, :text)")
        Request save(@Param("requestorId") Long userId, @Param("text") String text);
}