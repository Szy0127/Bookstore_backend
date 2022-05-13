package com.web.bookstore.bookstore_backend.controller;

import com.web.bookstore.bookstore_backend.entity.Book;
import com.web.bookstore.bookstore_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookService bookService;
    @RequestMapping("/getBooks")
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @RequestMapping("getBook")
    public Book getBookByID(
            @RequestParam("ID") Integer ID
    ){
        return bookService.getBookById(ID);
    }
}
