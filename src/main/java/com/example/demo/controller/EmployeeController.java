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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    TypeProductRepository typeProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/employee-register")
    public String showRegistrationForm(Employee employee, Model model) {
        List<Role> roles=roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("employee", employee);

        return "employee-register";
    }

    @PostMapping("/employee-register-process")
    public String processRegister(Employee employee) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);

        employeeRepository.save(employee);

        return "redirect:/login";
    }

    @PostMapping("/login")
    String login(Employee employee){
            return "/login";
        }

    @GetMapping("/admin/view")
    public String adminView() {
        return "admin";
    }

    @GetMapping("/employees")
    public String openEmployeeForm(Employee employee) {
        return "employee-form";
    }

    @GetMapping("/product-management-form")
    public String openProductManagementForm() {
        return "product-management-form";
    }

    @GetMapping("/insert-type-product-form")
    public String openInsertTypeProductForm(TypeProduct typeProduct) {
        return "insert-type-product-form";
    }

    @PostMapping("/insert-type-product-form")
    public String typeProductSubmit(@Valid TypeProduct typeProduct, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "insert-type-product-form";
        }
        typeProductRepository.save(typeProduct);
        return "type-product-success";
    }

    @GetMapping("/insert-product-form")
    public String openInsertProductForm(Product product) {
        return "insert-product-form";
    }

    @PostMapping("/insert-product-form")
    public String productSubmit(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "insert-product-form";
        }
        productRepository.save(product);
        return "product-success";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> listProducts = productRepository.findAll();
        model.addAttribute("listProducts", listProducts);

        return "products";
    }
    @PreAuthorize("hasRole(‘ROLE_ADMIN’)")

    @GetMapping("/product-edit/{id}")
    public String productEdit(@PathVariable int id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        model.addAttribute("product", product);
        return "edit-product-form";
    }

    @PostMapping("/product-update/{id}")
    public String userUpdate(@PathVariable int id, Product product) {
        product.setId(id);
        productRepository.save(product);
        return "redirect:/products";

    }

    @PostMapping("/product-delete/{id}")
    public String productDelete(@PathVariable int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        productRepository.delete(product);
        return "redirect:/products";
    }

    @GetMapping("/sorting-employees")
    public String sortingEmployees(Model model){
        List<Employee> employees=employeeRepository.findAll();
        model.addAttribute("employees",employees);
            return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-first-name-ASC")
    public String sortEmployeesByNameASC(Model model) {
        List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
        model.addAttribute("employees", employees);
        return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-first-name-DESC")
    public String sortEmployeesByNameDESC(Model model) {
        List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.DESC, "firstName"));
        model.addAttribute("employees", employees);
        return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-salary-ASC")
    public String sortEmployeesBySalaryASC(Model model) {
        List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "salary"));
        model.addAttribute("employees", employees);
        return "sorting-employees";
    }

    @GetMapping("/sort-employees-by-salary-DESC")
    public String sortEmployeesBySalaryDESC(Model model) {
        List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.DESC, "salary"));
        model.addAttribute("employees", employees);
        return "sorting-employees";
    }

    @GetMapping("/filter-orders-by-status-new")
    public String filterOrdersByStatusNew(Order order,Model model) {
        List<Order> allOrders = orderRepository.findAll();
        List<Order> orders=new ArrayList<>();
        for(int i=0;i<allOrders.size();i++){
            if(allOrders.get(i).getStatus()== Status.NEW){
                orders.add(allOrders.get(i));
            }
        }
        model.addAttribute("orders", orders);
        return "orders";
    }


    @GetMapping("/filter-orders-by-status-processed")
    public String filterOrdersByStatusProcessed(Order order,Model model) {
        List<Order> allOrders = orderRepository.findAll();
        List<Order> orders=new ArrayList<>();
        for(int i=0;i<allOrders.size();i++){
            if(allOrders.get(i).getStatus()== Status.PROCESSED){
                orders.add(allOrders.get(i));
            }
        }
        model.addAttribute("orders", orders);
        return "orders";
    }


    @GetMapping("/filter-orders-by-status-sent-by-courier")
    public String filterOrdersByStatusSentByCourier(Order order,Model model) {
        List<Order> allOrders = orderRepository.findAll();
        List<Order> orders=new ArrayList<>();
        for(int i=0;i<allOrders.size();i++){
            if(allOrders.get(i).getStatus()== Status.SENT_BY_COURIER){
                orders.add(allOrders.get(i));
            }
        }
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/filter-orders-by-status-completed")
    public String filterOrdersByStatusCompleted(Order order,Model model) {
        List<Order> allOrders = orderRepository.findAll();
        List<Order> orders=new ArrayList<>();
        for(int i=0;i<allOrders.size();i++){
            if(allOrders.get(i).getStatus()== Status.COMPLETED){
                orders.add(allOrders.get(i));
            }
        }
        model.addAttribute("orders", orders);
        return "orders";
    }


}
