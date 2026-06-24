<%-- Document : order-management Created on : Feb 7, 2026, 4:34:55 PM Author : dotha --%>

    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                        <fmt:setLocale value="vi_VN" />
                        <!DOCTYPE html>
                        <html>

                        <head>
                            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                            <title>ORDER MANAGERMENT</title>
                            <link rel="stylesheet"
                                href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
                            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
                            <link rel="stylesheet"
                                href="${pageContext.request.contextPath}/css/order-management.css?v=5">
                            <script src="${pageContext.request.contextPath}/js/order-management.js?v=5" defer></script>
                            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
                        </head>

                        <body>
                            <jsp:include page="taskbar.jsp"></jsp:include>
                            <div id="order-manager">
                                <h1 class="page-titleDH"><i class="fas fa-shopping-cart"></i> Quản lý Đơn Hàng</h1>

                                <!-- Stats -->
                                <div class="stats-rowDH">
                                    <div class="stat-boxDH">
                                        <div class="stat-headerDH">
                                            <div>
                                                <div class="stat-numberDH">${totalOrdersMonth}</div>
                                                <div class="stat-textDH">Tổng đơn hàng</div>
                                                <div class="stat-changeDH">

                                                    <c:if test="${growthOrders > 0}">
                                                        <div class="text-success">
                                                            <i class="fas fa-arrow-up"></i>
                                                            ${growthOrders}% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                    <c:if test="${growthOrders < 0}">
                                                        <div class="text-danger">
                                                            <i class="fas fa-arrow-down"></i>
                                                            ${growthOrders}% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                    <c:if test="${growthOrders == 0}">
                                                        <div class="text-secondary">
                                                            <i class="fas fa-minus"></i>
                                                            0% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                </div>
                                            </div>
                                            <div class="stat-iconDH"
                                                style="background: linear-gradient(135deg, #667eea, #764ba2);">
                                                <i class="fas fa-shopping-cart"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="stat-boxDH">
                                        <div class="stat-headerDH">
                                            <div>
                                                <div class="stat-numberDH">
                                                    <fmt:formatNumber value="${revenueMonth}"></fmt:formatNumber>₫
                                                </div>
                                                <div class="stat-textDH">Doanh thu tháng này</div>
                                                <div class="stat-changeDH">

                                                    <c:if test="${growthRevenue > 0}">
                                                        <div class="text-success">
                                                            <i class="fas fa-arrow-up"></i>
                                                            ${growthRevenue}% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                    <c:if test="${growthRevenue < 0}">
                                                        <div class="text-danger">
                                                            <i class="fas fa-arrow-down"></i>
                                                            ${growthRevenue}% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                    <c:if test="${growthRevenue == 0}">
                                                        <div class="text-secondary">
                                                            <i class="fas fa-minus"></i>
                                                            0% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                </div>
                                            </div>
                                            <div class="stat-iconDH"
                                                style="background: linear-gradient(135deg, #f093fb, #f5576c);">
                                                <i class="fas fa-dollar-sign"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="stat-boxDH">
                                        <div class="stat-headerDH">
                                            <div>
                                                <div class="stat-numberDH">${pendingMonth}</div>
                                                <div class="stat-textDH">Đơn chờ xác nhận </div>
                                                <div class="stat-changeDH">

                                                    <c:if test="${growthPending > 0}">
                                                        <div class="text-success">
                                                            <i class="fas fa-arrow-up"></i>
                                                            ${growthPending}% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                    <c:if test="${growthPending < 0}">
                                                        <div class="text-danger">
                                                            <i class="fas fa-arrow-down"></i>
                                                            ${growthPending}% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                    <c:if test="${growthPending == 0}">
                                                        <div class="text-secondary">
                                                            <i class="fas fa-minus"></i>
                                                            0% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                </div>
                                            </div>
                                            <div class="stat-iconDH"
                                                style="background: linear-gradient(135deg, #4facfe, #00f2fe);">
                                                <i class="fas fa-clock"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="stat-boxDH">
                                        <div class="stat-headerDH">
                                            <div>
                                                <div class="stat-numberDH"> ${completedMonth}</div>
                                                <div class="stat-textDH">Đơn hoàn thành</div>
                                                <div class="stat-changeDH">

                                                    <c:if test="${growthCompleted > 0}">
                                                        <div class="text-success">
                                                            <i class="fas fa-arrow-up"></i>
                                                            ${growthCompleted}% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                    <c:if test="${growthCompleted < 0}">
                                                        <div class="text-danger">
                                                            <i class="fas fa-arrow-down"></i>
                                                            ${growthCompleted}% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                    <c:if test="${growthCompleted == 0}">
                                                        <div class="text-secondary">
                                                            <i class="fas fa-minus"></i>
                                                            0% so với tháng trước
                                                        </div>
                                                    </c:if>

                                                </div>
                                            </div>
                                            <div class="stat-iconDH"
                                                style="background: linear-gradient(135deg, #43e97b, #38f9d7);">
                                                <i class="fas fa-check-circle"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Toolbar -->
                                <form action="ordermanagement" method="get" class="toolbar-DH">
                                    <!-- SEARCH FORM -->
                                    <div class="search-boxDH">
                                        <i class="fas fa-search"></i>
                                        <input type="text" name="keyword"
                                            placeholder="Tìm kiếm đơn hàng theo mã, tên khách hàng, SĐT..."
                                            value="${keyword}">

                                    </div>
                                    <!-- FILTER FORM -->
                                    <div class="filter-groupDH">
                                        <select class="filter-selectDH" name="status" onchange="this.form.submit()">
                                            <option value="ALL" ${status==null || status=='ALL' ? 'selected' : '' }>
                                                Tất cả trạng thái
                                            </option>

                                            <option value="Chờ xác nhận" ${status=='Chờ xác nhận' ? 'selected' : '' }>
                                                Chờ xác nhận
                                            </option>

                                            <option value="Đang giao" ${status=='Đang giao' ? 'selected' : '' }>
                                                Đang giao
                                            </option>

                                            <option value="Hoàn thành" ${status=='Hoàn thành' ? 'selected' : '' }>
                                                Hoàn thành
                                            </option>

                                            <option value="Đã hủy" ${status=='Đã hủy' ? 'selected' : '' }>
                                                Đã hủy
                                            </option>
                                        </select>
                                        <select class="filter-selectDH" name="dateFilter" onchange="this.form.submit()">
                                            <option value="ALL" ${dateFilter==null || dateFilter=='ALL' ? 'selected'
                                                : '' }>
                                                Tất cả thời gian
                                            </option>

                                            <option value="today" ${dateFilter=='today' ? 'selected' : '' }>
                                                Hôm nay
                                            </option>

                                            <option value="week" ${dateFilter=='week' ? 'selected' : '' }>
                                                Tuần này
                                            </option>

                                            <option value="month" ${dateFilter=='month' ? 'selected' : '' }>
                                                Tháng này
                                            </option>
                                            <option value="lastMonth" ${dateFilter=='lastMonth' ? 'selected' : '' }>
                                                Tháng trước
                                            </option>
                                        </select>
                                    </div>
                                </form>
                                <!-- Table -->
                                <div class="table-containerDH">
                                    <table class="tableDH">
                                        <thead>
                                            <tr>
                                                <th>Mã đơn</th>
                                                <th>Khách hàng</th>
                                                <th>Sản phẩm</th>
                                                <th>Tổng tiền gốc</th>
                                                <th>Phí ship</th>
                                                <th>Voucher</th>
                                                <th>Thực thu</th>
                                                <th>Trạng thái</th>
                                                <th>Ngày đặt</th>
                                                <th>Thao tác</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${data}" var="o">
                                                <tr>
                                                    <td><span class="order-idDH">#ORD${o.orderId}</span></td>
                                                    <td>
                                                        <div class="customer-infoDH">
                                                            <div class="customer-avatarDH">
                                                                ${fn:substring(o.fullName,0,1)}</div>
                                                            <div class="customer-detailsDH">
                                                                <div class="customer-nameDH">${o.fullName}</div>
                                                                <div class="customer-phoneDH">${o.phoneNumber}</div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div class="products-listDH">
                                                            <c:forEach items="${o.items}" var="i">
                                                                <div class="product-itemDH">
                                                                    ${i.productVariant.product.productName}
                                                                    <span class="product-qtyDH">x${i.quantity}</span>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </td>
                                                    <c:set var="subtotal" value="0" />
                                                    <c:forEach items="${o.items}" var="item">
                                                        <c:set var="subtotal" value="${subtotal + item.price * item.quantity}" />
                                                    </c:forEach>
                                                    <c:set var="shipFeeRow" value="${subtotal < 399000 ? 30000 : 0}" />
                                                    
                                                    <td>
                                                        <span class="subtotal-amountDH">
                                                            <fmt:formatNumber value="${subtotal}" type="currency" maxFractionDigits="0" />
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <span class="ship-feeDH ${shipFeeRow == 0 ? 'free-shipDH' : ''}">
                                                            <c:choose>
                                                                <c:when test="${shipFeeRow == 0}">Freeship</c:when>
                                                                <c:otherwise>+<fmt:formatNumber value="${shipFeeRow}" type="currency" maxFractionDigits="0" /></c:otherwise>
                                                            </c:choose>
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty o.voucher}">
                                                                <span class="voucher-badgeDH">${o.voucher.code}</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="no-voucherDH">-</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <span class="total-amountDH">
                                                            <fmt:formatNumber value="${o.totalAmount}" type="currency" maxFractionDigits="0" />
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${o.status == 'Chờ xác nhận'}">
                                                                <span class="status-badgeDH status-pendingDH">
                                                                    Chờ xác nhận
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${o.status == 'Đang giao'}">
                                                                <span class="status-badgeDH status-shippingDH">
                                                                    Đang giao
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${o.status == 'Hoàn thành'}">
                                                                <span class="status-badgeDH status-completedDH">
                                                                    Hoàn thành
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${o.status == 'Đã hủy'}">
                                                                <span class="status-badgeDH status-cancelledDH">
                                                                    Đã huỷ
                                                                </span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="status-badgeDH">
                                                                    ${o.status}
                                                                </span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${o.orderDateFormat}</td>
                                                    <td>
                                                        <div class="action-cellDH">
                                                            <button class="action-iconDH view-iconDH"
                                                                title="Xem chi tiết"
                                                                onclick="showOrderItems1('${o.orderId}')">
                                                                <i class="fas fa-eye"></i>
                                                            </button>
                                                            <form method="post"
                                                                action="${pageContext.request.contextPath}/ordermanagement"
                                                                style="display:inline;">
                                                                <input type="hidden" name="action" value="update">
                                                                <input type="hidden" name="orderId"
                                                                    value="${o.orderId}">
                                                                <input type="hidden" name="customerName"
                                                                    value="${o.fullName}">
                                                                <input type="hidden" name="phone"
                                                                    value="${o.phoneNumber}">
                                                                <input type="hidden" name="address"
                                                                    value="${o.shippingAddress}">
                                                                <select name="status" onchange="window.confirmStatusChange(this, '${o.status}')"
                                                                    class="status-selectDH" title="Đổi trạng thái">
                                                                    <option value="Chờ xác nhận"
                                                                        ${o.status=='Chờ xác nhận' ? 'selected' : '' }>
                                                                        Chờ xác nhận</option>
                                                                    <option value="Đang giao" ${o.status=='Đang giao'
                                                                        ? 'selected' : '' }>Đang giao</option>
                                                                    <option value="Hoàn thành" ${o.status=='Hoàn thành'
                                                                        ? 'selected' : '' }>Hoàn thành</option>
                                                                    <option value="Đã hủy" ${o.status=='Đã hủy'
                                                                        ? 'selected' : '' }>Đã hủy</option>
                                                                </select>
                                                            </form>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <!-- Pagination -->
                                    <div class="pagination-DH">

                                        <c:if test="${startPage > 1}">
                                            <a href="${pageContext.request.contextPath}/ordermanagement?page=${startPage - 1}&keyword=${keyword}&status=${status}&dateFilter=${dateFilter}"
                                                class="page-btnDH">
                                                <i class="fas fa-chevron-left"></i>
                                            </a>
                                        </c:if>

                                        <c:forEach begin="${startPage}" end="${endPage}" var="i">
                                            <a href="${pageContext.request.contextPath}/ordermanagement?page=${i}&keyword=${keyword}&status=${status}&dateFilter=${dateFilter}"
                                                class="page-btnDH ${i == page ? 'activeDH' : ''}">
                                                ${i}
                                            </a>
                                        </c:forEach>

                                        <c:if test="${endPage < totalPage}">
                                            <a href="${pageContext.request.contextPath}/ordermanagement?page=${endPage + 1}&keyword=${keyword}&status=${status}&dateFilter=${dateFilter}"
                                                class="page-btnDH">
                                                <i class="fas fa-chevron-right"></i>
                                            </a>
                                        </c:if>

                                    </div>
                                </div>
                                <h3 style="text-align: center">
                                    Tổng số trang: ${totalPage}
                                </h3>
                            </div>
                            <!--popup cho phần xem-->
                            <div class="popupOrder">
                                <div class="frame_overflow">
                                    <!-- AJAX sẽ inject vào đây -->
                                </div>
                                <button type="button" onclick="closePopup('.popupOrder')" id="closeBtn">
                                    <i class="fa-solid fa-xmark"></i>
                                </button>
                            </div>
                            <!-- Modal sửa đơn đã được thay bằng Dropdown Inline -->

                            <!--popup thông báo-->
                            <div class="messPopup">
                                <div class="messPopup-header">Thông báo</div>
                                <c:if test="${not empty sessionScope.successMessage}">
                                    <h3 style="color:green"><i class="fa-regular fa-circle-check"></i>
                                        ${sessionScope.successMessage}</h3>
                                </c:if>
                                <c:if test="${not empty sessionScope.errorMessage}">
                                    <h3 style="color:red"><i class="fa-regular fa-circle-xmark"></i>
                                        ${sessionScope.errorMessage}</h3>
                                </c:if>
                                <div class="btnConfirm">
                                    <button type="button" class="btac agreeBtn"
                                        onclick="closePopup('.messPopup')">Đóng</button>
                                </div>
                            </div>
                            <script>
                                function openPopup(selector) {
                                    let action = document.querySelector(selector);
                                    if (action) action.classList.add("openPopup");
                                    let overlay = document.querySelector(".all");
                                    if (overlay) overlay.classList.add("cover");
                                }

                                function closePopup(selector) {
                                    let action = document.querySelector(selector);
                                    if (action) action.classList.remove("openPopup");
                                    let overlay = document.querySelector(".all");
                                    if (overlay) overlay.classList.remove("cover");
                                }

                                window.addEventListener("load", function () {
                <c:if test="${not empty sessionScope.successMessage}">
                    openPopup(".messPopup");
                </c:if>
                <c:if test="${not empty sessionScope.errorMessage}">
                    openPopup(".messPopup");
                </c:if>
                                });
                            </script>

                            <c:if test="${not empty sessionScope.successMessage}">
                                <c:remove var="successMessage" scope="session" />
                            </c:if>
                            <c:if test="${not empty sessionScope.errorMessage}">
                                <c:remove var="errorMessage" scope="session" />
                            </c:if>
                            <div class="all"></div>
                            <!--</body>-->

                        </html>