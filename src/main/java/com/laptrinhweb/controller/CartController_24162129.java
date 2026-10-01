package com.laptrinhweb.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.laptrinhweb.model.Users_24162129;
import com.laptrinhweb.service.ICartService_24162129;
import com.laptrinhweb.service.impl.CartServiceImpl_24162129;

public class CartController_24162129 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICartService_24162129 cartService = new CartServiceImpl_24162129();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users_24162129 user = getUser(request.getSession(false));
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        HttpSession session = request.getSession();
        request.setAttribute("cartSuccess", session.getAttribute("cartSuccess"));
        request.setAttribute("cartError", session.getAttribute("cartError"));
        session.removeAttribute("cartSuccess");
        session.removeAttribute("cartError");
        request.setAttribute("cartItems", cartService.findItemsByUserId(user.getUserId()));
        request.getRequestDispatcher("/views/web/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Users_24162129 user = getUser(request.getSession(false));
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String action = request.getParameter("action");
        boolean success = false;
        try {
            if ("add".equals(action)) {
                success = cartService.addItem(user.getUserId(), parsePositive(request.getParameter("productId")),
                        parsePositive(request.getParameter("quantity")));
            } else if ("update".equals(action)) {
                success = cartService.updateItem(user.getUserId(), request.getParameter("cartItemId"),
                        parsePositive(request.getParameter("quantity")));
            } else if ("delete".equals(action)) {
                success = cartService.deleteItem(user.getUserId(), request.getParameter("cartItemId"));
            }
        } catch (NumberFormatException exception) {
            success = false;
        }
        request.getSession().setAttribute(success ? "cartSuccess" : "cartError",
                success ? "Đã cập nhật giỏ hàng." : "Số lượng không hợp lệ hoặc vượt quá tồn kho.");
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private int parsePositive(String value) {
        int number = Integer.parseInt(value);
        if (number < 1) {
            throw new NumberFormatException();
        }
        return number;
    }

    private Users_24162129 getUser(HttpSession session) {
        if (session == null || !(session.getAttribute("account") instanceof Users_24162129)) {
            return null;
        }
        Users_24162129 user = (Users_24162129) session.getAttribute("account");
        return user.getRoleId() == 2 ? user : null;
    }
}