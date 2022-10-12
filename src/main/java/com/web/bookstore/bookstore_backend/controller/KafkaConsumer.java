package com.web.bookstore.bookstore_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bookstore.bookstore_backend.Constant;
import com.web.bookstore.bookstore_backend.MsgUtil;
import com.web.bookstore.bookstore_backend.entity.BookItemSimple;
import com.web.bookstore.bookstore_backend.service.UserService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaTemplate<Integer,String> kafkaTemplate;

    @Autowired
    private WebSocketServer ws;

    @KafkaListener(topics = Constant.ORDER_REQUEST_TOPIC,groupId = "consumer-group")
    public void consumerRequest(ConsumerRecord<Integer,String> record) throws JsonProcessingException {
//        System.out.println(record.key());
//        System.out.println(record.value());
        Integer userID = record.key();
        List<BookItemSimple> books = new ObjectMapper().readValue(record.value(), new TypeReference<List<BookItemSimple>>() {});
//        System.out.println(books);
        try{
            userService.buyBooks(userID, books);
//            kafkaTemplate.send(Constant.ORDER_RESPONSE_TOPIC, userID,Constant.ORDER_SUCCESS);
            ws.sendMessageToUser(userID,true, MsgUtil.ORDER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            ws.sendMessageToUser(userID,false, MsgUtil.ORDER_FAIL);
//            kafkaTemplate.send(Constant.ORDER_RESPONSE_TOPIC, userID,Constant.ORDER_FAIL);
        }
    }

//    @KafkaListener(topics = Constant.ORDER_RESPONSE_TOPIC,groupId = "consumer-group")
//    public void consumerResponse(ConsumerRecord<Integer,String> record) {
////        System.out.println(record.key());
////        System.out.println(record.value());
//        Integer userID = record.key();
//        String res = record.value();
//        System.out.println(userID);
//        System.out.println(res);
//        //websocket
//    }

}
