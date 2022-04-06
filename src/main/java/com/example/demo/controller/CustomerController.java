package com.example.demo.controller;

import com.example.demo.entity.Purchase;
import com.example.demo.entity.TypeProduct;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.TypeProductRepository;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.NonUniqueResultException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    private PurchaseRepository purchaseRepository;

    List<Purchase> purchases=new ArrayList<>();

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

    @GetMapping("/search-product-by-category")
    public String searchProductByCategory(TypeProduct typeProduct) {
        return "search-product-by-category";
    }

    @GetMapping("/printing-product-by-category/{name}")
    public String printProductByCategory(@RequestParam String name, Model model) {

        TypeProduct typeProduct= typeProductRepository.findByNameContainingIgnoreCase(name);
        if(typeProduct==null){
            return "wrong-category-name";
        }
        List<Product> listProducts = productRepository.findAll();
        List<Product> correctCategory= new ArrayList<>();
        for(int i=0;i<listProducts.size();i++){
            if(listProducts.get(i).getTypeProduct().name.equals(typeProduct.name)&&listProducts.get(i).getQuantity()>0){
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
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        if(product.getQuantity()<quantity) {
            return "wrong-quantity";
        }

        String productName=product.getName();
        double productPrice=product.getPrice();
        //Timestamp timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        double finalPrice=quantity*product.getPrice();
        Purchase purchase=new Purchase(productName,quantity,productPrice,finalPrice);

        purchases.add(purchase);
        purchaseRepository.save(purchase);
        model.addAttribute("purchase",purchase);
        return "purchase-success";
    }



    @GetMapping("/stop-shopping")
    public String stopShopping(Model model){
        model.addAttribute("purchases",purchases);
        return "purchases";
    }
}
