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

    @RequestMapping("/getOrdersByTimeAndBook")
    public List<Order> getOrdersByTimeAndBook(
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestParam("bookName") String bookName
    ){
        if(!SessionUtil.checkAdmin()){
            return null;
        }
        return userService.getOrdersByTimeAndBook(start, end, bookName);
    }


    @RequestMapping("/getBookSaledByTimeBetween")
    public List<BookSaled> getBookSaledByTimeBetween(
            @RequestParam("start") String start,
            @RequestParam("end") String end
    ){
        if(!SessionUtil.checkAdmin()){
            return null;
        }
        return userService.getBookSaledByTimeBetween(start, end);
    }
    @RequestMapping("/getUserConsumedByTimeBetween")
    public List<UserConsumed> getUserConsumedByTimeBetween(
            @RequestParam("start") String start,
            @RequestParam("end") String end
    ){
        if(!SessionUtil.checkAdmin()){
            return null;
        }
        return userService.getUserConsumedByTimeBetween(start, end);
    }




}
