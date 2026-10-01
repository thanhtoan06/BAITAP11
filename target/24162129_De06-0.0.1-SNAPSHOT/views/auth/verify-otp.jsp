<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/common/header.jsp" %>
<section class="row justify-content-center">
    <div class="col-md-7 col-lg-5">
        <div class="bg-white rounded-3 shadow-sm p-4">
            <h1 class="h3 mb-3 text-center">Xác thực tài khoản</h1>
            <p class="text-muted text-center">Nhập mã OTP 6 chữ số đã được gửi đến email của bạn.</p>
            <c:if test="${not empty success}">
                <div class="alert alert-success"><c:out value="${success}" /></div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger"><c:out value="${error}" /></div>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/verify-otp">
                <div class="mb-4">
                    <label for="otp" class="form-label">Mã OTP</label>
                    <input type="text" class="form-control text-center fs-4" id="otp" name="otp"
                           required minlength="6" maxlength="6" pattern="[0-9]{6}" inputmode="numeric" autocomplete="one-time-code">
                </div>
                <button type="submit" class="btn btn-primary w-100">Xác nhận OTP</button>
            </form>
        </div>
    </div>
</section>
<%@ include file="/common/footer.jsp" %>