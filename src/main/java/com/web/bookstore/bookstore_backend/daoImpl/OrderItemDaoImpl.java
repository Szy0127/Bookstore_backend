package com.web.bookstore.bookstore_backend.daoImpl;


import com.web.bookstore.bookstore_backend.dao.OrderItemDao;
import com.web.bookstore.bookstore_backend.entity.OrderItem;
import com.web.bookstore.bookstore_backend.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class OrderItemDaoImpl implements OrderItemDao {


    @Autowired
    OrderItemRepository orderItemRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void saveOrderItems(List<OrderItem> items) {
//        orderItemRepository.save(item);

//        int res=1/0;
        orderItemRepository.saveAll(items);
    }


    @Transactional
    @Override
    public List<OrderItem> getOrderItemsByOrderID(String orderID) {
        return orderItemRepository.getOrderItemsByOrderID(orderID);
    }
}
