package com.web.bookstore.bookstore_backend.dao;

import com.web.bookstore.bookstore_backend.entity.Book;

import java.util.List;

public interface BookDao {
    Book getBookByID(Integer id);

    List<Book> getBooks();

    Book addBook(Book book);

    void removeBook(Integer bookID);

    Book updateBook(Book book);



}
