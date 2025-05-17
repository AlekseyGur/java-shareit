package ru.practicum.shareit.item.mapper;

import java.util.List;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@UtilityClass
public class ItemMapper {
    public static ItemDto toDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        if (item.getOwnerId() != null) {
            itemDto.setOwnerId(item.getOwnerId());
        }
        if (item.getRequestId() != null) {
            itemDto.setRequestId(item.getRequestId());
        }
        return itemDto;
    }

    public static Item toItem(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        if (itemDto.getOwnerId() != null) {
            item.setOwnerId(itemDto.getOwnerId());
        }
        if (itemDto.getRequestId() != null) {
            item.setRequestId(itemDto.getRequestId());
        }
        return item;
    }

    public static List<Item> toItem(List<ItemDto> itemsDto) {
        return itemsDto.stream().map(ItemMapper::toItem).toList();
    }

    public static List<ItemDto> toDto(List<Item> items) {
        return items.stream().map(ItemMapper::toDto).toList();
    }
}