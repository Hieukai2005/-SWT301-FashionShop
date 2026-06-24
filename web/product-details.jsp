<%-- Document : product-details Created on : Jan 25, 2026, 2:56:09 PM Author : dotha --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                    <!DOCTYPE html>
                    <html lang="vi">

                    <head>
                        <meta charset="UTF-8">
                        <title>Chi tiết sản phẩm</title>

                        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-style.css">
                        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
                        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat-ai.css">
                        <link rel="stylesheet"
                            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
                    </head>
                    <jsp:include page="/header.jsp"></jsp:include>

                    <body>
                        <c:set var="colorHex" value="#ccc" />

                        <c:choose>
                            <c:when test="${c.colorId == 1}">
                                <c:set var="colorHex" value="#000000" /> <!-- Đen -->
                            </c:when>
                            <c:when test="${c.colorId == 2}">
                                <c:set var="colorHex" value="#9E9E9E" /> <!-- Xám -->
                            </c:when>
                            <c:when test="${c.colorId == 3}">
                                <c:set var="colorHex" value="#E53935" /> <!-- Đỏ -->
                            </c:when>
                            <c:when test="${c.colorId == 4}">
                                <c:set var="colorHex" value="#D7CCC8" /> <!-- Be -->
                            </c:when>
                            <c:when test="${c.colorId == 5}">
                                <c:set var="colorHex" value="#FFFFFF" /> <!-- Trắng -->
                            </c:when>
                            <c:when test="${c.colorId == 6}">
                                <c:set var="colorHex" value="#FDD835" /> <!-- Vàng -->
                            </c:when>
                            <c:when test="${c.colorId == 7}">
                                <c:set var="colorHex" value="#EC407A" /> <!-- Hồng -->
                            </c:when>
                            <c:when test="${c.colorId == 8}">
                                <c:set var="colorHex" value="#6D4C41" /> <!-- Nâu -->
                            </c:when>
                            <c:when test="${c.colorId == 9}">
                                <c:set var="colorHex" value="#1E88E5" /> <!-- Xanh -->
                            </c:when>
                        </c:choose>
                        <c:if test="${empty requestScope.product}">
                            <div
                                style="max-width:1100px;margin:40px auto;background:#fff;padding:20px;border-radius:8px">
                                <h2>Không tìm thấy sản phẩm.</h2>
                                <a href="${pageContext.request.contextPath}/home">Quay lại trang home</a>
                            </div>
                        </c:if>

                        <c:if test="${not empty requestScope.product}">
                            <c:set var="product" value="${requestScope.product}" />

                            <div class="product-details-container">

                                <!-- Gallery -->
                                <div class="product-gallery">
                                    <img id="mainImage" class="main-image"
                                        src="${pageContext.request.contextPath}/images/product/${mainImage}"
                                        alt="${product.productName}">

                                    <div class="thumbnail-list">
                                        <c:forEach items="${thumbs}" var="t">
                                            <img class="thumbnail"
                                                src="${pageContext.request.contextPath}/images/product/${t.imagePath}"
                                                alt="thumb" onclick="changeMain(this.src)">
                                        </c:forEach>
                                    </div>

                                </div>

                                <div id="variantData" style="display:none;">
                                    <c:forEach items="${variants}" var="v">
                                        <span class="variant-item" data-colorid="${v.colorId}" data-sizeid="${v.sizeId}"
                                            data-qty="${v.quantity}">
                                        </span>
                                    </c:forEach>
                                </div>
                                <!-- để JS biết color hiện tại (server đã chọn) -->
                                <div id="pageState" data-colorid="${selectedColorId}"></div>
                                <!-- Info -->
                                <div class="product-info" data-id="${product.productId}"
                                    data-name="${product.productName}" data-price="${product.price}"
                                    data-image="${pageContext.request.contextPath}/images/${product.image}">

                                    <h1 class="product-title">${product.productName}</h1>

                                    <div class="product-code">Mã sản phẩm: ${product.productId}</div>

                                    <!-- Giá -->
                                    <div class="product-price">
                                        <!-- Nếu bạn có getSalePrice() thì dùng, không có thì dùng công thức -->
                                        <c:choose>
                                            <c:when test="${product.discount > 0}">
                                                <!-- Nếu bạn có getSalePrice() thì dùng, không có thì dùng công thức -->
                                                <span class="price-sale">
                                                    <fmt:formatNumber
                                                        value="${product.price - (product.price * product.discount / 100.0)}"
                                                        type="number" groupingUsed="true" />đ
                                                </span>
                                                <span class="price-original">
                                                    <fmt:formatNumber value="${product.price}" type="number"
                                                        groupingUsed="true" />đ
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="price-sale">
                                                    <fmt:formatNumber value="${product.price}" type="number"
                                                        groupingUsed="true" />đ
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <!-- Màu + size bạn đang để demo -->
                                    <div class="product-color">
                                        <span>Màu sắc:</span>

                                        <c:forEach items="${colors}" var="c">
                                            <%-- map color_id → màu --%>
                                                <c:set var="colorHex" value="#ccc" />
                                                <c:choose>
                                                    <c:when test="${c.colorId == 1}">
                                                        <c:set var="colorHex" value="#000000" />
                                                    </c:when>
                                                    <c:when test="${c.colorId == 2}">
                                                        <c:set var="colorHex" value="#9E9E9E" />
                                                    </c:when>
                                                    <c:when test="${c.colorId == 3}">
                                                        <c:set var="colorHex" value="#E53935" />
                                                    </c:when>
                                                    <c:when test="${c.colorId == 4}">
                                                        <c:set var="colorHex" value="#D7CCC8" />
                                                    </c:when>
                                                    <c:when test="${c.colorId == 5}">
                                                        <c:set var="colorHex" value="#FFFFFF" />
                                                    </c:when>
                                                    <c:when test="${c.colorId == 6}">
                                                        <c:set var="colorHex" value="#FDD835" />
                                                    </c:when>
                                                    <c:when test="${c.colorId == 7}">
                                                        <c:set var="colorHex" value="#EC407A" />
                                                    </c:when>
                                                    <c:when test="${c.colorId == 8}">
                                                        <c:set var="colorHex" value="#6D4C41" />
                                                    </c:when>
                                                    <c:when test="${c.colorId == 9}">
                                                        <c:set var="colorHex" value="#1E88E5" />
                                                    </c:when>
                                                </c:choose>

                                                <a
                                                    href="${pageContext.request.contextPath}/product-detail?id=${product.productId}&color=${c.colorId}&size=${selectedSizeId}">
                                                    <button type="button"
                                                        class="color-swatch ${c.colorId == selectedColorId ? 'active' : ''}"
                                                        title="${c.colorName}" style="background:${colorHex}"
                                                        onclick="window.location.href = '${pageContext.request.contextPath}/product-detail?id=${product.productId}&color=${c.colorId}&size=${selectedSizeId}'">
                                                    </button>
                                                </a>
                                        </c:forEach>
                                    </div>

                                    <!-- size trong th hết hàng thì k chọn được để thêm vào giỏ   -->

                                    <div class="product-size">
                                        <span>Chọn size:</span>

                                        <div class="size-list">
                                            <c:forEach items="${sizes}" var="s">
                                                <c:set var="stock" value="${sizeStockMap[s.sizeId]}" />

                                                <c:choose>
                                                    <c:when test="${stock != null && stock > 0}">
                                                        <button type="button"
                                                            class="size-btn ${s.sizeId == selectedSizeId ? 'active' : ''}"
                                                            onclick="window.location.href = '${pageContext.request.contextPath}/product-detail?id=${product.productId}&color=${selectedColorId}&size=${s.sizeId}'">
                                                            ${s.sizeName}
                                                        </button>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <button type="button" class="size-btn disabled" disabled>
                                                            ${s.sizeName}
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                        <a href="${pageContext.request.contextPath}/choose-size.jsp" class="size-guide-btn">
                                            <i class="fas fa-ruler-horizontal"></i> Hướng dẫn chọn size
                                        </a>
                                    </div>

                                    <div class="stock-info" style="margin-top:10px;">
                                        <span>Còn lại trong kho: </span>
                                        <strong id="stockText">
                                            <c:choose>
                                                <c:when test="${stockQty != null}">${stockQty}</c:when>
                                                <c:otherwise>--</c:otherwise>
                                            </c:choose>
                                        </strong>
                                    </div>

                                    <div class="product-quantity">
                                        <span>Số lượng:</span>
                                        <button class="qty-btn" id="decrease" type="button">-</button>
                                        <input type="text" value="1" id="quantity" readonly>
                                        <button class="qty-btn" id="increase" type="button">+</button>
                                    </div>

                                    <!-- Nút thêm giỏ -->
                                    <c:if test="${stockQty != null && stockQty == 0}">
                                        <button class="add-to-cart-btn" disabled style="opacity:0.5">
                                            Hết hàng
                                        </button>
                                    </c:if>

                                    <c:if test="${stockQty == null || stockQty > 0}">
                                        <button class="add-to-cart-btn" data-quantity="${stockQty}">
                                            Thêm vào giỏ hàng
                                        </button>
                                    </c:if>

                                    <!-- Mô tả -->
                                    <div class="product-description">
                                        <h2>Mô tả sản phẩm</h2>
                                        <p>
                                            <c:choose>
                                                <c:when test="${not empty product.description}">
                                                    ${product.description}
                                                </c:when>
                                                <c:otherwise>
                                                    (Chưa có mô tả cho sản phẩm này.)
                                                </c:otherwise>
                                            </c:choose>
                                        </p>
                                    </div>

                                    <!-- Thông tin chi tiết (fit model) -->
                                    <div class="product-details-tab">
                                        <div class="details-tab-header" onclick="toggleDetails()">
                                            <h2>Thông tin chi tiết</h2>
                                            <i class="fas fa-chevron-down" id="detailsToggleIcon"></i>
                                        </div>
                                        <div class="details-tab-content" id="detailsContent">
                                            <div class="detail-row">
                                                <span class="detail-label">Danh mục</span>
                                                <span class="detail-value">
                                                    <span class="badge badge-inactive">Category ID:
                                                        ${product.categoryId}</span>
                                                </span>
                                            </div>
                                            <div class="detail-row">
                                                <span class="detail-label">Giới tính</span>
                                                <span class="detail-value">${product.gender}</span>
                                            </div>
                                            <div class="detail-row">
                                                <span class="detail-label">Giảm giá</span>
                                                <span class="detail-value">${product.discount}%</span>
                                            </div>
                                            <div class="detail-row">
                                                <span class="detail-label">Còn hàng</span>
                                                <span class="detail-value">${stockQty != null ? stockQty : '--'}</span>
                                            </div>
                                            <div class="detail-row">
                                                <span class="detail-label">Tình trạng Sale</span>
                                                <span class="detail-value">
                                                    <c:choose>
                                                        <c:when test="${product.isSale}">
                                                            <span class="badge badge-sale">Đang sale
                                                                ${product.discount}%</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            Không áp dụng
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>
                                            <div class="detail-row">
                                                <span class="detail-label">Trạng thái</span>
                                                <span class="detail-value">
                                                    <c:choose>
                                                        <c:when test="${product.isActive}">
                                                            <span class="badge badge-active"><i
                                                                    class="fas fa-check-circle"
                                                                    style="margin-right: 4px;"></i>Đang mở bán</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-inactive"><i
                                                                    class="fas fa-times-circle"
                                                                    style="margin-right: 4px;"></i>Ngừng kinh
                                                                doanh</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>
                                            <div class="detail-row">
                                                <span class="detail-label">Ngày tạo</span>
                                                <span class="detail-value">${product.createdAt}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Script for toggle Details -->
                                    <script>
                                        function toggleDetails() {
                                            var content = document.getElementById("detailsContent");
                                            var header = document.querySelector(".details-tab-header");
                                            if (content.classList.contains("show")) {
                                                content.classList.remove("show");
                                                header.classList.remove("active");
                                            } else {
                                                content.classList.add("show");
                                                header.classList.add("active");
                                            }
                                        }
                                    </script>
                                </div>
                            </div>
                        </c:if>
                        <div class="related-products-section" style="max-width: 100%; margin: 40px auto 0 auto; padding: 0;">
                            <div class="video-banner-section">
                                <video autoplay muted loop playsinline class="bg-video">
                                    <source src="${pageContext.request.contextPath}/images/video/cmspage3-video-30012026.mp4" type="video/mp4">
                                </video>
                                <div class="video-overlay">
                                    <h2>SẢN PHẨM CÙNG PHONG CÁCH</h2>
                                </div>
                            </div>

                            <c:if test="${empty relatedProducts}">
                                <div class="no-related-products" style="text-align:center; padding: 50px;">
                                    Hiện tại chưa có sản phẩm cùng phong cách.
                                </div>
                            </c:if>

                            <c:if test="${not empty relatedProducts}">
                                <div class="related-products-container">
                                    <div class="related-products-list">
                                        <c:forEach items="${relatedProducts}" var="product">
                                            <a href="${pageContext.request.contextPath}/product-detail?id=${product.productId}" style="text-decoration:none; color:inherit;">
                                                <div class="related-product-item">
                                                    <div style="position:relative; width:100%;">
                                                        <img src="${pageContext.request.contextPath}/images/${product.image}"
                                                            alt="${product.productName}" />
                                                        <c:if test="${product.discount > 0}">
                                                            <div class="discount-badge">${product.discount}%</div>
                                                        </c:if>
                                                    </div>
                                                    <div class="product-info-related">
                                                        <div class="related-product-title">
                                                            ${product.productName}
                                                        </div>
                                                        <div class="related-product-price">
                                                            <!-- If you don't have getSalePrice in this scope, use price logic -->
                                                            <c:if test="${product.discount > 0}">
                                                                <span style="color:#e53935; font-weight:bold; font-size:1.05rem;"><fmt:formatNumber value="${product.price - (product.price * product.discount / 100.0)}" type="number" groupingUsed="true" />đ</span>
                                                                <span style="color:#999; text-decoration:line-through; font-size:0.85rem; margin-left:8px;"><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />đ</span>
                                                            </c:if>
                                                            <c:if test="${product.discount == 0}">
                                                                <span style="color:#e53935; font-weight:bold; font-size:1.05rem;"><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />đ</span>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </div>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div id="selectOptionPopup" class="popup-overlay">
                            <div class="popup-box">
                                <h3>Lỗi</h3>
                                <p>Bạn đang <strong id="content"></strong>hãy chọn lại trước khi thêm sản phẩm vào giỏ
                                    hàng.</p>
                                <button id="ok">OK</button>
                            </div>
                        </div>
                    </body>
                    <jsp:include page="/chat-AI.jsp"></jsp:include>
                    <jsp:include page="/footer.jsp"></jsp:include>
                    <script src="${pageContext.request.contextPath}/js/product-details.js"></script>

                    </html>