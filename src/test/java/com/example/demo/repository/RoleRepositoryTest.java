package com.example.demo.repository;


import com.example.demo.entity.Role;
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
public class RoleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RoleRepository repo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    Role role=new Role();
    @Test
    @Order(1)
    public void testCreateRole(){
        role.setName("ROLE_CUSTOMER");
        Role savedRole=repo.save(role);
        Role existRole=entityManager.find(Role.class,savedRole.getId());
        assertThat(role.getName()).isEqualTo(existRole.getName());
        assertThat(role.getUsers()).isEqualTo(existRole.getUsers());
        assertThat(role.getPrivileges()).isEqualTo(existRole.getPrivileges());
    }

    @Test
    @Order(6)
    public void testFindRoleIdByName(){
        Role role=repo.findByName("ROLE_CUSTOMER");
        assertThat(role.getId()).isEqualTo(22);
    }

    @Test
    @Order(7)
    public void testListRoles(){
        List<Role> roles=repo.findAll();
        assertThat(roles).size().isGreaterThan(0);
    }
}
