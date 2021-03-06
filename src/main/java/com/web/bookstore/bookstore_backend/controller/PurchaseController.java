package com.web.bookstore.bookstore_backend.controller;

import com.web.bookstore.bookstore_backend.SessionUtil;
import com.web.bookstore.bookstore_backend.entity.BookItemSimple;
import com.web.bookstore.bookstore_backend.entity.CartItem;
import com.web.bookstore.bookstore_backend.entity.Order;
import com.web.bookstore.bookstore_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PurchaseController {

    @Autowired
    private UserService userService;
    @PostMapping("/getCart")
    public List<CartItem> getCart(
//            @RequestParam("userID") int userID
    ){
        if(!SessionUtil.checkAuth()){
            return null;
        }
        Integer userID = SessionUtil.getUserID();
        return userService.getCart(userID);
    }

    @PostMapping("/updateCart")
    public void updateCart(
//            @RequestParam("userID") int userID,
            @RequestParam("bookID") int bookID,
            @RequestParam("amount") int amount
    ){
        if(!SessionUtil.checkAuth()){
            return ;
        }
        Integer userID = SessionUtil.getUserID();
        userService.updateCart(new CartItem(userID,bookID,amount));
    }

    @PostMapping("/removeCart")
    public void removeCart(
//            @RequestParam("userID") int userID,
            @RequestParam("bookID") int bookID
    ){
        if(!SessionUtil.checkAuth()){
            return ;
        }
        Integer userID = SessionUtil.getUserID();
        userService.removeCart(userID,bookID);
    }

    @PostMapping("/addCart")
    public boolean addCart(
//            @RequestParam("userID") int userID,
            @RequestParam("bookID") int bookID
    ){
        if(!SessionUtil.checkAuth()){
            return false;
        }
        Integer userID = SessionUtil.getUserID();
        return userService.addCart(userID, bookID);
    }

    @PostMapping("/getOrdersByUserID")
    public List<Order> getOrdersByUserID(
//            @RequestParam("userID") int userID
    ){
        if(!SessionUtil.checkAuth()){
            return null;
        }
        Integer userID = SessionUtil.getUserID();
        return userService.getOrdersByUserID(userID);

    }

    @PostMapping("/buyBooks")
    public boolean buyBooks(
//            @PathVariable("userID")int userID,
            @RequestBody List<BookItemSimple> books
    ){
        if(!SessionUtil.checkAuth()){
            return false;
        }
        Integer userID = SessionUtil.getUserID();
        try{
            return userService.buyBooks(userID,books);
        } catch (Exception e) {
            return false;
        }
    }
}
