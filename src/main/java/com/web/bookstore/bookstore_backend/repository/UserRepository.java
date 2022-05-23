package com.web.bookstore.bookstore_backend.repository;

import com.web.bookstore.bookstore_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {


    User getUserByUsername(String username);
}
