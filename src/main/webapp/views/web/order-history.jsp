<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="/common/header.jsp" %>
<section>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h1 class="h2 mb-1">Lịch sử đặt hàng</h1>
            <p class="text-muted mb-0">Theo dõi trạng thái các đơn hàng COD của bạn.</p>
        </div>
        <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary">Tiếp tục mua sắm</a>
    </div>

    <form action="${pageContext.request.contextPath}/orders" method="get" class="row g-2 align-items-end mb-4">
        <div class="col-sm-5 col-md-4">
            <label for="status" class="form-label">Lọc theo trạng thái</label>
            <select id="status" name="status" class="form-select">
                <option value="ALL" ${selectedStatus == 'ALL' ? 'selected' : ''}>Tất cả đơn hàng</option>
                <option value="NEW" ${selectedStatus == 'NEW' ? 'selected' : ''}>Đơn hàng mới</option>
                <option value="CONFIRMED" ${selectedStatus == 'CONFIRMED' ? 'selected' : ''}>Đã xác nhận</option>
                <option value="PACKING" ${selectedStatus == 'PACKING' ? 'selected' : ''}>Chuẩn bị hàng</option>
                <option value="SHIPPING" ${selectedStatus == 'SHIPPING' ? 'selected' : ''}>Vận chuyển</option>
                <option value="DELIVERING" ${selectedStatus == 'DELIVERING' ? 'selected' : ''}>Đang giao hàng</option>
                <option value="DELIVERED" ${selectedStatus == 'DELIVERED' ? 'selected' : ''}>Đã giao</option>
                <option value="CANCELLED" ${selectedStatus == 'CANCELLED' ? 'selected' : ''}>Đơn hàng hủy</option>
                <option value="RETURNED" ${selectedStatus == 'RETURNED' ? 'selected' : ''}>Đơn hàng hoàn</option>
            </select>
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary">Lọc</button>
        </div>
    </form>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="alert alert-info">Không có đơn hàng phù hợp.</div>
        </c:when>
        <c:otherwise>
            <div class="bg-white rounded-3 shadow-sm p-3">
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th>Mã đơn hàng</th>
                                <th>Ngày đặt</th>
                                <th>Sản phẩm</th>
                                <th>Thanh toán</th>
                                <th class="text-end">Tổng tiền</th>
                                <th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${orders}">
                                <tr>
                                    <td class="small text-break" style="max-width: 180px;"><c:out value="${order.orderId}" /></td>
                                    <td><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                                    <td>${order.itemCount} sản phẩm</td>
                                    <td><c:out value="${order.paymentMethod}" /></td>
                                    <td class="text-end fw-semibold"><fmt:formatNumber value="${order.totalAmount}" type="number" minFractionDigits="2" /></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.status == 'NEW' or order.status == 'PENDING'}"><span class="badge text-bg-primary"><c:out value="${order.statusLabel}" /></span></c:when>
                                            <c:when test="${order.status == 'CONFIRMED'}"><span class="badge text-bg-info"><c:out value="${order.statusLabel}" /></span></c:when>
                                            <c:when test="${order.status == 'PACKING' or order.status == 'SHIPPING' or order.status == 'DELIVERING'}"><span class="badge text-bg-warning"><c:out value="${order.statusLabel}" /></span></c:when>
                                            <c:when test="${order.status == 'DELIVERED'}"><span class="badge text-bg-success"><c:out value="${order.statusLabel}" /></span></c:when>
                                            <c:otherwise><span class="badge text-bg-secondary"><c:out value="${order.statusLabel}" /></span></c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr class="table-light">
                                    <td colspan="6" class="small text-muted">Người nhận: <c:out value="${order.receiverName}" /> - <c:out value="${order.receiverPhone}" /> - <c:out value="${order.shippingAddress}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</section>
<%@ include file="/common/footer.jsp" %>