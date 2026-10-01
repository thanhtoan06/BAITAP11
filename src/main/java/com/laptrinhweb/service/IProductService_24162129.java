package com.laptrinhweb.service;

import java.util.List;

import com.laptrinhweb.model.Product_24162129;

public interface IProductService_24162129 {

    List<Product_24162129> findAll();

    List<Product_24162129> findAllGroupBySeller();

    Product_24162129 findById(int id);
}