package com.laptrinhweb.controller.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.laptrinhweb.model.Users_24162129;
import com.laptrinhweb.service.IUserService_24162129;
import com.laptrinhweb.service.impl.UserServiceImpl_24162129;

@WebServlet("/admin/user")
public class UserController_24162129 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 5;

    private final IUserService_24162129 userService = new UserServiceImpl_24162129();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        switch (action) {
        case "add":
            forward(request, response, "/views/admin/user/user-add.jsp");
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
            userService.insert(readUser(request, 0, null));
            redirectToList(request, response);
        } else if ("edit".equals(action)) {
            updateUser(request, response);
        } else {
            showList(request, response);
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = Math.max(parseInt(request.getParameter("page"), 1), 1);
        int totalItems = userService.count();
        int maxPage = Math.max((int) Math.ceil((double) totalItems / PAGE_SIZE), 1);
        page = Math.min(page, maxPage);

        request.setAttribute("userList", userService.findAll(page, PAGE_SIZE));
        request.setAttribute("currentPage", page);
        request.setAttribute("maxPage", maxPage);
        forward(request, response, "/views/admin/user/user-list.jsp");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users_24162129 user = userService.findById(parseInt(request.getParameter("id"), 0));
        if (user == null) {
            redirectToList(request, response);
            return;
        }
        request.setAttribute("user", user);
        forward(request, response, "/views/admin/user/user-edit.jsp");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = parseInt(request.getParameter("userId"), 0);
        Users_24162129 existingUser = userService.findById(userId);
        if (existingUser != null) {
            userService.update(readUser(request, userId, existingUser));
        }
        redirectToList(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = parseInt(request.getParameter("id"), 0);
        if (userId > 0) {
            userService.delete(userId);
        }
        redirectToList(request, response);
    }

    private Users_24162129 readUser(HttpServletRequest request, int userId, Users_24162129 existingUser) {
        Users_24162129 user = new Users_24162129();
        user.setUserId(userId);
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setFullname(request.getParameter("fullname"));
        user.setPhone(request.getParameter("phone"));
        user.setImages(request.getParameter("images"));
        user.setRoleId(parseInt(request.getParameter("roleId"), 2));
        user.setStatus(parseInt(request.getParameter("status"), 0));
        user.setSellerId(existingUser == null ? 0 : existingUser.getSellerId());

        String password = request.getParameter("password");
        if ((password == null || password.isEmpty()) && existingUser != null) {
            password = existingUser.getPassword();
        }
        user.setPassword(password);
        user.setCode(existingUser == null ? null : existingUser.getCode());
        return user;
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }

    private void redirectToList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/admin/user");
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }
}