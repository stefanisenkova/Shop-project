package com.example.demo.repository;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Product;
import com.example.demo.entity.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TypeProductRepository extends JpaRepository<TypeProduct,Integer> {
    TypeProduct findByNameContainingIgnoreCase(String name);
//    @Query("SELECT u FROM TypeProduct u WHERE u.name = ?1")
//    public TypeProduct findByName(String type);
}
