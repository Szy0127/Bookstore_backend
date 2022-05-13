package com.web.bookstore.bookstore_backend.dao;

import com.web.bookstore.bookstore_backend.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getOrderByUserID(int userID);

    void saveOrder(Order order);
}
