package com.ankit.week_2_backend_demo_spring.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.week_2_backend_demo_spring.model.User;
import com.ankit.week_2_backend_demo_spring.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService service;

    public UsersController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<User> getallUsers() {
        return service.getallUsers();
    }

    @GetMapping("/{id}")
    public User getUserbyId(@PathVariable("id") int id) {
        return service.getUserbyId(id);
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        boolean created = service.createUser(user);
        if (created)
            return "User created.";
        else
            return "User already exists!";

    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = service.deleteUser(id);

        if (user == null)
            return "User does not exist.";
        else
            return "User successfully deleted";
    }

}
