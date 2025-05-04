package ru.practicum.shareit.booking.interfaces;

import java.util.List;
import java.util.Optional;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

public interface BookingRepository {
    Optional<BookingDto> add(Booking booking);

    Optional<BookingDto> get(Long id);

    List<BookingDto> getByUserId(Long userId);

    Optional<BookingDto> update(Booking booking);

    void delete(Long id);

    boolean checkIdExist(Long id);
}