<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<section>
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="bg-white rounded-3 shadow-sm p-4">
                <h1 class="h3 mb-4">Chỉnh sửa người dùng</h1>
                <form method="post" action="${pageContext.request.contextPath}/admin/user?action=edit">
                    <input type="hidden" name="userId" value="${user.userId}">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username" name="username" required maxlength="50" value="${user.username}">
                        </div>
                        <div class="col-md-6">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" required maxlength="100" value="${user.email}">
                        </div>
                        <div class="col-md-6">
                            <label for="fullname" class="form-label">Họ tên</label>
                            <input type="text" class="form-control" id="fullname" name="fullname" required maxlength="50" value="${user.fullname}">
                        </div>
                        <div class="col-md-6">
                            <label for="phone" class="form-label">Số điện thoại</label>
                            <input type="tel" class="form-control" id="phone" name="phone" maxlength="20" value="${user.phone}">
                        </div>
                        <div class="col-md-6">
                            <label for="password" class="form-label">Mật khẩu mới</label>
                            <input type="password" class="form-control" id="password" name="password" maxlength="50">
                            <div class="form-text">Để trống nếu không muốn đổi mật khẩu.</div>
                        </div>
                        <div class="col-md-6">
                            <label for="images" class="form-label">Hình ảnh</label>
                            <input type="text" class="form-control" id="images" name="images" maxlength="500" value="${user.images}">
                        </div>
                        <div class="col-md-6">
                            <label for="roleId" class="form-label">Vai trò</label>
                            <select class="form-select" id="roleId" name="roleId">
                                <option value="1" ${user.roleId == 1 ? 'selected' : ''}>Admin</option>
                                <option value="2" ${user.roleId == 2 ? 'selected' : ''}>User</option>
                                <option value="3" ${user.roleId == 3 ? 'selected' : ''}>Seller</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label for="status" class="form-label">Trạng thái</label>
                            <select class="form-select" id="status" name="status">
                                <option value="1" ${user.status == 1 ? 'selected' : ''}>Kích hoạt</option>
                                <option value="0" ${user.status == 0 ? 'selected' : ''}>Khóa</option>
                            </select>
                        </div>
                    </div>
                    <div class="d-flex gap-2 mt-4">
                        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        <a href="${pageContext.request.contextPath}/admin/user" class="btn btn-outline-secondary">Hủy</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<%@ include file="/common/footer.jsp" %>