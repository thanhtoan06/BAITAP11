<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<section>
    <div class="row justify-content-center">
        <div class="col-lg-7">
            <div class="bg-white rounded-3 shadow-sm p-4">
                <h1 class="h3 mb-4">Thêm danh mục mới</h1>
                <form method="post" action="${pageContext.request.contextPath}/admin/category?action=add">
                    <div class="mb-3">
                        <label for="categoryName" class="form-label">Tên danh mục</label>
                        <input type="text" class="form-control" id="categoryName" name="categoryName" required maxlength="200">
                    </div>
                    <div class="mb-3">
                        <label for="images" class="form-label">Hình ảnh</label>
                        <input type="text" class="form-control" id="images" name="images" maxlength="500" placeholder="Đường dẫn hình ảnh">
                    </div>
                    <div class="mb-4">
                        <label for="status" class="form-label">Trạng thái</label>
                        <select class="form-select" id="status" name="status">
                            <option value="1">Hoạt động</option>
                            <option value="0">Ẩn</option>
                        </select>
                    </div>
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">Lưu danh mục</button>
                        <a href="${pageContext.request.contextPath}/admin/category" class="btn btn-outline-secondary">Hủy</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<%@ include file="/common/footer.jsp" %>