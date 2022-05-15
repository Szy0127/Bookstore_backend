package com.web.bookstore.bookstore_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

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


    public Order(int userID, BigDecimal price, String address, String phone, String comment){
        this.userID = userID;
//        this.time = new Date().getTime();
        this.orderID = String.valueOf(new Date().getTime()) + new Random().nextInt(1000000);
        this.price = price;
        this.address = address;
        this.phone = phone;
        this.comment = comment;
        this.phase = 1;
    }
}
