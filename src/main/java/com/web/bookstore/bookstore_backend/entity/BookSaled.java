package com.web.bookstore.bookstore_backend.entity;

import lombok.Getter;
import org.springframework.data.relational.core.sql.In;

@Getter
public class BookSaled {

    private Book book;
    private Integer amount;

    public BookSaled(Book book, Integer amount){
        this.book = book;
        this.amount = amount;
    }
}
