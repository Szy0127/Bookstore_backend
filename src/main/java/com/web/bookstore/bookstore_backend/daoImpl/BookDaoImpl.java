package com.web.bookstore.bookstore_backend.daoImpl;

import com.web.bookstore.bookstore_backend.dao.BookDao;
import com.web.bookstore.bookstore_backend.entity.Book;
import com.web.bookstore.bookstore_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Cacheable(value={"Book"},key="#id")//sync=true避免缓存击穿  但是会导致redis服务中断后此函数永远返回空
    public Book getBookByID(Integer id){
        System.out.println("mysql get book"+id);
        return bookRepository.getBookByBookID(id);
    }


    @Override
    @Cacheable(value={"Books"},key = "'all'")
    public List<Book> getBooks() {
        System.out.println("mysql get all books");
        return bookRepository.findAll();
    }


    @Override
    @Caching(
            put=@CachePut(value={"Book"},key="#book.getBookID()"),
            evict = @CacheEvict(value={"Books"},key = "'all'")
    )
    public Book addBook(Book book) {
        System.out.println("mysql save book"+book.getBookID());
        bookRepository.save(book);
        return book;
    }

    @Override
    @Caching(evict = {
                    @CacheEvict(value={"Books"},key = "'all'"),
                    @CacheEvict(value={"Book"},key="#bookID")
    })
    public void removeBook(Integer bookID) {
        System.out.println("mysql remove book"+bookID);
        bookRepository.deleteById(bookID);
    }

    @Override
    @Caching(
            put=@CachePut(value={"Book"},key="#book.getBookID()"),
            evict = @CacheEvict(value={"Books"},key = "'all'")
    )
    public Book updateBook(Book book) {
        System.out.println("mysql update book"+book.getBookID());
        bookRepository.save(book);
        return book;
    }

}
