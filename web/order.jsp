<%-- 
    Document   : order
    Created on : Feb 21, 2026, 9:26:02 PM
    Author     : dotha
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đơn hàng của tôi</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat-ai.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order.css">
    </head>
    <jsp:include page="header.jsp"></jsp:include>
        <body>
            <div class="containerOD">
                <h1 class="page-titleOD">Đơn hàng của tôi</h1>

                <!-- Tabs -->
            <%-- THÊM MỚI: URL dùng key ASCII sạch, Servlet sẽ map sang status tiếng Việt --%>
            <div class="tabs-containerOD">
                <ul class="tabs-listOD">
                    <li class="tab-itemOD">
                        <a href="order?action=viewOrder&tab=all">
                            <button class="tab-btnOD ${empty selectedTab || selectedTab == 'all' ? 'activeOD' : ''}">Tất cả</button>
                        </a>
                    </li>
                    <li class="tab-itemOD">
                        <a href="order?action=viewOrder&tab=pending">
                            <button class="tab-btnOD ${selectedTab == 'pending' ? 'activeOD' : ''}">Chờ xác nhận</button>
                        </a>
                    </li>
                    <li class="tab-itemOD">
                        <a href="order?action=viewOrder&tab=shipping">
                            <button class="tab-btnOD ${selectedTab == 'shipping' ? 'activeOD' : ''}">Đang giao</button>
                        </a>
                    </li>
                    <li class="tab-itemOD">
                        <a href="order?action=viewOrder&tab=done">
                            <button class="tab-btnOD ${selectedTab == 'done' ? 'activeOD' : ''}">Hoàn thành</button>
                        </a>
                    </li>
                    <li class="tab-itemOD">
                        <a href="order?action=viewOrder&tab=cancelled">
                            <button class="tab-btnOD ${selectedTab == 'cancelled' ? 'activeOD' : ''}">Đã hủy</button>
                        </a>
                    </li>
                </ul>
            </div>

            <!-- Orders List -->
            <c:if test="${listOrdersEmpty != null}">
                <div class="empty-stateOD">
                    <div class="empty-iconOD">
                        <i class="fas fa-shopping-bag"></i>
                    </div>
                    <h3 class="empty-titleOD">Chưa có đơn hàng nào</h3>
                    <p class="empty-textOD">Bạn chưa có đơn hàng nào. Hãy bắt đầu mua sắm ngay!</p>
                    <button class="btn-primaryOD" onclick="window.location.href = '${pageContext.request.contextPath}/home'">Mua sắm ngay</button>
                </div>
            </c:if>
            <c:if test="${listOrdersEmpty == null}">

                <%-- THÊM MỚI: hiện thông báo khi tab được chọn không có đơn hàng nào --%>
                <c:if test="${tabOrdersEmpty != null}">
                    <div class="empty-stateOD">
                        <div class="empty-iconOD">
                            <i class="fas fa-shopping-bag"></i>
                        </div>
                        <h3 class="empty-titleOD">Không có đơn hàng nào</h3>
                        <p class="empty-textOD">Bạn chưa có đơn hàng nào ở trạng thái này.</p>
                    </div>
                </c:if>

                <c:if test="${tabOrdersEmpty == null}">
                    <div class="orders-listOD">
                        <c:forEach items="${orderItemsMap}" var="order">
                            <div class="order-cardOD">
                                <div class="order-headerOD">
                                    <div class="order-infoOD">
                                        <span class="order-idOD">#${order.key.orderId}</span>
                                        <span class="order-dateOD">Đặt ngày: ${order.key.getOrderDateFormat()}</span>
                                    </div>
                                    <span class="order-statusOD status-${order.key.status == 'Hoàn thành' ? 'completedOD' : order.key.status == 'Chờ xác nhận' ? 'pendingOD' : order.key.status == 'Đang giao' ? 'shippingOD' : 'cancelledOD'}">${order.key.status}</span>
                                </div>
                                <div class="order-bodyOD">
                                    <div class="order-itemsOD">
                                        <!-- Item 1 -->
                                        <c:forEach items="${order.value}" var="orderItem">
                                            <div class="order-itemOD">
                                                <div class="item-imageOD">
                                                    <img width="100px" height="100px" src="${pageContext.request.contextPath}/images/${orderItem.productVariant.image}">
                                                </div>
                                                <div class="item-detailsOD">
                                                    <div>
                                                        <div class="item-nameOD">${orderItem.getProductName()}</div>
                                                        <div class="item-variantOD">Màu: ${orderItem.productVariant.colorName} | Size: ${orderItem.productVariant.sizeName}</div>
                                                    </div>
                                                    <div class="item-priceOD">
                                                        <span class="price-amountOD">
                                                            <fmt:formatNumber value=" ${orderItem.price}" type="number" groupingUsed="true"></fmt:formatNumber>₫
                                                            </span>
                                                        <c:if test="${orderItem.getProductDiscountPrice() != 0}">
                                                            <span class="item-price-old-OD">
                                                                <fmt:formatNumber value="${orderItem.getProductDiscountPrice()}" type="number" groupingUsed="true"/>%
                                                            </span>
                                                        </c:if>
                                                        <span class="item-qtyOD">x${orderItem.quantity}</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>

                                <div class="order-footerOD">
                                    <div class="total-sectionOD">
                                        <span class="total-labelOD">Tổng cộng:</span>
                                        <span class="total-amountOD">
                                            <fmt:formatNumber value="${order.key.totalAmount}" type="number" groupingUsed="true"></fmt:formatNumber>₫
                                            </span>
                                        <c:if test="${order.key.getOriginalPrice() != order.key.totalAmount}">
                                            <span class="item-price-old-total-OD">
                                                <fmt:formatNumber value="${order.key.getOriginalPrice()}" type="number" groupingUsed="true"/>₫
                                            </span>
                                        </c:if>
                                    </div>
                                    <div class="order-actionsOD">
                                        <%-- THÊM MỚI: button hủy đơn — chỉ hiện khi đơn chưa bị hủy hoặc hoàn thành --%>
                                        <c:if test="${order.key.status != 'Đã hủy' && order.key.status != 'Hoàn thành'}">
                                            <form method="post" action="order" 
                                                  onsubmit="return confirm('Bạn có chắc muốn hủy đơn hàng #${order.key.orderId} không?');">
                                                <input type="hidden" name="action" value="cancelOrder"/>
                                                <input type="hidden" name="orderId" value="${order.key.orderId}"/>
                                                <button type="submit" class="btn-secondaryOD">Hủy đơn hàng</button>
                                            </form>
                                        </c:if>
                                        <%-- THÊM MỚI: nếu đơn đã hủy hoặc hoàn thành thì disable button --%>
                                        <c:if test="${order.key.status == 'Đã hủy' || order.key.status == 'Hoàn thành'}">
                                            <button class="btn-secondaryOD" disabled style="opacity: 0.5; cursor: not-allowed;">Hủy đơn hàng</button>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

            </c:if>
        </div>
    </body>
    <jsp:include page="/chat-AI.jsp"></jsp:include>
    <jsp:include page="footer.jsp"></jsp:include>
</html>
