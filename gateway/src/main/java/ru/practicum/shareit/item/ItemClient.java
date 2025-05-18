package ru.practicum.shareit.item;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import ru.practicum.shareit.item.dto.ItemDto;

import ru.practicum.shareit.client.BaseClient;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build());
    }

    public ResponseEntity<Object> getByUserId(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> add(ItemDto itemDto, Long userId) {
        return post("", userId, null, itemDto);
    }

    public ResponseEntity<Object> get(Long id) {
        return get("/" + id, null, null);
    }

    public ResponseEntity<Object> patch(ItemDto itemDto, long userId) {
        return patch("/" + itemDto.getId(), userId, itemDto);
    }

    public void delete(long id) {
        delete("/" + id);
    }

    public ResponseEntity<Object> findAvailableByNameOrDescription(String text) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("text", text);
        return get("/search?text={text}", null, parameters);
    }
}
