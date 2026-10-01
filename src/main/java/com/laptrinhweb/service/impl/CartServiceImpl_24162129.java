package com.laptrinhweb.service.impl;

import java.util.List;

import com.laptrinhweb.dao.ICartDao_24162129;
import com.laptrinhweb.dao.impl.CartDaoImpl_24162129;
import com.laptrinhweb.model.CartItem_24162129;
import com.laptrinhweb.service.ICartService_24162129;

public class CartServiceImpl_24162129 implements ICartService_24162129 {

    private final ICartDao_24162129 cartDao;

    public CartServiceImpl_24162129() {
        this(new CartDaoImpl_24162129());
    }

    public CartServiceImpl_24162129(ICartDao_24162129 cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public List<CartItem_24162129> findItemsByUserId(int userId) {
        return cartDao.findItemsByUserId(userId);
    }

    @Override
    public boolean addItem(int userId, int productId, int quantity) {
        return cartDao.addItem(userId, productId, quantity);
    }

    @Override
    public boolean updateItem(int userId, String cartItemId, int quantity) {
        return cartDao.updateItem(userId, cartItemId, quantity);
    }

    @Override
    public boolean deleteItem(int userId, String cartItemId) {
        return cartDao.deleteItem(userId, cartItemId);
    }
}