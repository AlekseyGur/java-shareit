package ru.practicum.shareit.booking.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.interfaces.BookingStorage;
import ru.practicum.shareit.booking.mapper.BookingMapper;

@Repository
public class BookingInMemoryStorage implements BookingStorage {
    public static Long bookingId = 0L;
    private static final HashMap<Long, BookingDto> bookings = new HashMap<>();

    @Override
    public Optional<BookingDto> add(Booking booking) {
        BookingDto bookingDto = BookingMapper.toDto(booking);
        Long id = getNextBookingId();
        bookings.put(id, bookingDto);
        return getBookingImpl(id);
    }

    @Override
    public Optional<BookingDto> get(Long id) {
        return getBookingImpl(id);
    }

    @Override
    public List<BookingDto> getByUserId(Long userId) {
        return bookings.values().stream().filter(x -> x.getBooker().equals(userId)).toList();
    }

    @Override
    public Optional<BookingDto> update(Booking booking) {
        Long id = booking.getId();
        BookingDto bookingDto = BookingMapper.toDto(booking);
        if (bookings.containsKey(id)) {
            bookings.put(id, bookingDto);
        }
        return getBookingImpl(booking.getId());
    }

    @Override
    public void delete(Long id) {
        if (bookings.containsKey(id)) {
            bookings.remove(id);
        }
    }

    @Override
    public boolean checkIdExist(Long id) {
        return bookings.containsKey(id);
    }

    private Optional<BookingDto> getBookingImpl(Long id) {
        return Optional.ofNullable(bookings.getOrDefault(id, null));
    }

    private Long getNextBookingId() {
        return ++bookingId;
    }
}