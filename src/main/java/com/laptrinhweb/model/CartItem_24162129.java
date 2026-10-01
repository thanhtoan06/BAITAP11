package com.laptrinhweb.model;

public class CartItem_24162129 {
    private String cartItemId;
    private int quantity;
    private double unitPrice;
    private int productId;
    private String cartId;
    private String productName;
    private String productImages;
    private int availableAmount;

    public CartItem_24162129() {
    }

    public CartItem_24162129(String cartItemId, int quantity, double unitPrice, int productId, String cartId) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productId = productId;
        this.cartId = cartId;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    @Override
    public String toString() {
        return "CartItem_24162129{" +
                "cartItemId='" + cartItemId + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", productId=" + productId +
                ", cartId='" + cartId + '\'' +
                '}';
    }
}