package com.web.bookstore.bookstore_backend.service;

import com.web.bookstore.bookstore_backend.entity.*;
import javafx.util.Pair;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface UserService {
    public boolean register(String username, String password, String email);
    public User login(String username,String password);

    public List<CartItem> getCart(int userID, String password);
    public void updateCart(CartItem cartItem);
    public boolean addCart(int userID,String password,int bookID);

    public void removeCart(int userID, int bookID);

    public List<Order> getOrderByUserID(int userID,String password);

    public boolean buyBooks(int userID, String password, List<BookItemSimple> books);

//    public void getOrder()
}
