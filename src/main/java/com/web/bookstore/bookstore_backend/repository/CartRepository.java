package com.web.bookstore.bookstore_backend.repository;

import com.web.bookstore.bookstore_backend.entity.CartItem;
import com.web.bookstore.bookstore_backend.entity.CartItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, CartItemPK> {

    @Query("select c from CartItem c where c.userID = :userID")
    List<CartItem> getCartItemsByUserID(
            @Param("userID")int userID
    );

//    @Query("select c from CartItem c where c.userID=:userID and c.bookID=:bookID")
//    CartItem getCartItemsByUserBook(
//            @Param("userID")int userID,
//            @Param("bookID")int bookID
//
//    );


}
