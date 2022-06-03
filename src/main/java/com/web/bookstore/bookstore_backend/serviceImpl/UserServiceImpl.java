package com.web.bookstore.bookstore_backend.serviceImpl;

import com.web.bookstore.bookstore_backend.dao.*;
import com.web.bookstore.bookstore_backend.entity.*;
import com.web.bookstore.bookstore_backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    BookDao bookDao;
    @Autowired
    CartDao cartDao;

    @Autowired
    OrderDao orderDao;



    public boolean valid(int userID,String password){
        User user = userDao.getUserByID(userID);
        if(user == null){
            return false;
        }
        return user.getPassword().equals(password);
    }
    @Override
    public boolean register(String username, String password, String email) {
        System.out.println(username);
        if(username.isEmpty() || username.length() > 20){
            return false;
        }
        if(email.length() > 50){
            return false;
        }

        userDao.register(new User(username,password,email));
        return true;
    }

    @Override
    public User login(String username, String password) {
        User user= userDao.getUserByName(username);//设置了uniquekey 保证唯一
        if(user==null){
            return null;
        }
        if(!user.getPassword().equals(password)){
            return null;
        }
        return user;
    }

    @Override
    public List<CartItem> getCart(int userID) {
        List<CartItem> cartItems = cartDao.getCartByUserID(userID);
        return cartItems;
    }

    @Transactional
    @Override
    public void updateCart(CartItem cartItem) {

        cartDao.updateCart(cartItem);
    }

    @Override
    public boolean addCart(int userID, int bookID) {
        cartDao.addCart(new CartItem(userID,bookID));
        return true;
    }

    @Override
    public void removeCart(int userID, int bookID) {
        cartDao.removeCart(new CartItem(userID,bookID));
    }

    @Override
    public List<Order> getOrderByUserID(int userID) {
        return orderDao.getOrderByUserID(userID);
    }


    @Transactional
    @Override
    public boolean buyBooks(int userID,List<BookItemSimple> books) {

        //判断是否有库存  计算价格
        BigDecimal price = new BigDecimal(0);
        for(BookItemSimple b:books){
            Book book = bookDao.getBookByID(b.getBookID());
            if(book.getInventory() <=0){
                return false;
            }
            price = price.add(book.getPrice().multiply(new BigDecimal(b.getAmount())));
        }

        //创建订单 放入items
        Order order = new Order(userID,price,"上海交通大学","54749110","",books);
        orderDao.saveOrder(order);

        //删掉购物车的对应items
        for(BookItemSimple b:books){
            removeCart(userID,b.getBookID());
        }

        return true;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public boolean banUser(int userID) {
        return userDao.banUser(userID);
    }
}
