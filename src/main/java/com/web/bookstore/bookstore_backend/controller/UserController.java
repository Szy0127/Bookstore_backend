package com.web.bookstore.bookstore_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
//import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
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

    @PostMapping("/register")
    public boolean register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email
    ){
        try{
            return userService.register(username,password,email);
        }catch (Exception e){
            return false;
        }
    }


    @PostMapping("/login")
    public User login(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){

        User res = userService.login(username,password);
        if(res == null){
            System.out.println(username+"login failed");
        }else{
            System.out.println(username+"login successfully");
        }
        return res;
    }

    @PostMapping("/getCart")
    public List<CartItem> getCart(
            @RequestParam("userID") int userID,
            @RequestParam("password") String password
    ){

//        System.out.println(userID);
        List<CartItem> res = userService.getCart(userID,password);
//        System.out.println(res);
        return res;
    }

    @PostMapping("/updateCart")
    public void updateCart(
            @RequestParam("userID") int userID,
            @RequestParam("bookID") int bookID,
            @RequestParam("amount") int amount
    ){
        userService.updateCart(new CartItem(userID,bookID,amount));
    }

    @PostMapping("/removeCart")
    public void removeCart(
            @RequestParam("userID") int userID,
            @RequestParam("bookID") int bookID

    ){
        userService.removeCart(userID,bookID);
    }

    @PostMapping("/addCart")
    public boolean addCart(
            @RequestParam("userID") int userID,
            @RequestParam("password") String password,
            @RequestParam("bookID") int bookID
    ){
        return userService.addCart(userID, password, bookID);
    }

    @PostMapping("/getOrder")
    public List<Order> getOrder(
            @RequestParam("userID") int userID,
            @RequestParam("password") String password
    ){
        List<Order> res = userService.getOrderByUserID(userID, password);

        return res;
    }
//@PostMapping("/getOrder")
//public String getOrder(
//        @RequestParam("userID") int userID,
//        @RequestParam("password") String password
//) throws JsonProcessingException {
//    List<OrderWrapper> res = userService.getOrderByUserID(userID, password);
//    String js = new ObjectMapper().writeValueAsString(res);
//    for(OrderWrapper orderWrapper:res){
//        System.out.println(orderWrapper.getOrderID());
//        for(BookItemWrapper bookItemWrapper:orderWrapper.getOrderItems()){
//            System.out.println(bookItemWrapper.getBook());
//        }
//    }
//    return js;
//}

    @PostMapping("/buyBooks/{userID}/{password}")//必须用/
    public boolean buyBooks(
            @PathVariable("userID")int userID,
            @PathVariable("password")String  password,
            @RequestBody List<BookItemSimple> books//之前的参数必须用path传
    ){
        return userService.buyBooks(userID,password,books);
    }

}
