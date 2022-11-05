package com.web.bookstore.bookstore_backend;

import com.web.bookstore.bookstore_backend.utils.Constant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class BookstoreBackendApplication {


    public static void main(String[] args) {
        SpringApplication.run(BookstoreBackendApplication.class, args);
    }

    @Bean
    public NewTopic OrderReqTopic(){
        return TopicBuilder.name(Constant.ORDER_REQUEST_TOPIC).partitions(1).replicas(1).build();
    }

//    @Bean
//    public NewTopic OrderResTopic(){
//        return TopicBuilder.name(Constant.ORDER_RESPONSE_TOPIC).partitions(1).replicas(1).build();
//    }
}
