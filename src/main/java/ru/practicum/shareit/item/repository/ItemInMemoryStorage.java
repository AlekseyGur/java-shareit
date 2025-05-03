package ru.practicum.shareit.item.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.interfaces.ItemStorage;

@Repository
public class ItemInMemoryStorage implements ItemStorage {
    public static Long itemId = 0L;
    private static final HashMap<Long, Item> items = new HashMap<>();

    @Override
    public Optional<Item> add(Item item) {
        Long id = getNextId();
        item.setId(id);
        items.put(id, item);
        return getImpl(id);
    }

    @Override
    public Optional<Item> get(Long id) {
        return getImpl(id);
    }

    @Override
    public List<Item> find(String query) {
        String q = query.toLowerCase();
        return items.values().stream()
                .filter(x -> x.getAvailable())
                .filter(x -> x.getName().toLowerCase().contains(q)
                        || x.getDescription().toLowerCase().contains(q))
                .toList();
    }

    @Override
    public List<Item> getByUserId(Long userId) {
        return items.values().stream().filter(x -> x.getOwner().equals(userId)).toList();
    }

    @Override
    public Optional<Item> update(Item item) {
        items.put(item.getId(), item);
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

    private Optional<Item> getImpl(Long id) {
        return Optional.ofNullable(items.getOrDefault(id, null));
    }

    private Long getNextId() {
        return ++itemId;
    }
}