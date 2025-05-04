package ru.practicum.shareit.request.mapper;

import java.util.List;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.model.Request;

@UtilityClass
public class RequestMapper {
    public static RequestDto toDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setDescription(request.getDescription());
        requestDto.setRequestor(request.getRequestor());
        requestDto.setCreatedAt(request.getCreatedAt());
        return requestDto;
    }

    public static Request toRequest(RequestDto requestDto) {
        Request request = new Request();
        request.setId(requestDto.getId());
        request.setDescription(requestDto.getDescription());
        request.setRequestor(requestDto.getRequestor());
        request.setCreatedAt(requestDto.getCreatedAt());
        return request;
    }

    public static List<Request> toRequest(List<RequestDto> itemsDto) {
        return itemsDto.stream().map(RequestMapper::toRequest).toList();
    }

    public static List<RequestDto> toDto(List<Request> items) {
        return items.stream().map(RequestMapper::toDto).toList();
    }
}