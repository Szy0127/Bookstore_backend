package com.web.bookstore.bookstore_backend.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UserStatistic {
    private BigDecimal consumed;
    private Integer bookAmount;
    private List<BookSaled> books;

}
