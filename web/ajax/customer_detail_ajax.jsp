<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<c:choose>
    <c:when test="${not empty customerOrders}">
        <div style="max-height: 400px; overflow-y: auto;">
            <table class="tableKH" style="width: 100%; border-collapse: collapse;">
                <thead>
                    <tr style="background:#f8f9fa;">
                        <th style="padding:10px; border-bottom:2px solid #ddd; text-align:left;">Mã Đơn</th>
                        <th style="padding:10px; border-bottom:2px solid #ddd; text-align:left;">Ngày Đặt</th>
                        <th style="padding:10px; border-bottom:2px solid #ddd; text-align:left;">Trạng Thái</th>
                        <th style="padding:10px; border-bottom:2px solid #ddd; text-align:right;">Tổng Tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${customerOrders}">
                        <tr>
                            <td style="padding:10px; border-bottom:1px solid #ddd;">#ORD${order.orderId}</td>
                            <td style="padding:10px; border-bottom:1px solid #ddd;">${order.orderDateFormat}</td>
                            <td style="padding:10px; border-bottom:1px solid #ddd;">
                                <c:choose>
                                    <c:when test="${order.status == 'Hoàn thành'}">
                                        <span style="color: #27ae60; font-weight: 500;">Hoàn thành</span>
                                    </c:when>
                                    <c:when test="${order.status == 'Đã hủy'}">
                                        <span style="color: #e74c3c; font-weight: 500;">Đã hủy</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #f39c12; font-weight: 500;">${order.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="padding:10px; border-bottom:1px solid #ddd; text-align:right;">
                                <strong style="color: #e74c3c;"><fmt:formatNumber value="${order.totalAmount}" pattern="#,###"/>₫</strong>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        <div style="text-align: center; padding: 20px; color: #7f8c8d;">
            <i class="fas fa-box-open fa-3x" style="color: #bdc3c7; margin-bottom: 15px;"></i>
            <p>Khách hàng này chưa có đơn hàng nào.</p>
        </div>
    </c:otherwise>
</c:choose>
