<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/common/header.jsp" %>
<section>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0">Quản lý người dùng</h1>
        <a href="${pageContext.request.contextPath}/admin/user?action=add" class="btn btn-primary">Thêm Người Dùng Mới</a>
    </div>

    <div class="bg-white rounded-3 shadow-sm p-3">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Họ tên</th>
                        <th>Số điện thoại</th>
                        <th>Vai trò</th>
                        <th>Trạng thái</th>
                        <th class="text-end">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty userList}">
                            <tr><td colspan="8" class="text-center text-muted py-4">Chưa có người dùng.</td></tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="user" items="${userList}">
                                <tr>
                                    <td><c:out value="${user.userId}" /></td>
                                    <td><c:out value="${user.username}" /></td>
                                    <td><c:out value="${user.email}" /></td>
                                    <td><c:out value="${user.fullname}" /></td>
                                    <td><c:out value="${user.phone}" /></td>
                                    <td><c:out value="${user.roleName}" /></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.status == 1}"><span class="badge text-bg-success">Kích hoạt</span></c:when>
                                            <c:otherwise><span class="badge text-bg-secondary">Khóa</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-end text-nowrap">
                                        <a href="${pageContext.request.contextPath}/admin/user?action=edit&id=${user.userId}" class="btn btn-sm btn-outline-primary">Sửa</a>
                                        <a href="${pageContext.request.contextPath}/admin/user?action=delete&id=${user.userId}" class="btn btn-sm btn-outline-danger"
                                           onclick="return confirm('Bạn có chắc muốn xóa người dùng này?');">Xóa</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <c:if test="${maxPage > 1}">
            <nav class="mt-4" aria-label="Phân trang người dùng">
                <ul class="pagination justify-content-center mb-0">
                    <c:forEach var="i" begin="1" end="${maxPage}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/user?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:if>
    </div>
</section>
<%@ include file="/common/footer.jsp" %>