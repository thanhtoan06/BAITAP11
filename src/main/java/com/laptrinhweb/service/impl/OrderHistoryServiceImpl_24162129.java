package com.laptrinhweb.service.impl;

import java.util.List;

import com.laptrinhweb.dao.IOrderHistoryDao_24162129;
import com.laptrinhweb.dao.impl.OrderHistoryDaoImpl_24162129;
import com.laptrinhweb.model.Order_24162129;
import com.laptrinhweb.service.IOrderHistoryService_24162129;

public class OrderHistoryServiceImpl_24162129 implements IOrderHistoryService_24162129 {

    private final IOrderHistoryDao_24162129 orderHistoryDao;

    public OrderHistoryServiceImpl_24162129() {
        this(new OrderHistoryDaoImpl_24162129());
    }

    public OrderHistoryServiceImpl_24162129(IOrderHistoryDao_24162129 orderHistoryDao) {
        this.orderHistoryDao = orderHistoryDao;
    }

    @Override
    public List<Order_24162129> findByUserId(int userId, String status) {
        return orderHistoryDao.findByUserId(userId, status);
    }
}