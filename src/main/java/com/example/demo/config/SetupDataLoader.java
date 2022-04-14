package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.entity.Privilege;
import com.example.demo.entity.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.PrivilegeRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = true;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        Privilege customer
                = createPrivilegeIfNotFound("CUSTOMER_PRIVILEGE");
        Privilege employee
                = createPrivilegeIfNotFound("EMPLOYEE_PRIVILEGE");
        List<Privilege> employeePrivileges = Arrays.asList(
                customer, employee);
        createRoleIfNotFound("ROLE_CUSTOMER", Arrays.asList(customer));
        Role employeeRole = createRoleIfNotFound("ROLE_EMPLOYEE", employeePrivileges);
        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}

