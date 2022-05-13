package com.web.bookstore.bookstore_backend.repository;

import com.web.bookstore.bookstore_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository <OrderItem,Integer> {


    @Query("select oi from OrderItem oi where oi.orderID=:orderID")
    List<OrderItem> getOrderItemsByOrderID(
            @Param("orderID") String orderID
    );
}
