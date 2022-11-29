package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping(name = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String allUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = (User) userService.loadUserByUsername(name);
        model.addAttribute("newUser", new User());
        model.addAttribute("user", user);
        model.addAttribute("usersList", userService.findAllUsers());
        model.addAttribute("rolesList", roleService.getAllRoles());
        return "users";
    }

    @PostMapping("/admin/edit/{id}")
    public String editUser(@ModelAttribute("newUser") User user,
                         @PathVariable("id") int id,
                         @RequestParam(value = "index", required = false) Integer[] identifiers) {

        if (identifiers != null) {
            for (Integer roleId : identifiers) {
                user.addRole(roleService.findRoleById(roleId));
            }
        } else {
            user.addRole(roleService.findRoleById(2));
        }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/add")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(value = "index", required = false) Integer[] id) {
        if (id != null) {
            for (Integer roleId : id) {
                user.addRole(roleService.findRoleById(roleId));
            }
        } else {
            user.addRole(roleService.findRoleById(2));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
