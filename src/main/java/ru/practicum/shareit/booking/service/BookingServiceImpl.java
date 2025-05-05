package ru.practicum.shareit.booking.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.system.exception.AccessDeniedException;
import ru.practicum.shareit.system.exception.NotFoundException;
import ru.practicum.shareit.system.exception.ValidationException;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    public BookingDto add(BookingDto booking, Long userId) {
        if (!itemService.checkIdExist(booking.getItemId())) {
            throw new NotFoundException("Вещь с таким id не найдена");
        }
        if (!itemService.isItemAvailable(booking.getItemId())) {
            throw new ValidationException("Эту вещь сейчас невозможно забронировать");
        }
        if (!userService.checkIdExist(userId)) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        Booking bookingNew = BookingMapper.toBooking(booking);
        bookingNew.setBookerId(userId);
        bookingNew.setStatus(BookingStatus.WAITING);
        BookingDto res = BookingMapper.toDto(bookingRepository.save(bookingNew));

        return addBookingInfo(res);
    }

    @Override
    public BookingDto get(Long id) {
        return getImpl(id);
    }

    @Override
    public List<BookingDto> getByBooker(Long userId, BookingStatus state) {
        return BookingMapper.toDto(bookingRepository.getByBookerIdAndStatus(userId, state));
    }

    @Override
    public List<BookingDto> getByOwner(Long userId, BookingStatus state) {
        List<Long> itemsIds = itemService.getByUserId(userId).stream().map(ItemDto::getId).toList();
        return BookingMapper.toDto(bookingRepository.getByItemIdInAndStatus(itemsIds, state));
    }

    @Override
    public BookingDto updateStatus(Long bookingId, Long userId, boolean approved) {
        Booking bookingSaved = checkAccessAndGetBooking(bookingId, userId);

        bookingSaved.setStatus(BookingStatus.CURRENT);

        return BookingMapper.toDto(bookingRepository.save(bookingSaved));
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingDto update(Long bookingId, BookingDto booking, Long userId) {
        Booking bookingSaved = checkAccessAndGetBooking(bookingId, userId);

        if (booking.getStatus() != null) {
            bookingSaved.setStatus(booking.getStatus());
        }

        return BookingMapper.toDto(bookingRepository.save(bookingSaved));
    }

    @Override
    public boolean checkIdExist(Long id) {
        return checkIdExistImpl(id);
    }

    private Booking checkAccessAndGetBooking(Long bookingId, Long userId) {

        if (bookingId == null || !checkIdExistImpl(bookingId)) {
            throw new NotFoundException("Бронь с таким id не найдена");
        }

        if (userId == null || !userService.checkIdExist(userId)) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        Booking bookingSaved = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронь с таким id не найдена"));

        if (bookingSaved.getBookerId() != userId) {
            throw new AccessDeniedException("Только владелец может редактировать заказ");
        }

        return bookingSaved;
    }

    public boolean checkIdExistImpl(Long id) {
        return bookingRepository.existsById(id);
    }

    private BookingDto getImpl(Long id) {
        BookingDto booking = bookingRepository.findById(id)
                .map(BookingMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Бронь с таким id не найдена"));

        return addBookingInfo(booking);
    }

    private List<BookingDto> addBookingInfo(List<BookingDto> bookings) {
        List<Long> itemsIds = bookings.stream().map(BookingDto::getItemId).toList();
        List<Long> usersIds = bookings.stream().map(BookingDto::getBookerId).toList();

        List<ItemDto> items = itemService.get(itemsIds);
        List<UserDto> users = userService.get(usersIds);

        Map<Long, ItemDto> itemsByIds = items.stream()
                .collect(Collectors.toMap(ItemDto::getId, Function.identity()));

        Map<Long, UserDto> usersByIds = users.stream()
                .collect(Collectors.toMap(UserDto::getId, Function.identity()));

        return bookings.stream()
                .map(x -> {
                    x.setItem(itemsByIds.getOrDefault(x.getItemId(), null));
                    x.setBooker(usersByIds.getOrDefault(x.getBookerId(), null));
                    return x;
                }).toList();
    }

    private BookingDto addBookingInfo(BookingDto booking) {
        return addBookingInfo(List.of(booking)).get(0);
    }

}