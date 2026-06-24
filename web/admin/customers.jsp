<%-- 
    Document   : customers
    Created on : Jan 27, 2026, 9:10:00 PM
    Author     : dotha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "vi_VN"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>customers</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css?v=2"/>
    </head>
    <style>
        .pagination-KH a {
            padding: 6px 12px;
            border: 1px solid #ddd;
            text-decoration: none;
            margin: 2px;
        }

        .pagination-KH a.active {
            background-color: #4CAF50;
            color: white;
        }

        /* Status and Action Buttons */
        .action-cellKH {
            display: flex;
            gap: 8px;
        }

        .action-iconKH {
            width: 32px;
            height: 32px;
            border-radius: 6px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            border: none;
            transition: all 0.3s;
        }

        .action-iconKH:hover {
            transform: translateY(-2px);
        }

        .view-iconKH {
            background: #e3f2fd;
            color: #2196f3;
        }

        .lock-iconKH {
            background: #fff3e0;
            color: #ff9800;
        }

        .unlock-iconKH {
            background: #eafaf1;
            color: #2ecc71;
        }
    </style>
</head>

        <!-- Sidebar -->
        <jsp:include page="taskbar.jsp"></jsp:include>

            <div class="contentDB" id="customersPage">
                <!-- Stats Row -->
                <div class="stats-rowKH">
                    <div class="stat-boxKH">
                        <div class="stat-numberKH">${totalCustomers}</div>
                    <div class="stat-textKH">Tổng khách hàng</div>
                </div>
                <div class="stat-boxKH">
                    <div class="stat-numberKH">${newCustomers}</div>
                    <div class="stat-textKH">Khách hàng mới</div>
                </div>
                <div class="stat-boxKH">
                    <div class="stat-numberKH">${vipCustomers}</div>
                    <div class="stat-textKH">Khách hàng VIP</div>
                </div>
            </div>

            <!-- Toolbar -->
            <form action="${pageContext.request.contextPath}/customersadmin" method="post" class="toolbar-KH">

                <div class="search-boxKH">
                    <i class="fas fa-search"></i>
                    <input type="text" name="keyword"
                           value="${keyword}"
                           placeholder="Tìm kiếm khách hàng...">
                </div>

                <div class="filter-groupKH">
                    <select class="filter-selectKH" name="tier"onchange="this.form.submit()">
                        <option value="ALL" ${tier == 'ALL' ? 'selected' : ''}>Tất cả hạng thẻ</option>
                        <option value="Gold" ${tier == 'Gold' ? 'selected' : ''}>Gold</option>
                        <option value="Silver" ${tier == 'Silver' ? 'selected' : ''}>Silver</option>
                        <option value="Bronze" ${tier == 'Bronze' ? 'selected' : ''}>Bronze</option>
                        <option value="Green" ${tier == 'Green' ? 'selected' : ''}>Green</option>
                    </select>

                    <!-- FILTER SORT -->
                    <select class="filter-selectKH" name="sort"onchange="this.form.submit()">
                        <option value="none" ${sort == 'none' ? 'selected' : ''}>Mặc định</option>
                        <option value="name_asc" ${sort == 'name_asc' ? 'selected' : ''}>Tên A-Z</option>
                        <option value="name_desc" ${sort == 'name_desc' ? 'selected' : ''}>Tên Z-A</option>
                        <option value="newest" ${sort == 'newest' ? 'selected' : ''}>Mới nhất</option>
                        <option value="oldest" ${sort == 'oldest' ? 'selected' : ''}>Cũ nhất</option>
                        <option value="spending_desc" ${sort == 'spending_desc' ? 'selected' : ''}>Chi tiêu cao nhất</option>
                    </select>
                    
                    <!-- FILTER DATE -->
                    <select class="filter-selectKH" name="dateJoined" onchange="this.form.submit()">
                        <option value="ALL" ${dateJoined == 'ALL' ? 'selected' : ''}>Mọi thời gian</option>
                        <option value="today" ${dateJoined == 'today' ? 'selected' : ''}>Hôm nay</option>
                        <option value="week" ${dateJoined == 'week' ? 'selected' : ''}>Tuần này</option>
                        <option value="month" ${dateJoined == 'month' ? 'selected' : ''}>Tháng này</option>
                        <option value="lastMonth" ${dateJoined == 'lastMonth' ? 'selected' : ''}>Tháng trước</option>
                    </select>
                </div>
            </form>

            <!-- Customers Table -->
            <div class="table-containerKH">
                <table class="tableKH">
                    <thead>
                        <tr>
                            <th>Khách hàng</th>
                            <th>Số điện thoại</th>
                            <th>Hạng thẻ</th>
                            <th>Tổng đơn</th>
                            <th>Tổng chi tiêu</th>
                            <th>Điểm tích lũy</th>
                            <th>Ngày tham gia</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="c" items="${listCustomers}">
                            <tr>
                                <td>
                                    <div class="customer-infoKH">
                                        <div class="customer-avatarKH">
                                            ${fn:substring(c.fullName,0,1)}
                                        </div>
                                        <div class="customer-detailsKH">
                                            <div class="customer-nameKH">${c.fullName}</div>
                                            <div class="customer-emailKH">${c.email}</div>
                                        </div>
                                    </div>
                                </td>

                                <td>${c.phoneNumber}</td>

                                <td>
                                    <span class="tier-badgeKH
                                          ${c.tier == 'Gold' ? 'tier-goldKH' :
                                            c.tier == 'Silver' ? 'tier-silverKH' :
                                            c.tier == 'Bronze' ? 'tier-bronzeKH' :
                                            'tier-greenKH'}">
                                              ${c.tier}
                                          </span>
                                    </td>

                                    <td>${c.totalOrders}</td>
                                    <td><fmt:formatNumber value="${c.totalSpending}" type="currency" maxFractionDigits="0"/></td>
                                    <td>${c.points}</td>
                                    <td>${c.createdAt}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${c.active}">
                                                <span style="color: #27ae60; font-weight: 600;"><i class="fas fa-check-circle"></i> Hoạt động</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: #e74c3c; font-weight: 600;"><i class="fas fa-lock"></i> Bị khóa</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="action-cellKH">
                                            <button onclick="viewCustomer(${c.userId})" title="Xem chi tiết" class="action-iconKH view-iconKH">
                                                <i class="fas fa-eye"></i>
                                            </button>
                                            <c:choose>
                                                <c:when test="${c.active}">
                                                    <button type="button" title="Khóa tài khoản" onclick="openLockPopup('${c.userId}', '${c.fullName}')" class="action-iconKH lock-iconKH" style="margin: 0;">
                                                        <i class="fas fa-lock"></i>
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="${pageContext.request.contextPath}/customersadmin" method="post" style="margin: 0;">
                                                        <input type="hidden" name="action" value="unlock">
                                                        <input type="hidden" name="userId" value="${c.userId}">
                                                        <button type="submit" title="Mở khóa tài khoản" class="action-iconKH unlock-iconKH">
                                                            <i class="fas fa-unlock"></i>
                                                        </button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <div class="pagination-KH">
                        <!-- Nút Previous -->
                        <c:if test="${currentPage > 1}">
                            <a class="page-btnKH"
                               href="customersadmin?page=${currentPage - 1}&keyword=${keyword}&tier=${tier}&sort=${sort}">
                                <i class="fas fa-chevron-left"></i>
                            </a>
                        </c:if>

                        <!-- Danh sách số trang -->
                        <c:forEach begin="1" end="${totalPage}" var="i">
                            <a class="page-btnKH ${i == currentPage ? 'activeKH' : ''}"
                               href="customersadmin?page=${i}&keyword=${keyword}&tier=${tier}&sort=${sort}">
                                ${i}
                            </a>
                        </c:forEach>

                        <!-- Nút Next -->
                        <c:if test="${currentPage < totalPage}">
                            <a class="page-btnKH"
                               href="customersadmin?page=${currentPage + 1}&keyword=${keyword}&tier=${tier}&sort=${sort}">
                                <i class="fas fa-chevron-right"></i>
                            </a>
                        </c:if>

                    </div>
                    <h3 style="text-align: center">
                        Tổng số trang: ${totalPage}
                    </h3>
                </div>
            </div>

            <!-- Customer Detail Modal -->
            <div id="customerModal" class="modal" style="display:none; position:fixed; z-index:1000; left:0; top:0; width:100%; height:100%; background-color:rgba(0,0,0,0.5);">
                <div class="modal-content" style="background-color:#fefefe; margin:5% auto; padding:20px; border:1px solid #888; width:80%; max-width:800px; border-radius:8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
                    <span class="close" onclick="closeModal()" style="color:#aaa; float:right; font-size:28px; font-weight:bold; cursor:pointer;">&times;</span>
                    <h2 style="margin-top:0; color:#2c3e50;"><i class="fas fa-user-circle"></i> Chi tiết Khách hàng</h2>
                    <div id="customerModalContent">
                        <!-- AJAX content will load here -->
                        <div style="text-align: center; padding: 20px;"><i class="fas fa-spinner fa-spin fa-2x"></i> Đang tải...</div>
                    </div>
                </div>
            </div>

            <!-- lớp phủ -->
            <div class="all"></div>
            
            <!-- popup khóa tài khoản-->
            <div class="deletePopup lockCustomerPopup">
                <h2>Bạn có muốn khóa tài khoản <span class="customer_name"></span> không?</h2>
                <div class="btnConfirm">
                    <form action="${pageContext.request.contextPath}/customersadmin" method="post">
                        <input type="hidden" name="action" value="lock">
                        <input type="hidden" name="userId" value="" class="user_id">
                        <button type="submit" class="btac agreeBtn">Đồng ý</button>
                    </form>
                    <button type="button" class="btac cancelBtn" onclick="closePopup('.lockCustomerPopup')">Hủy bỏ</button>
                </div>
            </div>

            <!--popup thông báo-->
            <div class="messPopup">
                <div class="messPopup-header">Thông báo</div>
                <c:if test="${not empty sessionScope.successMessage}">
                    <h3 style="color:green"><i class="fa-regular fa-circle-check"></i> ${sessionScope.successMessage}</h3>
                </c:if>
                <c:if test="${not empty sessionScope.errorMessage}">
                    <h3 style="color:red"><i class="fa-regular fa-circle-xmark"></i> ${sessionScope.errorMessage}</h3>
                </c:if>
                <div class="btnConfirm">
                    <button type="button" class="btac agreeBtn" onclick="closePopup('.messPopup')">Đóng</button>
                </div>
            </div>

            <script>
                // Mở/Đóng popup tùy chỉnh
                let cover = document.querySelector(".all");
                function openPopup(selector) {
                    let popup = document.querySelector(selector);
                    if (popup) {
                        popup.classList.add("openPopup");
                        if(cover) cover.classList.add("cover");
                    }
                }
                function closePopup(selector) {
                    let popup = document.querySelector(selector);
                    if (popup) {
                        popup.classList.remove("openPopup");
                        if(cover) cover.classList.remove("cover");
                    }
                }

                // Khi click Khóa tài khoản
                function openLockPopup(userId, fullName) {
                    let popup = document.querySelector(".lockCustomerPopup");
                    popup.querySelector(".user_id").value = userId;
                    popup.querySelector(".customer_name").textContent = fullName;
                    openPopup(".lockCustomerPopup");
                }

                // Show notification if any on load
                window.addEventListener("load", function () {
                    <c:if test="${not empty sessionScope.successMessage}">
                        openPopup(".messPopup");
                        <c:remove var="successMessage" scope="session"/>
                    </c:if>
                    <c:if test="${not empty sessionScope.errorMessage}">
                        openPopup(".messPopup");
                        <c:remove var="errorMessage" scope="session"/>
                    </c:if>
                });


                // AJAX view customer
                function viewCustomer(userId) {
                    document.getElementById('customerModal').style.display = 'block';
                    document.getElementById('customerModalContent').innerHTML = '<div style="text-align: center; padding: 20px;"><i class="fas fa-spinner fa-spin fa-2x"></i> Đang tải...</div>';
                    
                    fetch('${pageContext.request.contextPath}/customersadmin?uid=' + userId)
                        .then(response => response.text())
                        .then(html => {
                            document.getElementById('customerModalContent').innerHTML = html;
                        })
                        .catch(err => {
                            document.getElementById('customerModalContent').innerHTML = '<div style="color:red;">Lỗi tải dữ liệu. Vui lòng thử lại sau.</div>';
                        });
                }

                function closeModal() {
                    document.getElementById('customerModal').style.display = 'none';
                }

                // Close modal when clicking outside
                window.onclick = function(event) {
                    var modal = document.getElementById('customerModal');
                    if (event.target == modal) {
                        modal.style.display = "none";
                    }
                }
            </script>
        </body> 

    </html>
