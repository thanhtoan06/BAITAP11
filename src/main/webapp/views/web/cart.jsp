<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="/common/header.jsp" %>
<section>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h2 mb-0">Giỏ hàng</h1>
        <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary">Tiếp tục mua sắm</a>
    </div>

    <c:if test="${not empty cartSuccess}"><div class="alert alert-success"><c:out value="${cartSuccess}" /></div></c:if>
    <c:if test="${not empty cartError}"><div class="alert alert-danger"><c:out value="${cartError}" /></div></c:if>

    <c:choose>
        <c:when test="${empty cartItems}">
            <div class="alert alert-info">Giỏ hàng đang trống.</div>
        </c:when>
        <c:otherwise>
            <div class="bg-white rounded-3 shadow-sm p-3">
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th>Sản phẩm</th>
                                <th class="text-end">Đơn giá</th>
                                <th>Số lượng</th>
                                <th class="text-end">Thành tiền</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="total" value="0" />
                            <c:forEach var="item" items="${cartItems}">
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center gap-3">
                                            <img src="${pageContext.request.contextPath}/images/${item.productImages}" width="70" height="55" class="cart-image rounded" alt="${item.productName}">
                                            <div>
                                                <a href="${pageContext.request.contextPath}/product-detail?id=${item.productId}" class="fw-semibold text-decoration-none"><c:out value="${item.productName}" /></a>
                                                <div class="small text-muted">Tồn kho: ${item.availableAmount}</div>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="text-end"><fmt:formatNumber value="${item.unitPrice}" type="number" minFractionDigits="2" /></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/cart" method="post" class="d-flex gap-2 align-items-center">
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                            <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.availableAmount}" class="form-control form-control-sm" style="width: 80px;">
                                            <button type="submit" class="btn btn-sm btn-outline-primary">Sửa</button>
                                        </form>
                                    </td>
                                    <td class="text-end"><fmt:formatNumber value="${item.unitPrice * item.quantity}" type="number" minFractionDigits="2" /></td>
                                    <td class="text-end">
                                        <form action="${pageContext.request.contextPath}/cart" method="post">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                            <button type="submit" class="btn btn-sm btn-outline-danger">Xóa</button>
                                        </form>
                                    </td>
                                </tr>
                                <c:set var="total" value="${total + (item.unitPrice * item.quantity)}" />
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="3" class="text-end">Tổng cộng</th>
                                <th class="text-end text-primary"><fmt:formatNumber value="${total}" type="number" minFractionDigits="2" /></th>
                                <th></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr class="my-4">
                <h2 class="h5">Thanh toán khi nhận hàng (COD)</h2>
                <form action="${pageContext.request.contextPath}/checkout" method="post" class="row g-3 mt-1">
                    <div class="col-md-4">
                        <label for="receiverName" class="form-label">Tên người nhận</label>
                        <input id="receiverName" name="receiverName" type="text" class="form-control" required maxlength="100"
                               value="${sessionScope.account.fullname}">
                    </div>
                    <div class="col-md-4">
                        <label for="receiverPhone" class="form-label">Số điện thoại</label>
                        <input id="receiverPhone" name="receiverPhone" type="tel" class="form-control" required maxlength="20"
                               value="${sessionScope.account.phone}">
                    </div>
                    <div class="col-md-8">
                        <label for="shippingAddress" class="form-label">Địa chỉ nhận hàng</label>
                        <textarea id="shippingAddress" name="shippingAddress" class="form-control" rows="2" required maxlength="500"></textarea>
                    </div>
                    <div class="col-12">
                        <button type="submit" class="btn btn-success">Đặt hàng COD</button>
                    </div>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</section>
<style>
    .cart-image {
        object-fit: contain;
        background: #eef2f6;
    }
</style>
<%@ include file="/common/footer.jsp" %>