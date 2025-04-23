package ru.practicum.shareit.user.storage;

import java.util.HashMap;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.interfaces.UserStorage;

@Repository
public class UserInMemoryStorage implements UserStorage {
    public static Long userId = 0L;
    private static final HashMap<Long, User> users = new HashMap<>();

    @Override
    public Optional<User> add(User user) {
        Long id = getNextId();
        user.setId(id);
        users.put(id, user);
        return getImpl(id);
    }

    @Override
    public Optional<User> get(Long id) {
        return getImpl(id);
    }

    @Override
    public Optional<User> update(User user) {
        users.put(user.getId(), user);
        return getImpl(user.getId());
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

    private Optional<User> getImpl(Long id) {
        return Optional.ofNullable(users.getOrDefault(id, null));
    }

    private Long getNextId() {
        return ++userId;
    }

}