package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserService;

import java.security.Principal;

@Controller
public class UserController {

    private final MyUserService userService;

    public UserController(MyUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String getOneUser(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        return "/user";
    }

}
