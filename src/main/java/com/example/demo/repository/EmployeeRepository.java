package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query("SELECT u FROM Employee u WHERE u.lastName = ?1")
    public Employee findByLastName(String lastName);

    @Query("SELECT u FROM Employee u WHERE u.id = ?1")
    public Employee findById(long id);


}
