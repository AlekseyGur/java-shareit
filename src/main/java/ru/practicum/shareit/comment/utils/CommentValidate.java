package ru.practicum.shareit.comment.utils;

import jakarta.validation.Valid;
import lombok.experimental.UtilityClass;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.system.exception.ValidationException;

@UtilityClass
public class CommentValidate {
    public static void comment(@Valid CommentDto comment) {
        if (comment.getText().isBlank()) {
            throw new ValidationException("Укажите текст комментария");
        }

        if (comment.getItemId() == null) {
            throw new ValidationException("Укажите id вещи для комментирования");
        }
    }
}
