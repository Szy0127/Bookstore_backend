package com.web.bookstore.bookstore_backend.dao;

import com.web.bookstore.bookstore_backend.entity.OrderItem;

import java.util.List;

public interface OrderItemDao {

    List<OrderItem> getOrderItemsByOrderID(String orderID);

    void saveOrderItem(OrderItem orderItem);
}
