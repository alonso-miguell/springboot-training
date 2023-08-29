package com.deloitte.spring.demo.repositories;


import com.deloitte.spring.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{

    User findByUsername(String username);
}