package com.web.bookstore.bookstore_backend.serviceImpl;

import com.web.bookstore.bookstore_backend.service.TimeService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;



@Service
@SessionScope
//@Scope(value= WebApplicationContext.SCOPE_SESSION,proxyMode = ScopedProxyMode.INTERFACES)
public class TimeServiceImpl implements TimeService {

    long loginTime;

    @Override
    public void start() {
        loginTime = System.currentTimeMillis();
//        System.out.println(loginTime);
    }

    @Override
    public long finish() {
//        System.out.println(loginTime);
        if(loginTime==0){
            return 0;
        } else{
            return System.currentTimeMillis() - loginTime;
        }
    }
}
