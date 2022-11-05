package com.web.bookstore.bookstore_backend.controller;

import com.web.bookstore.bookstore_backend.utils.Constant;
import com.web.bookstore.bookstore_backend.utils.Msg;
import com.web.bookstore.bookstore_backend.utils.MsgUtil;
import com.web.bookstore.bookstore_backend.utils.SessionUtil;
import com.web.bookstore.bookstore_backend.entity.User;
import com.web.bookstore.bookstore_backend.service.TimeService;
import com.web.bookstore.bookstore_backend.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//@Scope("prototype")
@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    TimeService timeService;

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


//            System.out.println(this);
//            System.out.println(timeService);
            timeService.start();
//            System.out.println(username+"login succuess");
            return  MsgUtil.makeMsg(true, MsgUtil.LOGIN_SUCCESS_MSG, data);
        }
        System.out.println(username+"login failed");
        return MsgUtil.makeMsg(false,MsgUtil.LOGIN_USER_ERROR_MSG);
    }

    @RequestMapping("/logout")
    public long logout(){
//        System.out.println(this);
//        System.out.println(timeService);
        long res = timeService.finish();
        SessionUtil.removeSession();
        return res;
    }

    @RequestMapping("/checkSession")
    public boolean checkSession(){
        boolean success = SessionUtil.getAuth()!=null;
//        System.out.println("checksession"+success);
        return success;
    }


}
