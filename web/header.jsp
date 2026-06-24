<%-- Document : header Created on : Jan 24, 2026, 10:54:36 PM Author : dotha --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <div class="sticky-header-wrapper"
                style="position: sticky; top: 0; z-index: 1000; background: #fff; width: 100%; box-shadow: 0 4px 10px rgba(0,0,0,0.05);">
                
                <c:if test="${not empty sessionScope.customer}">
                    <jsp:useBean id="cartDAO" class="dal.CartDAO" scope="request" />
                    <c:set var="syncedCount" value="${cartDAO.getNumberCartByUserId(sessionScope.customer.userId)}" />
                    <c:if test="${syncedCount >= 0}">
                        <c:set var="cartCount" value="${syncedCount}" scope="session" />
                    </c:if>
                </c:if>

                <header>
                    <div class="header-top">
                        <div class="logo">VINAFA</div>
                        <div class="header-icons">
                            <!--            <form class="search-form" action="#" method="get" style="position:relative; margin-right: 20px;">
        <input type="text" placeholder="Tìm kiếm..." name="q" class="search-input">
        <button type="submit" class="search-btn"
                style="position:absolute; right:6px; top:50%; transform:translateY(-50%); background:transparent; border:none; cursor:pointer;">
            <i class="fas fa-search"></i>
        </button>
    </form>-->
                            <a href="#" title="Yêu thích"><i class="fas fa-heart"></i></a>
                            <a href="${pageContext.request.contextPath}/cart?action=viewcart" class="cart-icon"
                                title="Giỏ hàng">
                                <i class="fas fa-shopping-cart"></i>
                                <span class="cart-count">${sessionScope.cartCount != null ? sessionScope.cartCount :
                                    '*'}</span>
                            </a>
                            <a href="${pageContext.request.contextPath}/userinfomation" title="Tài khoản"><i
                                    class="fas fa-user"></i></a>
                        </div>
                    </div>
                </header>
                <jsp:useBean id="categoryDAO" class="dal.CategoryDAO" scope="request" />
                <c:set var="categories" value="${categoryDAO.allCategory}" />
                <style>
                    header {
                        position: static !important;
                    }

                    /* ========== MEGA MENU ========== */
                    .taskbar {
                        width: 100% !important;
                        max-width: 100% !important;
                        left: 0 !important;
                        right: auto !important;
                        transform: none !important;
                        padding: 0 !important;
                        margin: 0 !important;
                        gap: 0 !important;
                    }

                    .taskbar .nav-item {
                        position: relative;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        height: 48px;
                        transition: background-color 0.3s;
                        flex: 1;
                    }

                    .taskbar .nav-item>a {
                        height: 100%;
                        width: 100%;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        white-space: nowrap;
                    }

                    .taskbar .nav-item:hover,
                    .taskbar .nav-item.active {
                        background-color: #fff;
                    }

                    .taskbar .nav-item:hover>a,
                    .taskbar .nav-item.active>a {
                        color: #374047 !important;
                    }

                    .taskbar .mega-menu {
                        position: absolute;
                        top: 100%;
                        left: 0;
                        width: 800px;
                        background: #fff;
                        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                        display: flex;
                        padding: 25px;
                        gap: 30px;
                        visibility: hidden;
                        opacity: 0;
                        transform: translateY(10px);
                        transition: all 0.3s ease;
                        z-index: 1000;
                        border-top: 2px solid #e74c3c;
                        border-radius: 0 0 8px 8px;
                        cursor: default;
                    }

                    .taskbar .nav-item.has-mega:hover .mega-menu {
                        display: flex;
                        visibility: visible;
                        opacity: 1;
                        transform: translateY(0);
                    }

                    .taskbar .category-grid {
                        display: grid;
                        grid-template-columns: repeat(4, 1fr);
                        gap: 15px 30px;
                        width: 100%;
                    }

                    .taskbar .category-grid h4 {
                        font-size: 14px;
                        color: #333;
                        margin-bottom: 15px;
                        font-weight: 700;
                        text-transform: uppercase;
                        grid-column: 1 / -1;
                    }

                    .taskbar .category-grid a {
                        color: #666 !important;
                        text-transform: none !important;
                        font-size: 14px !important;
                        font-weight: 400 !important;
                        padding: 6px 0 !important;
                        height: auto !important;
                        display: inline-block;
                    }

                    .taskbar .category-grid a:hover {
                        color: #e74c3c !important;
                        text-decoration: underline !important;
                    }
                </style>
                <nav class="taskbar">
                    <div
                        class="nav-item ${pageContext.request.requestURI.endsWith('/home.jsp') || pageContext.request.requestURI.endsWith('/home') ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                    </div>

                    <div
                        class="nav-item has-mega ${param.gender == 'Nam' || requestScope.gender == 'Nam' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/product?gender=Nam">Nam</a>
                        <div class="mega-menu">
                            <div class="category-grid">
                                <h4>DANH MỤC SẢN PHẨM</h4>
                                <c:forEach items="${categories}" var="cat">
                                    <a
                                        href="${pageContext.request.contextPath}/product?gender=Nam&cid=${cat.categoryId}">${cat.categoryName}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <div
                        class="nav-item has-mega ${param.gender == 'Nữ' || requestScope.gender == 'Nữ' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/product?gender=Nữ">Nữ</a>
                        <div class="mega-menu">
                            <div class="category-grid">
                                <h4>DANH MỤC SẢN PHẨM</h4>
                                <c:forEach items="${categories}" var="cat">
                                    <a
                                        href="${pageContext.request.contextPath}/product?gender=Nữ&cid=${cat.categoryId}">${cat.categoryName}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <div
                        class="nav-item has-mega ${param.gender == 'Unisex' || requestScope.gender == 'Unisex' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/product?gender=Unisex">UniSex</a>
                        <div class="mega-menu">
                            <div class="category-grid">
                                <h4>DANH MỤC SẢN PHẨM</h4>
                                <c:forEach items="${categories}" var="cat">
                                    <a
                                        href="${pageContext.request.contextPath}/product?gender=Unisex&cid=${cat.categoryId}">${cat.categoryName}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="nav-item ${param.isSale == 'true' || requestScope.isSale == true ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/product?isSale=true">Flash Sale</a>
                    </div>
                </nav>
            </div>