package com.web.bookstore.bookstore_backend.daoImpl;

import com.web.bookstore.bookstore_backend.dao.UserDao;
import com.web.bookstore.bookstore_backend.entity.CartItem;
import com.web.bookstore.bookstore_backend.entity.CartItemPK;
import com.web.bookstore.bookstore_backend.entity.User;
import com.web.bookstore.bookstore_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


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

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    public boolean banUser(int userID) {
        User user = userRepository.getOne(userID);
        if(user.isAdmin()){
            return false;
        }
        user.setBan(!user.isBan());
        userRepository.save(user);
        return true;
    }
}
