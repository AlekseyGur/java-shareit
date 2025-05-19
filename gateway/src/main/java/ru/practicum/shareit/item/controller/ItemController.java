package ru.practicum.shareit.item.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.ItemClient;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.utils.Validate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ItemDto>> getByUserId(
            @RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId) {
        return itemClient.getByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ItemDto> add(
            @Valid @RequestBody ItemDto item,
            @RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId) {
        item.setOwnerId(userId);
        Validate.itemDto(item);
        return itemClient.add(item, userId);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ItemDto> get(@PathVariable Long itemId) {
        return itemClient.get(itemId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        itemClient.delete(id);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ItemDto> patch(
            @PathVariable Long itemId,
            @Valid @RequestBody ItemDto item,
            @RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId) {
        item.setId(itemId);
        item.setOwnerId(userId);
        return itemClient.patch(item, userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ItemDto>> search(@RequestParam String text) {
        return itemClient.findAvailableByNameOrDescription(text);
    }
}