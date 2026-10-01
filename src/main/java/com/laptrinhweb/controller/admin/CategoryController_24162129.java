package com.laptrinhweb.controller.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.laptrinhweb.model.Category_24162129;
import com.laptrinhweb.service.ICategoryService_24162129;
import com.laptrinhweb.service.impl.CategoryServiceImpl_24162129;

@WebServlet("/admin/category")
public class CategoryController_24162129 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 5;

    private final ICategoryService_24162129 categoryService = new CategoryServiceImpl_24162129();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        switch (action) {
        case "add":
            forward(request, response, "/views/admin/category/category-add.jsp");
            break;
        case "edit":
            showEditForm(request, response);
            break;
        case "delete":
            delete(request, response);
            break;
        case "list":
        default:
            showList(request, response);
            break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            categoryService.insert(readCategory(request, 0));
            redirectToList(request, response);
        } else if ("edit".equals(action)) {
            categoryService.update(readCategory(request, parseInt(request.getParameter("categoryId"), 0)));
            redirectToList(request, response);
        } else {
            showList(request, response);
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = Math.max(parseInt(request.getParameter("page"), 1), 1);
        int totalItems = categoryService.count();
        int maxPage = Math.max((int) Math.ceil((double) totalItems / PAGE_SIZE), 1);
        page = Math.min(page, maxPage);

        request.setAttribute("categoryList", categoryService.findAll(page, PAGE_SIZE));
        request.setAttribute("currentPage", page);
        request.setAttribute("maxPage", maxPage);
        forward(request, response, "/views/admin/category/category-list.jsp");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryId = parseInt(request.getParameter("id"), 0);
        Category_24162129 category = categoryService.findById(categoryId);
        if (category == null) {
            redirectToList(request, response);
            return;
        }
        request.setAttribute("category", category);
        forward(request, response, "/views/admin/category/category-edit.jsp");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int categoryId = parseInt(request.getParameter("id"), 0);
        if (categoryId > 0) {
            categoryService.delete(categoryId);
        }
        redirectToList(request, response);
    }

    private Category_24162129 readCategory(HttpServletRequest request, int categoryId) {
        Category_24162129 category = new Category_24162129();
        category.setCategoryId(categoryId);
        category.setCategoryName(request.getParameter("categoryName"));
        category.setImages(request.getParameter("images"));
        category.setStatus(parseInt(request.getParameter("status"), 0));
        return category;
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }

    private void redirectToList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/admin/category");
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }
}