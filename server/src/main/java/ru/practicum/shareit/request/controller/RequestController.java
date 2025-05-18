package ru.practicum.shareit.request.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.interfaces.RequestService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Validated
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public RequestDto createRequest(
            @RequestBody String text,
            @RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId) {
        return requestService.add(userId, text);
    }

    @GetMapping
    public List<RequestDto> getByUserId(
            @RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId) {
        return requestService.getByUserId(userId);
    }

    @GetMapping("/all")
    public List<RequestDto> getAll() {
        return requestService.getAll();
    }

    @GetMapping("/{requestId}")
    public RequestDto getById(@PathVariable Long requestId) {
        return requestService.get(requestId);
    }
}
