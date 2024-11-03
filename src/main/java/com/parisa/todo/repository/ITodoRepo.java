package com.parisa.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parisa.todo.model.ToDo;

@Repository
public interface ITodoRepo extends JpaRepository<ToDo, Long> {

}
