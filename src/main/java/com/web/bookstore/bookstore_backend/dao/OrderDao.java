package com.web.bookstore.bookstore_backend.dao;

import com.web.bookstore.bookstore_backend.entity.Order;

import java.sql.Timestamp;
import java.util.List;

public interface OrderDao {
    List<Order> getOrdersByUserID(int userID);

    void saveOrder(Order order);

    List<Order> getOrders();

    List<Order> getOrdersByTimeBetween(Timestamp start, Timestamp end);

    List<Order> getOrdersByTimeBefore(Timestamp t);

    List<Order> getOrdersByTimeAfter(Timestamp t);

    List<Order> getOrdersByUserAndTimeBetween(Integer userID,Timestamp start, Timestamp end);

    List<Order> getOrdersByUserAndTimeBefore(Integer userID,Timestamp t);

    List<Order> getOrdersByUserAndTimeAfter(Integer userID,Timestamp t);
}
