package com.laptrinhweb.service;

import java.util.List;

import com.laptrinhweb.model.Category_24162129;

public interface ICategoryService_24162129 {

    List<Category_24162129> findAll();

    List<Category_24162129> findAll(int page, int pageSize);

    int count();

    Category_24162129 findById(int id);

    void insert(Category_24162129 category);

    void update(Category_24162129 category);

    void delete(int id);
}