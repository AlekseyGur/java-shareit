package ru.practicum.shareit.request.interfaces;

import java.util.List;

import ru.practicum.shareit.request.dto.RequestDto;

public interface RequestService {
    RequestDto add(Long userId, String text);

    RequestDto get(Long requestId);

    List<RequestDto> getByUserId(Long userId);

    List<RequestDto> getAll();
}