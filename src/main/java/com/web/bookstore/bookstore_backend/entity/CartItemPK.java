package com.web.bookstore.bookstore_backend.entity;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@NoArgsConstructor
public class CartItemPK implements Serializable {

    private int userID;
    private int bookID;

    public CartItemPK(CartItem cartItem){
        userID = cartItem.getUserID();
        bookID = cartItem.getBookID();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemPK that = (CartItemPK) o;
        return Objects.equals(userID, that.userID) && Objects.equals(bookID, that.bookID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, bookID);
    }
}
