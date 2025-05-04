package ru.practicum.shareit.item.mapper;

import java.util.List;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.user.mapper.UserMapper;

@UtilityClass
public class ItemMapper {
    public static ItemDto toDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setOwner(UserMapper.toDto(item.getOwner()));
        if (item.getRequest() != null) {
            itemDto.setRequest(RequestMapper.toDto(item.getRequest()));
        }
        return itemDto;
    }

    public static Item toItem(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(UserMapper.toUser(itemDto.getOwner()));
        if (itemDto.getRequest() != null) {
            item.setRequest(RequestMapper.toRequest(itemDto.getRequest()));
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