package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.entities.UserRoleWrap;
import ru.kata.spring.boot_security.demo.services.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String admin(Model model) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(a.getName());
        model.addAttribute("user", user);
        return "admin";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("alluserlist", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/addnew")
    public String addNewUser(Model model) {
        UserRoleWrap userRoleWrap = new UserRoleWrap();
        userRoleWrap.setUser(new User());
        userRoleWrap.setRoleList(roleService.getAllRoles());
        model.addAttribute("userRoleWrap", userRoleWrap);
        return "newuser";
    }

    @PostMapping("/saveWrapper")
    public String saveWrapper(@ModelAttribute("userRoleWrap") UserRoleWrap userRoleWrap, Model model) {
        User user = userRoleWrap.getUser();
        user.setPassword(passwordEncoder.encode(userRoleWrap.getUser().getPassword()));
        List<Role> roleList = new ArrayList<>();
        for (Role role : userRoleWrap.getRoleList()) {
            if (role.getTrigger() != null) {
                roleList.add(role);
            }
        }
        user.setRoles(roleList);
        userService.save(user);
        return "redirect:./index";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String updateForm(@PathVariable(value = "id") long id, Model model) {
        UserRoleWrap userRoleWrap = new UserRoleWrap();
        userRoleWrap.setUser(userService.getById(id));
        userRoleWrap.setRoleList(roleService.getAllRoles());
        model.addAttribute("userRoleWrap", userRoleWrap);

//        User user = userService.getById(id);
//        model.addAttribute("user", user);
        return "update";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteThroughId(@PathVariable(value = "id") long id) {
        userService.deleteViaId(id);
        return "redirect:/admin/index";
    }

}
