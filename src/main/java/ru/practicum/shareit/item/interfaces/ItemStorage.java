package ru.practicum.shareit.item.interfaces;

import java.util.List;
import java.util.Optional;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public interface ItemStorage {
    Optional<ItemDto> add(Item item);

    Optional<ItemDto> get(Long id);

    List<ItemDto> getByUserId(Long userId);

    Optional<ItemDto> update(Item item);

    void delete(Long id);

    boolean checkIdExist(Long id);

    List<ItemDto> find(String query);
}