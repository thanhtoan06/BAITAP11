<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/common/header.jsp" %>
<section class="row justify-content-center">
    <div class="col-md-7 col-lg-5">
        <div class="bg-white rounded-3 shadow-sm p-4">
            <h1 class="h3 mb-4 text-center">Đăng nhập</h1>
            <c:if test="${not empty success}">
                <div class="alert alert-success"><c:out value="${success}" /></div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger"><c:out value="${error}" /></div>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/login">
                <div class="mb-3">
                    <label for="username" class="form-label">Tên đăng nhập</label>
                    <input type="text" class="form-control" id="username" name="username" required maxlength="50" value="${param.username}">
                </div>
                <div class="mb-4">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <input type="password" class="form-control" id="password" name="password" required maxlength="50">
                </div>
                <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
            </form>
            <p class="text-center mt-3 mb-0">Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a></p>
        </div>
    </div>
</section>
<%@ include file="/common/footer.jsp" %>