package com.web.bookstore.bookstore_backend.daoImpl;

import com.web.bookstore.bookstore_backend.dao.OrderDao;
import com.web.bookstore.bookstore_backend.entity.Order;
import com.web.bookstore.bookstore_backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    OrderRepository orderRepository;
    @Override
    public List<Order> getOrdersByUserID(int userID) {
        return orderRepository.getOrderByUserID(userID);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
//        int res=1/0;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByTimeBetween(Timestamp start, Timestamp end) {
        return orderRepository.getOrdersByTimeBetween(start, end);
    }

    @Override
    public List<Order> getOrdersByTimeBefore(Timestamp t) {
        return orderRepository.getOrdersByTimeBefore(t);
    }

    @Override
    public List<Order> getOrdersByTimeAfter(Timestamp t) {
        return orderRepository.getOrdersByTimeAfter(t);
    }

    @Override
    public List<Order> getOrdersByUserAndTimeAfter(Integer userID, Timestamp t) {
        return orderRepository.getOrdersByUserIDAndTimeAfter(userID,t);
    }

    @Override
    public List<Order> getOrdersByUserAndTimeBefore(Integer userID, Timestamp t) {
        return orderRepository.getOrdersByUserIDAndTimeBefore(userID,t);
    }

    @Override
    public List<Order> getOrdersByUserAndTimeBetween(Integer userID, Timestamp start, Timestamp end) {
        return orderRepository.getOrdersByUserIDAndTimeBetween(userID,start,end);
    }
}
