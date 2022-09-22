package com.web.bookstore.bookstore_backend.repository;


import com.web.bookstore.bookstore_backend.entity.OrderItem;
import com.web.bookstore.bookstore_backend.entity.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

    List<OrderItem> getOrderItemsByOrderID(String orderID);
}
