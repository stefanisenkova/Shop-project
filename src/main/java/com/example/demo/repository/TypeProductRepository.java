package com.example.demo.repository;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Product;
import com.example.demo.entity.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeProductRepository extends JpaRepository<TypeProduct,Integer> {
    List<TypeProduct> findByNameContainingIgnoreCase(String name);
//    @Query("SELECT u FROM TypeProduct u WHERE u.name = ?1")
//    public TypeProduct findByName(String type);
}
