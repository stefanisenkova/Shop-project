package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
}
