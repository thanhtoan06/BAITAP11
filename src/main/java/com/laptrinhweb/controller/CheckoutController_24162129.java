package com.laptrinhweb.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.laptrinhweb.model.Users_24162129;
import com.laptrinhweb.service.IOrderService_24162129;
import com.laptrinhweb.service.impl.OrderServiceImpl_24162129;

public class CheckoutController_24162129 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IOrderService_24162129 orderService = new OrderServiceImpl_24162129();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (getUser(request.getSession(false)) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("orderId", request.getParameter("orderId"));
        request.getRequestDispatcher("/views/web/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Users_24162129 user = getUser(request.getSession(false));
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String orderId = orderService.placeCodOrder(user.getUserId(), request.getParameter("receiverName"),
                request.getParameter("receiverPhone"), request.getParameter("shippingAddress"));
        if (orderId == null) {
            request.getSession().setAttribute("cartError",
                    "Không thể đặt hàng. Vui lòng kiểm tra thông tin và tồn kho.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/checkout?orderId=" + orderId);
    }

    private Users_24162129 getUser(HttpSession session) {
        if (session == null || !(session.getAttribute("account") instanceof Users_24162129)) {
            return null;
        }
        Users_24162129 user = (Users_24162129) session.getAttribute("account");
        return user.getRoleId() == 2 ? user : null;
    }
}