package com.ankit.week_2_backend_demo_spring.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import com.ankit.week_2_backend_demo_spring.model.User;
import com.ankit.week_2_backend_demo_spring.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Collection<User> getallUsers() {
        return repository.findAll();
    }

    public User getUserbyId(int id) {
        return repository.findById(id);
    }

    public boolean createUser(User user) {
        return repository.save(user);
    }

    public User deleteUser(int id) {
        return repository.delete(id);
    }
}
