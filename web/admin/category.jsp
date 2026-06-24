<%-- 
    Document   : category
    Created on : Jan 31, 2026, 6:29:27 PM
    Author     : dotha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Category"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý Danh Mục</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            /* ===================== TOAST NOTIFICATION ===================== */
            .toast-container {
                position: fixed;
                top: 24px;
                right: 24px;
                z-index: 9999;
                display: flex;
                flex-direction: column;
                gap: 10px;
            }

            .toast {
                display: flex;
                align-items: center;
                gap: 12px;
                padding: 14px 20px;
                border-radius: 10px;
                min-width: 320px;
                max-width: 420px;
                font-size: 14px;
                font-weight: 500;
                box-shadow: 0 8px 24px rgba(0,0,0,0.15);
                animation: slideInRight 0.35s ease, fadeOut 0.4s ease 3.6s forwards;
                color: #fff;
            }

            .toast.success {
                background: linear-gradient(135deg, #11998e, #38ef7d);
            }
            .toast.error   {
                background: linear-gradient(135deg, #ff416c, #ff4b2b);
            }
            .toast.warning {
                background: linear-gradient(135deg, #f7971e, #ffd200);
                color: #333;
            }

            .toast i {
                font-size: 18px;
                flex-shrink: 0;
            }

            .toast-close {
                margin-left: auto;
                background: none;
                border: none;
                color: inherit;
                cursor: pointer;
                opacity: 0.8;
                font-size: 16px;
                padding: 0;
            }

            @keyframes slideInRight {
                from {
                    transform: translateX(110%);
                    opacity: 0;
                }
                to   {
                    transform: translateX(0);
                    opacity: 1;
                }
            }
            @keyframes fadeOut {
                from {
                    opacity: 1;
                    transform: translateX(0);
                }
                to   {
                    opacity: 0;
                    transform: translateX(60px);
                }
            }

            /* ===================== MODAL OVERLAY ===================== */
            .modal-overlay {
                display: none;
                position: fixed;
                inset: 0;
                background: rgba(0,0,0,0.55);
                backdrop-filter: blur(4px);
                z-index: 1000;
                align-items: center;
                justify-content: center;
            }
            .modal-overlay.active {
                display: flex;
            }

            .modal-box {
                background: #fff;
                border-radius: 16px;
                padding: 32px;
                width: 100%;
                max-width: 460px;
                box-shadow: 0 20px 60px rgba(0,0,0,0.2);
                animation: popIn 0.28s cubic-bezier(0.34, 1.56, 0.64, 1);
                position: relative;
            }

            @keyframes popIn {
                from {
                    transform: scale(0.85);
                    opacity: 0;
                }
                to   {
                    transform: scale(1);
                    opacity: 1;
                }
            }

            .modal-header {
                display: flex;
                align-items: center;
                gap: 12px;
                margin-bottom: 24px;
            }

            .modal-icon {
                width: 44px;
                height: 44px;
                border-radius: 12px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 20px;
                color: #fff;
                flex-shrink: 0;
            }
            .modal-icon.add    {
                background: linear-gradient(135deg, #667eea, #764ba2);
            }
            .modal-icon.edit   {
                background: linear-gradient(135deg, #f093fb, #f5576c);
            }
            .modal-icon.delete {
                background: linear-gradient(135deg, #ff416c, #ff4b2b);
            }
            .modal-icon.view   {
                background: linear-gradient(135deg, #4facfe, #00f2fe);
            }

            .modal-title {
                font-size: 18px;
                font-weight: 700;
                color: #1a1a2e;
            }
            .modal-subtitle {
                font-size: 13px;
                color: #888;
                margin-top: 2px;
            }

            .modal-close {
                position: absolute;
                top: 16px;
                right: 16px;
                background: none;
                border: none;
                font-size: 20px;
                color: #999;
                cursor: pointer;
                width: 32px;
                height: 32px;
                border-radius: 8px;
                display: flex;
                align-items: center;
                justify-content: center;
                transition: background 0.2s;
            }
            .modal-close:hover {
                background: #f0f0f0;
                color: #333;
            }

            /* Form in Modal */
            .modal-form-group {
                margin-bottom: 20px;
            }
            .modal-form-group label {
                display: block;
                font-size: 13px;
                font-weight: 600;
                color: #444;
                margin-bottom: 8px;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }
            .modal-form-group input[type="text"] {
                width: 100%;
                padding: 12px 16px;
                border: 2px solid #e8e8e8;
                border-radius: 10px;
                font-size: 14px;
                color: #333;
                transition: border-color 0.2s, box-shadow 0.2s;
                outline: none;
                box-sizing: border-box;
            }
            .modal-form-group input[type="text"]:focus {
                border-color: #667eea;
                box-shadow: 0 0 0 4px rgba(102,126,234,0.12);
            }
            .modal-form-group .input-error {
                display: none;
                color: #ff4b2b;
                font-size: 12px;
                margin-top: 6px;
            }
            .modal-form-group.has-error input {
                border-color: #ff4b2b;
            }
            .modal-form-group.has-error .input-error {
                display: block;
            }

            .modal-actions {
                display: flex;
                gap: 12px;
                justify-content: flex-end;
                margin-top: 8px;
            }

            .btn-modal {
                padding: 11px 24px;
                border-radius: 10px;
                font-size: 14px;
                font-weight: 600;
                border: none;
                cursor: pointer;
                transition: all 0.2s;
                display: flex;
                align-items: center;
                gap: 8px;
            }
            .btn-modal-cancel {
                background: #f5f5f5;
                color: #555;
            }
            .btn-modal-cancel:hover {
                background: #e8e8e8;
            }

            .btn-modal-confirm {
                color: #fff;
            }
            .btn-modal-confirm.add  {
                background: linear-gradient(135deg, #667eea, #764ba2);
            }
            .btn-modal-confirm.edit {
                background: linear-gradient(135deg, #f093fb, #f5576c);
            }
            .btn-modal-confirm.delete {
                background: linear-gradient(135deg, #ff416c, #ff4b2b);
            }

            .btn-modal-confirm:hover {
                opacity: 0.9;
                transform: translateY(-1px);
            }

            /* Delete warning */
            .delete-warning {
                background: #fff5f5;
                border: 1px solid #ffe0e0;
                border-radius: 10px;
                padding: 16px;
                margin-bottom: 20px;
                display: flex;
                gap: 12px;
                align-items: flex-start;
            }
            .delete-warning i {
                color: #ff4b2b;
                font-size: 20px;
                flex-shrink: 0;
                margin-top: 2px;
            }
            .delete-warning p {
                font-size: 14px;
                color: #555;
                margin: 0;
                line-height: 1.5;
            }
            .delete-warning strong {
                color: #333;
            }

            /* View Modal - Stats */
            .view-stats-grid {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 12px;
                margin-bottom: 20px;
            }
            .view-stat-card {
                background: #f8f9ff;
                border-radius: 10px;
                padding: 14px 16px;
                text-align: center;
            }
            .view-stat-value {
                font-size: 22px;
                font-weight: 700;
                color: #1a1a2e;
            }
            .view-stat-label {
                font-size: 12px;
                color: #888;
                margin-top: 4px;
            }
            .view-revenue {
                background: linear-gradient(135deg, #667eea, #764ba2);
                border-radius: 10px;
                padding: 16px;
                text-align: center;
                color: #fff;
            }
            .view-revenue .rev-label {
                font-size: 13px;
                opacity: 0.85;
            }
            .view-revenue .rev-value {
                font-size: 22px;
                font-weight: 700;
                margin-top: 4px;
            }

            /* Empty state */
            .empty-state-DM {
                text-align: center;
                padding: 60px 20px;
                color: #aaa;
            }
            .empty-state-DM i {
                font-size: 48px;
                margin-bottom: 16px;
                display: block;
            }
            .empty-state-DM p {
                font-size: 16px;
            }

            /* Gradient colors array for cards */
            .grad-0 {
                background: linear-gradient(135deg, #667eea, #764ba2);
            }
            .grad-1 {
                background: linear-gradient(135deg, #f093fb, #f5576c);
            }
            .grad-2 {
                background: linear-gradient(135deg, #4facfe, #00f2fe);
            }
            .grad-3 {
                background: linear-gradient(135deg, #fa709a, #fee140);
            }
            .grad-4 {
                background: linear-gradient(135deg, #a8edea, #fed6e3);
            }
            .grad-5 {
                background: linear-gradient(135deg, #d299c2, #fef9d7);
            }
            .grad-6 {
                background: linear-gradient(135deg, #a1c4fd, #c2e9fb);
            }
            .grad-7 {
                background: linear-gradient(135deg, #fddb92, #d1fdff);
            }
            .grad-8 {
                background: linear-gradient(135deg, #96fbc4, #f9f586);
            }
            .grad-9 {
                background: linear-gradient(135deg, #ff9a9e, #fad0c4);
            }
        </style>
    </head>
    <body>
        <jsp:include page="/admin/taskbar.jsp"></jsp:include>

        <c:if test="${not empty toastMessage}">
            <div id="toastData"
                 data-type="${toastType}"
                 data-message="${toastMessage}"
                 style="display:none">
            </div>
        </c:if>
        <!--popup thông báo-->
        <div class="messPopup" id="messPopupGlass">
            <div class="messPopup-header">Thông báo</div>
            <h3 id="messPopupText" style="color:green"></h3>
            <div class="btnConfirm">
                <button type="button" class="btac agreeBtn" onclick="closeGlassPopup('messPopupGlass')">Đóng</button>
            </div>
        </div>

        <h1 class="page-titleDM"><i class="fas fa-tags"></i> Quản lý Danh Mục</h1>

        <div id="categoryDM">
            <!-- Toolbar -->
            <div class="toolbar-DM">
                <div class="searchDetails">
                    <form method="get" action="${pageContext.request.contextPath}/category" style="display:contents">
                        <input type="hidden" name="action" value="search"/>
                        <div class="search-boxDM">
                            <i class="fas fa-search"></i>
                            <input type="text" name="keyword" placeholder="Tìm kiếm danh mục..."
                                   value="${searchKeyword}">
                        </div>
                    </form>
                </div>
                <div class="addDetails">
                    <button class="add-btnDM" onclick="openAddModal()">
                        <i class="fas fa-tags"></i> Thêm danh mục
                    </button>
                    <button class="add-btnDM" onclick="openSizeModal()" style="background: linear-gradient(135deg, #11998e, #38ef7d);">
                        <i class="fas fa-ruler"></i> Thêm kích thước
                    </button>
                    <button class="add-btnDM" onclick="openColorModal()" style="background: linear-gradient(135deg, #f093fb, #f5576c);">
                        <i class="fas fa-palette"></i> Thêm màu sắc
                    </button>
                </div>
            </div>

            <!-- Categories Grid -->
            <div class="categories-gridDM">
                <c:choose>
                    <c:when test="${empty categories}">
                        <div class="empty-state-DM" style="grid-column: 1/-1;">
                            <i class="fas fa-tags"></i>
                            <p>Không có danh mục nào. Hãy thêm danh mục mới!</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="cat" items="${categories}" varStatus="status">
                            <div class="category-cardDM">
                                <div class="category-headerDM grad-${status.index % 10}">
                                    <div class="category-iconDM"><i class="fas fa-tags"></i></div>
                                    <div class="category-nameDM">${cat.categoryName}</div>
                                    <div class="category-countDM">${cat.totalProducts} sản phẩm</div>
                                </div>
                                <div class="category-bodyDM">
                                    <div class="category-infoDM">
                                        <span class="info-labelDM">Đang bán:</span>
                                        <span class="info-valueDM">${cat.activeProducts}</span>
                                    </div>
                                    <div class="category-infoDM">
                                        <span class="info-labelDM">Hết hàng:</span>
                                        <span class="info-valueDM">${cat.inactiveProducts}</span>
                                    </div>
                                    <div class="category-infoDM">
                                        <span class="info-labelDM">Doanh thu:</span>
                                        <span class="info-valueDM">
                                            <fmt:formatNumber value="${cat.totalRevenue}" type="number" groupingUsed="true"/>₫
                                        </span>
                                    </div>
                                    <div class="category-actionsDM">
                                        <button class="action-btn-DM btn-viewDM"
                                                onclick="openViewModal(${cat.categoryId}, '${cat.categoryName}',
                                                ${cat.totalProducts}, ${cat.activeProducts},
                                                ${cat.inactiveProducts}, ${cat.totalRevenue})">
                                            <i class="fas fa-eye"></i> Xem
                                        </button>
                                        <button class="action-btn-DM btn-editDM"
                                                onclick="openEditModal(${cat.categoryId}, '${cat.categoryName}')">
                                            <i class="fas fa-edit"></i> Sửa
                                        </button>
                                        <button class="action-btn-DM btn-deleteDM"
                                                onclick="openDeleteModal(${cat.categoryId}, '${cat.categoryName}')">
                                            <i class="fas fa-trash"></i> Xóa
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        

            <!--<div style="display: flex; gap: 20px; margin-top: 40px; margin-bottom: 40px; padding: 0 20px;">-->
            <div style="flex: 1; background: #fff; border-radius: 12px; padding: 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.05);" class="spc">
                <h3 style="margin-bottom: 15px; color: #1a1a2e;"><i class="fas fa-ruler"></i> Danh Sách Kích Thước</h3>
                <table style="width: 100%; border-collapse: collapse; text-align: left;">
                    <tr style="border-bottom: 2px solid #eee;">
                        <th style="padding: 10px;">ID</th>
                        <th style="padding: 10px;">Tên Size</th>
                        <th style="padding: 10px; text-align: right;">Hành động</th>
                    </tr>
                    <c:forEach var="s" items="${sizes}">
                        <tr style="border-bottom: 1px solid #f5f5f5;">
                            <td style="padding: 10px;" class="sc">${s.sizeId}</td>
                            <td style="padding: 10px; font-weight: bold;" class="sc">${s.sizeName}</td>
                            <td style="padding: 10px; text-align: right;" class="sc size-color">
                                <div class="color-size" style="display: flex; gap: 8px; justify-content: flex-end;">
                                <button class="action-btn-DM btn-editDM" onclick="openEditSizeModal(${s.sizeId}, '${s.sizeName}')" style="flex: 0 1 auto; padding: 6px 12px; width: max-content; background-color: #f7971e; color: white; border: none; border-radius: 5px; cursor: pointer;"><i class="fas fa-edit"></i> Sửa</button>
                                <button class="action-btn-DM btn-deleteDM" onclick="openDeleteSizeModal(${s.sizeId}, '${s.sizeName}')" style="flex: 0 1 auto; padding: 6px 12px; width: max-content; background-color: #ff4b2b; color: white; border: none; border-radius: 5px; cursor: pointer;"><i class="fas fa-trash"></i> Xóa</button>
                               </div>
                                </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div style="flex: 1; background: #fff; border-radius: 12px; padding: 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.05);">
                <h3 style="margin-bottom: 15px; color: #1a1a2e;"><i class="fas fa-palette"></i> Danh Sách Màu Sắc</h3>
                <table style="width: 100%; border-collapse: collapse; text-align: left;">
                    <tr style="border-bottom: 2px solid #eee;">
                        <th style="padding: 10px;">ID</th>
                        <th style="padding: 10px;">Tên Màu</th>
                        <th style="padding: 10px; text-align: right;">Hành động</th>
                    </tr>
                    <c:forEach var="c" items="${colors}">
                        <tr style="border-bottom: 1px solid #f5f5f5;">
                            <td style="padding: 10px;" class="sc">${c.colorId}</td>
                            <td style="padding: 10px; font-weight: bold;" class="sc">${c.colorName}</td>
                            <td style="padding: 10px; text-align: right;" class="sc">
                                <div class="color-size" style="display: flex; gap: 8px; justify-content: flex-end;">
                                <button class="action-btn-DM btn-editDM" onclick="openEditColorModal(${c.colorId}, '${c.colorName}')" style="flex: 0 1 auto; padding: 6px 12px; width: max-content; background-color: #f7971e; color: white; border: none; border-radius: 5px; cursor: pointer;"><i class="fas fa-edit"></i> Sửa</button>
                                <button class="action-btn-DM btn-deleteDM" onclick="openDeleteColorModal(${c.colorId}, '${c.colorName}')" style="flex: 0 1 auto; padding: 6px 12px; width: max-content; background-color: #ff4b2b; color: white; border: none; border-radius: 5px; cursor: pointer;"><i class="fas fa-trash"></i> Xóa</button>
                            </div>
                                </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        <!--</div>-->
              </div>          
        <!-- ===================== MODAL: THÊM DANH MỤC ===================== -->
        <div class="modal-overlay" id="addModal">
            <div class="modal-box">
                <button class="modal-close" onclick="closeModal('addModal')"><i class="fas fa-times"></i></button>
                <div class="modal-header">
                    <div class="modal-icon add"><i class="fas fa-plus"></i></div>
                    <div>
                        <div class="modal-title">Thêm Danh Mục Mới</div>
                        <div class="modal-subtitle">Tạo danh mục sản phẩm cho cửa hàng</div>
                    </div>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/category"
                      onsubmit="return validateCategoryForm('addCategoryName', 'addNameError')">
                    <input type="hidden" name="action" value="add"/>
                    <div class="modal-form-group" id="addFormGroup">
                        <label>Tên danh mục <span style="color:#ff4b2b">*</span></label>
                        <input type="text" name="categoryName" id="addCategoryName"
                               placeholder="Ví dụ: Áo Khoác, Giày Dép..." maxlength="100"/>
                        <div class="input-error" id="addNameError">Vui lòng nhập tên danh mục!</div>
                    </div>
                    <div class="modal-actions">
                        <button type="button" class="btn-modal btn-modal-cancel" onclick="closeModal('addModal')">
                            <i class="fas fa-times"></i> Hủy
                        </button>
                        <button type="submit" class="btn-modal btn-modal-confirm add">
                            <i class="fas fa-plus"></i> Thêm mới
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <!--MODEL THÊM SIZE-->
        <div class="modal-overlay" id="sizeModal">
            <div class="modal-box">
                <button class="modal-close" onclick="closeModal('sizeModal')"><i class="fas fa-times"></i></button>
                <div class="modal-header">
                    <div class="modal-icon add" style="background: linear-gradient(135deg, #11998e, #38ef7d);"><i class="fas fa-ruler"></i></div>
                    <div>
                        <div class="modal-title">Thêm Kích Thước Mới</div>
                        <div class="modal-subtitle">Ví dụ: S, M, L, XL, 39, 40...</div>
                    </div>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/size" onsubmit="return validateSimpleForm('sizeName', 'sizeError')">
                    <input type="hidden" name="action" value="add"/>
                    <div class="modal-form-group">
                        <label>Tên kích thước <span style="color:#ff4b2b">*</span></label>
                        <input type="text" name="sizeName" id="sizeName" placeholder="Nhập size..." maxlength="20"/>
                        <div class="input-error" id="sizeError">Vui lòng nhập kích thước!</div>
                    </div>
                    <div class="modal-actions">
                        <button type="button" class="btn-modal btn-modal-cancel" onclick="closeModal('sizeModal')">Hủy</button>
                        <button type="submit" class="btn-modal btn-modal-confirm" style="background: linear-gradient(135deg, #11998e, #38ef7d);">Thêm mới</button>
                    </div>
                </form>
            </div>
        </div>
        <!--MODEL THÊM MÀU-->
        <div class="modal-overlay" id="colorModal">
            <div class="modal-box">
                <button class="modal-close" onclick="closeModal('colorModal')"><i class="fas fa-times"></i></button>
                <div class="modal-header">
                    <div class="modal-icon add" style="background: linear-gradient(135deg, #f093fb, #f5576c);"><i class="fas fa-palette"></i></div>
                    <div>
                        <div class="modal-title">Thêm Màu Sắc Mới</div>
                        <div class="modal-subtitle">Ví dụ: Đỏ, Xanh Navy, Đen...</div>
                    </div>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/color" onsubmit="return validateSimpleForm('colorName', 'colorError')">
                    <input type="hidden" name="action" value="add"/>
                    <div class="modal-form-group">
                        <label>Tên màu sắc <span style="color:#ff4b2b">*</span></label>
                        <input type="text" name="colorName" id="colorName" placeholder="Nhập tên màu..." maxlength="50"/>
                        <div class="input-error" id="colorError">Vui lòng nhập tên màu!</div>
                    </div>
                    <div class="modal-actions">
                        <button type="button" class="btn-modal btn-modal-cancel" onclick="closeModal('colorModal')">Hủy</button>
                        <button type="submit" class="btn-modal btn-modal-confirm" style="background: linear-gradient(135deg, #f093fb, #f5576c);">Thêm mới</button>
                    </div>
                </form>
            </div>
        </div>
        <!-- ===================== MODAL: SỬA DANH MỤC ===================== -->
        <div class="modal-overlay" id="editModal">
            <div class="modal-box">
                <button class="modal-close" onclick="closeModal('editModal')"><i class="fas fa-times"></i></button>
                <div class="modal-header">
                    <div class="modal-icon edit"><i class="fas fa-edit"></i></div>
                    <div>
                        <div class="modal-title">Chỉnh Sửa Danh Mục</div>
                        <div class="modal-subtitle">Cập nhật thông tin danh mục</div>
                    </div>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/category"
                      onsubmit="return validateCategoryForm('editCategoryName', 'editNameError')">
                    <input type="hidden" name="action" value="edit"/>
                    <input type="hidden" name="categoryId" id="editCategoryId"/>
                    <div class="modal-form-group" id="editFormGroup">
                        <label>Tên danh mục <span style="color:#ff4b2b">*</span></label>
                        <input type="text" name="categoryName" id="editCategoryName"
                               placeholder="Nhập tên danh mục..." maxlength="100"/>
                        <div class="input-error" id="editNameError">Vui lòng nhập tên danh mục!</div>
                    </div>
                    <div class="modal-actions">
                        <button type="button" class="btn-modal btn-modal-cancel" onclick="closeModal('editModal')">
                            <i class="fas fa-times"></i> Hủy
                        </button>
                        <button type="submit" class="btn-modal btn-modal-confirm edit">
                            <i class="fas fa-save"></i> Lưu thay đổi
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <!-- ===================== MODAL: SỬA KÍCH THƯỚC ===================== -->
        <div class="modal-overlay" id="editSizeModal">
            <div class="modal-box">
                <button class="modal-close" onclick="closeModal('editSizeModal')"><i class="fas fa-times"></i></button>
                <div class="modal-header">
                    <div class="modal-icon edit" style="background: linear-gradient(135deg, #11998e, #38ef7d);"><i class="fas fa-edit"></i></div>
                    <div>
                        <div class="modal-title">Chỉnh Sửa Kích Thước</div>
                        <div class="modal-subtitle">Cập nhật giá trị size</div>
                    </div>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/size">
                    <input type="hidden" name="action" value="edit"/>
                    <input type="hidden" name="sizeId" id="editSizeId"/>
                    <div class="modal-form-group">
                        <label>Tên kích thước <span style="color:#ff4b2b">*</span></label>
                        <input type="text" name="sizeName" id="editSizeName" placeholder="Nhập size..." maxlength="20"/>
                    </div>
                    <div class="modal-actions">
                        <button type="button" class="btn-modal btn-modal-cancel" onclick="closeModal('editSizeModal')">Hủy</button>
                        <button type="submit" class="btn-modal btn-modal-confirm" style="background: linear-gradient(135deg, #11998e, #38ef7d);">Lưu thay đổi</button>
                    </div>
                </form>
            </div>
        </div>
        <!-- ===================== MODAL: SỬA MÀU ===================== -->
        <div class="modal-overlay" id="editColorModal">
            <div class="modal-box">
                <button class="modal-close" onclick="closeModal('editColorModal')"><i class="fas fa-times"></i></button>
                <div class="modal-header">
                    <div class="modal-icon edit" style="background: linear-gradient(135deg, #f093fb, #f5576c);"><i class="fas fa-edit"></i></div>
                    <div class="modal-title">Chỉnh Sửa Màu Sắc</div>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/color">
                    <input type="hidden" name="action" value="edit"/>
                    <input type="hidden" name="colorId" id="editColorId"/>
                    <div class="modal-form-group">
                        <label>Tên màu <span style="color:#ff4b2b">*</span></label>
                        <input type="text" name="colorName" id="editColorName" maxlength="50"/>
                    </div>
                    <div class="modal-actions">
                        <button type="button" class="btn-modal btn-modal-cancel" onclick="closeModal('editColorModal')">Hủy</button>
                        <button type="submit" class="btn-modal btn-modal-confirm" style="background: linear-gradient(135deg, #f093fb, #f5576c);">Cập nhật</button>
                    </div>
                </form>
            </div>
        </div>            
        <!-- ===================== MODAL: XÓA DANH MỤC ===================== -->
        <div class="deletePopup" id="deleteModalGlass">
            <div class="deletePopup-header">
                <i class="fa-regular fa-trash-can style-trash"></i> Xác Nhận Xóa
            </div>
            <h2>Bạn có chắc chắn muốn xóa danh mục <span id="deleteCategoryNameGlass"></span>?<br>
            <small style="font-size:0.85rem; font-weight:normal; color:#666;">Danh mục chỉ có thể xóa khi không còn sản phẩm liên kết.</small></h2>
            <div class="btnConfirm">
                <form method="post" action="${pageContext.request.contextPath}/category">
                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="categoryId" id="deleteCategoryIdGlass"/>
                    <button type="button" class="btac cancelBtn" onclick="closeGlassPopup('deleteModalGlass')">Hủy</button>
                    <button type="submit" class="btac agreeBtn">Xóa danh mục</button>
                </form>
            </div>
        </div>
        <!-- ===================== MODAL: XÓA DANH MỤC ===================== -->
        <div class="deletePopup" id="deleteSizeModalGlass">
            <div class="deletePopup-header">
                <i class="fa-regular fa-trash-can style-trash"></i> Xác Nhận Xóa Size
            </div>
            <h2>Bạn có chắc chắn muốn xóa kích thước <span id="delSizeNameDisplayGlass"></span>?</h2>
            <div class="btnConfirm">
                <form method="post" action="${pageContext.request.contextPath}/size">
                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="sizeId" id="deleteSizeIdGlass"/>
                    <button type="button" class="btac cancelBtn" onclick="closeGlassPopup('deleteSizeModalGlass')">Hủy</button>
                    <button type="submit" class="btac agreeBtn">Xóa vĩnh viễn</button>
                </form>
            </div>
        </div>
        <!-- ===================== MODAL:XÓA MÀU ===================== -->
        <div class="deletePopup" id="deleteColorModalGlass">
            <div class="deletePopup-header">
                <i class="fa-regular fa-trash-can style-trash"></i> Xóa Màu Sắc
            </div>
            <h2>Xác nhận xóa màu: <span id="delColorNameDisplayGlass"></span>?</h2>
            <div class="btnConfirm">
                <form method="post" action="${pageContext.request.contextPath}/color">
                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="colorId" id="deleteColorIdGlass"/>
                    <button type="button" class="btac cancelBtn" onclick="closeGlassPopup('deleteColorModalGlass')">Hủy</button>
                    <button type="submit" class="btac agreeBtn">Xóa</button>
                </form>
            </div>
        </div>
        <!-- ===================== MODAL: XEM DANH MỤC ===================== -->
        <div class="modal-overlay" id="viewModal">
            <div class="modal-box">
                <button class="modal-close" onclick="closeModal('viewModal')"><i class="fas fa-times"></i></button>
                <div class="modal-header">
                    <div class="modal-icon view"><i class="fas fa-eye"></i></div>
                    <div>
                        <div class="modal-title" id="viewCategoryTitle">Chi Tiết Danh Mục</div>
                        <div class="modal-subtitle">Thông tin tổng quan</div>
                    </div>
                </div>
                <div class="view-stats-grid">
                    <div class="view-stat-card">
                        <div class="view-stat-value" id="viewTotal">0</div>
                        <div class="view-stat-label">Tổng sản phẩm</div>
                    </div>
                    <div class="view-stat-card">
                        <div class="view-stat-value" style="color:#11998e" id="viewActive">0</div>
                        <div class="view-stat-label">Đang bán</div>
                    </div>
                    <div class="view-stat-card" style="grid-column: 1 / -1;">
                        <div class="view-stat-value" style="color:#ff4b2b" id="viewInactive">0</div>
                        <div class="view-stat-label">Hết hàng / Tạm ẩn</div>
                    </div>
                </div>
                <div class="view-revenue">
                    <div class="rev-label">Tổng doanh thu (đơn hoàn thành)</div>
                    <div class="rev-value" id="viewRevenue">0₫</div>
                </div>
                <div class="modal-actions" style="margin-top:20px">
                    <button type="button" class="btn-modal btn-modal-cancel" onclick="closeModal('viewModal')">
                        <i class="fas fa-times"></i> Đóng
                    </button>
                </div>
            </div>
        </div>
<!-- ===================== MODAL: XEM SIZE ===================== -->
        <div class="modal-overlay" id="viewSizeModal">
    <div class="modal-box">
        <button class="modal-close" onclick="closeModal('viewSizeModal')"><i class="fas fa-times"></i></button>
        <div class="modal-header">
            <div class="modal-icon view" style="background: linear-gradient(135deg, #11998e, #38ef7d);"><i class="fas fa-ruler"></i></div>
            <div>
                <div class="modal-title" id="viewSizeTitle">Chi Tiết Kích Thước</div>
                <div class="modal-subtitle">Thông tin tổng quan</div>
            </div>
        </div>
        <div class="view-stats-grid">
            <div class="view-stat-card" style="grid-column: 1 / -1; background: linear-gradient(135deg, #11998e, #38ef7d); color: white;">
                <div class="view-stat-value" id="viewSizeTotal" style="color: white;">0</div>
                <div class="view-stat-label" style="color: rgba(255,255,255,0.8);">Sản phẩm đang dùng size này</div>
            </div>
        </div>
        <div class="modal-actions" style="margin-top:20px">
            <button type="button" class="btn-modal btn-modal-cancel" onclick="closeModal('viewSizeModal')">
                <i class="fas fa-times"></i> Đóng
            </button>
        </div>
    </div>
</div>
        <!-- ===================== MODAL: XEM MÀU ===================== -->
<div class="modal-overlay" id="viewColorModal">
    <div class="modal-box">
        <button class="modal-close" onclick="closeModal('viewColorModal')"><i class="fas fa-times"></i></button>
        <div class="modal-header">
            <div class="modal-icon view" style="background: linear-gradient(135deg, #f093fb, #f5576c);"><i class="fas fa-palette"></i></div>
            <div>
                <div class="modal-title" id="viewColorTitle">Chi Tiết Màu Sắc</div>
                <div class="modal-subtitle">Thông tin tổng quan</div>
            </div>
        </div>
        <div class="view-stats-grid">
            <div class="view-stat-card" style="grid-column: 1 / -1; background: linear-gradient(135deg, #f093fb, #f5576c); color: white;">
                <div class="view-stat-value" id="viewColorTotal" style="color: white;">0</div>
                <div class="view-stat-label" style="color: rgba(255,255,255,0.8);">Sản phẩm đang dùng màu này</div>
            </div>
        </div>
        <div class="modal-actions" style="margin-top:20px">
            <button type="button" class="btn-modal btn-modal-cancel" onclick="closeModal('viewColorModal')">
                <i class="fas fa-times"></i> Đóng
            </button>
        </div>
    </div>
</div>
        <script>
            function openGlassPopup(id) {
                document.getElementById(id).classList.add("openPopup");
                let overlay = document.querySelector(".all");
                if (overlay) overlay.classList.add("cover");
            }
            function closeGlassPopup(id) {
                document.getElementById(id).classList.remove("openPopup");
                let overlay = document.querySelector(".all");
                if (overlay) overlay.classList.remove("cover");
            }

            function showToast(type, message) {
                const text = document.getElementById('messPopupText');
                if(type === 'error' || type === 'warning') {
                    text.style.color = 'red';
                    text.innerHTML = '<i class="fa-regular fa-circle-xmark"></i> ' + message;
                } else {
                    text.style.color = 'green';
                    text.innerHTML = '<i class="fa-regular fa-circle-check"></i> ' + message;
                }
                openGlassPopup('messPopupGlass');
            }

            // ---- Modal Controls ----
            function openModal(id) {
                document.getElementById(id).classList.add("active");
                document.body.style.overflow = "hidden";
            }

            function closeModal(id) {
                document.getElementById(id).classList.remove("active");
                document.body.style.overflow = "";
                // Reset errors
                document.querySelectorAll("#" + id + " .modal-form-group").forEach(g => g.classList.remove("has-error"));
            }
            // 
            // Close on overlay click
            document.querySelectorAll(".modal-overlay").forEach(overlay => {
                overlay.addEventListener("click", function (e) {
                    if (e.target === this)
                        closeModal(this.id);
                });
            });
            // ---- Open Modals ----
            function openAddModal() {
                document.getElementById("addCategoryName").value = "";
                openModal("addModal");
                setTimeout(() => document.getElementById("addCategoryName").focus(), 300);
            }

            function openEditModal(id, name) {
                document.getElementById("editCategoryId").value = id;
                document.getElementById("editCategoryName").value = name;
                openModal("editModal");
                setTimeout(() => document.getElementById("editCategoryName").focus(), 300);
            }
            // ---- Open Modals Size----
            function openSizeModal() {
                document.getElementById("sizeName").value = "";
                openModal("sizeModal");
                setTimeout(() => document.getElementById("sizeName").focus(), 300);
            }

            // ---- Open Modals Color ----
            function openColorModal() {
                document.getElementById("colorName").value = "";
                openModal("colorModal");
                setTimeout(() => document.getElementById("colorName").focus(), 300);
            }
            function openDeleteModal(id, name) {
                document.getElementById("deleteCategoryIdGlass").value = id;
                document.getElementById("deleteCategoryNameGlass").textContent = '"' + name + '"';
                openGlassPopup("deleteModalGlass");
            }
            // ---- delete size ----
            function openEditSizeModal(id, name) {
                document.getElementById("editSizeId").value = id;
                document.getElementById("editSizeName").value = name;
                openModal("editSizeModal");
            }

            function openDeleteSizeModal(id, name) {
                document.getElementById("deleteSizeIdGlass").value = id;
                document.getElementById("delSizeNameDisplayGlass").textContent = '"' + name + '"';
                openGlassPopup("deleteSizeModalGlass");
            }
            // ---- delete color ----
            function openEditColorModal(id, name) {
                document.getElementById("editColorId").value = id;
                document.getElementById("editColorName").value = name;
                openModal("editColorModal");
            }

            function openDeleteColorModal(id, name) {
                document.getElementById("deleteColorIdGlass").value = id;
                document.getElementById("delColorNameDisplayGlass").textContent = '"' + name + '"';
                openGlassPopup("deleteColorModalGlass");
            }
            //
            function openViewModal(id, name, total, active, inactive, revenue) {
                document.getElementById("viewCategoryTitle").textContent = name;
                document.getElementById("viewTotal").textContent = total;
                document.getElementById("viewActive").textContent = active;
                document.getElementById("viewInactive").textContent = inactive;
                // Format revenue
                const fmt = new Intl.NumberFormat("vi-VN");
                document.getElementById("viewRevenue").textContent = fmt.format(revenue) + "₫";
                openModal("viewModal");
            }

            // ---- Form Validation ----
            function validateCategoryForm(inputId, errorId) {
                const input = document.getElementById(inputId);
                const errorEl = document.getElementById(errorId);
                const formGroup = input.closest(".modal-form-group");
                const value = input.value.trim();
                if (!value) {
                    errorEl.textContent = "Vui lòng nhập tên danh mục!";
                    formGroup.classList.add("has-error");
                    input.focus();
                    return false;
                }
                if (value.length < 2) {
                    errorEl.textContent = "Tên danh mục phải có ít nhất 2 ký tự!";
                    formGroup.classList.add("has-error");
                    input.focus();
                    return false;
                }
                if (value.length > 100) {
                    errorEl.textContent = "Tên danh mục không được quá 100 ký tự!";
                    formGroup.classList.add("has-error");
                    input.focus();
                    return false;
                }
                formGroup.classList.remove("has-error");
                return true;
            }

            // Real-time input clearing errors
            document.getElementById("addCategoryName").addEventListener("input", function () {
                this.closest(".modal-form-group").classList.remove("has-error");
            });
            document.getElementById("editCategoryName").addEventListener("input", function () {
                this.closest(".modal-form-group").classList.remove("has-error");
            });
            // size - color
            document.getElementById("sizeName").addEventListener("input", function () {
                this.closest(".modal-form-group").classList.remove("has-error");
            });
            document.getElementById("colorName").addEventListener("input", function () {
                this.closest(".modal-form-group").classList.remove("has-error");
            });
            // ---- Realtime Search ----
            const searchInput = document.querySelector(".search-boxDM input");
            let searchTimeout;
            searchInput.addEventListener("input", function () {
                clearTimeout(searchTimeout);
                searchTimeout = setTimeout(() => {
                    const kw = this.value.trim();
                    window.location.href = "${pageContext.request.contextPath}/category?action=search&keyword=" + encodeURIComponent(kw);
                }, 500);
            });
            document.addEventListener("DOMContentLoaded", function () {
                const toastData = document.getElementById("toastData");
                if (toastData) {
                    const type = toastData.getAttribute("data-type") || "success";
                    const message = toastData.getAttribute("data-message");
                    if (message)
                        showToast(type, message);
                }
            });
            //
            function validateSimpleForm(inputId, errorId) {
                const input = document.getElementById(inputId);
                const errorEl = document.getElementById(errorId);
                const formGroup = input.closest(".modal-form-group");

                if (!input.value.trim()) {
                    formGroup.classList.add("has-error");
                    errorEl.style.display = "block";
                    input.focus();
                    return false;
                }

                formGroup.classList.remove("has-error");
                errorEl.style.display = "none";
                return true;
            }

        </script>
        <div class="all"></div>
    </body>
</html>
