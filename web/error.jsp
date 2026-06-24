<%-- 
    Document   : error
    Created on : Feb 3, 2026, 9:21:53 PM
    Author     : DELL P5530
--%>

<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Có lỗi xảy ra</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error-page.css"/>
    </head>
    <body>

        <div class="error-container">
            <div class="error-code">
                <%= request.getAttribute("javax.servlet.error.status_code") %>
            </div>

            <div class="error-title">
                Oops! Có lỗi xảy ra
            </div>

            <div class="error-message">
                <strong>Thông báo:</strong>
                <%= request.getAttribute("javax.servlet.error.message") %>
                <br><br>
                Trang bạn truy cập hiện không khả dụng hoặc bạn không có quyền truy cập.
            </div>


            <div class="btn-group">
                <a href="<%= request.getContextPath() %>/home" class="btn-home">Trang chủ</a>
                <a href="javascript:history.back()" class="btn-back">Quay lại</a>
            </div>
        </div>
    </body>
</html>
