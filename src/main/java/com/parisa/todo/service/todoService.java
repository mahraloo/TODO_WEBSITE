package com.parisa.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.parisa.todo.model.ToDo;
import com.parisa.todo.repository.ITodoRepo;

@Service
public class todoService {

    @Autowired
    ITodoRepo repo;

    public List<ToDo> getAllTodoItems(String username) {
        List<ToDo> list = repo.findAll((Sort.by(Sort.Direction.DESC, "id")));

        List<ToDo> filteredList = list.stream()
                .filter(todo -> todo.getUsername().equals(username))
                .collect(Collectors.toList());

        return filteredList;
    }

    public ToDo getTodoItemById(Long id) {
        return repo.findById(id).get();
    }

    public boolean updateStatus(Long id, boolean isCompleted) {
        ToDo item = getTodoItemById(id);
        if (isCompleted) {
            item.setStatus("Completed");
        } else {
            item.setStatus("Not Completed");
        }
        return insertNewTodoOrUpdateAnItem(item);
    }

    public boolean insertNewTodoOrUpdateAnItem(ToDo item) {
        ToDo updatedItem = repo.save(item);

        if (getTodoItemById(updatedItem.getId()) != null) {
            return true;
        }
        return false;
    }

    public boolean deleteAnItem(Long id) {
        repo.deleteById(id);

        if (getTodoItemById(id) == null) {
            return true;
        }
        return false;
    }
}
