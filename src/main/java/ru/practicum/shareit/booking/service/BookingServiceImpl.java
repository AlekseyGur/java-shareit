package ru.practicum.shareit.booking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.system.exception.AccessDeniedException;
import ru.practicum.shareit.system.exception.NotFoundException;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingStorage;
    private final UserService userService;

    @Override
    public BookingDto get(Long id) {
        return getImpl(id);
    }

    private BookingDto getImpl(Long id) {
        return bookingStorage.findById(id)
                .map(BookingMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Бронь с таким id не найдена"));
    }

    @Override
    public List<BookingDto> getByUserId(Long userId) {
        return BookingMapper.toDto(bookingStorage.getByBookerId(userId));
    }

    @Override
    public BookingDto add(BookingDto booking, Long userId) {
        User user = Optional.of(userService.get(userId))
                .map(UserMapper::toUser)
                .get();

        Booking bookingSaved = BookingMapper.toBooking(booking);
        bookingSaved.setBooker(user);
        return BookingMapper.toDto(bookingStorage.save(bookingSaved));
    }

    @Override
    public void delete(Long id) {
        bookingStorage.deleteById(id);
    }

    @Override
    public BookingDto update(Long bookingId, BookingDto booking, Long userId) {
        Booking bookingSaved = checkAccessAndGetBooking(bookingId, booking, userId);

        if (booking.getStatus() != null) {
            bookingSaved.setStatus(booking.getStatus());
        }

        return BookingMapper.toDto(bookingStorage.save(bookingSaved));
    }

    @Override
    public boolean checkIdExist(Long id) {
        return bookingStorage.existsById(id);
    }

    private Booking checkAccessAndGetBooking(Long bookingId, BookingDto booking, Long userId) {
        if (booking == null || !checkIdExist(bookingId)) {
            throw new NotFoundException("Бронь с таким id не найдена");
        }

        if (!userService.checkIdExist(userId)) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        Booking bookingSaved = bookingStorage.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронь с таким id не найдена"));

        if (bookingSaved.getBooker().getId() != userId) {
            throw new AccessDeniedException("Только владелец может редактировать заказ");
        }

        return bookingSaved;
    }

}