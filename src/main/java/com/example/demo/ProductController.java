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
    @GetMapping("/search-by-price-greater-than")
    public String searchByPriceGreaterThan(Product product){
        return "search-by-price-greater-than";
    }
    @GetMapping("/printing-by-greater-price/{price}")
    public String printProductByPriceGreaterThan(@RequestParam double price,Model model){
        List<Product> product= productRepository.findByPriceGreaterThanEqual(price) ;
        model.addAttribute("product", product);
        return "printing-by-greater-price";
    }
    @GetMapping("/search-by-price-less-than")
    public String searchByPriceLessThan(Product product){
        return "search-by-price-less-than";
    }
    @GetMapping("/printing-by-less-price/{price}")
    public String printProductByLessThan(@RequestParam double price,Model model){
        List<Product> product= productRepository.findByPriceLessThan(price) ;
        model.addAttribute("product", product);
        return "printing-by-less-price";
    }
    @GetMapping("/search-by-quantity-greater-than")
    public String searchByQuantityGreaterThan(Product product){
        return "search-by-quantity-greater-than";
    }
    @GetMapping("/printing-by-greater-quantity/{quantity}")
    public String printProductByQuantityGreaterThan(@RequestParam double quantity,Model model){
        List<Product> product= productRepository.findByQuantityGreaterThanEqual(quantity) ;
        model.addAttribute("product", product);
        return "printing-by-greater-quantity";
    }
    @GetMapping("/search-by-quantity-less-than")
    public String searchByQuantityLessThan(Product product){
        return "search-by-quantity-less-than";
    }
    @GetMapping("/printing-by-less-quantity/{quantity}")
    public String printQuantityByLessThan(@RequestParam double quantity,Model model){
        List<Product> product= productRepository.findByQuantityLessThan(quantity) ;
        model.addAttribute("product", product);
        return "printing-by-less-quantity";
    }

}