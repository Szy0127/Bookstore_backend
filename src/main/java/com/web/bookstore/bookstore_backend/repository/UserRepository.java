package com.web.bookstore.bookstore_backend.repository;

import com.web.bookstore.bookstore_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {


    User getUserByUsername(String username);
}
