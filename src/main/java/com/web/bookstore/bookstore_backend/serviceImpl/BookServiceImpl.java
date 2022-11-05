package com.web.bookstore.bookstore_backend.serviceImpl;

import com.web.bookstore.bookstore_backend.dao.BookDao;
import com.web.bookstore.bookstore_backend.entity.Book;
import com.web.bookstore.bookstore_backend.service.BookService;
import com.web.bookstore.bookstore_backend.utils.SolrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private SolrUtil solrUtil;

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
        Book book = new Book(isbn,name,type,author,price,description,inventory,image);
//        System.out.println(book.getBookID());
        bookDao.addBook(book);
//        System.out.println(book.getBookID());
        solrUtil.addBook(book);
        return true;
    }

    @Override
    public boolean removeBook(Integer bookID) {
        bookDao.removeBook(bookID);
        solrUtil.removeBook(bookID);
        return true;
    }

    @Override
    public boolean updateBook(Book book) {
        bookDao.updateBook(book);
        return true;
    }

    @Override
    public boolean updateBookIndex(Book book) {
        bookDao.updateBook(book);
        solrUtil.addBook(book);
        return true;
    }

    @Override
    public List<Book> searchBooksByDescription(String description) {
        List<Integer> bookIDs = solrUtil.query(description);
        List<Book> res=  new ArrayList<>();
        for(Integer id:bookIDs){
            Book book = bookDao.getBookByID(id);
            if(book!=null){
                res.add(book);
            }
        }
        return res;
    }
}
