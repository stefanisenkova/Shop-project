package com.example.demo.repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PurchaseRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository repo;


    @Test
    @org.junit.jupiter.api.Order(22)//самостоятелно работи
    public void testCreatePurchases(){
        List<Purchase> purchases=new ArrayList<>();
        Purchase purchase=new Purchase();
        Product product=productRepository.findByName("Picture");
        purchase.setProduct(product);
        String name =product.getName();
        purchase.setName(name);
        purchase.setProductPrice(product.getPrice());
        purchase.setQuantity(1);
        purchase.setTotalAmount(50);
        purchases.add(purchase);
        Purchase saved=purchaseRepository.save(purchase);
        Purchase exist=entityManager.find(Purchase.class,saved.getId());
        assertThat(purchase.getName()).isEqualTo(exist.getName());
        assertThat(purchase.getProductPrice()).isEqualTo(exist.getProductPrice());
        assertThat(purchase.getQuantity()).isEqualTo(exist.getQuantity());
        assertThat(purchase.getTotalAmount()).isEqualTo(exist.getTotalAmount());

    }

    @Test
    @org.junit.jupiter.api.Order(24)
    public void testAddOrderId(){
        List<Purchase> purchases=purchaseRepository.findByPurchaseId(12);
        Order order=repo.getById(10);

        for(int i=0;i<purchases.size();i++){
            purchases.get(i).setOrder(order);
            Purchase saved=purchaseRepository.save(purchases.get(i));
            Purchase exist=entityManager.find(Purchase.class,saved.getId());
            assertThat(purchases.get(i).getOrder()).isEqualTo(exist.getOrder());
        }
    }

    @Test
    @org.junit.jupiter.api.Order(25)
    public void testListPurchases(){
        List<Purchase> purchases=purchaseRepository.findAll();
        assertThat(purchases).size().isGreaterThan(0);
    }

    @Test
    @org.junit.jupiter.api.Order(27)
    public void testDeletePurchaseById(){
        int id=12;
        boolean isExistBeforeDelete=purchaseRepository.findById(id).isPresent();
        purchaseRepository.deleteById(id);
        boolean notExistAfterDelete=purchaseRepository.findById(id).isPresent();
        assertTrue(isExistBeforeDelete);
        assertFalse(notExistAfterDelete);
    }

}
