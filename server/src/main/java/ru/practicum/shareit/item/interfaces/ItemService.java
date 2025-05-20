package ru.practicum.shareit.item.interfaces;

import java.util.List;

import ru.practicum.shareit.item.dto.ItemDto;

public interface ItemService {
    ItemDto add(ItemDto item, Long userId);

    ItemDto get(Long id);

    List<ItemDto> getByRequestIds(List<Long> requestIds);

    List<ItemDto> get(List<Long> ids);

    boolean isItemAvailable(Long itemId);

    List<ItemDto> findAvailableByNameOrDescription(String query);

    List<ItemDto> getByUserId(Long userId);

    void delete(Long id);

    ItemDto patch(ItemDto item, Long userId);

    boolean checkIdExist(Long id);
}