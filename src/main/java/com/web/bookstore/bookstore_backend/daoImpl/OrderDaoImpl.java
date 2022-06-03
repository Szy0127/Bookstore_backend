package com.web.bookstore.bookstore_backend.daoImpl;

import com.web.bookstore.bookstore_backend.dao.OrderDao;
import com.web.bookstore.bookstore_backend.entity.Order;
import com.web.bookstore.bookstore_backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    OrderRepository orderRepository;
    @Override
    public List<Order> getOrdersByUserID(int userID) {
        return orderRepository.getOrderByUserID(userID);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
