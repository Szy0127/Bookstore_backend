package com.web.bookstore.bookstore_backend.serviceImpl;

import com.web.bookstore.bookstore_backend.dao.BookDao;
import com.web.bookstore.bookstore_backend.entity.Book;
import com.web.bookstore.bookstore_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Book getBookById(Integer id) {
        return bookDao.getBookByID(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public boolean addBook(String isbn, String name, String type, String author, BigDecimal price, String description, Integer inventory, String image) {
        bookDao.addBook(new Book(isbn,name,type,author,price,description,inventory,image ));
        return true;
    }

    @Override
    public boolean removeBook(Integer bookID) {
        bookDao.removeBook(bookID);
        return true;
    }

    @Override
    public boolean updateBook(Book book) {
        bookDao.updateBook(book);
        return  true;
    }
}
