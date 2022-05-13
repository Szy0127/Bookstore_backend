package com.web.bookstore.bookstore_backend.daoImpl;

import com.web.bookstore.bookstore_backend.dao.OrderItemDao;
import com.web.bookstore.bookstore_backend.entity.OrderItem;
import com.web.bookstore.bookstore_backend.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    @Autowired
    OrderItemRepository orderItemRepository;
    @Override
    public List<OrderItem> getOrderItemsByOrderID(String orderID) {
        return orderItemRepository.getOrderItemsByOrderID(orderID);
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
