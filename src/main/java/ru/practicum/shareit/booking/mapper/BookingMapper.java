package ru.practicum.shareit.booking.mapper;

import java.util.HashMap;
import java.util.List;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.booking.mapper.BookingMapper;

public class BookingMapper {

    public static BookingDto toDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setStart(booking.getStart());

        if (booking.getStatus() != null) {
            bookingDto.setStatus(booking.getStatus().ordinal());
        }

        if (booking.getItem() != null) {
            bookingDto.setItem(booking.getItem().getId());
        }

        if (booking.getBooker() != null) {
            bookingDto.setBooker(booking.getBooker().getId());
        }

        return bookingDto;
    }

    public static List<BookingDto> toDto(List<Booking> bookings) {
        return bookings.stream().map(BookingMapper::toDto).toList();
    }

    public static Booking toBooking(BookingDto bookingDto, ItemDto item, UserDto booker) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setEnd(bookingDto.getEnd());
        booking.setStart(bookingDto.getStart());

        BookingStatus bookingStatus = BookingStatus.values()[bookingDto.getStatus()];
        booking.setStatus(bookingStatus);

        if (booker != null) {
            booking.setBooker(UserMapper.toUser(booker));
        }

        if (item != null) {
            booking.setItem(ItemMapper.toItem(item));
        }

        return booking;
    }

    public static List<Booking> toBooking(List<BookingDto> bookingsDto, List<ItemDto> items, List<UserDto> bookers) {
        HashMap<Long, ItemDto> iMap = new HashMap<>() {
            {
                for (ItemDto item : items) {
                    put(item.getId(), item);
                }
            }
        };
        HashMap<Long, UserDto> uMap = new HashMap<>() {
            {
                for (UserDto user : bookers) {
                    put(user.getId(), user);
                }
            }
        };

        return bookingsDto.stream().map(b -> toBooking(
                b,
                iMap.getOrDefault(b.getItem(), null),
                uMap.getOrDefault(b.getBooker(), null))).toList();
    }
}