<%-- 
    Document   : dashboard
    Created on : Jan 26, 2026, 8:53:48 PM
    Author     : dotha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <fmt:setLocale value="vi_VN" />

                <!DOCTYPE html>
                <html>
                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <title>Dashboard</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css"/>
                    <script src="https://cdn.jsdelivr.net/npm/chart.js" defer></script>
                    <script src="${pageContext.request.contextPath}/js/dashboard.js" defer></script>
                </head>
                <body>
                    <!-- Sidebar -->
                    <jsp:include page="taskbar.jsp"></jsp:include>

                    <!-- Main Content -->
                    <div class="main-containerDB">
                        <!-- Header -->
                        <!--                <div class="headerDB">
                    <h1 class="page-titleDB" id="title-page">Tổng quan</h1>
                    <div class="header-actionsDB">
                        <button class="notification-btnDB">
                            <i class="fas fa-bell"></i>
                            <span class="badge-notifDB">99999999</span>
                        </button>
                        <div class="user-infoDB">
                            <div class="user-avatarDB">Lạy</div>
                            <span class="user-nameDB">Bố</span>
                        </div>
                    </div>
                </div>-->

                        <!-- Content -->
                        <div class="contentDB" id="dashboardPage">
                            <!-- Stats Cards -->
                            <div class="stats-gridDB">
                                <div class="stat-cardDB">
                                    <div class="stat-headerDB">
                                        <div>
                                            <div class="stat-valueDB">${requestScope.orderTotalMonth}</div>
                                            <div class="stat-labelDB">Tổng đơn hàng</div>
                                        </div>
                                        <div class="stat-iconDB stat-icon-blueDB">
                                            <i class="fas fa-shopping-cart"></i>
                                        </div>
                                    </div>
                                    <div class="stat-changeDB stat-change-upDB">
                                        <c:if test="${requestScope.growthOrder > 0}">
                                            <div class="pTag static_up">
                                                <i class="fas fa-arrow-up"></i>
                                                <p>
                                                    ${requestScope.growthOrder}% so với tháng trước
                                                </p>

                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.growthOrder < 0}">
                                            <div class="pTag static_down">
                                                <i class="fas fa-arrow-down"></i>
                                                <p>
                                                    ${requestScope.growthOrder}% so với tháng trước
                                                </p>
                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.growthOrder == 0}">
                                            <div class="pTag static_balance">
                                                <p>
                                                    ${requestScope.growthOrder}% so với tháng trước
                                                </p>

                                            </div>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="stat-cardDB">
                                    <div class="stat-headerDB">
                                        <div>
                                            <div class="stat-valueDB">${requestScope.totalAmountMonth}đ</div>
                                            <div class="stat-labelDB">Doanh thu</div>
                                        </div>
                                        <div class="stat-iconDB stat-icon-greenDB">
                                            <i class="fas fa-dollar-sign"></i>
                                        </div>
                                    </div>
                                    <div class="stat-changeDB stat-change-upDB">
                                        <c:if test="${requestScope.growthTotalAmount > 0}">
                                            <div class="pTag static_up">
                                                <i class="fas fa-arrow-up"></i>
                                                <p>
                                                    ${requestScope.growthTotalAmount}% so với tháng trước
                                                </p>
                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.growthTotalAmount < 0}">
                                            <div class="pTag static_down">
                                                <i class="fas fa-arrow-down"></i>
                                                <p>
                                                    ${requestScope.growthTotalAmount}% so với tháng trước
                                                </p>
                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.growthTotalAmount == 0}">
                                            <div class="pTag static_balance">
                                                <p>
                                                    ${requestScope.growthTotalAmount}% so với tháng trước
                                                </p>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="stat-cardDB">
                                    <div class="stat-headerDB">
                                        <div>
                                            <div class="stat-valueDB">${requestScope.totalProductNew}</div>
                                            <div class="stat-labelDB">Sản phẩm</div>
                                        </div>
                                        <div class="stat-iconDB stat-icon-orangeDB">
                                            <i class="fas fa-box"></i>
                                        </div>
                                    </div>
                                    <div class="stat-changeDB stat-change-upDB">
                                        <c:if test="${requestScope.newProducts > 0}">
                                            <div class="pTag static_up">
                                                <i class="fas fa-arrow-up"></i>
                                                <p>
                                                    ${requestScope.newProducts} sản phẩm mới so với tháng trước
                                                </p>
                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.newProducts < 0}">
                                            <div class="pTag static_down">
                                                <i class="fas fa-arrow-down"></i>
                                                <p>
                                                    ${requestScope.newProducts} sản phẩm mới so với tháng trước
                                                </p>
                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.newProducts == 0}">
                                            <div class="pTag static_balance">
                                                <p>
                                                    ${requestScope.newProducts} sản phẩm mới so với tháng trước
                                                </p>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="stat-cardDB">
                                    <div class="stat-headerDB">
                                        <div>
                                            <div class="stat-valueDB">${requestScope.totalUserNew}</div>
                                            <div class="stat-labelDB">Khách hàng</div>
                                        </div>
                                        <div class="stat-iconDB stat-icon-redDB">
                                            <i class="fas fa-users"></i>
                                        </div>
                                    </div>
                                    <div class="stat-changeDB stat-change-upDB">
                                        <c:if test="${requestScope.newUser > 0}">
                                            <div class="pTag static_up">
                                                <i class="fas fa-arrow-up"></i>
                                                <p>
                                                    ${requestScope.newUser} người dùng mới so với tháng trước
                                                </p>
                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.newUser < 0}">
                                            <div class="pTag static_down">
                                                <i class="fas fa-arrow-down"></i>
                                                <p>
                                                    ${requestScope.newUser} người dùng mới so với tháng trước
                                                </p>
                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.newUser == 0}">
                                            <div class="pTag static_balance">
                                                <p>
                                                    ${requestScope.newUser} người dùng mới so với tháng trước
                                                </p>

                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <!-- Charts -->
                            <div class="charts-gridDB">
                                <div class="chart-cardDB">
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 5px;">
                                        <div>
                                            <h3 class="chart-titleDB">Doanh thu theo tháng ${selectedYear}</h3>
                                            <p class="chart-subtitleDB">Biểu đồ cột doanh thu các tháng trong năm</p>
                                        </div>
                                        <form action="generaladmin" method="get">
                                <select name="year" onchange="this.form.submit()" style="padding: 6px 10px; border-radius: 6px; border: 1px solid #ced4da; font-size: 14px; cursor: pointer; outline: none;">
                                                <c:forEach items="${listYears}" var="y">
                                        <option value="${y}" ${y == selectedYear ? 'selected' : ''}>Năm ${y}</option>
                                                </c:forEach>
                                            </select>
                                        </form>
                                    </div>
                                    <div class="chart-containerDB">
                                        <canvas id="revenueChart"></canvas>
                                    </div>
                                </div>

                                <div class="chart-cardDB">
                                    <h3 class="chart-titleDB">Đơn hàng theo trạng thái</h3>
                                    <div class="chart-containerDB">
                                        <canvas id="statusChart"></canvas>
                                    </div>
                                </div>
                            </div>

                            <!-- Recent Orders Table -->
                            <div class="table-cardDB">
                                <div class="table-headerDB">
                                    <h3 class="table-titleDB">Đơn hàng gần đây</h3>
                                    <!--<button class="view-all-btnDB">Xem tất cả</button>-->
                                </div>

                                <table class="tableDB">
                                    <thead>
                                        <tr>
                                            <th>Mã đơn</th>
                                            <th>Khách hàng</th>
                                            <th>Tổng tiền gốc</th>
                                            <th>Phí ship</th>
                                            <th>Voucher</th>
                                            <th>Thực thu</th>
                                            <th>Trạng thái</th>
                                            <th>Ngày đặt</th>
                                            <th>Chi tiết</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.listRecentOrder}" var="recentOrder">
                                            <tr>
                                                <td>#ORD${recentOrder.orderId}</td>
                                                <td>${recentOrder.fullName}</td>
                                                
                                                <c:set var="subtotalDB" value="0" />
                                                <c:forEach items="${recentOrder.items}" var="item">
                                                    <c:set var="subtotalDB" value="${subtotalDB + item.price * item.quantity}" />
                                                </c:forEach>
                                                <c:set var="shipFeeDB" value="${subtotalDB < 399000 ? 30000 : 0}" />
                                                
                                                <td>
                                                    <span class="subtotal-amountDH">
                                                        <fmt:formatNumber value="${subtotalDB}" type="currency" maxFractionDigits="0" />
                                                    </span>
                                                </td>
                                                <td>
                                                    <span class="ship-feeDH ${shipFeeDB == 0 ? 'free-shipDH' : ''}">
                                                        <c:choose>
                                                            <c:when test="${shipFeeDB == 0}">Freeship</c:when>
                                                            <c:otherwise>+<fmt:formatNumber value="${shipFeeDB}" type="currency" maxFractionDigits="0" /></c:otherwise>
                                                        </c:choose>
                                                    </span>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty recentOrder.voucher}">
                                                            <span class="voucher-badgeDH">${recentOrder.voucher.code}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="no-voucherDH">-</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <span class="total-amountDH">
                                                        <fmt:formatNumber value="${recentOrder.totalAmount}" type="currency" maxFractionDigits="0" />
                                                    </span>
                                                </td>
                                                
                                                <c:if test="${recentOrder.status == 'Chờ xác nhận'}">
                                                    <td><span class="status-badgeDB status-pendingDB">Chờ xác nhận</span></td>
                                                </c:if>
                                                <c:if test="${recentOrder.status == 'Hoàn thành'}">
                                                    <td><span class="status-badgeDB status-completedDB">Hoàn thành</span></td>
                                                </c:if>
                                                <c:if test="${recentOrder.status == 'Đang giao'}">
                                                    <td><span class="status-badgeDB status-shippingDB">Đang giao</span></td>
                                                </c:if>
                                                <c:if test="${recentOrder.status == 'Đã hủy'}">
                                                    <td><span class="status-badgeDB status-cancelledDB">Đã hủy</span></td> 
                                                </c:if>
                                                <td>${recentOrder.orderDateFormat}</td>
                                                <td>
                                                    <button class="action-btnDB btn-viewDB" onclick="showOrderItems('${recentOrder.orderId}')"><i class="fas fa-eye"></i></button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!--popup cho phần xem-->
                    <div class="popupOrder">
                        <div class="frame_overflow">
                            <!--.-->
                        </div>
            <button type="button" onclick="closePopup('.popupOrder')" id="closeBtn"><i class="fa-solid fa-xmark"></i></button>
                    </div>
                    </div>
                    <div class="all"></div>

                    <script>
            document.addEventListener('DOMContentLoaded', function() {
                            // Monthly Revenue Chart
                            const revenueCtx = document.getElementById('revenueChart').getContext('2d');
                            const revenueData = [
                                <c:forEach items="${requestScope.monthlyRevenue}" var="rev" varStatus="status">
                                    ${rev}${!status.last ? ',' : ''}
                                </c:forEach>
                            ];

                            new Chart(revenueCtx, {
                                type: 'bar',
                                data: {
                                    labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
                                    datasets: [{
                                        label: 'Doanh thu (đ)',
                                        data: revenueData,
                                        backgroundColor: '#e63946',
                                        borderRadius: 5
                                    }]
                                },
                                options: {
                                    responsive: true,
                                    maintainAspectRatio: false,
                                    scales: {
                                        y: {
                                            beginAtZero: true,
                                            ticks: {
                                    callback: function(value) {
                                                    return value.toLocaleString('vi-VN') + 'đ';
                                                }
                                            }
                                        }
                                    },
                                    plugins: {
                                        legend: {
                                            display: false
                                        }
                                    }
                                }
                            });

                            // Order Status Chart
                            const statusCtx = document.getElementById('statusChart').getContext('2d');
                            const statusData = [
                                <c:forEach items="${requestScope.orderStatusCounts}" var="count" varStatus="status">
                                    ${count}${!status.last ? ',' : ''}
                                </c:forEach>
                            ];

                            new Chart(statusCtx, {
                                type: 'doughnut',
                                data: {
                                    labels: ['Hoàn thành', 'Chờ xác nhận', 'Đang giao', 'Đã hủy'],
                                    datasets: [{
                                        data: statusData,
                                        backgroundColor: ['#2ecc71', '#f1c40f', '#3498db', '#e74c3c'],
                                    }]
                                },
                                options: {
                                    responsive: true,
                                    maintainAspectRatio: false,
                                    plugins: {
                                        legend: {
                                            position: 'bottom'
                                        }
                                    }
                                }
                            });
                        });
                    </script>
                </body>
                </html>