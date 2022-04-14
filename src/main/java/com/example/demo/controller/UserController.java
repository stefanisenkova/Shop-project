package com.example.demo.controller;


import com.example.demo.Status;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/employee-register")
    public String showRegistrationForm(User user, Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);

        return "employee-register";
    }

    @PostMapping("/employee-register-process")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return "redirect:/login";
    }

    @PostMapping("/login")
    String login(User user) {
        return "/login";
    }

    @GetMapping("/menu")
    public String openUserForm(User user) {
        return "menu";
    }


    @GetMapping("/sorting-employees")
    public String sortingEmployees(Model model) {
        List<User> employees = userRepository.findAll();
        model.addAttribute("employees", employees);
        return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-first-name-ASC")
    public String sortEmployeesByNameASC(Model model) {
        List<User> employees = userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
        model.addAttribute("employees", employees);
        return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-first-name-DESC")
    public String sortEmployeesByNameDESC(Model model) {
        List<User> employees = userRepository.findAll(Sort.by(Sort.Direction.DESC, "firstName"));
        model.addAttribute("employees", employees);
        return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-salary-ASC")
    public String sortEmployeesBySalaryASC(Model model) {
        List<User> employees = userRepository.findAll(Sort.by(Sort.Direction.ASC, "salary"));
        model.addAttribute("employees", employees);
        return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-salary-DESC")
    public String sortEmployeesBySalaryDESC(Model model) {
        List<User> employees = userRepository.findAll(Sort.by(Sort.Direction.DESC, "salary"));
        model.addAttribute("employees", employees);
        return "sorting-employees";
    }
}

