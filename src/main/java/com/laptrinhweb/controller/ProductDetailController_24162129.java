package com.laptrinhweb.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.laptrinhweb.model.Product_24162129;
import com.laptrinhweb.service.IProductService_24162129;
import com.laptrinhweb.service.impl.ProductServiceImpl_24162129;

@WebServlet("/product-detail")
public class ProductDetailController_24162129 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IProductService_24162129 productService = new ProductServiceImpl_24162129();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParameter = request.getParameter("id");
        Product_24162129 product = null;

        try {
            if (idParameter != null && !idParameter.trim().isEmpty()) {
                product = productService.findById(Integer.parseInt(idParameter.trim()));
            }
        } catch (NumberFormatException exception) {
            request.setAttribute("productNotFound", true);
        }

        if (product == null) {
            request.setAttribute("productNotFound", true);
        }
        request.setAttribute("product", product);
        request.getRequestDispatcher("/views/web/product-detail.jsp").forward(request, response);
    }
}