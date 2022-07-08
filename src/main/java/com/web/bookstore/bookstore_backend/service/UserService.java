package com.web.bookstore.bookstore_backend.service;

import com.web.bookstore.bookstore_backend.entity.*;
import javafx.util.Pair;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface UserService {
    public boolean register(String username, String password, String email);
    public User login(String username,String password);

    public List<CartItem> getCart(int userID);
    public void updateCart(CartItem cartItem);
    public boolean addCart(int userID,int bookID);

    public void removeCart(int userID, int bookID);

    public List<Order> getOrdersByUserID(int userID);

    public boolean buyBooks(int userID, List<BookItemSimple> books) throws Exception;

    public List<User> getUsers();

//    public void getOrder()

    public boolean banUser(int userID);

    public List<Order> getOrdersByTimeAndBook(String start,String end,String bookName);
    public List<Order> getOrdersByUserAndTimeAndBook(Integer userID,String start,String end,String bookName);

    public List<BookSaled> getBookSaledByTimeBetween(String start, String end);

    public UserStatistic getUserStatistic(Integer userID,String start, String end);
    public List<UserConsumed> getUserConsumedByTimeBetween(String start, String end);
}
