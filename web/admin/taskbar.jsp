<%-- 
    Document   : taskbar
    Created on : Jan 27, 2026, 12:26:22 PM
    Author     : dotha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Inline CSS chống cache để đảm bảo giao diện active/hover luôn ăn css mới nhất -->
<style>
    .nav-linkDB {
        border-left: 3px solid transparent !important;
    }
    .nav-linkDB.activeDB, .nav-linkDB:hover {
        background: rgba(52, 152, 219, 0.2) !important;
        color: white !important;
        border-left: 3px solid #3498db !important;
    }
</style>
<!-- Sidebar -->
<div class="sidebarDB">
    <div class="logo-sectionDB" id="sectionLogo">
        <div class="logo-titleDB">VINAFA</div>
        <div class="logo-subtitleDB">Admin Dashboard</div>
    </div>

    <ul class="nav-menuDB">
        <li class="nav-itemDB">
            <a href="${pageContext.request.contextPath}/generaladmin" class="nav-linkDB" id="tongquan">
                <i class="fas fa-home nav-iconDB"></i>
                <span>Tổng quan</span>
            </a>
        </li>
        <li class="nav-itemDB">
            <a href="${pageContext.request.contextPath}/productadmin" class="nav-linkDB" id="sanpham">
                <i class="fas fa-box nav-iconDB"></i>
                <span>Sản phẩm</span>
            </a>
        </li>
        <li class="nav-itemDB">
            <a href="${pageContext.request.contextPath}/ordermanagement" class="nav-linkDB" id="donhang">
                <i class="fas fa-shopping-cart nav-iconDB"></i>
                <span>Đơn hàng</span>
            </a>
        </li>
        <li class="nav-itemDB">
            <a href="${pageContext.request.contextPath}/customersadmin" class="nav-linkDB" id="khachhang">
                <i class="fas fa-users nav-iconDB"></i>
                <span>Khách hàng</span>
            </a>
        </li>
        <li class="nav-itemDB">
            <a href="${pageContext.request.contextPath}/category" class="nav-linkDB" id="danhmuc">
                <i class="fas fa-tags nav-iconDB"></i>
                <span>Danh mục</span>
            </a>
        </li>
        <li class="nav-itemDB">
            <a href="${pageContext.request.contextPath}/voucheradmin" class="nav-linkDB" id="magiamgia">
                <i class="fas fa-ticket-alt nav-iconDB"></i>
                <span>Mã giảm giá</span>
            </a>
        </li>
    </ul>
    <input type="hidden" name="contextPath" value="${pageContext.request.contextPath}">
</div>
<script>
    (function () {
        var currentUrl = window.location.href.split('?')[0];
        var currentPath = window.location.pathname;
        var navLinks = document.querySelectorAll('.nav-linkDB');
        var activeFound = false;

        navLinks.forEach(function (link) {
            link.classList.remove('activeDB');
        });

        navLinks.forEach(function (link) {
            var linkUrl = link.href.split('?')[0];
            if (linkUrl && linkUrl.length > 0) {
                if (currentUrl.startsWith(linkUrl) || currentUrl === linkUrl) {
                    link.classList.add('activeDB');
                    activeFound = true;
                }
            }
        });

        if (!activeFound && (currentPath.endsWith("/admin") || currentPath.endsWith("/admin/"))) {
            var tongquan = document.getElementById('tongquan');
            if (tongquan) {
                tongquan.classList.add('activeDB');
            }
        }
    })();
</script>
<script src="${pageContext.request.contextPath}/js/taskbar.js?v=5"></script>
