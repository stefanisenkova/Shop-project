package com.example.demo.repository;


import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.lastName = ?1")
    public User findByLastName(String lastName);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    public User findById(long id);


}
