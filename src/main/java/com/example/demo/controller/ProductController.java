package com.example.demo.controller;

import com.example.demo.entity.TypeProduct;
import com.example.demo.repository.ProductRepository;
import com.example.demo.entity.Product;
import com.example.demo.repository.TypeProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TypeProductRepository typeProductRepository;

    @GetMapping("/product-management-form")
    public String openProductManagementForm() {
        return "product-management-form";
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

    @GetMapping("/sort-products")
    public String sortProducts(Model model) {
        List<Product> listProducts = productRepository.findAll();
        model.addAttribute("listProducts", listProducts);

        return "sort-products";
    }

    @GetMapping("/sort-products-by-name-ASC")
    public String sortProductsByNameASC(Model model) {
        List<Product> listProducts = productRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        model.addAttribute("listProducts", listProducts);
        return "sort-products";
    }

    @GetMapping("/sort-products-by-name-DESC")
    public String sortProductsByNameDESC(Model model) {
        List<Product> listProducts = productRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        model.addAttribute("listProducts", listProducts);
        return "sort-products";
    }

    @GetMapping("/sort-products-by-price-ASC")
    public String sortProductsByPriceASC(Model model) {
        List<Product> listProducts = productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
        model.addAttribute("listProducts", listProducts);
        return "sort-products";
    }

    @GetMapping("/sort-products-by-price-DESC")
    public String sortProductsByPriceDESC(Model model) {
        List<Product> listProducts = productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
        model.addAttribute("listProducts", listProducts);
        return "sort-products";
    }

    @GetMapping("/sort-products-by-expires-ASC")
    public String sortProductsByExpiresASC(Model model) {
        List<Product> listProducts = productRepository.findAll(Sort.by(Sort.Direction.ASC, "expiresIn"));
        model.addAttribute("listProducts", listProducts);
        return "sort-products";
    }

    @GetMapping("/sort-products-by-expires-DESC")
    public String sortProductsByExpiresDESC(Model model) {
        List<Product> listProducts = productRepository.findAll(Sort.by(Sort.Direction.DESC, "expiresIn"));
        model.addAttribute("listProducts", listProducts);
        return "sort-products";
    }

    @GetMapping("/search-by-price-greater-than")
    public String searchByPriceGreaterThan(Product product) {
        return "search-by-price-greater-than";
    }

    @GetMapping("/printing-by-greater-price/{price}")
    public String printProductByPriceGreaterThan(@RequestParam double price, Model model) {
        List<Product> product = productRepository.findByPriceGreaterThanEqual(price);
        model.addAttribute("product", product);
        return "printing-by-greater-price";
    }

    @GetMapping("/search-by-price-less-than")
    public String searchByPriceLessThan(Product product) {
        return "search-by-price-less-than";
    }

    @GetMapping("/printing-by-less-price/{price}")
    public String printProductByLessThan(@RequestParam double price, Model model) {
        List<Product> product = productRepository.findByPriceLessThan(price);
        model.addAttribute("product", product);
        return "printing-by-less-price";
    }

    @GetMapping("/search-by-quantity-greater-than")
    public String searchByQuantityGreaterThan(Product product) {
        return "search-by-quantity-greater-than";
    }

    @GetMapping("/printing-by-greater-quantity/{quantity}")
    public String printProductByQuantityGreaterThan(@RequestParam double quantity, Model model) {
        List<Product> product = productRepository.findByQuantityGreaterThanEqual(quantity);
        model.addAttribute("product", product);
        return "printing-by-greater-quantity";
    }

    @GetMapping("/search-by-quantity-less-than")
    public String searchByQuantityLessThan(Product product) {
        return "search-by-quantity-less-than";
    }

    @GetMapping("/printing-by-less-quantity/{quantity}")
    public String printQuantityByLessThan(@RequestParam double quantity, Model model) {
        List<Product> product = productRepository.findByQuantityLessThan(quantity);
        model.addAttribute("product", product);
        return "printing-by-less-quantity";
    }

    @GetMapping("/search-specific-product-form")
    public String searchSpecificProduct(Product product) {
        return "search-specific-product-form";
    }

    @GetMapping("/printing-specific-product/{name}")
    public String printSpecificProduct(@RequestParam String name, Model model) {
        List<Product> product = productRepository.findByNameContainingIgnoreCase(name);
        if (product.size() != 1) {
            return "wrong-specific-name";
        }
        model.addAttribute("product", product);
        return "printing-specific-product";
    }

    @GetMapping("/search-product-by-name")
    public String searchProductByName(Product product) {
        return "search-product-by-name";
    }

    @GetMapping("/printing-product-by-name/{name}")
    public String printProductByName(@RequestParam String name, Model model) {


        List<Product> product = productRepository.findByNameContainingIgnoreCase(name);
        if (product.size() != 1) {
            return "wrong-name";
        }
        if (product.get(0).getQuantity() > 0) {
            model.addAttribute("product", product);
        }
        return "printing-product-by-name";
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

}
