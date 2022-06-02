package com.web.bookstore.bookstore_backend.dao;

import com.web.bookstore.bookstore_backend.entity.User;

import java.util.List;

public interface UserDao {

    void register(User user);
    User getUserByName(String username);
    User getUserByID(int userID);

    List<User> getUsers();

    void banUser(int userID);
}
