package com.example.demo.config;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findById(Long.parseLong(id));

        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(employee);
    }
}