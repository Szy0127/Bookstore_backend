package com.web.bookstore.bookstore_backend;

import com.web.bookstore.bookstore_backend.controller.BookController;
import com.web.bookstore.bookstore_backend.dao.BookDao;
import com.web.bookstore.bookstore_backend.entity.Book;
import com.web.bookstore.bookstore_backend.utils.SolrUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookstoreBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    //需要把websocket注释了

    @Autowired
    private BookDao bookDao;
    @Autowired
    private SolrUtil solrUtil;

    @Autowired
    private BookController bookController;
    @Test
    void solrAddBooks(){
        solrUtil.addBooks(bookDao.getBooks());
    }

    @Test
    void solrquery(){
        List<Integer> books = solrUtil.query("经典");
        System.out.println(books);
        books = solrUtil.query("*");
        System.out.println(books);
    }

    @Test
    void solrremove(){
//        Book book = new Book();
//        book.setBookID(123);
//        book.setDescription("fadfsafda");
//        solrUtil.addBook(book);
        solrUtil.removeBook(123);
    }


}
