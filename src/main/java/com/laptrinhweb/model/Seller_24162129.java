package com.laptrinhweb.model;

public class Seller_24162129 {
    private int sellerId;
    private String sellerName;
    private String images;
    private int status;

    public Seller_24162129() {
    }

    public Seller_24162129(int sellerId, String sellerName, String images, int status) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.images = images;
        this.status = status;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Seller_24162129{" +
                "sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", images='" + images + '\'' +
                ", status=" + status +
                '}';
    }
}