package ru.practicum.shareit.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.system.exception.AccessDeniedException;
import ru.practicum.shareit.system.exception.NotFoundException;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.booking.interfaces.BookingRepository;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingStorage;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    public Booking get(Long id) {
        BookingDto bookingDto = bookingStorage.get(id)
                .orElseThrow(() -> new NotFoundException("Бронь с id = " + id + " не найдена"));

        return BookingMapper.toBooking(
                bookingDto,
                itemService.get(bookingDto.getId()),
                userService.get(bookingDto.getBooker()));
    }

    @Override
    public List<Booking> getByUserId(Long userId) {
        List<ItemDto> items = List.of();
        List<UserDto> bookers = List.of();
        return BookingMapper.toBooking(
                bookingStorage.getByUserId(userId),
                items,
                bookers);
    }

    @Override
    public Booking add(Booking booking, Long userId) {
        BookingDto bookingDto = bookingStorage.add(booking).orElse(null);
        return BookingMapper.toBooking(
                bookingDto,
                itemService.get(bookingDto.getId()),
                userService.get(userId));
    }

    @Override
    public void delete(Long id) {
        bookingStorage.delete(id);
    }

    @Override
    public Booking update(Long bookingId, Booking booking, Long userId) {
        Booking bookingSaved = checkAccessAndGetBooking(bookingId, booking, userId);

        if (booking.getStatus() != null) {
            bookingSaved.setStatus(booking.getStatus());
        }

        BookingDto bookingDto = bookingStorage.update(booking).orElse(null);
        return BookingMapper.toBooking(
                bookingDto,
                itemService.get(bookingDto.getId()),
                userService.get(userId));
    }

    @Override
    public boolean checkIdExist(Long id) {
        return bookingStorage.checkIdExist(id);
    }

    private Booking checkAccessAndGetBooking(Long bookingId, Booking booking, Long userId) {
        if (booking == null || !checkIdExist(bookingId)) {
            throw new NotFoundException("Бронь с id = " + bookingId + " не найдена");
        }

        if (!userService.checkIdExist(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }

        BookingDto bookingDto = bookingStorage.get(bookingId).get();
        Booking bookingSaved = BookingMapper.toBooking(
                bookingDto,
                itemService.get(bookingDto.getId()),
                userService.get(userId));

        if (bookingSaved.getBooker().getId() != userId) {
            throw new AccessDeniedException("Только владелец может редактировать заказ");
        }

        return bookingSaved;
    }

}