package ru.practicum.shareit.booking.mapper;

import java.util.List;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.mapper.BookingMapper;

@UtilityClass
public class BookingMapper {

    public static BookingDto toDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setStart(booking.getStart());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setItemId(booking.getItemId());
        bookingDto.setBookerId(booking.getBookerId());
        return bookingDto;
    }

    public static List<BookingDto> toDto(List<Booking> bookings) {
        return bookings.stream().map(BookingMapper::toDto).toList();
    }

    public static Booking toBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setEnd(bookingDto.getEnd());
        booking.setStart(bookingDto.getStart());
        booking.setStatus(bookingDto.getStatus());
        booking.setItemId(bookingDto.getItemId());
        booking.setBookerId(bookingDto.getBookerId());
        return booking;
    }

    public static List<Booking> toBooking(List<BookingDto> bookingsDto) {
        return bookingsDto.stream().map(BookingMapper::toBooking).toList();
    }
}