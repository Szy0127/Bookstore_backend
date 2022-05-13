package com.web.bookstore.bookstore_backend.dao;

import com.web.bookstore.bookstore_backend.entity.CartItem;

import java.util.List;

public interface CartDao {
    public List<CartItem> getCartByUserID(int userID);

    public void updateCart(CartItem cartItem);

    public void addCart(CartItem cartItem);

    public void removeCart(CartItem cartItem);
}
