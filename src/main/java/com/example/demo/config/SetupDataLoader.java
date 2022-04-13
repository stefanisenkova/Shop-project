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
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        List<Privilege> employeePrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
      //  Role adminRole = createRoleIfNotFound("ROLE_ADMIN", employeePrivileges);
        createRoleIfNotFound("ROLE_CUSTOMER", Arrays.asList(readPrivilege));
        Role employeeRole = createRoleIfNotFound("ROLE_EMPLOYEE", employeePrivileges);
/*
       User user = new User();
       // user.setId(2L);
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test2@test.com");
        user.setPassword(passwordEncoder.encode("test"));
        user.setAge(24);
        user.setSalary(8000);
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

 */



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

