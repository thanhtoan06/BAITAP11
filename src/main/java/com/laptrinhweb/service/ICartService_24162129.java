package com.laptrinhweb.service;

import java.util.List;

import com.laptrinhweb.model.CartItem_24162129;

public interface ICartService_24162129 {

    List<CartItem_24162129> findItemsByUserId(int userId);

    boolean addItem(int userId, int productId, int quantity);

    boolean updateItem(int userId, String cartItemId, int quantity);

    boolean deleteItem(int userId, String cartItemId);
}