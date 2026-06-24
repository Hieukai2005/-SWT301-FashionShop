<%-- 
    Document   : discount-code
    Created on : Jan 27, 2026, 9:25:40 PM
    Author     : dotha
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css"/>
    </head>
    <!-- Coupons Page -->
    <body>
        <jsp:include page="/admin/taskbar.jsp"></jsp:include>
            <div class="contentDB" id="couponsPage">
                <!-- Stats Row -->
                <div class="stats-rowCP">
                    <div class="stat-boxCP">
                        <div class="stat-numberCP">${numberOfVoucherSession}</div>
                    <div class="stat-textCP">Tổng mã giảm giá</div>
                </div>
                <div class="stat-boxCP">
                    <div class="stat-numberCP">${currentlyOperating}</div>
                    <div class="stat-textCP">Đang hoạt động</div>
                </div>
                <div class="stat-boxCP">
                    <div class="stat-numberCP">${notYet}</div>
                    <div class="stat-textCP">Chưa diễn ra</div>
                </div>
                <div class="stat-boxCP">
                    <div class="stat-numberCP">${notActive}</div>
                    <div class="stat-textCP">Chưa Active</div>
                </div>
                <div class="stat-boxCP">
                    <div class="stat-numberCP">${expired}</div>
                    <div class="stat-textCP">Hết hạn</div>
                </div>
                <div class="stat-boxCP">
                    <div class="stat-numberCP">${percent}</div>
                    <div class="stat-textCP">Giảm theo % sản phẩm</div>
                </div>
                <div class="stat-boxCP">
                    <div class="stat-numberCP">${fixed}</div>
                    <div class="stat-textCP">Giảm giá cố định</div>
                </div>
                <div class="stat-boxCP">
                    <div class="stat-numberCP">${numberVoucherUsed}</div>
                    <div class="stat-textCP">Lượt sử dụng</div>
                </div>
            </div>

            <!-- Toolbar -->
            <div class="toolbar-CP">
                <form action="voucheradmin" method="post">
                    <div class="search-boxCP">
                        <input type="text" name="searchValue" value="${searchValue == null ? '' : searchValue}" placeholder="Tìm kiếm mã giảm giá...">
                        <button type="submit" id="searchBtn"><i class="fas fa-search"></i></button>
                    </div>
                    <input type="hidden" name="status" value="${status == null ? '' : status}">
                    <input type="hidden" name="typeOfDiscount" value="${typeOfDiscount == null ? '' : typeOfDiscount}">
                </form>

                <div class="filter-groupCP">
                    <form action="voucheradmin" method="post">
                        <select class="filter-selectCP" name="status" onchange="this.closest('form').submit()">
                            <option value="allStatus" ${status == 'allStatus' ? 'selected' : ''}>Tất cả trạng thái</option>
                            <option value="currentlyOperating" ${status == 'currentlyOperating' ? 'selected' : ''}>Đang hoạt động</option>
                            <option value="expired" ${status == 'expired' ? 'selected' : ''}>Đã hết hạn</option>
                            <option value="notYet" ${status == 'notYet' ? 'selected' : ''}>Chưa diễn ra</option>
                            <option value="notActive" ${status == 'notActive' ? 'selected' : ''}>Chưa Active</option>
                        </select>
                        <input type="hidden" name="searchValue" value="${searchValue == null ? '' : searchValue}">
                        <input type="hidden" name="typeOfDiscount" value="${typeOfDiscount == null ? '' : typeOfDiscount}">
                    </form>

                    <form action="voucheradmin" method="post">
                        <select class="filter-selectCP" name="typeOfDiscount" onchange="this.closest('form').submit()">
                            <option value="allDiscount" ${typeOfDiscount == 'allDiscount' ? 'selected' : ''}>Loại giảm giá</option>
                            <option value="percent" ${typeOfDiscount == 'percent' ? 'selected' : ''}>Phần trăm</option>
                            <option value="fixed" ${typeOfDiscount == 'fixed' ? 'selected' : ''}>Số tiền cố định</option>
                        </select>
                        <input type="hidden" name="searchValue" value="${searchValue == null ? '' : searchValue}">
                        <input type="hidden" name="status" value="${status == null ? '' : status}">
                    </form>
                    <button type="button" class="add-btnCP" onclick="openAddVoucherPopup()">
                        <i class="fas fa-plus"></i>
                        Tạo mã mới
                    </button>
                </div>
            </div>

            <!-- Coupons Grid -->
            <div class="coupons-gridCP">
                <c:if test="${emptyVoucherList != null}">
                    <h2 style="color: red; text-align: center; display: block;">${emptyVoucherList}</h2>
                </c:if>
                <c:if test="${emptyVoucherList == null}">
                    <c:forEach items="${listVouchers}" var="voucher">
                        <div class="coupon-cardCP" data-voucher-id="${voucher.voucherId}" data-discount-type="${voucher.discountType}" data-max-discount="${voucher.maxDiscount}" data-quantity="${voucher.quantity}" data-start-date="${voucher.startDate}" data-end-date="${voucher.endDate}" data-min-order="${voucher.minOrderValue}" coupon-cardCP data-discount-value="${voucher.discountValue}" data-active="${voucher.isActive}" data-thanh="${123}">
                            <div class="coupon-headerCP">
                                <div class="coupon-codeCP">${voucher.code}</div>
                                <div class="coupon-nameCP">Mã Voucher ${voucher.voucherId}</div>
                            </div>
                            <div class="coupon-bodyCP">
                                <div class="coupon-detailCP">
                                    <div class="coupon-iconCP">
                                        <c:if test="${voucher.discountType == 'percent'}">
                                            <i class="fas fa-percentage"></i>
                                        </c:if>
                                        <c:if test="${voucher.discountType == 'fixed'}">
                                            <i class="fas fa-gift"></i>
                                        </c:if>
                                    </div>
                                    <span class="coupon-labelCP">Giảm giá:</span>
                                    <c:choose>
                                        <c:when test="${voucher.discountType == 'percent'}">
                                            <span class="coupon-valueCP">${voucher.discountValue}%</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="coupon-valueCP">
                                                <fmt:formatNumber value="${voucher.discountValue}" type="number" groupingUsed="true"></fmt:formatNumber>đ
                                                </span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="coupon-detailCP">
                                    <div class="coupon-iconCP">
                                        <i class="fas fa-shopping-cart"></i>
                                    </div>
                                    <span class="coupon-labelCP">Đơn tối thiểu:</span>
                                    <span class="coupon-valueCP">
                                        <fmt:formatNumber value="${voucher.minOrderValue}" type="number" groupingUsed="true"></fmt:formatNumber>₫
                                        </span>
                                    </div>
                                    <div class="coupon-detailCP">
                                        <div class="coupon-iconCP">
                                            <i class="fas fa-calendar"></i>
                                        </div>
                                        <span class="coupon-labelCP">Diễn ra - Hết hạn:</span>
                                        <span class="coupon-valueCP">${voucher.startDateFormat}-${voucher.endDateFormat}</span>
                                </div>
                                <div class="coupon-detailCP">
                                    <div class="coupon-iconCP">
                                        <i class="fas fa-users"></i>
                                    </div>
                                    <span class="coupon-labelCP">Đã dùng:</span>
                                    <span class="coupon-valueCP">${voucher.uesedCount}/${voucher.quantity}</span>
                                </div>
                                <div class="coupon-actionsCP">
                                    <button class="action-btn-CP btn-editCP editVoucherButton" onclick="editVoucher(this)" type="button">
                                        <i class="fas fa-edit"></i> Sửa
                                    </button>
                                    <form action="voucheradmin" method="post">
                                        <button class="action-btn-CP btn-deleteCP" type="submit" name="action" value="deleteVoucher">
                                            <i class="fas fa-trash"></i> Xóa
                                        </button>
                                        <input type="hidden" name="id" value="${voucher.voucherId}">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>

            <!-- Pagination -->
            <c:if test="${emptyVoucherList == null}">
                <div class="pagination-SP">
                    <c:if test="${startPage > 1}">
                        <a class="page-btnSP" href="${pageContext.request.contextPath}/voucheradmin?page=${startPage - 1}&searchValue=${searchValue}&status=${status}&typeOfDiscount=${typeOfDiscount}">
                            <i class="fas fa-chevron-left"></i>
                        </a>
                    </c:if>
                    <c:forEach begin="${startPage}" end="${endPage}" var="no">
                        <a href="${pageContext.request.contextPath}/voucheradmin?page=${no}&searchValue=${searchValue}&status=${status}&typeOfDiscount=${typeOfDiscount}" class="page-btnSP paging ${no == page ? 'active' : ''}">
                            ${no}
                        </a>
                    </c:forEach>
                    <c:if test="${endPage < totalOfPage}">
                        <a class="page-btnSP" href="${pageContext.request.contextPath}/voucheradmin?page=${endPage + 1}&searchValue=${searchValue}&status=${status}&typeOfDiscount=${typeOfDiscount}">
                            <i class="fas fa-chevron-right"></i>
                        </a>
                    </c:if>
                </div>
                <h3 style="text-align: center">Tổng số trang: ${totalOfPage}</h3>
            </c:if>
        </div>
        <c:if test="${not empty voucherMessgae}">
            <div class="messPopup" id="popupMS">
                <div class="messPopup-header">Thông báo</div>
                <c:choose>
                    <c:when test="${fn:containsIgnoreCase(voucherMessgae, 'thất bại') || fn:containsIgnoreCase(voucherMessgae, 'lỗi') || fn:containsIgnoreCase(voucherMessgae, 'tồn tại')}">
                        <h3 style="color:red"><i class="fa-regular fa-circle-xmark"></i> ${voucherMessgae}</h3>
                    </c:when>
                    <c:otherwise>
                        <h3 style="color:green"><i class="fa-regular fa-circle-check"></i> ${voucherMessgae}</h3>
                    </c:otherwise>
                </c:choose>
                <div class="btnConfirm">
                    <button type="button" class="btac agreeBtn" onclick="closePopup('#popupMS')">Đóng</button>
                </div>
            </div>
            <script>
                function openPopup(selector) {
                    let action = document.querySelector(selector);
                    if(action) action.classList.add("openPopup");
                    let overlay = document.querySelector(".all");
                    if (overlay) overlay.classList.add("cover");
                }
                function closePopup(selector) {
                    let action = document.querySelector(selector);
                    if(action) action.classList.remove("openPopup");
                    let overlay = document.querySelector(".all");
                    if (overlay) overlay.classList.remove("cover");
                }
                document.addEventListener('DOMContentLoaded', function() {
                    openPopup('#popupMS');
                });
            </script>
        </c:if>

        <!--popup thêm, sửa voucher-->
        <div class="overlay">
            <div class="popup voucher-popup-VC">
                <div class="popup-header-VC">
                    <i class="fas fa-ticket-alt"></i>
                    <h2 id="popupTitle">Thêm Voucher Mới</h2>
                </div>
                <form action="voucheradmin" method="post" id="voucherForm" onsubmit="return validateVoucherForm()">
                    <div class="form-body-VC">
                        <div class="form-group-VC">
                            <label><i class="fas fa-barcode"></i> Mã Voucher</label>
                            <input type="text" name="code" placeholder="VD: SALE2026" required>
                        </div>
                        <div class="form-row-VC">
                            <div class="form-group-VC">
                                <label><i class="fas fa-tags"></i> Loại giảm giá</label>
                                <select name="discount_type" id="discountTypeSelect" onchange="toggleMaxDiscount()">
                                    <option value="percent">Phần trăm (%)</option>
                                    <option value="fixed">Số tiền cố định</option>
                                </select>
                            </div>
                            <div class="form-group-VC">
                                <label><i class="fas fa-coins"></i> <span id="discountValueLabel">Giá trị giảm (%)</span></label>
                                <input type="number" step="0.01" name="discount_value" id="discountValueInput" min="0" placeholder="VD: 20" required>
                            </div>
                        </div>
                        <div class="form-row-VC">
                            <div class="form-group-VC">
                                <label><i class="fas fa-shopping-cart"></i> Đơn tối thiểu (₫)</label>
                                <input type="number" step="0.01" name="min_order_value" placeholder="VD: 200000" required>
                            </div>
                            <div class="form-group-VC" id="max-discount">
                                <label><i class="fas fa-hand-holding-usd"></i> Giảm tối đa (₫)</label>
                                <input type="number" step="0.01" name="max_discount" placeholder="VD: 50000">
                            </div>
                        </div>
                        <div class="form-group-VC">
                            <label><i class="fas fa-layer-group"></i> Số lượng</label>
                            <input type="number" name="quantity" placeholder="VD: 100" required>
                        </div>
                        <div class="form-row-VC">
                            <div class="form-group-VC">
                                <label><i class="fas fa-calendar-plus"></i> Ngày bắt đầu</label>
                                <input type="datetime-local" name="start_date" step="1" required>
                            </div>
                            <div class="form-group-VC">
                                <label><i class="fas fa-calendar-minus"></i> Ngày kết thúc</label>
                                <input type="datetime-local" name="end_date" step="1" required>
                            </div>
                        </div>
                        <div class="form-group-VC checkbox-VC">
                            <label class="toggle-switch-VC">
                                <input type="checkbox" name="is_active" value="1">
                                <span class="toggle-slider-VC"></span>
                            </label>
                            <span class="toggle-label-VC">Kích hoạt voucher</span>
                        </div>
                    </div>
                    <div class="form-actions-VC">
                        <button type="button" class="btn-cancel-VC" onclick="closeVoucherPopup()">
                            <i class="fas fa-times"></i> Huỷ
                        </button>
                        <button type="submit" class="btn-save-VC" id="saveVoucher">
                            <i class="fas fa-save"></i> Lưu
                        </button>
                        <input type="hidden" name="action" id="action">
                        <input type="hidden" name="voucher_id">
                    </div>
                </form>
            </div>
        </div>
        <div class="all"></div>
    </body>
    <script>
        // Toggle hiển thị trường "Giảm tối đa" dựa trên loại giảm giá
        function toggleMaxDiscount() {
            var discountType = document.getElementById('discountTypeSelect').value;
            var maxDiscountGroup = document.getElementById('max-discount');
            var discountValueLabel = document.getElementById('discountValueLabel');
            var discountValueInput = document.getElementById('discountValueInput');
            
            if (discountType === 'fixed') {
                maxDiscountGroup.style.display = 'none';
                maxDiscountGroup.querySelector('input').value = '';
                discountValueLabel.textContent = 'Giá trị giảm (₫)';
                discountValueInput.removeAttribute('max');
                discountValueInput.placeholder = 'VD: 50000';
            } else {
                maxDiscountGroup.style.display = '';
                discountValueLabel.textContent = 'Giá trị giảm (%)';
                discountValueInput.setAttribute('max', '100');
                discountValueInput.placeholder = 'VD: 20';
            }
        }

        // Validate form trước khi submit
        function validateVoucherForm() {
            var discountType = document.getElementById('discountTypeSelect').value;
            var discountValue = parseFloat(document.getElementById('discountValueInput').value);
            
            if (discountType === 'percent') {
                if (isNaN(discountValue) || discountValue <= 0 || discountValue > 100) {
                    alert('Giá trị giảm theo phần trăm phải lớn hơn 0 và không quá 100%!');
                    return false;
                }
            }
            return true;
        }

        // Mở popup tạo mới (reset form)
        function openAddVoucherPopup() {
            var overlay = document.querySelector('.overlay');
            var form = document.getElementById('voucherForm');
            form.reset();
            document.getElementById('popupTitle').textContent = 'Thêm Voucher Mới';
            document.getElementById('action').value = 'addVoucher';
            document.querySelector('input[name="voucher_id"]').value = '';
            toggleMaxDiscount(); // reset hiển thị
            overlay.style.display = 'flex';
        }

        // Đóng popup
        function closeVoucherPopup() {
            document.querySelector('.overlay').style.display = 'none';
        }

        // Chỉnh sửa voucher
        function editVoucher(data) {
            var formEdit = document.querySelector('.overlay');
            document.querySelector('#action').value = 'editVoucher';
            document.getElementById('popupTitle').textContent = 'Chỉnh Sửa Voucher';

            formEdit.style.display = 'flex';
            var dataForm = data.closest('.coupon-cardCP');
            var code = dataForm.querySelector('.coupon-codeCP').innerHTML;
            var voucherId = dataForm.dataset.voucherId;
            var discountType = dataForm.dataset.discountType;
            var discountValue = dataForm.dataset.discountValue;
            var minOrder = dataForm.dataset.minOrder;
            var maxDiscount = dataForm.dataset.maxDiscount;
            var quantity = dataForm.dataset.quantity;
            var startDate = dataForm.dataset.startDate;
            var endDate = dataForm.dataset.endDate;
            var isActive = parseInt(dataForm.dataset.active);
            

            formEdit.querySelector('input[name="code"]').value = code;
            formEdit.querySelector('input[name="voucher_id"]').value = voucherId;
            formEdit.querySelector('select[name="discount_type"]').value = discountType;
            formEdit.querySelector('input[name="discount_value"]').value = discountValue;
            formEdit.querySelector('input[name="min_order_value"]').value = minOrder;
            
            // Toggle trước khi set giá trị max discount
            toggleMaxDiscount();
            
            if (discountType === 'percent' && parseFloat(maxDiscount) !== 0.0) {
                formEdit.querySelector('input[name="max_discount"]').value = maxDiscount;
            }
            formEdit.querySelector('input[name="quantity"]').value = quantity;
            formEdit.querySelector('input[name="start_date"]').value = startDate;
            formEdit.querySelector('input[name="end_date"]').value = endDate;
            formEdit.querySelector('input[name="is_active"]').checked = parseInt(dataForm.dataset.active) === 1;
        };

        // Khởi tạo trạng thái ban đầu
        document.addEventListener('DOMContentLoaded', function() {
            toggleMaxDiscount();
        });
    </script>
</html>