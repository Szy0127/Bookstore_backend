package com.web.bookstore.bookstore_backend.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;




@Getter
public class OrderWrapper {

    private String orderID;
    private Timestamp time;
    private BigDecimal price;
    private String address;
    private String phone;
    private String comment;
    private int phase;

    private List<BookItemWrapper> orderItems;

    public OrderWrapper(Order order,List<BookItemWrapper> orderItems){
        this.orderID = order.getOrderID();
        this.time = order.getTime();
        this.price = order.getPrice();
        this.address = order.getAddress();
        this.phone = order.getPhone();
        this.comment = order.getComment();
        this.phase = order.getPhase();
        this.orderItems = orderItems;
    }
}
