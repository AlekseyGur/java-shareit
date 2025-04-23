package ru.practicum.shareit.item;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.item.utils.Validate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getByUserId(@RequestHeader(value = "X-Sharer-User-Id", required = true) Long userId) {
        return itemService.getByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemDto add(
                    @RequestBody ItemDto item,
            @RequestHeader(value = "X-Sharer-User-Id", required = true) Long userId) {
        item.setOwner(userId);
        Validate.item(item);
        return itemService.add(item);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto get(@PathVariable Long id) {
        return itemService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto patch(
            @PathVariable Long itemId,
            @RequestBody ItemDto item,
                    @RequestHeader(value = "X-Sharer-User-Id", required = true) Long userId) {
        item.setId(itemId);
        item.setOwner(userId);
        return itemService.patch(item);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> search(@RequestParam String text) {
        return itemService.find(text);
    }
}