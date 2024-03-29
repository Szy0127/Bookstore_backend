package com.web.bookstore.bookstore_backend.controller;

import com.web.bookstore.bookstore_backend.Constant;
import com.web.bookstore.bookstore_backend.Msg;
import com.web.bookstore.bookstore_backend.MsgUtil;
import com.web.bookstore.bookstore_backend.SessionUtil;
import com.web.bookstore.bookstore_backend.entity.User;
import com.web.bookstore.bookstore_backend.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    UserService userService;

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
    public Msg login(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){

        User user = userService.login(username,password);
        if(user != null){
            if(user.isBan()){
                return MsgUtil.makeMsg(false, MsgUtil.BANNED);
            }
            JSONObject obj = new JSONObject();
            obj.put(Constant.USER_ID, user.getUserID());
            obj.put(Constant.USERNAME, user.getUsername());

            if(user.isAdmin()){
                obj.put(Constant.USER_TYPE, Constant.MANAGER);
            }else{
                obj.put(Constant.USER_TYPE, Constant.CUSTOMER);
            }
//            obj.put(Constant.USER_TYPE, 0);
            SessionUtil.setSession(obj);
//            System.out.println("set session");
            JSONObject data = JSONObject.fromObject(user);
            data.remove(Constant.PASSWORD);
            return  MsgUtil.makeMsg(true, MsgUtil.LOGIN_SUCCESS_MSG, data);
        }

        return MsgUtil.makeMsg(false,MsgUtil.LOGIN_USER_ERROR_MSG);
    }

    @RequestMapping("/logout")
    public void logout(){
        SessionUtil.removeSession();
    }

    @RequestMapping("/checkSession")
    public boolean checkSession(){
        return SessionUtil.getAuth()!=null;
    }


}
