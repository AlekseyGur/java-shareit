package ru.practicum.shareit.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.item.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i.available FROM Item i WHERE i.id = :itemId")
    Boolean findAvailableByItemId(@Param("itemId") Long itemId);

    List<Item> getByIdIn(List<Long> itemIds);

    List<Item> findByOwnerId(Long ownerId);

    List<Item> findByNameContainingIgnoreCaseAndAvailableTrueOrDescriptionContainingIgnoreCaseAndAvailableTrue(
            String name,
                    String description);
}