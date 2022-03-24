package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/sort-products")
    public String sortProducts(Model model) {
        List<Product> listProducts = productRepository.findAll();
        model.addAttribute("listProducts", listProducts);

        return "sort-products";
    }

    @GetMapping("/sort-products-by-name-ASC")
    public String sortProductsByNameASC(Model model){
        List<Product> listProducts=productRepository.findAll(Sort.by(Sort.Direction.ASC,"name"));
        model.addAttribute("listProducts",listProducts);
        return "sort-products";
    }

    @GetMapping("/sort-products-by-name-DESC")
    public String sortProductsByNameDESC(Model model){
        List<Product> listProducts=productRepository.findAll(Sort.by(Sort.Direction.DESC,"name"));
        model.addAttribute("listProducts",listProducts);
        return "sort-products";
    }

    @GetMapping("/sort-products-by-price-ASC")
    public String sortProductsByPriceASC(Model model){
        List<Product> listProducts=productRepository.findAll(Sort.by(Sort.Direction.ASC,"price"));
        model.addAttribute("listProducts",listProducts);
        return "sort-products";
    }

    @GetMapping("/sort-products-by-price-DESC")
    public String sortProductsByPriceDESC(Model model){
        List<Product> listProducts=productRepository.findAll(Sort.by(Sort.Direction.DESC,"price"));
        model.addAttribute("listProducts",listProducts);
        return "sort-products";
    }

    @GetMapping("/sort-products-by-expires-ASC")
    public String sortProductsByExpiresASC(Model model){
        List<Product> listProducts=productRepository.findAll(Sort.by(Sort.Direction.ASC,"expiresIn"));
        model.addAttribute("listProducts",listProducts);
        return "sort-products";
    }

    @GetMapping("/sort-products-by-expires-DESC")
    public String sortProductsByExpiresDESC(Model model){
        List<Product> listProducts=productRepository.findAll(Sort.by(Sort.Direction.DESC,"expiresIn"));
        model.addAttribute("listProducts",listProducts);
        return "sort-products";
    }

    @GetMapping("/search-specific-product-form")
    public String searchSpecificProduct(Product product){
        return "search-specific-product-form";
    }

    @GetMapping("/printing-specific-product/{name}")
    public String printSpecificProduct(@RequestParam String name,Model model){
        Product product = productRepository.findByName(name)
                .orElseThrow(()-> new IllegalArgumentException("Invalid product name: "+name));
        model.addAttribute("product", product);
        return "printing-specific-product";
    }
}