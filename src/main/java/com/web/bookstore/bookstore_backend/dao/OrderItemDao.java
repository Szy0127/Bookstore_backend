package com.web.bookstore.bookstore_backend.dao;

import com.web.bookstore.bookstore_backend.entity.OrderItem;

import java.util.List;

public interface OrderItemDao {
    void saveOrderItems(List<OrderItem> items);

    List<OrderItem> getOrderItemsByOrderID(String orderID);
}
