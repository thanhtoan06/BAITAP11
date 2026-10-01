package com.laptrinhweb.model;

import java.sql.Timestamp;

public class Order_24162129 {
    private String orderId;
    private int userId;
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private double totalAmount;
    private String paymentMethod;
    private String status;
    private Timestamp orderDate;
    private int itemCount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getStatusLabel() {
        return switch (status) {
        case "NEW", "PENDING" -> "Đơn hàng mới";
        case "CONFIRMED" -> "Đã xác nhận";
        case "PACKING" -> "Chuẩn bị hàng";
        case "SHIPPING" -> "Vận chuyển";
        case "DELIVERING" -> "Đang giao hàng";
        case "DELIVERED" -> "Đã giao";
        case "CANCELLED" -> "Đơn hàng hủy";
        case "RETURNED" -> "Đơn hàng hoàn";
        default -> status;
        };
    }
}