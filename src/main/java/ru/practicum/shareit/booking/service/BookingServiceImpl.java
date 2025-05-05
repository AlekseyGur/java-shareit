package ru.practicum.shareit.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.system.exception.AccessDeniedException;
import ru.practicum.shareit.system.exception.NotFoundException;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.user.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingStorage;
    private final UserService userService;
    private final ItemService itemService;

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
    public List<BookingDto> getByBooker(Long userId, BookingStatus state) {
        return BookingMapper.toDto(bookingStorage.getByBookerIdAndStatus(userId, state));
    }

    @Override
    public List<BookingDto> getByOwner(Long userId, BookingStatus state) {
        List<Long> itemsIds = itemService.getByUserId(userId).stream().map(ItemDto::getId).toList();
        return BookingMapper.toDto(bookingStorage.getByItemIdInAndStatus(itemsIds, state));
    }

    @Override
    public BookingDto updateStatus(Long bookingId, Long userId, boolean approved) {
        Booking bookingSaved = checkAccessAndGetBooking(bookingId, userId);

        bookingSaved.setStatus(BookingStatus.CURRENT);

        return BookingMapper.toDto(bookingStorage.save(bookingSaved));
    }

    @Override
    public BookingDto add(BookingDto booking, Long userId) {
        Booking bookingSaved = BookingMapper.toBooking(booking);
        bookingSaved.setBookerId(userId);
        return BookingMapper.toDto(bookingStorage.save(bookingSaved));
    }

    @Override
    public void delete(Long id) {
        bookingStorage.deleteById(id);
    }

    @Override
    public BookingDto update(Long bookingId, BookingDto booking, Long userId) {
        Booking bookingSaved = checkAccessAndGetBooking(bookingId, userId);

        if (booking.getStatus() != null) {
            bookingSaved.setStatus(booking.getStatus());
        }

        return BookingMapper.toDto(bookingStorage.save(bookingSaved));
    }

    @Override
    public boolean checkIdExist(Long id) {
        return bookingStorage.existsById(id);
    }

    private Booking checkAccessAndGetBooking(Long bookingId, Long userId) {

        if (bookingId == null || !checkIdExist(bookingId)) {
            throw new NotFoundException("Бронь с таким id не найдена");
        }

        if (userId == null || !userService.checkIdExist(userId)) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        Booking bookingSaved = bookingStorage.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронь с таким id не найдена"));

        if (bookingSaved.getBookerId() != userId) {
            throw new AccessDeniedException("Только владелец может редактировать заказ");
        }

        return bookingSaved;
    }

}