package com.laptrinhweb.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.laptrinhweb.model.Users_24162129;
import com.laptrinhweb.service.IUserService_24162129;
import com.laptrinhweb.service.impl.UserServiceImpl_24162129;

@WebServlet("/login")
public class LoginController_24162129 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24162129 userService = new UserServiceImpl_24162129();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("success", session.getAttribute("success"));
        session.removeAttribute("success");
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Users_24162129 user = userService.login(username, password);

        if (user == null) {
            request.setAttribute("error", "Tài khoản/Mật khẩu sai hoặc chưa kích hoạt OTP!");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("account", user);
        String target;
        if (user.getRoleId() == 1) {
            target = "/admin/home";
        } else if (user.getRoleId() == 3) {
            target = "/seller/home";
        } else {
            target = "/home";
        }
        response.sendRedirect(request.getContextPath() + target);
    }
}