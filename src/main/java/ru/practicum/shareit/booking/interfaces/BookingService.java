package ru.practicum.shareit.booking.interfaces;

import java.util.List;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.system.exception.AccessDeniedException;
import ru.practicum.shareit.system.exception.NotFoundException;

public interface BookingService {
    Booking get(Long id) throws NotFoundException;

    List<Booking> getByUserId(Long userId);

    Booking add(Booking booking, Long userId);

    void delete(Long id);

    Booking update(Long bookingId, Booking booking, Long userId) throws NotFoundException, AccessDeniedException;

    boolean checkIdExist(Long id);
}