package ru.practicum.shareit.request.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import ru.practicum.shareit.request.RequestClient;
import ru.practicum.shareit.request.dto.RequestDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
@Validated
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<RequestDto> createRequest(
            @RequestBody RequestDto requestDto,
                    @RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId) {
        return requestClient.add(userId, requestDto.getDescription());
    }

    @GetMapping
    public ResponseEntity<List<RequestDto>> getByUserId(
            @RequestHeader(value = "X-Sharer-User-Id", required = true) @Positive Long userId) {
        return requestClient.getByUserId(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RequestDto>> getAll() {
        return requestClient.getAll();
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<RequestDto> getById(@PathVariable @Positive Long requestId) {
        return requestClient.get(requestId);
    }
}
