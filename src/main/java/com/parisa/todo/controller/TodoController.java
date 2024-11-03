package com.parisa.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parisa.todo.model.ToDo;
import com.parisa.todo.model.itemDto;
import com.parisa.todo.service.todoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ToDo")
public class TodoController {

    @Autowired
    private todoService service;

    @GetMapping("/mainpage")
    public String viewAllItems(Model model, @ModelAttribute("message") String message) {
        model.addAttribute("todoList", service.getAllTodoItems(UserController.USERNAME));
        model.addAttribute("userInfo", UserController.USERNAME);
        model.addAttribute("msg", message);
        return "/todo/mainPage";
    }

    @PostMapping("/updateToDoStatus")
    public String updateTodoStatus(Model model, @RequestParam Long id, @RequestParam boolean completed) {
        try {
            ToDo toDo = service.getTodoItemById(id);
            model.addAttribute("todo", toDo);
            if (completed) {
                toDo.setStatus("Completed");
            } else {
                toDo.setStatus("Not Completed");
            }
            service.insertNewTodoOrUpdateAnItem(toDo);
        } catch (Exception e) {
        }

        return "redirect:/ToDo/mainpage";
    }

    @GetMapping("/addToDoItem")
    public String showCreatePage(Model model) {
        model.addAttribute("todo", new itemDto());
        return "/todo/create";
    }

    @PostMapping("/addToDoItem")
    public String saveTodoItem(Model model, @RequestBody String title) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(title);

            // Access the value of "title"
            String titleValue = jsonNode.get("title").asText();
            String userNameValue = UserController.USERNAME;

            ToDo newItem = new ToDo();
            newItem.setTitle(titleValue);
            newItem.setUsername(userNameValue);
            newItem.setStatus("Not Completed");

            if (service.insertNewTodoOrUpdateAnItem(newItem)) {
                model.addAttribute("Save Success");
                return "redirect:/ToDo/mainpage";
            } else {
                model.addAttribute("Save Failure");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        viewAllItems(model, "");
        return "redirect:/ToDo/mainpage";
    }

    @GetMapping("/editToDoItem")
    public String editToDoItem(@RequestParam Long id, Model model) {

        try {
            ToDo todo = service.getTodoItemById(id);
            model.addAttribute("todo", todo);
            itemDto item = new itemDto();
            item.setTitle(todo.getTitle());
            item.setStatus(todo.getStatus());
            model.addAttribute("todoDto", item);
        } catch (Exception e) {
            return "redirect:/ToDo";
        }

        return "todo/EditTodoItem";
    }

    @PostMapping("/editToDoItem")
    public String editSaveTodoItem(
            Model model,
            @RequestParam Long id,
            @Valid @ModelAttribute itemDto item,
            BindingResult result) {

        try {
            ToDo toDo = service.getTodoItemById(id);
            model.addAttribute("todo", toDo);
            if (result.hasErrors()) {
                result.addError(new FieldError("todo", "title", "Description is required!!"));
                return "todo/EditToDoItem";
            }

            toDo.setTitle(item.getTitle());
            service.insertNewTodoOrUpdateAnItem(toDo);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return "redirect:/ToDo/mainpage";
    }

    @GetMapping("/deleteToDoItem")
    public String deleteTodoItem(@RequestParam Long id) {

        try {
            service.deleteAnItem(id);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return "redirect:/ToDo/mainpage";
    }

}
