package com.web.bookstore.bookstore_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "cart_item")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@IdClass(value = CartItemPK.class)
public class CartItem {

    @Id
    @Column(name="userID")
    private int userID;

    @Id
    @Column(name="bookID")
    private int bookID;


    @Column(name="amount")
    private int amount;

    public CartItem(int userID,int bookID){
        this.userID = userID;
        this.bookID = bookID;
        this.amount = 1;
    }

    public CartItem(int userID,int bookID,int amount){
        this.userID = userID;
        this.bookID = bookID;
        this.amount = amount;
    }

}