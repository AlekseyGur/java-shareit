package ru.practicum.shareit.system.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.system.exception.GatewayException;

@Component
@Slf4j
public class ConvertResponse {
    private final ObjectMapper mapper;

    public ConvertResponse(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
    }

    public <T> ResponseEntity<T> toEntity(ResponseEntity<Object> source, Class<T> targetType) {
        if (source.getStatusCode().is2xxSuccessful()) {
            try {
                String json = mapper.writeValueAsString(source.getBody());
                T body = mapper.readValue(json, targetType);
                return ResponseEntity
                        .status(source.getStatusCode())
                        .headers(source.getHeaders())
                        .body(body);
            } catch (JsonProcessingException e) {
                throw new GatewayException(source.getStatusCode(), source.getHeaders(), source.getBody());
            }
        }
        throw new GatewayException(source.getStatusCode(), source.getHeaders(), source.getBody());
    }

    public <T> ResponseEntity<T> toEntity(ResponseEntity<Object> source, TypeReference<T> targetType) {
        if (source.getStatusCode().is2xxSuccessful()) {
            try {
                String json = mapper.writeValueAsString(source.getBody());
                T body = mapper.readValue(json, targetType);
                return ResponseEntity
                        .status(source.getStatusCode())
                        .headers(source.getHeaders())
                        .body(body);
            } catch (JsonProcessingException e) {
                throw new GatewayException(source.getStatusCode(), source.getHeaders(), source.getBody());
            }
        }
        throw new GatewayException(source.getStatusCode(), source.getHeaders(), source.getBody());
    }
}