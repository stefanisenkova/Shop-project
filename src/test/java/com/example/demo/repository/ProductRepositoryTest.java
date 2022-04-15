package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.entity.TypeProduct;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ProductRepository repo;
    @Autowired
    private TypeProductRepository typeProductRepository;

    @Test
    @Order(13)
    public void testCreateProduct(){
        Product product=new Product();
        product.setName("Picture");
        product.setQuantity(20);
        TypeProduct typeProduct=typeProductRepository.findByName("Others");
        product.setTypeProduct(typeProduct);
        product.setPrice(50);

        Product saved=repo.save(product);
        Product exist=entityManager.find(Product.class,saved.getId());
        assertThat(product.getName()).isEqualTo(exist.getName());
        assertThat(product.getQuantity()).isEqualTo(exist.getQuantity());
        assertThat(product.getTypeProduct()).isEqualTo(exist.getTypeProduct());
        assertThat(product.getPrice()).isEqualTo(exist.getPrice());
    }

    @Test
    @Order(16)
    public void testFindProductByPriceGreaterThanEqual8(){
        List<Product> products =repo.findByPriceGreaterThanEqual(8);
        assertThat(products.get(0).getName()).isEqualTo("Meat");
        assertThat(products.get(1).getName()).isEqualTo("Lipstick");
        assertThat(products.get(2).getName()).isEqualTo("Picture");
    }

    @Test
    @Order(17)
    public void testFindProductByPriceLessThan8(){
        List<Product> products = repo.findByPriceLessThan(8);
        assertThat(products.get(0).getName()).isEqualTo("Water");
        assertThat(products.get(1).getName()).isEqualTo("Toilet paper");
    }

    @Test
    @Order(18)
    public void testFindProductByQuantityGreaterThanEqual30(){
        List<Product> products = repo.findByQuantityGreaterThanEqual(30);
        assertThat(products.get(0).getName()).isEqualTo("Meat");
        assertThat(products.get(1).getName()).isEqualTo("Water");
    }

    @Test
    @Order(19)
    public void testFindProductByQuantityLessThan30(){
        List<Product> products = repo.findByQuantityLessThan(30);
        assertThat(products.get(0).getName()).isEqualTo("Toilet paper");
        assertThat(products.get(1).getName()).isEqualTo("Lipstick");
        assertThat(products.get(2).getName()).isEqualTo("Picture");

    }

    @Test
    @Order(20)
    public void testFindProductByNameContainingIgnoreCase(){
        List <Product> products = repo.findByNameContainingIgnoreCase("Pi");
        assertThat(products.get(0).getName()).isEqualTo("Picture");

    }

    @Test
    @Order(21)
    public void testListProducts(){
        List<Product> products=repo.findAll();
        assertThat(products).size().isGreaterThan(0);
    }

    @Test
    @Order(28)
    public void testDeleteProductById(){
        Integer id=15;
        boolean isExistBeforeDelete=repo.findById(id).isPresent();
        repo.deleteById(id);
        boolean notExistAfterDelete=repo.findById(id).isPresent();
        assertTrue(isExistBeforeDelete);
        assertFalse(notExistAfterDelete);
    }
}
