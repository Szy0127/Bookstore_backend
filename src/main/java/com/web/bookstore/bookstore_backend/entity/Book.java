package com.web.bookstore.bookstore_backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "book")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Book {

    @Id
    @Column(name = "bookID")
    private int bookID;

    private String isbn;
    private String name;
    private String type;
    private String author;
    private Double price;
    private String description;
    private Integer inventory;
    private String image;
}
