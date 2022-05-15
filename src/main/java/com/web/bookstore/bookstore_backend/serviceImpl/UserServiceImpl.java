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
import java.util.LinkedList;
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
    @Autowired
    OrderItemDao orderItemDao;


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
    public List<BookItemWrapper> getCart(int userID, String password) {
        if(!valid(userID,password)){
            return null;
        }
        List<CartItem> cartItems = cartDao.getCartByUserID(userID);
        LinkedList<BookItemWrapper> cartItemWrappers=new LinkedList<>();
        for(CartItem cartItem:cartItems){
            Book book = bookDao.getBookByID(cartItem.getBookID());
            cartItemWrappers.add(new BookItemWrapper(book, cartItem.getAmount()));
//            System.out.println(book);
        }
        return cartItemWrappers;
    }

    @Transactional
    @Override
    public void updateCart(CartItem cartItem) {
//        System.out.println(cartItem);
        cartDao.updateCart(cartItem);
    }

    @Override
    public boolean addCart(int userID, String password, int bookID) {
        if(!valid(userID,password)){
            return false;
        }
        cartDao.addCart(new CartItem(userID,bookID));
        return true;
    }

    @Override
    public void removeCart(int userID, int bookID) {
        cartDao.removeCart(new CartItem(userID,bookID));
    }

    @Override
    public List<OrderWrapper> getOrderByUserID(int userID, String password) {
        if(!valid(userID,password)){
            return null;
        }
        List<Order> orders = orderDao.getOrderByUserID(userID);
        List<OrderWrapper> res = new ArrayList<>();
        for (Order order : orders) {
//            System.out.println(order.getOrderID());
            List<OrderItem> orderItems = orderItemDao.getOrderItemsByOrderID(order.getOrderID());
            List<BookItemWrapper> wrappers = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                BookItemWrapper bookItemWrapper = new BookItemWrapper(bookDao.getBookByID(orderItem.getBookID()), orderItem.getAmount());
                wrappers.add(bookItemWrapper);
//                System.out.println(bookItemWrapper.getBook());
            }
            res.add(new OrderWrapper(order, wrappers));
        }
//        System.out.println(res.get(2).getOrderItems().get(0).getBook());
        return res;
    }


    @Transactional
    @Override
    public boolean buyBooks(int userID,String password,List<BookItemSimple> books) {
        if(!valid(userID,password)){
            return false;
        }

        BigDecimal price = new BigDecimal(0);
        for(BookItemSimple b:books){
            Book book = bookDao.getBookByID(b.getBookID());
            if(book.getInventory() <=0){
                return false;
            }
            price = price.add(book.getPrice().multiply(new BigDecimal(b.getAmount())));
        }
        Order order = new Order(userID,price,"上海交通大学","54749110","");
        orderDao.saveOrder(order);
        for(BookItemSimple b:books){
            orderItemDao.saveOrderItem(new OrderItem(order.getOrderID(),b.getBookID(),b.getAmount()));
            removeCart(userID,b.getBookID());
        }
        return true;
    }
}
