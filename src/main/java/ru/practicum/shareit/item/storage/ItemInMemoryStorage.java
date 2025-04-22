package ru.practicum.shareit.item.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.interfaces.ItemStorage;
import ru.practicum.shareit.item.mapper.ItemMapper;

@Repository
public class ItemInMemoryStorage implements ItemStorage {
    public static Long itemId = 0L;
    private static final HashMap<Long, ItemDto> items = new HashMap<>();

    @Override
    public Optional<ItemDto> add(Item item) {
        ItemDto itemDto = ItemMapper.itemToDto(item);
        Long id = getNextId();
        itemDto.setId(id);
        items.put(id, itemDto);
        return getImpl(id);
    }

    @Override
    public Optional<ItemDto> get(Long id) {
        return getImpl(id);
    }

    @Override
    public List<ItemDto> find(String query) {
        String q = query.toLowerCase();
        return items.values().stream()
                .filter(x -> x.getAvailable())
                .filter(x -> x.getName().toLowerCase().contains(q)
                        || x.getDescription().toLowerCase().contains(q))
                .toList();
    }

    @Override
    public List<ItemDto> getByUserId(Long userId) {
        return items.values().stream().filter(x -> x.getOwner().equals(userId)).toList();
    }

    @Override
    public Optional<ItemDto> update(Item item) {
        items.put(item.getId(), ItemMapper.itemToDto(item));
        return getImpl(item.getId());
    }

    @Override
    public void delete(Long id) {
        if (items.containsKey(id)) {
            items.remove(id);
        }
    }

    @Override
    public boolean checkIdExist(Long id) {
        return items.containsKey(id);
    }

    private Optional<ItemDto> getImpl(Long id) {
        return Optional.ofNullable(items.getOrDefault(id, null));
    }

    private Long getNextId() {
        return ++itemId;
    }
}