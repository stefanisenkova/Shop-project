package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    TypeProductRepository typeProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @GetMapping("/customer-form")
    public String openCustomerForm(){
        return "customer-form";
    }
    @GetMapping("/view-products")
    public String listProducts(Model model) {
        List<Product> listProducts = productRepository.findAll();
        model.addAttribute("listProducts", listProducts);
        return "view-products";
    }
}
