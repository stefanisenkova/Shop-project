package com.example.demo;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.naming.Name;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByNameContaining(String name, Sort sort);

//    @Query("SELECT u FROM Product u WHERE u.name = ?1")
//    public Product findByName(String name);

    public java.util.Optional<Product> findByName(String name);
    public List<Product> findByPriceGreaterThanEqual(double price);
    public List<Product> findByPriceLessThan(double price);
    public List<Product> findByQuantityGreaterThanEqual(double quantity);
    public List<Product> findByQuantityLessThan(double quantity);


}
