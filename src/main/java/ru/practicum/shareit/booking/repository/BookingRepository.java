package ru.practicum.shareit.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getByItemIdInAndStatus(List<Long> itemsIds, BookingStatus state);

    List<Booking> getByBookerIdAndStatus(Long userId, BookingStatus state);
}