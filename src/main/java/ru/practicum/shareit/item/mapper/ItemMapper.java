package ru.practicum.shareit.item.mapper;

import java.util.List;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@UtilityClass
public class ItemMapper {
    public static ItemDto itemToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setOwner(item.getOwner());
        if (item.getRequest() != null) {
            itemDto.setRequest(item.getRequest());
        }
        return itemDto;
    }

    public static Item dtoToItem(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(itemDto.getOwner());
        return item;
    }

    public static List<Item> dtoToItem(List<ItemDto> itemsDto) {
        return itemsDto.stream().map(ItemMapper::dtoToItem).toList();
    }

    public static List<ItemDto> itemToDto(List<Item> items) {
        return items.stream().map(ItemMapper::itemToDto).toList();
    }
}