package com.laptrinhweb.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.laptrinhweb.model.Product_24162129;
import com.laptrinhweb.service.IProductService_24162129;
import com.laptrinhweb.service.impl.ProductServiceImpl_24162129;

@WebServlet("/products")
public class ProductListController_24162129 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IProductService_24162129 productService = new ProductServiceImpl_24162129();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product_24162129> productList = productService.findAll();
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("/views/web/product-list.jsp").forward(request, response);
    }
}