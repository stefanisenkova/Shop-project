package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query("SELECT u FROM Customer u WHERE u.lastName = ?1")
    public Customer findByLastName(String lastName);

    @Query("SELECT u FROM Customer u WHERE u.id = ?1")
    public Customer findById(long id);

}
