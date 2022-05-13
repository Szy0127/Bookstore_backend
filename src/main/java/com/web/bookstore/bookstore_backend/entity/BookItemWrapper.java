package com.web.bookstore.bookstore_backend.entity;


import lombok.Getter;



@Getter
public class BookItemWrapper {

    private Book book;

    private int amount;


    public BookItemWrapper(Book book, int amount){
        this.book = book;
        this.amount = amount;
    }

}
