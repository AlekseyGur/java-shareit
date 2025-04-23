package ru.practicum.shareit.item.interfaces;

import java.util.List;
import java.util.Optional;

import ru.practicum.shareit.item.model.Item;

public interface ItemStorage {
    Optional<Item> add(Item item);

    Optional<Item> get(Long id);

    List<Item> getByUserId(Long userId);

    Optional<Item> update(Item item);

    void delete(Long id);

    boolean checkIdExist(Long id);

    List<Item> find(String query);
}