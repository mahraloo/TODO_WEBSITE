package com.parisa.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.parisa.todo.model.User;
import com.parisa.todo.model.itemDto;
import com.parisa.todo.service.userService;

@Controller
@RequestMapping("/ToDo")
public class UserController {

    @Autowired
    userService service;

    private boolean isLoggedin = false;
    public static String USERNAME;

    @GetMapping({ "", "/" })
    public String sessionCheck(Model model, @ModelAttribute("message") String message) {
        model.addAttribute("addTodo", new itemDto());
        if (isLoggedin) {
            return "redirect:/ToDo/mainpage";
        }
        model.addAttribute("user", new User());
        return "/todo/index";
    }

    @PostMapping("/login")
    public String login(Model model, String username, String password, @ModelAttribute("message") String message) {
        User user = service.Login(username, password);
        if (user != null) {
            USERNAME = username;
            model.addAttribute("userInfo", username);
            model.addAttribute("addTodo", new itemDto());
            isLoggedin = true;
            return "redirect:/ToDo/mainpage";
        }
        User user2 = new User();
        user2.setUsername(username);
        user2.setPassword(password);

        signIn(user2, message, model);
        return "/todo/index";
    }

    @PostMapping("/signup")
    public String signIn(User user,
            @ModelAttribute("message") String message, Model model) {

        if (service.SignUp(user.getUsername(), user.getPassword()) != null) {
            model.addAttribute("userInfo", user.getUsername());
            USERNAME = user.getUsername();
            model.addAttribute("addTodo", new itemDto());
            isLoggedin = true;
            return "redirect:/ToDo/mainpage";
        } else {
            model.addAttribute("msg", "Please try again!!");
            isLoggedin = false;
            return "/todo/index";
        }
    }

    @GetMapping("/loggedOut")
    public String signOut() {
        isLoggedin = false;
        USERNAME = "";
        return "redirect:/ToDo";
    }

}
