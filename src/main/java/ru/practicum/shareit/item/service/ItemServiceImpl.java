package ru.practicum.shareit.item.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.system.exception.AccessDeniedException;
import ru.practicum.shareit.system.exception.NotFoundException;
import ru.practicum.shareit.user.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemStorage;
    private final UserService userService;

    @Override
    public ItemDto add(ItemDto itemDto) {
        Item item = ItemMapper.toItem(itemDto);
        if (!userService.checkIdExist(item.getOwner())) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        return ItemMapper.toDto(itemStorage.add(item).orElse(null));
    }

    @Override
    public ItemDto get(Long id) {
        return itemStorage.get(id)
                .map(ItemMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Вещь с таким id не найдена"));
    }

    @Override
    public List<ItemDto> find(String query) {
        if (query.isBlank()) {
            return List.of();
        }
        return ItemMapper.toDto(itemStorage.find(query));
    }

    @Override
    public List<ItemDto> getByUserId(Long userId) {
        return ItemMapper.toDto(itemStorage.getByUserId(userId));
    }

    @Override
    public void delete(Long id) {
        itemStorage.delete(id);
    }

    @Override
    public ItemDto patch(ItemDto itemDto) {
        Item itemSaved = checkAccess(itemDto);

        if (itemDto.getName() != null) {
            itemSaved.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            itemSaved.setDescription(itemDto.getDescription());
        }

        if (itemDto.getRequest() != null) {
            itemSaved.setRequest(itemDto.getRequest());
        }

        if (itemDto.getAvailable() != null) {
            itemSaved.setAvailable(itemDto.getAvailable());
        }

        return ItemMapper.toDto(itemStorage.update(itemSaved).orElse(null));
    }

    @Override
    public boolean checkIdExist(Long id) {
        return itemStorage.checkIdExist(id);
    }

    private Item checkAccess(ItemDto item) {
        Long itemId = item.getId();
        Long userId = item.getOwner();

        if (item == null || !checkIdExist(itemId)) {
            throw new NotFoundException("Вещь с таким id не найдена");
        }

        if (!userService.checkIdExist(userId)) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        Item itemSaved = itemStorage.get(itemId).get();

        if (!itemSaved.getOwner().equals(userId)) {
            throw new AccessDeniedException("Только владелец может редактировать вещь");
        }

        return itemSaved;
    }

}