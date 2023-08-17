package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String user(Model model) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(a.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        User userRoles = userService.getById(user.getId());
        user.setRoles(userRoles.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/showFormForUpdateUser/{id}")
    public String updateFormUser(@PathVariable(value = "id") long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "updateUser";
    }
}
