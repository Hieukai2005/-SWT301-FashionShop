<%-- 
    Document   : product-page
    Created on : Jan 27, 2026, 12:16:52 PM
    Author     : dotha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css"/>
        <script src="${pageContext.request.contextPath}/js/productAdmin.js" defer></script>
    </head>
    <body>
        <!-- Sidebar -->
        <jsp:include page="taskbar.jsp"></jsp:include>
            <div class="contentDB" id="productsPage" >
                <!-- Toolbar -->
                <form action="productadmin" method="post" id="searchForm" class="toolbar-SP">
                    <!--Tìm kiém-->
                    <div id="formSearch">
                        <div class="search-boxSP">
                            <input type="text" name="text" value="${requestScope.text}" placeholder="Tìm kiếm sản phẩm...">                      
                    </div>
                    <button type="submit" id="searchBtn"><i class="fas fa-search"></i></button>
                </div>
                <!--Lọc cate-->
                <div class="filter-groupSP">
                    <select name="cid" onchange="this.form.submit()" class="filter-selectSP" id="filterByCate">
                        <option value="0" ${cid == 0 ? 'selected' : ''}>Tất cả danh mục</option>
                        <c:forEach items="${listAllCategory}" var="category">
                            <option value="${category.categoryId}" ${cid == category.categoryId ? 'selected' : ''}>${category.categoryName}</option>
                        </c:forEach>
                    </select>
                    <!--Lọc giảm giắ-->
                    <select name="statusSale" onchange="this.form.submit()" class="filter-selectSP" id="filterByCate">
                        <option value=''>Tất cả trạng thái giảm</option>
                        <option value="on" ${"on" == statusSale ? 'selected' : ''}>Đang giảm</option>
                        <option value="off" ${"off" == statusSale ? 'selected' : ''}>Không giảm</option>
                        <option value="under30" ${"under30" == statusSale ? 'selected' : ''}>Dưới 30%</option>
                        <option value="30to50" ${"30to50" == statusSale ? 'selected' : ''}>Từ 30% - 50%</option>
                        <option value="over50" ${"over50" == statusSale ? 'selected' : ''}>Trên 50%</option>
                    </select>
                    <!--Trạng thái hiển thị-->
                    <select name="statusActive" onchange="this.form.submit()" class="filter-selectSP" id="filterByCate">
                        <option value=''>Tất cả hiển thị</option>
                        <option value="on" ${"on" == statusActive ? 'selected' : ''}>Đang hiện</option>
                        <option value="off" ${"off" == statusActive ? 'selected' : ''}>Đang ẩn</option>                                                
                    </select>
                    <!--.Thêm mới sản phẩm-->
                    <button class="add-btnSP" type="button" onclick="openPopup('.addPopup')">
                        <i class="fas fa-plus"></i>
                        Thêm sản phẩm
                    </button>
                </div>
            </form>

            <!-- Products Grid -->
            <div class="products-gridSP">
                <!-- Product 1 -->
                <c:forEach items="${requestScope.listPagingAdmin}" var="productAdmin">
                    <div class="product-cardSP">
                        <div class="product-imageSP" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);">
                            <img src="${pageContext.request.contextPath}/images/${productAdmin.image}" alt="${productAdmin.productName}"/>
                            <!--Hiện Ẩn sản phẩm-->
                            <c:choose>
                                <c:when test="${productAdmin.isActive == true}">
                                    <span class="stock-activeSP stock-visible"><i class="fa-solid fa-eye"></i></span>
                                    </c:when>                            
                                    <c:otherwise>
                                    <span class="stock-activeSP stock-hidden"><i class="fa-solid fa-eye-slash"></i></span>
                                    </c:otherwise>
                                </c:choose>
                            <!--Hiện % giảm giá-->
                            <c:choose>
                                <c:when test="${productAdmin.discount > 0}">
                                    <span class="stock-saleSP stock-outSP">-${productAdmin.discount}%</span>
                                </c:when>                            
                                <c:otherwise>
                                    <span class="stock-badgeSP stock-inSP"></span>
                                </c:otherwise>
                            </c:choose>
                            <!--Hiện trạng thái còn hết hàng-->
                            <c:choose>
                                <c:when test="${productAdmin.totalStock <= 0}">
                                    <span class="stock-badgeSP stock-outSP">Hết hàng</span>
                                </c:when>
                                <c:when test="${productAdmin.totalStock <= 10}">
                                    <span class="stock-badgeSP stock-lowSP">Sắp hết</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="stock-badgeSP stock-inSP">Còn hàng</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="product-infoSP">
                            <h3 class="product-nameSP">${productAdmin.productName}</h3>
                            <p class="product-categorySP">${productAdmin.category.categoryName}</p>

                            <div class="product-detailsSP"> 
                                <!--Cái nào giảm giá thì có giá cũ mới-->
                                <c:choose>
                                    <c:when test="${productAdmin.discount > 0}">
                                        <span class="product-priceSale"><fmt:formatNumber value="${productAdmin.price}" type="currency" maxFractionDigits="0"/></span>
                                        <span class="product-priceSP">
                                            <fmt:formatNumber value="${productAdmin.salePrice}" type="currency" maxFractionDigits="0"/>
                                        </span>                                         
                                    </c:when>                            
                                    <c:otherwise>
                                        <span class="product-priceSP"><fmt:formatNumber value="${productAdmin.price}" type="currency" maxFractionDigits="0"/></span>                                
                                    </c:otherwise>
                                </c:choose>
                                <span class="product-stockSP">Kho: ${productAdmin.totalStock}</span>
                            </div>
                            <div class="product-actionsSP">
                                <button class="action-btn-SP btn-editSP" onclick="window.location.href = 'updateproduct?pid=${productAdmin.productId}'">
                                    <i class="fas fa-edit"></i> Sửa/Chi tiết
                                </button>

                                <button class="action-btn-SP btn-deleteSP" onclick="openConfirmPopup('.deletePopup', '${productAdmin.productId}', '${productAdmin.productName}')">
                                    <i class="fas fa-trash"></i> Xóa
                                </button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <!-- Pagination -->
            <div class="pagination-SP">
                <c:if test="${not empty requestScope.emptyProductAdmin}">
                    <p style="color:red;text-align: center">${requestScope.emptyProductAdmin}</p>
                </c:if>
                <c:if test="${startPage > 1}">
                    <a class="page-btnSP" href="${pageContext.request.contextPath}/productadmin?page=${startPage - 1}&text=${text}&cid=${cid}&statusSale=${statusSale}&statusActive=${statusActive}">
                        <i class="fas fa-chevron-left"></i>
                    </a>
                </c:if>
                <c:forEach begin="${startPage}" end="${endPage}" var="no">
                    <a href="${pageContext.request.contextPath}/productadmin?page=${no}&text=${requestScope.text}&cid=${requestScope.cid}&statusSale=${statusSale}&statusActive=${statusActive}" class="page-btnSP paging ${no == requestScope.page ? 'active' : ''}">
                        ${no}
                    </a>
                </c:forEach>
                <c:if test="${endPage < numberOfPage}">
                    <a class="page-btnSP" href="${pageContext.request.contextPath}/productadmin?page=${endPage + 1}&text=${requestScope.text}&cid=${requestScope.cid}&statusSale=${statusSale}&statusActive=${statusActive}">
                        <i class="fas fa-chevron-right"></i>
                    </a>
                </c:if>
            </div>
            <h3 style="text-align: center">Tổng số trang: ${numberOfPage}</h3>
        </div>

        <!--add popup thêm sản phẩm-->
        <div class="addPopup">
            <h2>Thêm Sản Phẩm</h2>
            <form action="addproductadmin" method="post" id="addForm">
                <div class="frame">
                    <div class="fra frame_A">
                        <h3>Chi tiết sản phẩm</h3>
                        <div class="details-info">
                            <label>Tên sản phẩm:</label>
                            <input type="text" name="name" placeholder="Tên sản phẩm">
                            <span id="error-info"></span>
                        </div>
                        <div class="details-info">
                            <label>Giá tiền: </label>
                            <input type="text" name="price" placeholder="Giá tiền">
                            <span id="error-info"></span>
                        </div>

                        <div class="details-info">
                            <label>Ảnh:</label>
                            <input type="text" name="image" placeholder="Ảnh sản phẩm">
                            <span id="error-info"></span>
                        </div>
                        <div class="details-info">
                            <label>Số lượng:</label>
                            <input type="text" name="quantity" placeholder="Số lượng">
                            <span id="error-info"></span>
                        </div> 
                        <div class="details-info">
                            <label>Mô tả:</label>
                            <textarea name="description" rows="5" cols="10" placeholder="Mô tả sản phẩm"></textarea>
                            <span id="error-info"></span>
                        </div>
                    </div>
                    <div class="fra frame_B">
                        <h3>Thông tin phân loại</h3>
                        <div class="details-info">
                            <label>Danh mục:</label>
                            <select name="category">
                                <option disabled selected hidden value="">Danh mục</option>
                                <c:forEach items = "${requestScope.listAllCategory}" var="category">
                                    <option value="${category.categoryId}">${category.categoryName}</option>
                                </c:forEach>
                            </select>
                            <span id="error-info"></span>
                        </div>
                        <div class="details-info">
                            <label>Giới tính:</label>
                            <select name="gender">
                                <option disabled selected hidden value="">Giới tính</option>
                                <option value="Nam">Nam</option>
                                <option value="Nữ">Nữ</option>
                                <option value="Unisex">Unisex</option>
                            </select>
                            <span id="error-info"></span>
                        </div>
                        <div class="details-info">
                            <label>Kích thước:</label>
                            <select name="size">
                                <option disabled selected hidden value="">Kích thước</option>
                                <c:forEach items = "${requestScope.listAllSize}" var="size">
                                    <option value="${size.sizeId}">${size.sizeName}</option>
                                </c:forEach>
                            </select>
                            <span id="error-info"></span>
                        </div>                              
                        <div class="details-info">
                            <label>Màu sắc:</label>
                            <select name="color">
                                <option disabled selected hidden value="">Màu sắc</option>
                                <c:forEach items = "${requestScope.listAllColor}" var="color">
                                    <option value="${color.colorId}">${color.colorName}</option>
                                </c:forEach>
                            </select>
                            <span id="error-info"></span>
                        </div> 
                        <h3>Giảm giá</h3>
                        <div class="details-info radio">
                            <input type="radio" name="is_sale" class="isSale" value="1" checked> Bật
                            <input type="radio" name="is_sale" class="isSale" value="0"> Tắt
                        </div> 
                        <div class="details-info" id="discount-sale">
                            <label>Mức giảm(%):</label> 
                            <input type="text" name="discount" placeholder="% giảm giá">
                            <span id="error-info"></span>
                        </div>
                        <h3>Ẩn hiện</h3>
                        <div class="details-info radio">                   
                            <input type="radio" name="is_active" class="isActive" value="1" checked> Hiện
                            <input type="radio" name="is_active" class="isActive" value="0"> Ẩn
                        </div>
                    </div>
                    <input type="hidden" value="${requestScope.page}" name="page">
                    <input type="hidden" value="${requestScope.text}" name="text">
                    <input type="hidden" value="${requestScope.cid}" name="cid">
                    <input type="hidden" value="${requestScope.statusSale}" name="statusSale">
                    <input type="hidden" value="${requestScope.statusActive}" name="statusActive">
                </div>
                <button type="submit" id="addBtn"><i class="fa-solid fa-plus"></i> Thêm sản phẩm</button>
                <span id="error-submit"></span>
            </form>
            <button type="button" id="closeBtn" onclick="closePopup('#addForm', '.addPopup')"><i class="fa-solid fa-x"></i></button>
        </div>
        <!--lớp phủ-->
        <div class="all"></div>

        <!--popup xóa sản phẩm-->
        <div class="deletePopup">
            <div class="deletePopup-header">
                <i class="fa-regular fa-trash-can style-trash"></i> Xác nhận xóa?
            </div>
            <h2>Bạn có muốn xóa <span id="product_name"></span> không?</h2>
            <div class="btnConfirm">
                <form action="deleteproductadmin" method="post">
                    <input type="hidden" value="" name="pid" id="product_id">
                    <input type="hidden" value="${requestScope.page}" name="page">
                    <input type="hidden" value="${requestScope.text}" name="text">
                    <input type="hidden" value="${requestScope.cid}" name="cid">
                    <input type="hidden" value="${requestScope.statusSale}" name="statusSale">
                    <input type="hidden" value="${requestScope.statusActive}" name="statusActive">
                    <button type="submit" class="btac agreeBtn" onclick="">Đồng ý</button>
                </form>
                <button type="button" class="btac cancelBtn" onclick="closeConfirmPopup('.deletePopup')">Hủy bỏ</button>
            </div>
        </div>
        <!--popup thống báo-->
        <div class="messPopup">
            <div class="messPopup-header">Thông báo</div>
            <c:if test="${not empty sessionScope.messSuccess}">
                <h3 style="color:green"><i class="fa-regular fa-circle-check"></i> ${sessionScope.messSuccess}</h3>
            </c:if>
            <c:if test="${not empty sessionScope.messFail}">
                <h3 style="color:red"><i class="fa-regular fa-circle-xmark"></i> ${sessionScope.messFail}</h3>
            </c:if>
            <div class="btnConfirm">
                <button type="button" class="btac agreeBtn" onclick="closeConfirmPopup('.messPopup')">Đóng</button>
            </div>
        </div>
        <script>

            // POPUP thông báo khi laod trang xong từ trnag xóa hoặc thêm
            // đồng thời có thì xóa luôn key trên vùn session sau thông báo để tránh load trang lặp lại
            window.addEventListener("load", function () {
            <c:if test="${not empty sessionScope.messSuccess}">
                openPopup(".messPopup");
                <c:remove var="messSuccess" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.messFail}">
                openPopup(".messPopup");
                <c:remove var="messFail" scope="session" />
            </c:if>
            });
        </script>
    </body>
</html>
