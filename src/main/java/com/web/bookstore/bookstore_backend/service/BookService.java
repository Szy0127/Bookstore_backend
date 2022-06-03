package com.web.bookstore.bookstore_backend.service;

import com.web.bookstore.bookstore_backend.entity.Book;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {

    Book getBookById(Integer id);

    List<Book> getBooks();


    boolean addBook(String isbn, String name , String type, String author, BigDecimal price,String description,Integer inventory,String image);

    boolean removeBook(Integer bookID);
}
