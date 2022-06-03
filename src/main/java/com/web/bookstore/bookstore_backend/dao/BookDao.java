package com.web.bookstore.bookstore_backend.dao;

import com.web.bookstore.bookstore_backend.entity.Book;

import java.util.List;

public interface BookDao {
    Book getBookByID(Integer id);

    List<Book> getBooks();

    void addBook(Book book);

    void removeBook(Integer bookID);

    void updateBook(Book book);
}
