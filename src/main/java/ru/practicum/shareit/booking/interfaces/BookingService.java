package ru.practicum.shareit.booking.interfaces;

import java.util.List;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.system.exception.AccessDeniedException;
import ru.practicum.shareit.system.exception.NotFoundException;

public interface BookingService {
    BookingDto get(Long id) throws NotFoundException;

    List<BookingDto> getByBooker(Long userId, BookingStatus state);

    List<BookingDto> getByOwner(Long userId, BookingStatus state);

    BookingDto add(BookingDto booking, Long userId);

    BookingDto updateStatus(Long bookingId, Long userId, boolean approved);

    void delete(Long id);

    BookingDto update(Long bookingId, BookingDto booking, Long userId) throws NotFoundException, AccessDeniedException;

    boolean checkIdExist(Long id);

    boolean checkItemForBookerApproved(Long bookerId, Long itemId);
}