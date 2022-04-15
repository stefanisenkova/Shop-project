package com.example.demo.repository;

import com.example.demo.Status;
import com.example.demo.entity.Order;
import com.example.demo.entity.Purchase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository repo;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductRepository productRepository;


    @Test
    @org.junit.jupiter.api.Order(23)
    public void testCreateOrder(){
        List<Purchase> purchases=purchaseRepository.findByPurchaseId(12);
        Order order=new Order();
        order.setPurchases( purchases);
        order.setPrice(purchases.get(0).getTotalAmount());
        order.setDate(Timestamp.valueOf(LocalDateTime.now()));
        order.setStatus(Status.NEW);

        Order savedOrder=repo.save(order);
        Order existOrder=entityManager.find(Order.class,savedOrder.getId());
        assertThat(order.getStatus()).isEqualTo(existOrder.getStatus());
        assertThat(order.getDate()).isEqualTo(existOrder.getDate());
        assertThat(order.getPrice()).isEqualTo(existOrder.getPrice());
        assertThat(order.getPurchases()).isEqualTo(existOrder.getPurchases());
    }

    @Test
    @org.junit.jupiter.api.Order(26)
    public void testListOrders(){
        List<Order> orders=repo.findAll();
        assertThat(orders).size().isGreaterThan(0);
    }


    @Test
    @org.junit.jupiter.api.Order(29)
    public void testDeleteOrderById(){
        int id=10;
        boolean isExistBeforeDelete=repo.findById(id).isPresent();
        repo.deleteById(id);
        boolean notExistAfterDelete=repo.findById(id).isPresent();
        assertTrue(isExistBeforeDelete);
        assertFalse(notExistAfterDelete);
    }
}
