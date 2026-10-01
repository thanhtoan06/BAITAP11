package com.laptrinhweb.service.impl;

import java.util.List;

import com.laptrinhweb.dao.ICategoryDao_24162129;
import com.laptrinhweb.dao.impl.CategoryDaoImpl_24162129;
import com.laptrinhweb.model.Category_24162129;
import com.laptrinhweb.service.ICategoryService_24162129;

public class CategoryServiceImpl_24162129 implements ICategoryService_24162129 {

    private final ICategoryDao_24162129 categoryDao;

    public CategoryServiceImpl_24162129() {
        this(new CategoryDaoImpl_24162129());
    }

    public CategoryServiceImpl_24162129(ICategoryDao_24162129 categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category_24162129> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<Category_24162129> findAll(int page, int pageSize) {
        return categoryDao.findAll(page, pageSize);
    }

    @Override
    public int count() {
        return categoryDao.count();
    }

    @Override
    public Category_24162129 findById(int id) {
        return categoryDao.findById(id);
    }

    @Override
    public void insert(Category_24162129 category) {
        categoryDao.insert(category);
    }

    @Override
    public void update(Category_24162129 category) {
        categoryDao.update(category);
    }

    @Override
    public void delete(int id) {
        categoryDao.delete(id);
    }
}