package com.web.bookstore.bookstore_backend.service;

import com.web.bookstore.bookstore_backend.entity.Book;

import java.util.List;

public interface BookService {

    Book getBookById(Integer id);

    List<Book> getBooks();
}
