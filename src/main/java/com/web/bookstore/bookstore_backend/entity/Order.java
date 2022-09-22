package com.web.bookstore.bookstore_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "bookorder")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Order {
    @Id
    @Column(name = "orderID")
    private String orderID;

    private int userID;

    @Column(name="time")
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp time;

    private BigDecimal price;
    private String address;
    private String phone;
    private String comment;
    private int phase;

//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JoinColumn(name="orderID")
//    private Set<OrderItem> orderItems;


    public Order(int userID, BigDecimal price, String address, String phone, String comment, List<BookItemSimple> books){
        this.userID = userID;
//        this.time = new Date().getTime();
        this.orderID = String.valueOf(new Date().getTime()) + new Random().nextInt(1000000);
        this.price = price;
        this.address = address;
        this.phone = phone;
        this.comment = comment;
        this.phase = 1;


        //由于与前端的接口设计中 所有OrderItem是包含在order中被返回的  所以取消级联后前端无法展示
//        this.orderItems = new HashSet<>();
//        for(BookItemSimple b:books){
//            this.orderItems.add(new OrderItem(this.orderID, b.getBookID(), b.getAmount()));
//        }
    }

}
