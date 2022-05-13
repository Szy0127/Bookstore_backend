package com.web.bookstore.bookstore_backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto不支持mysql
    private int userID;

    private String username;
    private String password;
    private String email;



    public User(String username,String password,String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }


}
