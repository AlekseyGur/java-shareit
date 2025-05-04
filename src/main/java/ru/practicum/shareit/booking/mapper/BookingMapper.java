package ru.practicum.shareit.booking.mapper;

import java.util.List;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.booking.mapper.BookingMapper;

public class BookingMapper {

    public static BookingDto toDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setStart(booking.getStart());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setItem(ItemMapper.toDto(booking.getItem()));
        bookingDto.setBooker(UserMapper.toDto(booking.getBooker()));
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
        booking.setItem(ItemMapper.toItem(bookingDto.getItem()));
        booking.setBooker(UserMapper.toUser(bookingDto.getBooker()));
        return booking;
    }

    public static List<Booking> toBooking(List<BookingDto> bookingsDto) {
        return bookingsDto.stream().map(BookingMapper::toBooking).toList();
    }
}