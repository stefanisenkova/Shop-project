package com.example.demo.repository;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Order(8)
    public void testCreateUser(){
        User user=new User();
        user.setFirstName("Kaloqn");
        user.setLastName("Petkov");
        user.setAge(22);
        user.setEmail("k_ivanov@gmail.com");
        user.setPassword("123456");
        user.setSalary(1200);
        Collection<Role> roles=new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_CUSTOMER"));
        user.setRoles(roles);

        User savedUser=repo.save(user);
        User existUser=entityManager.find(User.class,savedUser.getId());
        assertThat(user.getFirstName()).isEqualTo(existUser.getFirstName());
        assertThat(user.getLastName()).isEqualTo(existUser.getLastName());
        assertThat(user.getAge()).isEqualTo(existUser.getAge());
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(existUser.getPassword());
        assertThat(user.getSalary()).isEqualTo(existUser.getSalary());
        assertThat(user.getRoles()).isEqualTo(existUser.getRoles());
    }

    @Test
    @Order(9)
    public void testFindUserByLastName(){
        User exist=repo.findByLastName("Petkov");
        assertThat(exist.getFirstName()).isEqualTo("Kaloqn");
    }

    @Test
    @Order(10)
    public void testFindUserById(){
        User exist=repo.findByLastName("Petkov");
        long id=exist.getId();
        User exist2=repo.findById(id);
        assertThat(exist2.getAge()).isEqualTo(22);
    }

    @Test
    @Order(11)
    public void testListUsers(){
        List<User> users=repo.findAll();
        assertThat(users).size().isGreaterThan(0);
    }
}
