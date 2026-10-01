<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/common/header.jsp" %>
<section class="row justify-content-center">
    <div class="col-md-8 col-lg-6">
        <div class="bg-white rounded-3 shadow-sm p-4">
            <h1 class="h3 mb-4 text-center">Đăng ký tài khoản</h1>
            <c:if test="${not empty error}">
                <div class="alert alert-danger"><c:out value="${error}" /></div>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/register">
                <div class="mb-3">
                    <label for="username" class="form-label">Tên đăng nhập</label>
                    <input type="text" class="form-control" id="username" name="username" required maxlength="50" value="${param.username}">
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required maxlength="100" value="${param.email}">
                </div>
                <div class="mb-3">
                    <label for="fullname" class="form-label">Họ và tên</label>
                    <input type="text" class="form-control" id="fullname" name="fullname" required maxlength="50" value="${param.fullname}">
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label">Số điện thoại</label>
                    <input type="tel" class="form-control" id="phone" name="phone" required maxlength="20" value="${param.phone}">
                </div>
                <div class="mb-4">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <input type="password" class="form-control" id="password" name="password" required maxlength="50">
                </div>
                <button type="submit" class="btn btn-primary w-100">Đăng ký</button>
            </form>
            <p class="text-center mt-3 mb-0">Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></p>
        </div>
    </div>
</section>
<%@ include file="/common/footer.jsp" %>