<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vouchers</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-profile.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat-ai.css">
        <style>
            .voucher-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
                gap: 20px;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/header.jsp"></jsp:include>
            <div class="containerUS">
                <!-- Sidebar -->
                <div class="sidebarUS">
                    <div class="${user.getTier()}">
                    <div class="user-greetingUS">Xin chào!</div>
                    <div class="user-phoneUS">${user.email}</div>
                    <div class="user-tierUS">
                        <span class="tier-labelUS">Hạng thẻ:</span>
                        <span class="tier-valueUS">${user.getTier()}</span>
                        <span class="tier-labelUS">Đã Đặt:</span>
                        <span class="tier-valueUS">${user.totalOrders == -1 ? 0 : user.totalOrders}</span>
                    </div>
                    <c:if test="${neededAmountTopromoted != null}">
                        <div class="points-infoUS">Cần chi tiêu
                            <fmt:formatNumber value="${neededAmountTopromoted}"></fmt:formatNumber>đ để thăng lên hạng ${nextRank}
                            </div>
                    </c:if>
                    <div class="points-infoUS">Tổng chi tiêu: 
                        <fmt:formatNumber value="${totalSpending}"></fmt:formatNumber>đ
                        </div>
                    </div>

                    <ul class="menu-listUS">
                        <li class="menu-itemUS">
                            <a href="${pageContext.request.contextPath}/userinfomation?action=viewInfomation" class="menu-linkUS">
                                <div class="menu-iconUS">
                                    <i class="fas fa-user"></i>
                                </div>
                                <div class="menu-contentUS">
                                    <div class="menu-titleUS">
                                        Thông tin tài khoản
                                    </div>
                                    <div class="menu-subtitleUS">Cập nhật thông tin tài khoản</div>
                                </div>
                                <span class="menu-arrowUS">
                                    <i class="fas fa-arrow-right"></i>
                                </span>
                            </a>
                        </li>

                        <li class="menu-itemUS">
                            <a href="#" class="menu-linkUS">
                            <div class="menu-iconUS">
                                <i class="fas fa-ticket-alt"></i>
                            </div>
                            <div class="menu-contentUS">
                                <div class="menu-titleUS">
                                    Mã ưu đãi
                                    <span class="badgeUS badge-numberUS">${numberVocher == -1 ? 0 : numberVocher}</span>
                                </div>
                                <div class="menu-subtitleUS">Quản lý mã dành riêng cho bạn</div>
                            </div>
                            <span class="menu-arrowUS">
                                <i class="fas fa-arrow-right"></i>
                            </span>
                        </a>
                    </li>

                    <li class="menu-itemUS">
                        <a href="${pageContext.request.contextPath}/order" class="menu-linkUS">
                            <div class="menu-iconUS">
                                <i class="fas fa-shopping-bag"></i>
                            </div>
                            <div class="menu-contentUS">
                                <div class="menu-titleUS">Đơn hàng</div>
                                <div class="menu-subtitleUS">Tra cứu thông tin vận chuyển</div>
                            </div>
                            <span class="menu-arrowUS">
                                <i class="fas fa-arrow-right"></i>
                            </span>
                        </a>
                    </li>

                    <c:if test="${sessionScope.customer.roleId == 1}">
                        <li class="menu-itemUS">
                            <a href="${pageContext.request.contextPath}/generaladmin" class="menu-linkUS">
                                <div class="menu-iconUS">
                                    <i class="fas fa-user-cog"></i>
                                </div>
                                <div class="menu-contentUS">
                                    <div class="menu-titleUS">Admin</div>
                                    <div class="menu-subtitleUS">Quản lí sản phẩm, đơn hàng, khách hàng,...</div>
                                </div>
                                <span class="menu-arrowUS">
                                    <i class="fas fa-arrow-right"></i>
                                </span>
                            </a>
                        </li>
                    </c:if>

                    <li class="menu-itemUS logout-itemUS">
                        <a href="${pageContext.request.contextPath}/dispatchcontroller?action=logout" class="menu-linkUS">
                            <div class="menu-iconUS">
                                <i class="fas fa-sign-out-alt"></i>
                            </div>
                            <div class="menu-contentUS">
                                <div class="menu-titleUS">Đăng xuất</div>
                            </div>
                            <span class="menu-arrowUS">
                                <i class="fas fa-arrow-right"></i>
                            </span>
                        </a>
                    </li>
                </ul>
            </div>

            <!-- Main Content -->
            <div class="view-sectionEDU" id="viewSectionEDU">
                <h1 class="page-titleUS">Mã ưu đãi</h1>
                <p class="page-descriptionUS">Quản lý mã dành riêng cho bạn. Sử dụng các mã giảm giá này khi thanh toán để nhận ưu đãi.</p>

                <div class="voucher-grid">
                    <c:if test="${empty listVouchers}">
                        <p style="color: #666; font-style: italic;">Bạn hiện không có mã ưu đãi nào.</p>
                    </c:if>
                    <c:if test="${not empty listVouchers}">
                        <c:forEach items="${listVouchers}" var="voucher">
                            <div class="voucher-box" style="margin:0;">
                                <div class="voucher-code">${voucher.code}</div>
                                <div class="voucher-discount">Giảm
                                    <c:choose>
                                        <c:when test="${fn:toLowerCase(voucher.discountType) == 'percent'}">
                                            <fmt:formatNumber value="${voucher.discountValue}" type="number" groupingUsed="true"></fmt:formatNumber>%
                                            (Tối đa <fmt:formatNumber value="${voucher.maxDiscount}" type="number" groupingUsed="true"></fmt:formatNumber>đ)
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber value="${voucher.discountValue}" type="number" groupingUsed="true"></fmt:formatNumber>đ
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="voucher-condition">Đơn tối thiểu <fmt:formatNumber value="${voucher.minOrderValue}"></fmt:formatNumber>đ</div>
                                <div class="voucher-expiry">HSD: ${voucher.endDateFormat}</div>
                                <button class="voucher-btn" onclick="copyCode(this)" data-code="${voucher.code}">Copy Mã</button>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>

        </div><!-- /containerUS -->

    <jsp:include page="/chat-AI.jsp"></jsp:include>
    <jsp:include page="/footer.jsp"></jsp:include>

    <script>
        function copyCode(t) {
            var voucherCode = t.dataset.code;
            navigator.clipboard.writeText(voucherCode)
                .then(() => {
                    alert("Đã copy: " + voucherCode);
                })
                .catch(err => {
                    console.error("Lỗi copy:", err);
                });
        }
    </script>
</body>
</html>
