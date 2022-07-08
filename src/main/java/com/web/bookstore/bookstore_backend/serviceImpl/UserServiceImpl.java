package com.web.bookstore.bookstore_backend.serviceImpl;

import com.web.bookstore.bookstore_backend.dao.*;
import com.web.bookstore.bookstore_backend.entity.*;
import com.web.bookstore.bookstore_backend.service.UserService;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        if(!email.contains("@")){
            return false;
        }
        if(!email.contains(".")){
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


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean buyBooks(int userID,List<BookItemSimple> books) throws Exception{

        //判断是否有库存  计算价格
        BigDecimal price = new BigDecimal(0);
        for(BookItemSimple b:books){
            Book book = bookDao.getBookByID(b.getBookID());
            if(!bookDao.buyBook(b.getBookID(),b.getAmount())){
                continue;
            }
            price = price.add(book.getPrice().multiply(new BigDecimal(b.getAmount())));
        }

        if(price.equals(new BigDecimal(0))){
            throw new Exception();
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
    public List<Order> getOrdersByTimeAndBook(String start, String end, String bookName) {
        List<Order> orders = getOrdersByTime(start, end);
        return orderFiltedByBook(orders, bookName);
    }

    public List<Order> orderFiltedByBook(List<Order> origin,String bookName){
        if(bookName.isEmpty()){
            return origin;
        }
        List<Order> res = new ArrayList<>();
        for(Order order:origin){
            for (OrderItem orderItem : order.getOrderItems()) {
                Book book = orderItem.getBook();
                if (book.getName().contains(bookName)) {
                    res.add(order);
                    break;
                }
            }
        }
        return res;
    }

    @Override
    public List<Order> getOrdersByUserAndTimeAndBook(Integer userID, String start, String end, String bookName) {
        List<Order> orders = getOrdersByUserAndTime(userID, start, end);
        return orderFiltedByBook(orders, bookName);
    }

    private List<BookSaled> _getBookSaled(List<Order> orders){
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

    private List<UserConsumed> _getUserConsumed(List<Order> orders){
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

    private Timestamp str2timestamp(String s){
        if(s.isEmpty()){
            return null;
        }
        Timestamp ts = null;
        try {
            ts = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(s).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return ts;
    }
    private Timestamp str2timestamp_end(String s){
        if(s.isEmpty()){
            return null;
        }
        //默认s是当天0点 这样当天的都不会被包括 往后加一天
        Timestamp ts = null;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,1);
            date = calendar.getTime();
            ts = new Timestamp(date.getTime() - 1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return ts;
    }

    private List<Order> getOrdersByTime(String start,String end){
        Timestamp be = str2timestamp(start);
        Timestamp en = str2timestamp_end(end);
        if(be==null&&en==null){
            return orderDao.getOrders();
        }
        if(be==null){
            return orderDao.getOrdersByTimeBefore(en);
        }
        if(en==null){
            return orderDao.getOrdersByTimeAfter(be);
        }
        return orderDao.getOrdersByTimeBetween(be, en);
    }

    private List<Order> getOrdersByUserAndTime(Integer userID,String start,String end){
        Timestamp be = str2timestamp(start);
        Timestamp en = str2timestamp_end(end);
        if(be==null&&en==null){
            return orderDao.getOrdersByUserID(userID);
        }
        if(be==null){
            return orderDao.getOrdersByUserAndTimeBefore(userID, en);
        }
        if(en==null){
            return orderDao.getOrdersByUserAndTimeAfter(userID, be);
        }
        return orderDao.getOrdersByUserAndTimeBetween(userID, be, en);
    }

    @Override
    public List<BookSaled> getBookSaledByTimeBetween(String start, String end) {
        return _getBookSaled(getOrdersByTime(start,end));
    }

    @Override
    public List<UserConsumed> getUserConsumedByTimeBetween(String start, String end) {
        return _getUserConsumed(getOrdersByTime(start, end));
    }

    @Override
    public UserStatistic getUserStatistic(Integer userID, String start, String end) {
        List<Order> orders = getOrdersByUserAndTime(userID, start, end);
        Map<Integer,Integer> bookSaled = new HashMap<>();
        BigDecimal consumed = new BigDecimal(0);
        Integer bookAmount = 0;
        for(Order order:orders){
            for (OrderItem orderItem : order.getOrderItems()) {
                Integer bookID = orderItem.getBookID();
                Integer amount = 0;
                if(bookSaled.containsKey(bookID)){
                    amount += bookSaled.get(bookID);
                }
                amount += orderItem.getAmount();
                bookAmount += orderItem.getAmount();
                bookSaled.put(bookID, amount);
            }
            consumed = consumed.add(order.getPrice());
        }
        List<BookSaled> res = new ArrayList<>();
        for(Map.Entry<Integer,Integer> entry:bookSaled.entrySet()){
            res.add(new BookSaled(bookDao.getBookByID(entry.getKey()), entry.getValue()));
        }
        UserStatistic userStatistic = new UserStatistic();
        userStatistic.setBooks(res);
        userStatistic.setBookAmount(bookAmount);
        userStatistic.setConsumed(consumed);
        System.out.println(userStatistic);
        return userStatistic;
    }
}
