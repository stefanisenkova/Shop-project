package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContaining(String name, Sort sort);

    public List<Product> findByPriceGreaterThanEqual(double price);

    public List<Product> findByPriceLessThan(double price);

    public List<Product> findByQuantityGreaterThanEqual(double quantity);

    public List<Product> findByQuantityLessThan(double quantity);

    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT u FROM Product u WHERE u.name = ?1")
    public Product findByName(String name);
}
