package ru.practicum.shareit.booking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.booking.model.BookingStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto create(
            @RequestBody BookingDto dto,
            @RequestParam(required = true) Long userId) {
        return bookingService.add(dto, userId);
    }

    @PatchMapping("/{bookingId}")
    public void updateStatus(
            @PathVariable Long bookingId,
            @RequestParam Long userId,
            @RequestParam(required = false, defaultValue = "true") boolean approved) {
        bookingService.updateStatus(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto get(@PathVariable Long bookingId) {
        return bookingService.get(bookingId);
    }

    @GetMapping
    public List<BookingDto> getByUser(
            @RequestParam(required = false, defaultValue = "ALL") BookingStatus state,
            @RequestParam Long userId) {
        return bookingService.getByBooker(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getByOwner(
            @RequestParam(required = false, defaultValue = "ALL") BookingStatus state,
            @RequestParam Long userId) {
        return bookingService.getByOwner(userId, state);
    }
}
