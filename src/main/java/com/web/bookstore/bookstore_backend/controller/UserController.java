package com.web.bookstore.bookstore_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
//import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bookstore.bookstore_backend.Msg;
import com.web.bookstore.bookstore_backend.MsgUtil;
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

    @RequestMapping("/getUsers")
    public List<User> getUsers(){
        if(!SessionUtil.checkAdmin()){
            return null;
        }
        System.out.println(userService.getUsers());

        return userService.getUsers();
    }

    @RequestMapping("/banUser")
    public Msg banUser(
            @RequestParam("userID") Integer userID
    ){
        if(!SessionUtil.checkAdmin()){
            return MsgUtil.makeMsg(false,MsgUtil.AUTH);
        }
        userService.banUser(userID);

        return MsgUtil.makeMsg(true,MsgUtil.SUCCESS_MSG);
    }

    @RequestMapping("/getOrders")
    public List<Order> getOrders(){
        if(!SessionUtil.checkAdmin()){
            return null;
        }
        return userService.getOrders();
    }

    @RequestMapping("/getBookSaled")
    public List<BookSaled> getBookSaled(){
        if(!SessionUtil.checkAdmin()){
            return null;
        }
        return userService.getBookSaled();
    }
    @RequestMapping("/getUserConsumed")
    public List<UserConsumed> getUserConsumed(){
        if(!SessionUtil.checkAdmin()){
            return null;
        }
        return userService.getUserConsumed();
    }


}
