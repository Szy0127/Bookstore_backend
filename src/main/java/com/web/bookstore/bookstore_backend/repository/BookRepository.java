package com.web.bookstore.bookstore_backend.repository;

import com.web.bookstore.bookstore_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    @Query("select b from Book b")
    List<Book> getBooks();

    @Query("select b from Book b where b.bookID=:ID")
    Book getBookByID(
            @Param("ID")Integer ID
    );
}
