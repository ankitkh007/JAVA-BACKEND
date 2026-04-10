package repository;

import model.User;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private Map<Integer, User> users = new ConcurrentHashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }

    public User findById(int id) {
        return users.get(id);
    }

    public boolean exists(int id) {
        return users.containsKey(id);
    }

    public void save(User user) {
        users.put(user.id, user);
    }
}
