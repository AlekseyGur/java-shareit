package ru.practicum.shareit.request.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.interfaces.RequestService;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.system.exception.NotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    @Override
    @Transactional
    public RequestDto add(Long userId, String text) {
        return RequestMapper.toDto(requestRepository.save(userId, text));
    }

    @Override
    public RequestDto get(Long id) {
        return requestRepository.findById(id)
                .map(RequestMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Запрос с таким id не найден"));
    }

    @Override
    public List<RequestDto> getAll() {
        return requestRepository.findAll().stream().map(RequestMapper::toDto).toList();
    }

    @Override
    public List<RequestDto> getByUserId(Long userId) {
        return requestRepository.getByRequestorId(userId).stream().map(RequestMapper::toDto).toList();
    }
}