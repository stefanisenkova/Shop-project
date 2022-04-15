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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TypeProductRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TypeProductRepository repo;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Order(12)
    public void testCreateTypeProductName(){
        TypeProduct typeProduct=new TypeProduct();
        typeProduct.setName("Others");
        TypeProduct saved=repo.save(typeProduct);
        TypeProduct exist=entityManager.find(TypeProduct.class,saved.getId());
        assertThat(typeProduct.getName()).isEqualTo(exist.getName());
    }

    @Test
    @Order(14)//като се пусне самостоятелно работи
    public void testFindByNameContainingIgnoreCase(){
        List<TypeProduct>  typeProduct= repo.findByNameContainingIgnoreCase("Ot");
        List<Product> listProducts = productRepository.findAll();
        Product correctCategory= new Product();
        for(int i=0;i<listProducts.size();i++){
            if(listProducts.get(i).getTypeProduct().name.equals(typeProduct.get(0).name)&&listProducts.get(i).getQuantity()>0){
                correctCategory=(listProducts.get(i));

            }
        }
        assertThat(correctCategory.getName()).isEqualTo("Picture");
    }

    @Test
    @Order(15)
    public void testListTypesProduct(){
        List<TypeProduct> typeProducts=repo.findAll();
        assertThat(typeProducts).size().isGreaterThan(0);
    }

}
