package ru.practicum.shareit.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.practicum.shareit.item.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerId(Long ownerId);

    List<Item> findByNameContainingIgnoreCaseAndAvailableTrueOrDescriptionContainingIgnoreCaseAndAvailableTrue(
            String name,
                    String description);
}