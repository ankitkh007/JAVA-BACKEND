package com.ankit.week_2_backend_demo_spring.repository;

import com.ankit.week_2_backend_demo_spring.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private Map<Integer, User> users = new ConcurrentHashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }

    public User findById(int id) {
        return users.get(id);
    }

    public boolean save(User user) {
        return users.putIfAbsent(user.id, user) == null;
    }

    public User delete(int id) {
        return users.remove(id);
    }
}
