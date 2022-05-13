package com.web.bookstore.bookstore_backend.dao;

import com.web.bookstore.bookstore_backend.entity.User;

public interface UserDao {

    void register(User user);
    User getUserByName(String username);
    User getUserByID(int userID);
}
