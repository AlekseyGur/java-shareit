package ru.practicum.shareit.user.storage;

import java.util.HashMap;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.interfaces.UserStorage;
import ru.practicum.shareit.user.mapper.UserMapper;

@Repository
public class UserInMemoryStorage implements UserStorage {
    public static Long userId = 0L;
    private static final HashMap<Long, UserDto> users = new HashMap<>();

    @Override
    public Optional<UserDto> add(User user) {
        UserDto userDto = UserMapper.userToDto(user);
        Long id = getNextId();
        userDto.setId(id);
        users.put(id, userDto);
        return getImpl(id);
    }

    @Override
    public Optional<UserDto> get(Long id) {
        return getImpl(id);
    }

    @Override
    public Optional<UserDto> patch(User user) {
        Long id = user.getId();
        UserDto userSaved = getImpl(id).get();

        if (user.getEmail() != null) {
            userSaved.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            userSaved.setName(user.getName());
        }

        users.put(id, userSaved);
        return getImpl(id);
    }

    @Override
    public void delete(Long id) {
        if (users.containsKey(id)) {
            users.remove(id);
        }
    }

    @Override
    public boolean checkIdExist(Long id) {
        return users.containsKey(id);
    }

    @Override
    public boolean checkEmailExist(String email) {
        return users.values().stream().anyMatch(x -> x.getEmail().equals(email));
    }

    private Optional<UserDto> getImpl(Long id) {
        return Optional.ofNullable(users.getOrDefault(id, null));
    }

    private Long getNextId() {
        return ++userId;
    }

}