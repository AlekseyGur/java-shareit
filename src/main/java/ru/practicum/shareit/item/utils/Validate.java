package ru.practicum.shareit.item.utils;

import jakarta.validation.Valid;
import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.system.exception.ValidationException;

@UtilityClass
public class Validate {
    public static void item(@Valid ItemDto item) {
        if (item.getOwner() == null) {
            throw new ValidationException("Укажите владельца");
        }

        if (item.getName() != null && item.getName().isBlank()) {
            throw new ValidationException("Укажите название");
        }

        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException("Задайте описание");
        }

        if (item.getAvailable() == null) {
            throw new ValidationException("Укажите статус о том, доступна или нет вещь для аренды");
        }
    }

    public static void itemDto(@Valid ItemDto item) {
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidationException("Укажите название");
        }

        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException("задайте описание");
        }
    }
}
