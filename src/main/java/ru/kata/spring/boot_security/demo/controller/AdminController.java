package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserService;

@Controller
@RequestMapping(name = "/admin")
public class AdminController {

    private final MyUserService userService;

    @Autowired
    public AdminController(MyUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String allUsers(Model model) {
        model.addAttribute("usersList", userService.findAllUsers());
        return "/users";
    }

    @GetMapping(value = "/admin/edit/{id}")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOneUser(id));
        return "/editPage";
    }

    @PatchMapping(value = "/admin/edit/{id}")
    public String editUser(@ModelAttribute("user") User user,
                           @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/add")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        return "/addUser";
    }

    @PostMapping(value = "/admin/add")
    public String addUser(@ModelAttribute("user") User user,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/add";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping(value="/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
