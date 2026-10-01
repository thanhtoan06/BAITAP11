package com.laptrinhweb.model;

public class Cart_24162129 {
    private String cartId;
    private int userId;
    private java.sql.Timestamp buyDate;
    private int status;

    public Cart_24162129() {
    }

    public Cart_24162129(String cartId, int userId, java.sql.Timestamp buyDate, int status) {
        this.cartId = cartId;
        this.userId = userId;
        this.buyDate = buyDate;
        this.status = status;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public java.sql.Timestamp getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(java.sql.Timestamp buyDate) {
        this.buyDate = buyDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cart_24162129{" +
                "cartId='" + cartId + '\'' +
                ", userId=" + userId +
                ", buyDate=" + buyDate +
                ", status=" + status +
                '}';
    }
}