package com.web.bookstore.bookstore_backend.repository;

import com.web.bookstore.bookstore_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {


    List<Order> getOrderByUserID(int id);
}
