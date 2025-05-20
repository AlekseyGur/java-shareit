package ru.practicum.shareit.request.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.interfaces.RequestService;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.system.exception.NotFoundException;
import ru.practicum.shareit.user.interfaces.UserService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    @Transactional
    public RequestDto add(Long userId, String text) {
        if (!userService.checkIdExist(userId)) {
            throw new NotFoundException("Пользователь с таким id не найден");
        }

        Request request = new Request();
        request.setRequestorId(userId);
        request.setDescription(text);

        return addItems(RequestMapper.toDto(requestRepository.save(request)));
    }

    @Override
    public RequestDto get(Long id) {
        return requestRepository.findById(id)
                .map(RequestMapper::toDto)
                .map(this::addItems)
                .orElseThrow(() -> new NotFoundException("Запрос с таким id не найден"));
    }

    @Override
    public List<RequestDto> getAll() {
        return requestRepository.findAll().stream()
                .map(RequestMapper::toDto)
                .map(this::addItems)
                .toList();
    }

    @Override
    public List<RequestDto> getByUserId(Long userId) {
        return requestRepository.getByRequestorId(userId).stream()
                .map(RequestMapper::toDto)
                .map(this::addItems)
                .toList();
    }

    private List<RequestDto> addItems(List<RequestDto> requests) {
        List<Long> requestIds = requests.stream().map(RequestDto::getId).toList();
        List<ItemDto> items = itemService.getByRequestIds(requestIds);

        Map<Long, List<ItemDto>> itemByRequestId = items.stream()
                .collect(Collectors.groupingBy(ItemDto::getRequestId));

        return requests.stream()
                .map(request -> {
                    List<ItemDto> it = itemByRequestId.getOrDefault(request.getId(), List.of());

                    request.setItems(it);

                    return request;
                }).toList();
    }

    private RequestDto addItems(RequestDto requestDto) {
        return addItems(List.of(requestDto)).get(0);
    }
}