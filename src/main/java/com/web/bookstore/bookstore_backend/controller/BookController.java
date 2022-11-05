package com.web.bookstore.bookstore_backend.controller;

import com.web.bookstore.bookstore_backend.utils.Msg;
import com.web.bookstore.bookstore_backend.utils.MsgUtil;
import com.web.bookstore.bookstore_backend.utils.SessionUtil;
import com.web.bookstore.bookstore_backend.entity.Book;
import com.web.bookstore.bookstore_backend.entity.BookItemSimple;
import com.web.bookstore.bookstore_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @RequestMapping("addBook")
    public Msg addBook(
            @RequestParam("isbn") String isbn,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("author") String author,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam("inventory") Integer inventory,
            @RequestParam("image") String image
    ){
        if(!SessionUtil.checkAdmin()){
            return MsgUtil.makeMsg(false,MsgUtil.AUTH);
        }
        if(bookService.addBook(isbn, name, type, author, price, description, inventory, image)){

            return MsgUtil.makeMsg(true, MsgUtil.SUCCESS_MSG);
        }
        return MsgUtil.makeMsg(false, MsgUtil.ERROR_MSG);
    }

    @RequestMapping("removeBook")
    public Msg removeBook(
            @RequestParam("bookID") Integer bookiD
    ){
        if(!SessionUtil.checkAdmin()){
            return MsgUtil.makeMsg(false,MsgUtil.AUTH);
        }
        if (bookService.removeBook(bookiD)) {
            return MsgUtil.makeMsg(true, MsgUtil.SUCCESS_MSG);
        }else{
            return MsgUtil.makeMsg(false, MsgUtil.ERROR_MSG);
        }
    }

    @PostMapping("/updateBook")
    public Msg updateBook(
            @RequestBody Book book
    ) {
        if (!SessionUtil.checkAdmin()) {
            return MsgUtil.makeMsg(false, MsgUtil.AUTH);
        }
        if (bookService.updateBook(book)) {
            return MsgUtil.makeMsg(true, MsgUtil.SUCCESS_MSG);
        } else {
            return MsgUtil.makeMsg(false, MsgUtil.ERROR_MSG);
        }
    }
}
