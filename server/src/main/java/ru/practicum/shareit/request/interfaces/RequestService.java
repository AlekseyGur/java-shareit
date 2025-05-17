package ru.practicum.shareit.request.interfaces;

import java.util.List;

import ru.practicum.shareit.request.dto.RequestDto;

public interface RequestService {
    RequestDto add(Long userId, String text);

    RequestDto get(Long requestId);

    List<RequestDto> getByUserId(Long userId);

    List<RequestDto> getAll();

    // ItemDto add(ItemDto item, Long userId);

    // ItemDto get(Long id);

    // List<ItemDto> get(List<Long> ids);

    // boolean isItemAvailable(Long itemId);

    // List<ItemDto> findAvailableByNameOrDescription(String query);

    // List<ItemDto> getByUserId(Long userId);

    // void delete(Long id);

    // ItemDto patch(ItemDto item, Long userId);

    // boolean checkIdExist(Long id);
}