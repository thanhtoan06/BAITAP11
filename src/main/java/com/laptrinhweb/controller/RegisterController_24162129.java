package com.laptrinhweb.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.laptrinhweb.service.IUserService_24162129;
import com.laptrinhweb.service.impl.UserServiceImpl_24162129;

@WebServlet("/register")
public class RegisterController_24162129 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24162129 userService = new UserServiceImpl_24162129();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");

        if (isBlank(username) || isBlank(email) || isBlank(password) || isBlank(fullname) || isBlank(phone)) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin đăng ký.");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }

        boolean registered = userService.register(username.trim(), email.trim(), password, fullname.trim(), phone.trim());
        if (registered) {
            request.getSession().setAttribute("registrationEmail", email.trim());
            request.getSession().setAttribute("success", "Đăng ký thành công. Mã OTP đã được gửi đến email của bạn.");
            response.sendRedirect(request.getContextPath() + "/verify-otp");
            return;
        }

        request.setAttribute("error", "Username hoặc email đã tồn tại, hoặc không thể gửi mã OTP.");
        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}