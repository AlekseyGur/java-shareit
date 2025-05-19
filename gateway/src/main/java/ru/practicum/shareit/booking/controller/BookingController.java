package ru.practicum.shareit.booking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.BookingClient;
import ru.practicum.shareit.booking.dto.BookingDto;

import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.utils.BookingValidate;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
	private final BookingClient bookingClient;

	@PostMapping
	public ResponseEntity<BookingDto> add(
			@Valid @RequestBody BookingDto bookingDto,
			@RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId) {
		bookingDto.setBookerId(userId);
		BookingValidate.bookingDto(bookingDto);
		return bookingClient.add(bookingDto, userId);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<BookingDto> updateStatus(
			@PathVariable Long bookingId,
			@RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId,
			@RequestParam(defaultValue = "true") boolean approved) {
		return bookingClient.updateStatus(bookingId, userId, approved);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<BookingDto> get(@PathVariable Long bookingId) {
		return bookingClient.get(bookingId);
	}

	@GetMapping
	public ResponseEntity<List<BookingDto>> getByUser(
			@RequestParam(defaultValue = "ALL") BookingStatus state,
			@RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId) {
		return bookingClient.getByBooker(userId, state);
	}

	@GetMapping("/owner")
	public ResponseEntity<List<BookingDto>> getByOwner(
			@RequestParam(defaultValue = "ALL") BookingStatus state,
			@RequestHeader(value = "X-Sharer-User-Id", required = false) @Positive Long userId) {
		return bookingClient.getByOwner(userId, state);
	}
}
