package com.web.bookstore.bookstore_backend.repository;

import com.web.bookstore.bookstore_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {


    List<Order> getOrderByUserID(int id);

    List<Order> getOrdersByTimeBetween(Timestamp start, Timestamp end);

    List<Order> getOrdersByTimeBefore(Timestamp t);

    List<Order> getOrdersByTimeAfter(Timestamp t);
}
