package com.laptrinhweb.service.impl;

import java.util.List;

import com.laptrinhweb.dao.IProductDao_24162129;
import com.laptrinhweb.dao.impl.ProductDaoImpl_24162129;
import com.laptrinhweb.model.Product_24162129;
import com.laptrinhweb.service.IProductService_24162129;

public class ProductServiceImpl_24162129 implements IProductService_24162129 {

    private final IProductDao_24162129 productDao;

    public ProductServiceImpl_24162129() {
        this(new ProductDaoImpl_24162129());
    }

    public ProductServiceImpl_24162129(IProductDao_24162129 productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product_24162129> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product_24162129> findAllGroupBySeller() {
        return productDao.findAllGroupBySeller();
    }

    @Override
    public Product_24162129 findById(int id) {
        return productDao.findById(id);
    }
}