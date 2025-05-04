package ru.practicum.shareit.booking.interfaces;

import java.util.List;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.system.exception.AccessDeniedException;
import ru.practicum.shareit.system.exception.NotFoundException;

public interface BookingService {
    BookingDto get(Long id) throws NotFoundException;

    List<BookingDto> getByUserId(Long userId);

    BookingDto add(BookingDto booking, Long userId);

    void delete(Long id);

    BookingDto update(Long bookingId, BookingDto booking, Long userId) throws NotFoundException, AccessDeniedException;

    boolean checkIdExist(Long id);
}