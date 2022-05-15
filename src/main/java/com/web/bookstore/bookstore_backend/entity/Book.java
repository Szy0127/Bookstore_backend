package com.web.bookstore.bookstore_backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Entity
@Table(name = "book")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Book {

    @Id
    @Column(name = "bookID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto不支持mysql
    private int bookID;

    private String isbn;
    private String name;
    private String type;
    private String author;

    @Column(length=10,scale=2)
    private BigDecimal price;

    private String description;
    private Integer inventory;
    private String image;
}
