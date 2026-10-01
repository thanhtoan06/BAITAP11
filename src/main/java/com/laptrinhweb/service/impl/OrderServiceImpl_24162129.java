package com.laptrinhweb.service.impl;

import com.laptrinhweb.dao.IOrderDao_24162129;
import com.laptrinhweb.dao.impl.OrderDaoImpl_24162129;
import com.laptrinhweb.service.IOrderService_24162129;

public class OrderServiceImpl_24162129 implements IOrderService_24162129 {

    private final IOrderDao_24162129 orderDao;

    public OrderServiceImpl_24162129() {
        this(new OrderDaoImpl_24162129());
    }

    public OrderServiceImpl_24162129(IOrderDao_24162129 orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public String placeCodOrder(int userId, String receiverName, String receiverPhone, String shippingAddress) {
        if (isBlank(receiverName) || isBlank(receiverPhone) || isBlank(shippingAddress)) {
            return null;
        }
        return orderDao.placeCodOrder(userId, receiverName.trim(), receiverPhone.trim(), shippingAddress.trim());
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}