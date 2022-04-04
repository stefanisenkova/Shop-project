package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeProductRepository extends JpaRepository<TypeProduct,Integer> {
    public java.util.Optional<TypeProduct> findByType(String type);

}
