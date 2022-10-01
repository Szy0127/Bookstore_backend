package com.web.bookstore.bookstore_backend.daoImpl;

import com.web.bookstore.bookstore_backend.dao.BookDao;
import com.web.bookstore.bookstore_backend.entity.Book;
import com.web.bookstore.bookstore_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getBookByID(Integer id){
        return bookRepository.getBookByBookID(id);
    }


    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }


    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void removeBook(Integer bookID) {
        bookRepository.deleteById(bookID);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }



    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public boolean buyBook(Integer id,Integer amount) {
        Book book = bookRepository.getBookByBookID(id);
        if(book.getInventory() < amount){
            return false;
        }
        book.setInventory(book.getInventory()-amount);
        bookRepository.save(book);
        return true;
    }
}
