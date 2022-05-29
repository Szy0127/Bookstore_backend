package com.web.bookstore.bookstore_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
//import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bookstore.bookstore_backend.SessionUtil;
import com.web.bookstore.bookstore_backend.entity.*;
import com.web.bookstore.bookstore_backend.service.UserService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/getCart")
    public List<CartItem> getCart(
            @RequestParam("userID") int userID
    ){
        if(!SessionUtil.checkAuth()){
            return null;
        }
        return userService.getCart(userID);
    }

    @PostMapping("/updateCart")
    public void updateCart(
            @RequestParam("userID") int userID,
            @RequestParam("bookID") int bookID,
            @RequestParam("amount") int amount
    ){
        if(!SessionUtil.checkAuth()){
            return ;
        }
        userService.updateCart(new CartItem(userID,bookID,amount));
    }

    @PostMapping("/removeCart")
    public void removeCart(
            @RequestParam("userID") int userID,
            @RequestParam("bookID") int bookID
    ){
        if(!SessionUtil.checkAuth()){
            return ;
        }
        userService.removeCart(userID,bookID);
    }

    @PostMapping("/addCart")
    public boolean addCart(
            @RequestParam("userID") int userID,
            @RequestParam("bookID") int bookID
    ){
        if(!SessionUtil.checkAuth()){
            return false;
        }
        return userService.addCart(userID, bookID);
    }

    @PostMapping("/getOrder")
    public List<Order> getOrder(
            @RequestParam("userID") int userID
    ){

        return userService.getOrderByUserID(userID);

    }

//    @PostMapping("/buyBooks/{userID}/{password}")//必须用/
//    public boolean buyBooks(
//            @PathVariable("userID")int userID,
//            @PathVariable("password")String  password,
//            @RequestBody List<BookItemSimple> books//之前的参数必须用path传
     @PostMapping("/buyBooks/{userID}")
    public boolean buyBooks(
            @PathVariable("userID")int userID,
            @RequestBody List<BookItemSimple> books
    ){
        if(!SessionUtil.checkAuth()){
            return false;
        }
        return userService.buyBooks(userID,books);
    }

}
