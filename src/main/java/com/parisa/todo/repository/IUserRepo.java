package com.parisa.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parisa.todo.model.AuthResponse;
import com.parisa.todo.model.User;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer> {
    // @Query(value = "SELECT username FROM user WHERE username = :username AND
    // password =:password", nativeQuery = true)
    // AuthResponse login(String username, String password);

    // @Query(value = "INSERT INTO user(username, password) VALUES(:username,
    // :password)", nativeQuery = true)
    // void signUp(String username, String password);
}