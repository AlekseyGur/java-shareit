package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.practicum.shareit.system.client.BaseClient;
import ru.practicum.shareit.system.client.ConvertResponse;
import ru.practicum.shareit.user.dto.UserDto;

@Service
public class UserClient extends BaseClient {
    ConvertResponse convertResponse;

    public UserClient(@Value("${shareit-server.url}") String serverUrl, ConvertResponse convertResponse) {
        super(serverUrl, "/users");
        this.convertResponse = convertResponse;
    }

    public ResponseEntity<UserDto> get(long userId) {
        return convertResponse.toEntity(get("/" + userId, null, null), UserDto.class);
    }

    public ResponseEntity<UserDto> save(UserDto userDto) {
        return convertResponse.toEntity(post("", null, null, userDto), UserDto.class);
    }

    public ResponseEntity<UserDto> patch(UserDto userDto) {
        long userId = userDto.getId();
        return convertResponse.toEntity(patch("/" + userId, userId, userDto), UserDto.class);
    }

    public void delete(long id) {
        delete("/" + id, null, null);
    }
}
