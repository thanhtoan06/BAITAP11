<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="/common/header.jsp" %>
<section>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h1 class="h2 mb-1">Danh sách sản phẩm</h1>
            <p class="text-muted mb-0">Sản phẩm được nhóm theo từng cửa hàng.</p>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty productList}">
            <div class="alert alert-info">Chưa có sản phẩm nào.</div>
        </c:when>
        <c:otherwise>
            <c:remove var="currentSellerId" />
            <c:forEach var="product" items="${productList}">
                <c:if test="${currentSellerId == null || currentSellerId != product.sellerId}">
                    <c:if test="${currentSellerId != null}">
                        </tbody>
                        </table>
                    </div>
                    </c:if>
                    <c:set var="currentSellerId" value="${product.sellerId}" />
                    <div class="bg-white rounded-3 shadow-sm p-3 mb-4">
                        <h2 class="h5 border-bottom pb-2 mb-0">
                            Cửa hàng #${product.sellerId}
                            <span class="text-muted fw-normal">- <c:out value="${product.sellerName}" /></span>
                        </h2>
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0">
                                <thead>
                                    <tr>
                                        <th scope="col">Hình ảnh</th>
                                        <th scope="col">Tên sản phẩm</th>
                                        <th scope="col">Mã sản phẩm</th>
                                        <th scope="col">Danh mục</th>
                                        <th scope="col">Giá</th>
                                        <th scope="col">Amount</th>
                                        <th scope="col">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                </c:if>
                <tr>
                    <td class="product-image-cell">
                        <c:choose>
                            <c:when test="${not empty product.images and (product.images.startsWith('http://') or product.images.startsWith('https://'))}">
                                <img src="${product.images}" width="150" height="120" alt="${product.productName}" class="product-image rounded"
                                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/placeholder-product.svg'">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/images/${product.images}" width="150" height="120"
                                     alt="${product.productName}" class="product-image rounded"
                                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/placeholder-product.svg'">
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/product-detail?id=${product.productId}" class="fw-semibold text-decoration-none">
                            <c:out value="${product.productName}" />
                        </a>
                    </td>
                    <td><c:out value="${product.productCode}" /></td>
                    <td><c:out value="${product.categoryName}" /></td>
                    <td><fmt:formatNumber value="${product.price}" type="number" minFractionDigits="2" /> </td>
                    <td><c:out value="${product.amount}" /></td>
                    <td>
                        <c:choose>
                            <c:when test="${product.amount <= 0}">
                                <span class="text-muted">Hết hàng</span>
                            </c:when>
                            <c:when test="${sessionScope.account == null}">
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-sm btn-outline-primary">Đăng nhập để mua</a>
                            </c:when>
                            <c:when test="${sessionScope.account.roleId != 2}">
                                <span class="text-muted">Tài khoản User mới được mua hàng</span>
                            </c:when>
                            <c:otherwise>
                                <form action="${pageContext.request.contextPath}/cart" method="post" class="d-flex gap-2 align-items-center">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="productId" value="${product.productId}">
                                    <input type="number" name="quantity" value="1" min="1" max="${product.amount}" class="form-control form-control-sm" style="width: 75px;">
                                    <button type="submit" class="btn btn-sm btn-primary">Thêm</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            </table>
            </div>
        </c:otherwise>
    </c:choose>
</section>
<style>
    .product-image-cell {
        width: 170px;
        min-width: 170px;
    }

    .product-image {
        display: block;
        width: 150px;
        height: 120px;
        object-fit: contain;
        background: #eef2f6;
    }
</style>
<%@ include file="/common/footer.jsp" %>