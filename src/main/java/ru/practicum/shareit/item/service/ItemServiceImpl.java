package ru.practicum.shareit.item.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.system.exception.AccessDeniedException;
import ru.practicum.shareit.system.exception.NotFoundException;
import ru.practicum.shareit.user.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public ItemDto add(ItemDto itemDto, Long userId) {
        if (!userService.checkIdExist(userId)) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toDto(itemRepository.save(item));
    }

    @Override
    public ItemDto get(Long id) {
        return itemRepository.findById(id)
                .map(ItemMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Вещь с таким id не найдена"));
    }

    @Override
    public List<ItemDto> find(String query) {
        if (query.isBlank()) {
            return List.of();
        }
        return ItemMapper.toDto(
                itemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableTrue(query,
                        query));
    }

    @Override
    public List<ItemDto> getByUserId(Long userId) {
        return ItemMapper.toDto(itemRepository.findByOwnerId(userId));
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public ItemDto patch(ItemDto itemDto, Long userId) {
        Item itemSaved = checkAccess(itemDto, userId);

        if (itemDto.getName() != null) {
            itemSaved.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            itemSaved.setDescription(itemDto.getDescription());
        }

        if (itemDto.getRequestId() != null) {
            itemSaved.setRequestId(itemDto.getRequestId());
        }

        if (itemDto.getAvailable() != null) {
            itemSaved.setAvailable(itemDto.getAvailable());
        }

        return ItemMapper.toDto(itemRepository.save(itemSaved));
    }

    @Override
    public boolean checkIdExist(Long id) {
        return itemRepository.existsById(id);
    }

    private Item checkAccess(ItemDto item, Long userId) {
        Long itemId = item.getId();

        if (item == null || !checkIdExist(itemId)) {
            throw new NotFoundException("Вещь с таким id не найдена");
        }

        if (!userService.checkIdExist(userId)) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        Item itemSaved = itemRepository.findById(itemId).get();

        if (!itemSaved.getOwnerId().equals(userId)) {
            throw new AccessDeniedException("Только владелец может редактировать вещь");
        }

        return itemSaved;
    }

}