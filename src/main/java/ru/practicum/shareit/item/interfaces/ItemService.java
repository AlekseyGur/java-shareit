package ru.practicum.shareit.item.interfaces;

import java.util.List;

import ru.practicum.shareit.item.model.Item;

public interface ItemService {
    Item add(Item item);

    Item get(Long id);

    List<Item> find(String query);

    List<Item> getByUserId(Long userId);

    void delete(Long id);

    Item patch(Item item);

    boolean checkIdExist(Long id);
}