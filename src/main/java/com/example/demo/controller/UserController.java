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
    TypeProductRepository typeProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

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
    public String openEmployeeForm(User user) {
        return "menu";
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

    List<Purchase> purchases = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<Double> quantity = new ArrayList<>();
    List<Order> orders = new ArrayList<>();
    double finalPrice = 0;
    Purchase purchase;
    Product product;

    @GetMapping("/customer-form")
    public String openCustomerForm() {
        return "customer-form";
    }

    @GetMapping("/view-products")
    public String listProduct(Model model) {
        List<Product> listProducts = productRepository.findAll();
        List<Product> correctProducts = new ArrayList<>();
        for (int i = 0; i < listProducts.size(); i++) {
            if (listProducts.get(i).getQuantity() > 0) {
                correctProducts.add(listProducts.get(i));
            }
        }
        model.addAttribute("correctProducts", correctProducts);
        return "view-products";
    }

    @GetMapping("/search-product-by-category")
    public String searchProductByCategory(TypeProduct typeProduct) {
        return "search-product-by-category";
    }

    @GetMapping("/printing-product-by-category/{name}")
    public String printProductByCategory(@RequestParam String name, Model model) {

        List<TypeProduct> typeProduct = typeProductRepository.findByNameContainingIgnoreCase(name);
        if (typeProduct.size() != 1) {
            return "wrong-category-name";
        }
        List<Product> listProducts = productRepository.findAll();
        List<Product> correctCategory = new ArrayList<>();
        for (int i = 0; i < listProducts.size(); i++) {
            if (listProducts.get(i).getTypeProduct().name.equals(typeProduct.get(0).name) && listProducts.get(i).getQuantity() > 0) {
                correctCategory.add(listProducts.get(i));
            }
        }
        model.addAttribute("listProducts", correctCategory);
        return "printing-product-by-category";
    }

    @GetMapping("/buy-something-form")
    public String buySomething(Product product) {
        return "buy-something-form";
    }


    @PostMapping("/purchase/{id}/{quantity}")
    public String purchase(@RequestParam int id, @RequestParam double quantity, Model model) {
        product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        if (product.getQuantity() < quantity) {
            return "wrong-quantity";
        }

        this.quantity.add(quantity);
        double price = quantity * product.getPrice();
        purchase = new Purchase(product.getName(), product.getPrice(), quantity, price);
        purchase.setProduct(product);
        finalPrice += price;

        purchases.add(purchase);
        model.addAttribute("purchase", purchase);
        return "purchase-success";
    }

    @GetMapping("/stop-shopping")
    public String stopShopping(Order order, Model model) {
        if (purchases.isEmpty()) {
            return "bad-customer";
        }
        for (int i = 0; i < quantity.size(); i++) {
            product.setQuantity(product.getQuantity() - quantity.get(i));
        }
        productRepository.save(product);

        order.setPrice(finalPrice);
        order.setStatus(Status.NEW);
        orderRepository.save(order);
        for (int i = 0; i < purchases.size(); i++) {
            purchases.get(i).setOrder(order);
            purchaseRepository.save(purchases.get(i));
        }

//        for(int i=0;i<purchases.size();i++){
//            purchaseRepository.save(purchases.get(i));
//        }

        model.addAttribute("purchases", purchases);
        return "purchases";
    }

    @GetMapping("/orders")
    public String orders(Order order, Model model) {
        orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/order-details/{id}")
    public String orderDetails(@PathVariable int id, Model model) {
        List<Purchase> chosePurchases = purchaseRepository.findByOrderId(id);
        model.addAttribute("chosePurchases", chosePurchases);
        return "order-details";
    }

    @PostMapping("/order-status-update/{id}")
    public String orderUpdate(@PathVariable int id, Order order) {
        order.setId(id);
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == order.getId()) {
                orders.get(i).setStatus(order.getStatus());
            }
            orderRepository.save(orders.get(i));
        }
        return "redirect:/orders";
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

