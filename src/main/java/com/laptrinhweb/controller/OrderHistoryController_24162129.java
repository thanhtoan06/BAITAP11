package com.laptrinhweb.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.laptrinhweb.model.Order_24162129;
import com.laptrinhweb.model.Users_24162129;
import com.laptrinhweb.service.IOrderHistoryService_24162129;
import com.laptrinhweb.service.impl.OrderHistoryServiceImpl_24162129;

public class OrderHistoryController_24162129 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final List<String> VALID_STATUSES = Arrays.asList(
            "NEW", "CONFIRMED", "PACKING", "SHIPPING", "DELIVERING", "DELIVERED", "CANCELLED", "RETURNED");
    private final IOrderHistoryService_24162129 orderHistoryService = new OrderHistoryServiceImpl_24162129();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users_24162129 user = getUser(request.getSession(false));
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String status = request.getParameter("status");
        if (!VALID_STATUSES.contains(status)) {
            status = null;
        }
        List<Order_24162129> orders = orderHistoryService.findByUserId(user.getUserId(), status);
        request.setAttribute("orders", orders);
        request.setAttribute("selectedStatus", status == null ? "ALL" : status);
        request.getRequestDispatcher("/views/web/order-history.jsp").forward(request, response);
    }

    private Users_24162129 getUser(HttpSession session) {
        if (session == null || !(session.getAttribute("account") instanceof Users_24162129)) {
            return null;
        }
        Users_24162129 user = (Users_24162129) session.getAttribute("account");
        return user.getRoleId() == 2 ? user : null;
    }
}