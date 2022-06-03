package com.web.bookstore.bookstore_backend.entity;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserConsumed {
    private User user;
    private BigDecimal consumed;


    public UserConsumed(User user,BigDecimal consumed){
        this.user = user;
        this.consumed = consumed;
    }
}
