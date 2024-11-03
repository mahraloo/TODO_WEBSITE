package com.parisa.todo.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parisa.todo.model.AuthResponse;
import com.parisa.todo.model.User;
import com.parisa.todo.repository.IUserRepo;

@Service
public class userService {

    @Autowired
    IUserRepo repo;

    public User Login(String username, String password) {
        List<User> users = repo.findAll();

        List<User> filteredList = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .filter(user -> user.getPassword().equals(password))
                .collect(Collectors.toList());

        if (filteredList.size() == 0) {
            return null;
        }
        return filteredList.get(0);
    }

    public User SignUp(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User insertedUser = repo.save(user);
        return insertedUser;
    }
}
