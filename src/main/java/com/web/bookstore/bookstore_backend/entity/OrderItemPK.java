package com.web.bookstore.bookstore_backend.entity;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
public class OrderItemPK implements Serializable {

    private String orderID;
    private int bookID;

    public OrderItemPK(OrderItem orderItem){
        orderID = orderItem.getOrderID();
        bookID = orderItem.getBookID();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPK that = (OrderItemPK) o;
        return Objects.equals(orderID, that.orderID) && Objects.equals(bookID, that.bookID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, bookID);
    }
}
