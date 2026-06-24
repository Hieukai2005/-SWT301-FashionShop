<%-- Document : cart Created on : Feb 6, 2026, 10:54:31 PM Author : dotha --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <title>CART</title>
                    <link rel="stylesheet"
                        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css" />
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat-ai.css">
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
                </head>
                <jsp:include page="/header.jsp"></jsp:include>

                <body>
                    <!-- Banner miễn phí vận chuyển -->
                    <div class="shipping-banner-CA">
                        Miễn phí vận chuyển cho đơn hàng từ 399.000₫ - Đổi hàng miễn phí trong 30 ngày
                    </div>

                    <div class="cart-wrapper-CA">
                        <!-- Breadcrumb -->
                        <div class="breadcrumb-CA">
                            <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
                            <span>/</span>
                            <span>Giỏ hàng</span>
                        </div>

                        <c:if test="${requestScope.listCartItemViews == null}">
                            <div class="cart-wrapper-CA">
                                <div class="cart-header-CA">
                                    <h1>Giỏ Hàng</h1>
                                </div>

                                <div class="empty-cart-CA">
                                    <div class="empty-icon-CA">🛒</div>
                                    <p class="empty-text-CA">Giỏ hàng của bạn đang trống</p>
                                    <a href="${pageContext.request.contextPath}/home" class="shop-btn-CA">Mua Sắm
                                        Ngay</a>
                                </div>
                            </div>
                        </c:if>
                        <c:set value="${discountValue != null ? discountValue : 0}" var="discountValue"></c:set>
                        <c:if test="${requestScope.listCartItemViews != null}">
                            <div class="cart-header-CA">
                                <h1><i class="fas fa-shopping-cart"></i> Giỏ Hàng</h1>
                                <p class="cart-count-CA">Bạn đang có <strong>${sessionScope.cartCount} sản phẩm</strong>
                                    trong giỏ hàng</p>
                            </div>

                            <!-- Layout chính -->
                            <div class="cart-layout-CA">
                                <!-- Danh sách sản phẩm -->
                                <div class="cart-items-section-CA">
                                    <!-- Header bảng -->
                                    <div class="cart-table-header-CA">
                                        <div>Sản phẩm</div>
                                        <div>Đơn giá</div>
                                        <div>Số lượng</div>
                                        <div>Thành tiền</div>
                                        <div></div>
                                    </div>
                                    <c:set var="totalPrice" value="0"></c:set>
                                    <c:forEach items="${requestScope.listCartItemViews}" var="cartItemView">
                                        <div class="cart-item-CA">
                                            <div class="item-product-CA"
                                                onclick="window.location.href = '${pageContext.request.contextPath}/product-detail?id=${cartItemView.productId}&color=${cartItemView.colorId}&size=${cartItemView.sizeId}'">
                                                <img src="${pageContext.request.contextPath}/images/${cartItemView.image}"
                                                    alt="Áo Thun" class="item-image-CA">
                                                <div class="item-info-CA">
                                                    <div class="item-name-CA">${cartItemView.productName}</div>
                                                    <div class="item-details-CA">Màu sắc: ${cartItemView.color}</div>
                                                    <div class="item-details-CA">Kích thước: ${cartItemView.size}</div>
                                                    <div class="item-details-CA">Mã SP: ${cartItemView.productId}</div>
                                                    <div class="item-details-CA">Cart Item Id:
                                                        ${cartItemView.cartItemId}</div>
                                                </div>
                                            </div>
                                            <div class="item-price-CA">
                                                <fmt:formatNumber
                                                    value="${cartItemView.price - (cartItemView.price * cartItemView.discount / 100.0)}"
                                                    type="number" groupingUsed="true" />đ
                                                <c:if test="${cartItemView.discount != 0}">
                                                    <span class="item-price-old-CA">
                                                        <fmt:formatNumber value="${cartItemView.discount}" type="number"
                                                            groupingUsed="true" />%
                                                    </span>
                                                </c:if>
                                            </div>
                                            <div class="item-quantity-CA">
                                                <div class="quantity-box-CA">
                                                    <c:choose>
                                                        <c:when test="${cartItemView.quantity > 1}">
                                                            <a href="${pageContext.request.contextPath}/cart?action=desc&cartItemId=${cartItemView.cartItemId}"
                                                                class="qty-btn-CA">−</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${pageContext.request.contextPath}/cart?action=delete&cartItemId=${cartItemView.cartItemId}"
                                                                class="qty-btn-CA">−</a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <form action="${pageContext.request.contextPath}/cart" method="post"
                                                        style="margin: 0; display: inline-block;">
                                                        <input type="hidden" name="action" value="updateQuantity">
                                                        <input type="hidden" name="cartItemId"
                                                            value="${cartItemView.cartItemId}">
                                                        <input type="number" name="quantity" class="qty-value-CA"
                                                            value="${cartItemView.quantity}" min="1"
                                                            style="width: 40px; text-align: center; border: none; outline: none; background: transparent; font-size: 14px; font-weight: bold; -moz-appearance: textfield;"
                                                            onchange="this.form.submit()"
                                                            onkeypress="if(event.keyCode==13) { this.blur(); return false; }">
                                                    </form>
                                                    <a href="${pageContext.request.contextPath}/cart?action=asc&cartItemId=${cartItemView.cartItemId}"
                                                        class="qty-btn-CA">+</a>
                                                </div>
                                            </div>
                                            <div class="item-total-CA">
                                                <fmt:formatNumber
                                                    value="${(cartItemView.price - (cartItemView.price * cartItemView.discount / 100.0)) * cartItemView.quantity}"
                                                    type="number" groupingUsed="true" />đ
                                                <c:set var="totalPrice"
                                                    value="${totalPrice + ((cartItemView.price - (cartItemView.price * cartItemView.discount / 100.0)) * cartItemView.quantity)}">
                                                </c:set>
                                            </div>
                                            <div class="item-remove-CA">
                                                <a href="${pageContext.request.contextPath}/cart?action=delete&cartItemId=${cartItemView.cartItemId}"
                                                    class="remove-btn-CA">×</a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Tóm tắt đơn hàng -->
                                <div class="order-summary-CA">
                                    <h2 class="summary-title-CA"><i class="fas fa-file-invoice-dollar"></i> Tóm Tắt Đơn
                                        Hàng</h2>
                                    <% session.setAttribute("totalPrice", request.getAttribute("totalPrice")); %>
                                        <!-- Mã giảm giá -->
                                        <div class="promo-section-CA">
                                            <label class="promo-label-CA">Mã giảm giá</label>
                                            <div class="promo-input-wrapper-CA">
                                                <form action="VoucherCart" class="promo-form" method="get">
                                                    <input type="text" name="voucherCode" class="promo-input-CA"
                                                        placeholder="Nhập mã giảm giá" value="${voucher}">
                                                    <input type="submit" class="promo-btn-CA" value="Áp Dụng">
                                                    <input type="hidden" name="action"> <!--mặc định value là rỗng-->
                                                </form>
                                            </div>
                                        </div>
                                        <!-- Chi tiết giá -->

                                        <div class="summary-details-CA">
                                            <div class="summary-row-CA">
                                                <span class="summary-label-CA">Tạm tính:</span>
                                                <span class="summary-value-CA" id="provisionalcalculation">
                                                    <fmt:formatNumber value="${totalPrice}" type="number"
                                                        groupingUsed="true" />₫
                                                </span>
                                            </div>
                                            <div class="summary-row-CA">
                                                <span class="summary-label-CA">Giảm giá sản phẩm:</span>
                                                <span class="summary-value-CA discount">
                                                    -<fmt:formatNumber value="${discountValue}" type="number"
                                                        groupingUsed="true"></fmt:formatNumber>₫
                                                </span>
                                            </div>
                                            <div class="summary-row-CA">
                                                <span class="summary-label-CA">Phí vận chuyển:</span>
                                                <span class="summary-value-CA free">
                                                    <c:choose>
                                                        <c:when test="${totalPrice > 399000}">
                                                            Miễn Phí
                                                        </c:when>
                                                        <c:otherwise>
                                                            30.000₫
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>
                                        </div>

                                        <!-- Tổng cộng -->
                                        <div class="summary-total-CA">
                                            <span class="total-label-CA">Tổng cộng:</span>
                                            <span class="total-value-CA">
                                                <fmt:formatNumber value="${totalPriceFinal}" type="number"
                                                    groupingUsed="true"></fmt:formatNumber>₫
                                            </span>
                                        </div>

                                        <!-- Nút thanh toán -->
                                        <a href="${pageContext.request.contextPath}/order?action=checkout"
                                            class="checkout-btn-CA">ĐẶT HÀNG</a>

                                        <a href="${pageContext.request.contextPath}/home" class="continue-shopping-CA">←
                                            Tiếp tục mua sắm</a>
                        </c:if>
                        <!-- Lưu ý -->
                        <div class="checkout-note-CA">
                            Miễn phí đổi trả trong 30 ngày tại tất cả cửa hàng VINAFA
                        </div>
                    </div>
                    </div>
                    </div>
                    <% String voucherCode=request.getParameter("voucherCode"); if (voucherCode !=null) { %>
                        <!-- Popup -->
                        <div id="voucherPopup" class="popup">
                            <div class="popup-content">
                                <span id="popupMessage">
                                    <c:choose>
                                        <c:when test="${voucherNotFound != null}">
                                            ${voucherNotFound}
                                        </c:when>
                                        <c:when test="${nullVoucherCode != null}">
                                            ${nullVoucherCode}
                                        </c:when>
                                        <c:when test="${voucherExpired != null}">
                                            ${voucherExpired}
                                        </c:when>
                                        <c:when test="${voucherNotAvailable != null}">
                                            ${voucherNotAvailable}
                                        </c:when>
                                        <c:when test="${voucherEarly != null}">
                                            ${voucherEarly}
                                        </c:when>
                                        <c:when test="${minOrderValueViolation != null}">
                                            Bạn cần mua thêm <fmt:formatNumber
                                                value="${minOrderValueViolation - totalPrice}" type="number"
                                                groupingUsed="true"></fmt:formatNumber>₫
                                            Để có thể sử dụng voucher này
                                            <a id="more-product" href="${pageContext.request.contextPath}/home">Xem thêm
                                                sản phẩm tại đây</a>
                                        </c:when>
                                        <c:when test="${ExceededUsage != null}">
                                            ${ExceededUsage}
                                        </c:when>
                                        <c:otherwise>
                                            <span style="display: block; margin: 0 auto; text-align: center">Áp dụng mã
                                                thành công!</span>
                                            Bạn được giảm <fmt:formatNumber value="${discountValue}" type="number"
                                                groupingUsed="true"></fmt:formatNumber>₫
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                                <button id="closePopup" style="display: block; margin: 5px auto">OK</button>
                            </div>
                        </div>
                        <% } %>
                            <c:if test="${message != null}">
                                <div id="voucherPopup" class="popup">
                                    <div class="popup-content">
                                        <span id="popupMessage">${message}</span>
                                        <button id="closePopup" style="display: block; margin: 5px auto">OK</button>
                                    </div>
                                </div>
                            </c:if>
                </body>
                <jsp:include page="/chat-AI.jsp"></jsp:include>
                <jsp:include page="/footer.jsp"></jsp:include>
                <script src="${pageContext.request.contextPath}/js/cart.js"></script>

                </html>