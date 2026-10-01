<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<section class="p-4 bg-white rounded-3 shadow-sm">
    <h1 class="h2">Trang quản trị</h1>
    <p class="mb-0">Đây là trang kiểm tra layout dành cho quản trị viên.</p>
    <div class="row g-3 mt-3">
        <div class="col-md-6">
            <a href="${pageContext.request.contextPath}/admin/category" class="btn btn-primary w-100 py-3">
                Quản lý Danh mục
            </a>
        </div>
        <div class="col-md-6">
            <a href="${pageContext.request.contextPath}/admin/user" class="btn btn-outline-primary w-100 py-3">
                Quản lý Người dùng
            </a>
        </div>
    </div>
</section>
<%@ include file="/common/footer.jsp" %>