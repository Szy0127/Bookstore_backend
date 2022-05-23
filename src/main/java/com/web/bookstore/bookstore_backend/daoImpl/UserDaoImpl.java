package com.web.bookstore.bookstore_backend.daoImpl;

import com.web.bookstore.bookstore_backend.dao.UserDao;
import com.web.bookstore.bookstore_backend.entity.User;
import com.web.bookstore.bookstore_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;

    @Override
    public void register(User user) {
        userRepository.save(user);

    }

    @Override
    public User getUserByName(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public User getUserByID(int userID) {
        return userRepository.getOne(userID);
    }
}
