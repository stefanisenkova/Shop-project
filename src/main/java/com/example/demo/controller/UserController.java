package com.example.demo.controller;


import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    double salary = 0;


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
        for (int i = 0; i < employees.size(); i++) {
            if (Objects.equals(employees.get(i).getSalary(), salary)) {
                employees.remove(employees.get(i));
                model.addAttribute("employees", employees);

            }
        }
        return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-first-name-DESC")
    public String sortEmployeesByNameDESC(Model model) {
        List<User> employees = userRepository.findAll(Sort.by(Sort.Direction.DESC, "firstName"));
        for (int i = 0; i < employees.size(); i++) {
            if (Objects.equals(employees.get(i).getSalary(), salary)) {
                employees.remove(employees.get(i));
                model.addAttribute("employees", employees);

            }
        }
        return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-salary-ASC")
    public String sortEmployeesBySalaryASC(Model model) {
        List<User> employees = userRepository.findAll(Sort.by(Sort.Direction.ASC, "salary"));
        for (int i = 0; i < employees.size(); i++) {
            if (Objects.equals(employees.get(i).getSalary(), salary)) {
                employees.remove(employees.get(i));
                model.addAttribute("employees", employees);

            }
        }
        return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-salary-DESC")
    public String sortEmployeesBySalaryDESC(Model model) {
        List<User> employees = userRepository.findAll(Sort.by(Sort.Direction.DESC, "salary"));
        for (int i = 0; i < employees.size(); i++) {
            if (Objects.equals(employees.get(i).getSalary(), salary)) {
                employees.remove(employees.get(i));
                model.addAttribute("employees", employees);

            }
        }
        return "sorting-employees";
    }
}
