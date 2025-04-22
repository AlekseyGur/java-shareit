package ru.practicum.shareit.item;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.item.interfaces.ItemStorage;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    @Override
    public Item add(Item item) {
        if (!userService.checkIdExist(item.getOwner())) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        return ItemMapper.dtoToItem(itemStorage.add(item).orElse(null));
    }

    @Override
    public Item get(Long id) {
        return itemStorage.get(id)
                .map(ItemMapper::dtoToItem)
                .orElseThrow(() -> new NotFoundException("Вещь с таким id не найдена"));
    }

    @Override
    public List<Item> find(String query) {
        if (query.isBlank()) {
            return List.of();
        }
        return ItemMapper.dtoToItem(itemStorage.find(query));
    }

    @Override
    public List<Item> getByUserId(Long userId) {
        return ItemMapper.dtoToItem(itemStorage.getByUserId(userId));
    }

    @Override
    public void delete(Long id) {
        itemStorage.delete(id);
    }

    @Override
    public Item patch(Item item) {
        Item itemSaved = checkAccess(item);

        if (item.getName() != null) {
            itemSaved.setName(item.getName());
        }

        if (item.getDescription() != null) {
            itemSaved.setDescription(item.getDescription());
        }

        if (item.getRequest() != null) {
            itemSaved.setRequest(item.getRequest());
        }

        if (item.getAvailable() != null) {
            itemSaved.setAvailable(item.getAvailable());
        }

        return ItemMapper.dtoToItem(itemStorage.update(itemSaved).orElse(null));
    }

    @Override
    public boolean checkIdExist(Long id) {
        return itemStorage.checkIdExist(id);
    }

    private Item checkAccess(Item item) {
        Long itemId = item.getId();
        Long userId = item.getOwner();

        if (item == null || !checkIdExist(itemId)) {
            throw new NotFoundException("Вещь с таким id не найдена");
        }

        if (!userService.checkIdExist(userId)) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        Item itemSaved = itemStorage.get(itemId).map(ItemMapper::dtoToItem).get();

        if (!itemSaved.getOwner().equals(userId)) {
            throw new AccessDeniedException("Только владелец может редактировать вещь");
        }

        return itemSaved;
    }

}