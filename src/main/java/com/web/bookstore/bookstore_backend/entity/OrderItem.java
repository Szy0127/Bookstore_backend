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
@IdClass(value = OrderItemPK.class)
public class OrderItem {

    @Id
    @Column(name="orderID")
    private String orderID;

    @Id
    @Column(name="bookID")
    private int bookID;

    @OneToOne(fetch = FetchType.LAZY)//cascade none
    @JoinColumn(name="bookID",referencedColumnName = "bookID",insertable = false,updatable = false)
    private Book book;
    private int amount;

    public OrderItem(String orderID,int bookID,int amount){
        this.orderID = orderID;
        this.bookID = bookID;
        this.amount = amount;
    }
}
