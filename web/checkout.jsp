<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@ page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Xác nhận thanh toán - VINAFA</title>
                <link rel="stylesheet"
                    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/checkout.css" />
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
            </head>

            <body>
                <jsp:include page="/header.jsp"></jsp:include>

                <div class="checkout-wrapper">
                    <div class="checkout-header">
                        <h1>Xác nhận thanh toán</h1>
                        <div class="checkout-steps">
                            <div class="step">
                                <div class="step-number">1</div>
                                Giỏ hàng
                            </div>
                            <div class="step-line"></div>
                            <div class="step active">
                                <div class="step-number">2</div>
                                Thanh toán
                            </div>
                            <div class="step-line"></div>
                            <div class="step">
                                <div class="step-number">3</div>
                                Hoàn tất
                            </div>
                        </div>
                    </div>

                    <div class="checkout-grid">
                        <div class="left-section">
                            <!-- Delivery Info Card -->
                            <div class="checkout-section">
                                <h3 class="section-title"><i class="fas fa-map-marker-alt"></i> Thông tin giao hàng</h3>
                                <div class="address-info">
                                    <div class="info-row">
                                        <span class="info-label">Người nhận:</span>
                                        <span class="info-value">${sessionScope.customer.fullName}</span>
                                    </div>
                                    <div class="info-row">
                                        <span class="info-label">Số điện thoại:</span>
                                        <span class="info-value">${sessionScope.customer.phoneNumber}</span>
                                    </div>
                                    <div class="info-row">
                                        <span class="info-label">Địa chỉ nhận hàng:</span>
                                        <span class="info-value">${sessionScope.customer.address}</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Payment Method Card -->
                            <div class="checkout-section">
                                <h3 class="section-title"><i class="fas fa-credit-card"></i> Phương thức thanh toán</h3>
                                <div class="payment-methods">
                                    <label class="payment-method active">
                                        <input type="radio" name="payment_method" value="cod" checked>
                                        <i class="fas fa-truck"></i>
                                        <div>
                                            <strong>Thanh toán khi nhận hàng (COD)</strong>
                                            <p style="font-size: 13px; color: #666; margin: 0;">Giao hàng và thu tiền
                                                tận nơi trên toàn quốc.</p>
                                        </div>
                                    </label>
                                </div>
                            </div>

                            <!-- Items Card -->
                            <div class="checkout-section">
                                <h3 class="section-title"><i class="fas fa-shopping-bag"></i> Sản phẩm
                                    (${listCartItemViews.size()} sản phẩm)</h3>
                                <div class="checkout-items">
                                    <c:forEach items="${listCartItemViews}" var="item">
                                        <div class="checkout-item">
                                            <img src="${pageContext.request.contextPath}/images/${item.image}"
                                                class="item-img" alt="${item.productName}">
                                            <div class="item-details">
                                                <div class="item-name">${item.productName}</div>
                                                <div class="item-variant">Kích thước: ${item.size} / Màu sắc:
                                                    ${item.color} / SL: ${item.quantity}</div>
                                            </div>
                                            <div class="item-qty-price">
                                                <div class="item-price">
                                                    <fmt:formatNumber
                                                        value="${(item.price * (1 - item.discount / 100.0)) * item.quantity}"
                                                        type="number" groupingUsed="true" />₫
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                        <div class="right-section">
                            <!-- Summary Card -->
                            <div class="checkout-section summary-card">
                                <h3 class="section-title"><i class="fas fa-file-invoice-dollar"></i> Tổng kết đơn hàng
                                </h3>
                                <div class="summary-details">
                                    <div class="summary-line">
                                        <span>Tạm tính</span>
                                        <span>
                                            <fmt:formatNumber value="${totalPrice}" type="number" groupingUsed="true" />
                                            ₫
                                        </span>
                                    </div>
                                    <div class="summary-line">
                                        <span>Giảm giá</span>
                                        <span>-
                                            <fmt:formatNumber value="${discountValue}" type="number"
                                                groupingUsed="true" />₫
                                        </span>
                                    </div>
                                    <div class="summary-line">
                                        <span>Phí vận chuyển</span>
                                        <span>
                                            <c:choose>
                                                <c:when test="${totalPrice > 399000}">Miễn Phí</c:when>
                                                <c:otherwise>30.000₫</c:otherwise>
                                            </c:choose>
                                        </span>
                                    </div>
                                    <c:if test="${not empty voucherCode}">
                                        <div class="summary-line"
                                            style="color: #2ecc71; font-size: 13px; font-weight: 500;">
                                            <span><i class="fas fa-tag"></i> Voucher áp dụng:</span>
                                            <span>${voucherCode}</span>
                                        </div>
                                    </c:if>

                                    <div class="summary-total">
                                        <span class="total-label">Tổng cộng</span>
                                        <span class="total-value">
                                            <fmt:formatNumber value="${totalPriceFinal}" type="number"
                                                groupingUsed="true" />₫
                                        </span>
                                    </div>
                                </div>

                                <a href="${pageContext.request.contextPath}/order?action=pay&vouchercode=${voucherCode}"
                                    class="btn-place-order">XÁC NHẬN ĐẶT HÀNG</a>
                                <a href="${pageContext.request.contextPath}/cart?action=viewcart"
                                    class="back-to-cart"><i class="fas fa-chevron-left"></i> Quay lại giỏ hàng</a>
                            </div>
                        </div>
                    </div>
                </div>

                <jsp:include page="/footer.jsp"></jsp:include>
            </body>

            </html>