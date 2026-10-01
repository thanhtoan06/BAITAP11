package com.laptrinhweb.service;

import java.util.List;

import com.laptrinhweb.model.Order_24162129;

public interface IOrderHistoryService_24162129 {

    List<Order_24162129> findByUserId(int userId, String status);
}