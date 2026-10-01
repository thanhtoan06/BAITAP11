<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản trị - 24162129 Made</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
    <%@ include file="/common/header.jsp" %>
    <div class="container-fluid flex-grow-1">
        <div class="row min-vh-100">
            <aside class="col-md-3 col-lg-2 bg-dark text-white p-3">
                <h5 class="mb-4">Quản trị hệ thống</h5>
                <nav class="nav flex-column">
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/admin/home">Tổng quan</a>
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/products">Sản phẩm</a>
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/home">Về trang chủ</a>
                </nav>
            </aside>
            <main class="col-md-9 col-lg-10 p-4">
                ${decoratedBody}
            </main>
        </div>
    </div>
    <%@ include file="/common/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>