<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/common/header.jsp" %>
<section>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0">Quản lý danh mục</h1>
        <a href="${pageContext.request.contextPath}/admin/category?action=add" class="btn btn-primary">Thêm Danh Mục Mới</a>
    </div>

    <div class="bg-white rounded-3 shadow-sm p-3">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Tên danh mục</th>
                        <th>Hình ảnh</th>
                        <th>Trạng thái</th>
                        <th class="text-end">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty categoryList}">
                            <tr><td colspan="5" class="text-center text-muted py-4">Chưa có danh mục.</td></tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="category" items="${categoryList}">
                                <tr>
                                    <td><c:out value="${category.categoryId}" /></td>
                                    <td><c:out value="${category.categoryName}" /></td>
                                    <td class="category-image-cell">
                                        <c:choose>
                                            <c:when test="${not empty category.images}">
                                                <c:choose>
                                                    <c:when test="${category.images.startsWith('http://') or category.images.startsWith('https://')}">
                                                        <img src="${category.images}" alt="${category.categoryName}" width="90" height="70"
                                                             class="category-image img-thumbnail"
                                                             onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/placeholder-product.svg'">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${pageContext.request.contextPath}/images/${category.images}"
                                                             alt="${category.categoryName}" width="90" height="70"
                                                             class="category-image img-thumbnail"
                                                             onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/placeholder-product.svg'">
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${pageContext.request.contextPath}/images/placeholder-product.svg"
                                                     alt="Không có hình ảnh" width="90" height="70" class="category-image img-thumbnail">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${category.status == 1}"><span class="badge text-bg-success">Hoạt động</span></c:when>
                                            <c:otherwise><span class="badge text-bg-secondary">Ẩn</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-end text-nowrap">
                                        <a href="${pageContext.request.contextPath}/admin/category?action=edit&id=${category.categoryId}" class="btn btn-sm btn-outline-primary">Sửa</a>
                                        <a href="${pageContext.request.contextPath}/admin/category?action=delete&id=${category.categoryId}" class="btn btn-sm btn-outline-danger"
                                           onclick="return confirm('Bạn có chắc muốn xóa danh mục này?');">Xóa</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <c:if test="${maxPage > 1}">
            <nav class="mt-4" aria-label="Phân trang danh mục">
                <ul class="pagination justify-content-center mb-0">
                    <c:forEach var="i" begin="1" end="${maxPage}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/category?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:if>
    </div>
</section>
<style>
    .category-image-cell {
        width: 120px;
        min-width: 120px;
    }

    .category-image {
        display: block;
        width: 90px;
        height: 70px;
        object-fit: contain;
        background: #eef2f6;
    }
</style>
<%@ include file="/common/footer.jsp" %>