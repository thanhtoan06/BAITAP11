<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="/common/header.jsp" %>
<section>
    <c:choose>
        <c:when test="${productNotFound or empty product}">
            <div class="alert alert-warning">
                Không tìm thấy sản phẩm.
                <a href="${pageContext.request.contextPath}/products" class="alert-link">Quay lại danh sách</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="bg-white rounded-3 shadow-sm p-4">
                <div class="row g-4">
                    <div class="col-md-5 text-center">
                        <c:choose>
                            <c:when test="${not empty product.images and (product.images.startsWith('http://') or product.images.startsWith('https://'))}">
                                <img src="${product.images}" width="360" height="280" class="product-detail-image rounded"
                                     alt="${product.productName}"
                                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/placeholder-product.svg'"/>
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/images/${product.images}" width="360" height="280"
                                     class="product-detail-image rounded" alt="${product.productName}"
                                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/placeholder-product.svg'"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-7">
                        <h1 class="h2 mb-3"><c:out value="${product.productName}" /></h1>
                        <dl class="row mb-0">
                            <dt class="col-sm-4">Mã sản phẩm</dt>
                            <dd class="col-sm-8"><c:out value="${product.productCode}" /></dd>
                            <dt class="col-sm-4">Danh mục</dt>
                            <dd class="col-sm-8"><c:out value="${product.categoryName}" /></dd>
                            <dt class="col-sm-4">Cửa hàng</dt>
                            <dd class="col-sm-8"><c:out value="${product.sellerName}" /></dd>
                            <dt class="col-sm-4">Giá</dt>
                            <dd class="col-sm-8 fw-semibold text-primary">
                                <fmt:formatNumber value="${product.price}" type="number" minFractionDigits="2" />
                            </dd>
                            <dt class="col-sm-4">Amount</dt>
                            <dd class="col-sm-8"><c:out value="${product.amount}" /></dd>
                        </dl>
                        <c:if test="${sessionScope.account != null and sessionScope.account.roleId == 2 and product.amount > 0}">
                            <form action="${pageContext.request.contextPath}/cart" method="post" class="d-flex gap-2 align-items-center mt-4">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="productId" value="${product.productId}">
                                <label for="quantity" class="fw-semibold">Số lượng</label>
                                <input id="quantity" type="number" name="quantity" value="1" min="1" max="${product.amount}" class="form-control" style="width: 90px;">
                                <button type="submit" class="btn btn-primary">Thêm vào giỏ</button>
                            </form>
                        </c:if>
                        <c:if test="${product.amount == 0}">
                            <p class="text-danger mt-4 mb-0">Sản phẩm đã hết hàng.</p>
                        </c:if>
                    </div>
                </div>
                <hr class="my-4">
                <h2 class="h5">Description</h2>
                <p class="mb-4"><c:out value="${product.description}" /></p>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary">Quay lại danh sách</a>
            </div>
        </c:otherwise>
    </c:choose>
</section>
<style>
    .product-detail-image {
        width: 360px;
        height: 280px;
        max-width: 100%;
        object-fit: contain;
        background: #eef2f6;
    }
</style>
<%@ include file="/common/footer.jsp" %>