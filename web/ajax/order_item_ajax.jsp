<%-- 
    Document   : order_item_ajax
    Created on : Feb 12, 2026, 8:18:32 PM
    Author     : DELL P5530
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<!-- Header Thông tin đơn hàng (Chỉ hiện 1 lần) -->
<c:if test="${orderFinal != null}">
    <div class="order-popup-header-OD">
        <div class="header-main-OD">
            <i class="fas fa-file-invoice"></i>
            <div>
                <h2>Chi tiết đơn hàng #ORD${orderFinal.orderId}</h2>
                <p class="order-date-OD"><i class="far fa-calendar-alt"></i> Ngày đặt: ${orderFinal.orderDateFormat}</p>
            </div>
        </div>
        <div class="status-wrap-OD">
            <span class="status-label-OD">Trạng thái:</span>
            <span class="status-badge-OD status-${orderFinal.status == 'Chờ xác nhận' ? 'pending' : (orderFinal.status == 'Đang giao' ? 'shipping' : (orderFinal.status == 'Hoàn thành' ? 'completed' : 'cancelled'))}-OD">
                ${orderFinal.status}
            </span>
        </div>
    </div>

    <div class="order-customer-info-OD">
        <div class="info-item-OD">
            <label><i class="fas fa-user"></i> Khách hàng:</label>
            <span>${orderFinal.fullName}</span>
        </div>
        <div class="info-item-OD">
            <label><i class="fas fa-phone"></i> Số điện thoại:</label>
            <span>${orderFinal.phoneNumber}</span>
        </div>
        <div class="info-item-OD full-width-OD">
            <label><i class="fas fa-map-marker-alt"></i> Địa chỉ giao hàng:</label>
            <span>${orderFinal.shippingAddress}</span>
        </div>
    </div>
</c:if>

<h3 class="product-list-title-OD"><i class="fas fa-shopping-basket"></i> Danh sách sản phẩm</h3>

<div class="order-items-container-OD">
    <c:forEach items="${listOrderItems}" var="oItem">
        <div class="product-card-OD">
            <div class="product-img-OD">
                <img src="${pageContext.request.contextPath}/images/${oItem.productVariant.image}" alt="${oItem.productVariant.product.productName}" />
            </div>
            
            <div class="product-info-OD">
                <h4 class="product-name-OD">${oItem.productVariant.product.productName}</h4>
                <div class="product-meta-OD">
                    <span>Phân loại: <strong>${oItem.productVariant.color.colorName} / ${oItem.productVariant.size.sizeName}</strong></span>
                    <span>Số lượng: <strong>x${oItem.quantity}</strong></span>
                </div>
                <div class="product-price-row-OD">
                    <div class="unit-price-OD">Đơn giá: <fmt:formatNumber value="${oItem.price}" type="currency" maxFractionDigits="0"/></div>
                    <div class="item-total-OD">Thành tiền: <strong><fmt:formatNumber value="${oItem.totalAmount}" type="currency" maxFractionDigits="0"/></strong></div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<!-- Bảng tóm tắt thông tin thanh toán cuối cùng -->
<c:if test="${orderFinal != null}">
    <c:set var="shippingFee" value="${originalTotal >= 399000 ? 0 : 30000}" />
    <c:set var="discountAmount" value="${originalTotal + shippingFee - orderFinal.totalAmount}" />
    <c:if test="${discountAmount < 0}"><c:set var="discountAmount" value="0" /></c:if>

    <div class="order-summary-OD">
        <h3 class="summary-title-OD"><i class="fas fa-receipt"></i> Tổng kết thanh toán</h3>
        
        <div class="summary-row-OD">
            <span class="summary-label-OD"><i class="fas fa-box"></i> Tổng tiền sản phẩm:</span>
            <span class="summary-value-OD">
                <fmt:formatNumber value="${originalTotal}" type="currency" maxFractionDigits="0"/>
            </span>
        </div>

        <div class="summary-row-OD">
            <span class="summary-label-OD"><i class="fas fa-truck"></i> Phí vận chuyển:</span>
            <span class="summary-value-OD ${shippingFee == 0 ? 'free-shipping-OD' : ''}">
                <c:choose>
                    <c:when test="${shippingFee == 0}">
                        <i class="fas fa-check-circle"></i> Miễn phí
                    </c:when>
                    <c:otherwise>
                        <fmt:formatNumber value="${shippingFee}" type="currency" maxFractionDigits="0"/>
                    </c:otherwise>
                </c:choose>
            </span>
        </div>

        <c:if test="${appliedVoucher != null}">
            <div class="summary-row-OD voucher-row-OD">
                <span class="summary-label-OD"><i class="fas fa-ticket-alt"></i> Mã giảm giá:</span>
                <span class="summary-value-OD">
                    <span class="voucher-badge-OD">${appliedVoucher.code}</span>
                    <span class="voucher-type-OD">
                        (<c:choose>
                            <c:when test="${appliedVoucher.discountType == 'percent'}">
                                Giảm <fmt:formatNumber value="${appliedVoucher.discountValue}" type="number" maxFractionDigits="0"/>%
                            </c:when>
                            <c:otherwise>
                                Giảm <fmt:formatNumber value="${appliedVoucher.discountValue}" type="currency" maxFractionDigits="0"/>
                            </c:otherwise>
                        </c:choose>)
                    </span>
                </span>
            </div>
            <div class="summary-row-OD discount-row-OD">
                <span class="summary-label-OD"><i class="fas fa-tags"></i> Số tiền được giảm:</span>
                <span class="summary-value-OD discount-value-OD">
                    -<fmt:formatNumber value="${discountAmount}" type="currency" maxFractionDigits="0"/>
                </span>
            </div>
        </c:if>

        <div class="summary-divider-OD"></div>

        <div class="summary-row-OD total-row-OD">
            <span class="summary-label-OD"><i class="fas fa-money-bill-wave"></i> Thực thu (sau giảm):</span>
            <span class="summary-value-OD total-value-OD">
                <fmt:formatNumber value="${orderFinal.totalAmount}" type="currency" maxFractionDigits="0"/>
            </span>
        </div>
    </div>
</c:if>