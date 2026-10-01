package com.laptrinhweb.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.laptrinhweb.service.IUserService_24162129;
import com.laptrinhweb.service.impl.UserServiceImpl_24162129;

@WebServlet("/verify-otp")
public class VerifyOTPController_24162129 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24162129 userService = new UserServiceImpl_24162129();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("success", session.getAttribute("success"));
        session.removeAttribute("success");
        request.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("registrationEmail");
        String otp = request.getParameter("otp");

        if (email != null && otp != null && userService.verifyOTP(email, otp.trim())) {
            session.removeAttribute("registrationEmail");
            session.setAttribute("success", "Kích hoạt tài khoản thành công! Hãy đăng nhập.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("error", "Mã OTP không chính xác, vui lòng thử lại!");
        request.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(request, response);
    }
}