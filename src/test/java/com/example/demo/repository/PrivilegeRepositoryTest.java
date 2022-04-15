package com.example.demo.repository;

import com.example.demo.entity.Privilege;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrivilegeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PrivilegeRepository repo;
    @Autowired
    private RoleRepository roleRepository;


    @Test
    @Order(2)
    public void testCreatePrivilege(){
        Privilege privilege=new Privilege();
        privilege.setName("CUSTOMER_PRIVILEGE");
        Privilege savedPrivilege=repo.save(privilege);
        Privilege existPrivilege=entityManager.find(Privilege.class,savedPrivilege.getId());
        assertThat(privilege.getName()).isEqualTo(existPrivilege.getName());
        assertThat(privilege.getRoles()).isEqualTo(existPrivilege.getRoles());
    }

    @Test
    @Order(3)
    public void testMappingRoleAndPrivilege(){
        Privilege privilege=repo.findByName("CUSTOMER_PRIVILEGE");
        Collection<Role> roles= new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_CUSTOMER"));
        privilege.setRoles(roles);
        Privilege savedPrivilege=repo.save(privilege);
        Privilege existPrivilege=entityManager.find(Privilege.class,savedPrivilege.getId());
        assertThat(privilege.getRoles()).isEqualTo(existPrivilege.getRoles());

    }

    @Test
    @Order(4)
    public void testFindPrivilegeIdByName(){
        Privilege privilege=repo.findByName("CUSTOMER_PRIVILEGE");
        assertThat(privilege.getId()).isEqualTo(13);
    }

    @Test
    @Order(5)
    public void testListPrivilege(){
        List<Privilege> privileges=repo.findAll();
        assertThat(privileges).size().isGreaterThan(0);
    }
}
