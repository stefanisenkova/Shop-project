package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByLastName(username);

        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(employee);
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findById(Math.toIntExact(id));

        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(employee);
    }
}