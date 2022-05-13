package com.web.bookstore.bookstore_backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "order_item")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto不支持mysql
    private String id;

    @Column(name="orderID")
    private String orderID;
    private int bookID;

    private int amount;

    public OrderItem(String orderID,int bookID,int amount){
        this.orderID = orderID;
        this.bookID = bookID;
        this.amount = amount;
    }
}
