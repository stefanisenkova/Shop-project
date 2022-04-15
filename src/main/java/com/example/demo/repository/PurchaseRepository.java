package com.example.demo.repository;

import com.example.demo.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
    @Query("SELECT u FROM Purchase u WHERE u.order.id = ?1")
    public List <Purchase> findByOrderId(int orderId);
    @Query("SELECT u FROM Purchase u WHERE u.id = ?1")
    public List <Purchase> findByPurchaseId(int id);
}
