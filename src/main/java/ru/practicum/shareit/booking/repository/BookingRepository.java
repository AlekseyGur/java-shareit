package ru.practicum.shareit.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getByItemIdInAndStatus(List<Long> itemsIds, BookingStatus state);

    List<Booking> getByBookerId(Long userId);

    List<Booking> getByBookerIdAndStatus(Long userId, BookingStatus state);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM bookings
                WHERE item_id = :itemId
                AND booker_id = :bookerId
                AND status = :statusId
                LIMIT 1
            )""", nativeQuery = true)
    Boolean checkItemForBookerApproved(
            @Param("bookerId") Long bookerId,
            @Param("itemId") Long itemId,
            @Param("statusId") Integer statusId);
}