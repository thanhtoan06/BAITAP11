<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/common/header.jsp" %>
<section class="text-center py-4">
    <c:choose>
        <c:when test="${not empty orderId}">
            <div class="bg-white rounded-3 shadow-sm p-5 mx-auto" style="max-width: 680px;">
                <div class="display-5 text-success mb-3">Đặt hàng thành công</div>
                <p class="lead mb-2">Đơn hàng của bạn sẽ được thanh toán khi nhận hàng (COD).</p>
                <p class="text-muted">Mã đơn hàng: <strong><c:out value="${orderId}" /></strong></p>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary mt-3">Tiếp tục mua sắm</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning">Không tìm thấy thông tin đơn hàng.</div>
            <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-primary">Quay lại giỏ hàng</a>
        </c:otherwise>
    </c:choose>
</section>
<%@ include file="/common/footer.jsp" %>