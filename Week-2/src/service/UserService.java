package service;

import repository.UserRepository;
import model.User;
import java.util.*;

public class UserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Collection<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(int id) {
        return repository.findById(id);
    }

    public boolean createUser(User user) {
        if (repository.exists(user.id))
            return false;
        // otherwise
        repository.save(user);
        return true;
    }

}
