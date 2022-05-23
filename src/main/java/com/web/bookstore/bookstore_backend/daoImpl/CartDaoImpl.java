package com.web.bookstore.bookstore_backend.daoImpl;

import com.web.bookstore.bookstore_backend.dao.CartDao;
import com.web.bookstore.bookstore_backend.entity.CartItem;
import com.web.bookstore.bookstore_backend.entity.CartItemPK;
import com.web.bookstore.bookstore_backend.repository.CartRepository;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class CartDaoImpl implements CartDao {

    @Autowired
    CartRepository cartRepository;

    @Override
    public List<CartItem> getCartByUserID(int userID) {
        return cartRepository.getCartItemsByUserID(userID);
    }

    @Override
    public void updateCart(CartItem cartItem) {
        CartItem cart = cartRepository.getOne(new CartItemPK(cartItem));
        cart.setAmount(cartItem.getAmount());
        cartRepository.save(cart);
    }

    @Override
    public void addCart(CartItem cartItem) {
        cartRepository.save(cartItem);
    }

    @Override
    public void removeCart(CartItem cartItem) {
//        cartRepository.delete(cartRepository.getCartItemsByUserBook(cartItem.getUserID(),cartItem.getBookID()));
        CartItemPK id = new CartItemPK(cartItem);
        Optional<CartItem> res = cartRepository.findById(id);
        if(res.isPresent()){
            cartRepository.deleteById(new CartItemPK(cartItem));
        }
    }
}
