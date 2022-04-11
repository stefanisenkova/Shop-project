package com.example.demo.controller;

import com.example.demo.Status;
import com.example.demo.entity.Order;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.TypeProduct;
import com.example.demo.repository.*;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    TypeProductRepository typeProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;


    List<Purchase> purchases =new ArrayList<>();
    List<Product> products=new ArrayList<>();
    List<Double> quantity=new ArrayList<>();
    List<Order> orders=new ArrayList<>();
    double finalPrice=0;
    Purchase purchase;Product product;

    @GetMapping("/customer-form")
    public String openCustomerForm(){
        return "customer-form";
    }
    @GetMapping("/view-products")
    public String listProducts(Model model) {
        List<Product> listProducts = productRepository.findAll();
        List<Product> correctProducts = new ArrayList<>();
        for(int i=0;i< listProducts.size();i++){
            if(listProducts.get(i).getQuantity()>0){
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

        List<TypeProduct>  typeProduct= typeProductRepository.findByNameContainingIgnoreCase(name);
        if(typeProduct.size()!=1){
            return "wrong-category-name";
        }
        List<Product> listProducts = productRepository.findAll();
        List<Product> correctCategory= new ArrayList<>();
        for(int i=0;i<listProducts.size();i++){
            if(listProducts.get(i).getTypeProduct().name.equals(typeProduct.get(0).name)&&listProducts.get(i).getQuantity()>0){
                correctCategory.add(listProducts.get(i));
            }
        }
        model.addAttribute("listProducts", correctCategory);
        return "printing-product-by-category";
    }

    @GetMapping("/buy-something-form")
    public String buySomething(Product product){
        return "buy-something-form";
    }

    @PostMapping("/purchase/{id}/{quantity}")
    public String purchase(@RequestParam int id,@RequestParam double quantity,Model model){
         product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        if(product.getQuantity()<quantity) {
            return "wrong-quantity";
        }

        this.quantity.add(quantity);
        double price=quantity*product.getPrice();
        purchase=new Purchase(product.getName(),product.getPrice(),quantity,price);
        purchase.setProduct(product);
        finalPrice+=price;

        purchases.add(purchase);
        model.addAttribute("purchase",purchase);
        return "purchase-success";
    }

    @GetMapping("/stop-shopping")
    public String stopShopping(Order order, Model model){
        if(purchases.isEmpty()){
            return "bad-customer";
        }
        for(int i=0;i< quantity.size();i++){
            product.setQuantity(product.getQuantity()-quantity.get(i));
        }
        productRepository.save(product);

        order.setPrice(finalPrice);
        order.setStatus(Status.NEW);
        orderRepository.save(order);
        for(int i=0;i< purchases.size();i++){
            purchases.get(i).setOrder(order);
            purchaseRepository.save(purchases.get(i));
        }

//        for(int i=0;i<purchases.size();i++){
//            purchaseRepository.save(purchases.get(i));
//        }

        model.addAttribute("purchases",purchases);
        return "purchases";
    }

    @GetMapping("/orders")
    public String orders(Order order,Model model){
        orders =orderRepository.findAll();
        model.addAttribute("orders",orders);
        return "orders";
    }

    @GetMapping("/order-details/{id}")
    public String orderDetails(@PathVariable int id,Model model){
        List <Purchase> chosePurchases=purchaseRepository.findByOrderId(id);
        model.addAttribute("chosePurchases",chosePurchases);
        return "order-details";
    }

    @PostMapping("/order-status-update/{id}")
    public String orderUpdate(@PathVariable int id, Order order) {
        order.setId(id);
        for(int i=0;i<orders.size();i++){
            if(orders.get(i).getId()==order.getId()){
                orders.get(i).setStatus(order.getStatus());
            }
            orderRepository.save(orders.get(i));
        }
        return "redirect:/orders";

    }
}
