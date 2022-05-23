package com.web.bookstore.bookstore_backend.repository;

import com.web.bookstore.bookstore_backend.entity.CartItem;
import com.web.bookstore.bookstore_backend.entity.CartItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, CartItemPK> {



    List<CartItem> getCartItemsByUserID(int userID);



}
