package com.web.bookstore.bookstore_backend.serviceImpl;

import com.web.bookstore.bookstore_backend.dao.*;
import com.web.bookstore.bookstore_backend.entity.*;
import com.web.bookstore.bookstore_backend.service.UserService;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Order> getOrdersByUserID(int userID) {
        return orderDao.getOrdersByUserID(userID);
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

    @Override
    public List<Order> getOrders() {
        return orderDao.getOrders();
    }

    @Override
    public List<BookSaled> getBookSaled() {
        List<Order> orders= getOrders();
        Map<Integer,Integer> bookSaled = new HashMap<>();
        for(Order order:orders){
            for (OrderItem orderItem : order.getOrderItems()) {
                Integer bookID = orderItem.getBookID();
                Integer amount = 0;
                if(bookSaled.containsKey(bookID)){
                    amount += bookSaled.get(bookID);
                }
                amount += orderItem.getAmount();
                bookSaled.put(bookID, amount);
            }
        }
        List<BookSaled> res = new ArrayList<>();
        for(Map.Entry<Integer,Integer> entry:bookSaled.entrySet()){
            res.add(new BookSaled(bookDao.getBookByID(entry.getKey()), entry.getValue()));
        }
        return res;
    }

    @Override
    public List<UserConsumed> getUserConsumed() {
        List<Order> orders= getOrders();
        Map<Integer,BigDecimal> userConsumed = new HashMap<>();
        for(Order order:orders){
            Integer userID = order.getUserID();
            BigDecimal consumed = new BigDecimal(0);
            if (userConsumed.containsKey(userID)) {
                consumed = consumed.add(userConsumed.get(userID));
            }
            consumed = consumed.add(order.getPrice());
            userConsumed.put(userID, consumed);
        }
        List<UserConsumed> res = new ArrayList<>();
        for(Map.Entry<Integer,BigDecimal> entry:userConsumed.entrySet()){
            res.add(new UserConsumed(userDao.getUserByID(entry.getKey()), entry.getValue()));
        }
        System.out.println(res);
        return res;
    }
}
