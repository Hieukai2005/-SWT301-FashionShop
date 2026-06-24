<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html lang="vi">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>VINAFA SALE</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-style.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat-ai.css">
                <script defer src="${pageContext.request.contextPath}/js/filter.js"></script>
            </head>
            <!-- Header -->
            <jsp:include page="/header.jsp"></jsp:include>

            <body>
                <!-- Modal chọn thông tin sản phẩm trước khi thêm vào giỏ hàng -->
                <!-- Banner -->
                <div class="banner" style="position: relative; height: 400px; background: #d32f2f; overflow: hidden;">
                    <img src="https://2885371169.e.cdneverest.net/Simiconnector/24-25_1_bannercate_desktop-230126.webp"
                        alt="Canifa Sale Banner" style="width:100%; height:400px; object-fit:cover; display:block;">
                    <div class="banner-content"
                        style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; display: flex; flex-direction: column; justify-content: flex-start; align-items: flex-start; background: rgba(0,0,0,0);">
                        <h1
                            style="color: #fff; margin: 90px 0 0 40px; text-shadow: 1px 1px 8px #b71c1c; font-size: 3.2rem;">
                            Sale
                        </h1>
                        <div class="breadcrumb" style="margin-left: 40px; color: #fff; margin-top: 10px;">
                            <a href="#" style="color: #fff; text-decoration: underline;">Sản Phẩm</a> /
                            <span>Sale</span>
                        </div>
                    </div>
                </div>

                <!-- Category Tabs -->
                <div style="max-width: 1200px; margin: 0 auto; padding: 0 20px; margin-bottom: 30px;">
                    <div class="category-tabs">
                        <button class="active">Tất cả</button>
                    </div>
                </div>

                <!-- Main Container -->
                <div class="container">
                    <!-- Sidebar -->
                    <aside class="sidebar">
                        <form action="product">
                            <input type="hidden" name="gender" value="${requestScope.gender}">
                            <!-- Danh mục -->
                            <div class="filter-section">
                                <h3>
                                    Danh mục
                                    <span class="toggle-icon"><i class="fas fa-chevron-down"></i></span>
                                </h3>
                                <div class="filter-content">
                                    <input type="radio" name="cid" ${requestScope.cid==0 ? 'checked' : '' }
                                        value="${category.categoryId}" onchange="this.form.submit()"> Tất cả
                                    <br>
                                    <div style="margin: 5px 0"></div>
                                    <!--Load danh mục-->
                                    <c:forEach items="${requestScope.listAllCategory}" var="category">
                                        <input type="radio" name="cid" ${requestScope.cid==category.categoryId
                                            ? 'checked' : '' } value="${category.categoryId}"
                                            onchange="this.form.submit()"> ${category.categoryName}
                                        <br>
                                        <div style="margin: 5px 0"></div>
                                    </c:forEach>
                                </div>
                            </div>

                            <!-- Phần trăm giảm -->
                            <div class="filter-section">
                                <h3>
                                    Phần trăm giảm
                                    <span class="toggle-icon"><i class="fas fa-chevron-down"></i></span>
                                </h3>
                                <div class="filter-content">
                                    <label><input type="radio" name="discountRange" value="0"
                                            onchange="this.form.submit()" ${requestScope.discountRange==0 ? 'checked'
                                            : '' }> Tất cả</label>
                                    <label><input type="radio" name="discountRange" value="1"
                                            onchange="this.form.submit()" ${requestScope.discountRange==1 ? 'checked'
                                            : '' }> &lt; 20%</label>
                                    <label><input type="radio" name="discountRange" value="2"
                                            onchange="this.form.submit()" ${requestScope.discountRange==2 ? 'checked'
                                            : '' }> 20% - 30%</label>
                                    <label><input type="radio" name="discountRange" value="3"
                                            onchange="this.form.submit()" ${requestScope.discountRange==3 ? 'checked'
                                            : '' }> 30% - 40%</label>
                                    <label><input type="radio" name="discountRange" value="4"
                                            onchange="this.form.submit()" ${requestScope.discountRange==4 ? 'checked'
                                            : '' }> 40% - 50%</label>
                                    <label><input type="radio" name="discountRange" value="5"
                                            onchange="this.form.submit()" ${requestScope.discountRange==5 ? 'checked'
                                            : '' }> 50%+</label>
                                </div>
                            </div>
                            <!-- Kích cỡ -->
                            <!--                    <div class="filter-section">
                                            <h3>
                                                Kích cỡ
                                                <span class="toggle-icon"><i class="fas fa-chevron-down"></i></span>
                                            </h3>
                                            <div class="filter-content">
                                                <label><input type="checkbox"> XS</label>
                                                <label><input type="checkbox"> S</label>
                                                <label><input type="checkbox"> M</label>
                                                <label><input type="checkbox"> L</label>
                                                <label><input type="checkbox"> XL</label>
                                            </div>
                                        </div>-->
                            <!-- Màu sắc -->
                            <!--                    <div class="filter-section">
                                            <h3>
                                                Màu sắc
                                                <span class="toggle-icon"><i class="fas fa-chevron-down"></i></span>
                                            </h3>
                                            <div class="filter-content">
                                                <label><input type="checkbox"> Đỏ</label>
                                                <label><input type="checkbox"> Xanh</label>
                                                <label><input type="checkbox"> Vàng</label>
                                                <label><input type="checkbox"> Đen</label>
                                                <label><input type="checkbox"> Trắng</label>
                                            </div>
                                        </div>-->
                            <!--Gía tiền-->
                            <div class="filter-section">
                                <h3>
                                    Khoảng giá
                                    <span class="toggle-icon"><i class="fas fa-chevron-down"></i></span>
                                </h3>
                                <div class="filter-content">
                                    <label><input type="radio" name="priceRange" value="0" onchange="this.form.submit()"
                                            ${requestScope.priceRange==0 ? 'checked' : '' }>Tất cả</label>
                                    <label><input type="radio" name="priceRange" value="1" onchange="this.form.submit()"
                                            ${requestScope.priceRange==1 ? 'checked' : '' }>Dưới 200.000đ</label>
                                    <label><input type="radio" name="priceRange" value="2" onchange="this.form.submit()"
                                            ${requestScope.priceRange==2 ? 'checked' : '' }>200.000đ - 500.000đ</label>
                                    <label><input type="radio" name="priceRange" value="3" onchange="this.form.submit()"
                                            ${requestScope.priceRange==3 ? 'checked' : '' }>500.000đ -
                                        1.000.000đ</label>
                                    <label><input type="radio" name="priceRange" value="4" onchange="this.form.submit()"
                                            ${requestScope.priceRange==4 ? 'checked' : '' }>Trên 1.000.000đ</label>
                                </div>
                            </div>
                        </form>
                    </aside>

                    <!-- Main Content -->
                    <div class="main-content">
                        <!-- Content Header -->
                        <div class="content-header">
                            <div class="product-count">${requestScope.numberOfProduct} sản phẩm</div>

                            <form action="product">
                                <input type="hidden" name="gender" value="${requestScope.gender}">
                                <input type="hidden" name="page" value="${requestScope.page}">
                                <input type="hidden" name="cid" value="${requestScope.cid}">
                                <input type="hidden" name="discountRange" value="${requestScope.discountRange}">
                                <input type="hidden" name="priceRange" value="${requestScope.priceRange}">
                                <div class="frame-search-sort">
                                    <!--tìm kiếm-->
                                    <div class="search-div">
                                        <input type="text"
                                            value="${not empty requestScope.textSearch ? requestScope.textSeach : ''}"
                                            name="text_search" class="search-input" placeholder="Tìm kiếm...">
                                        <button type="submit" class="search-btn"
                                            style="position:absolute; right:6px; top:50%; transform:translateY(-50%); background:transparent; border:none; cursor:pointer;">
                                            <i class="fas fa-search"></i>
                                        </button>
                                    </div>
                                    <!--.-->
                                    <div class="sort-section">
                                        <label for="sort">Sắp xếp:</label>
                                        <select id="sort" name="sort" onchange="this.form.submit()">
                                            <option value="" ${requestScope.priceRange=="" ? 'selected' : '' }>Tất cả
                                            </option>
                                            <option value="newest" ${requestScope.sort=="newest" ? 'selected' : '' }>Mới
                                                nhất</option>
                                            <option value="lowToHigh" ${requestScope.sort=="lowToHigh" ? 'selected' : ''
                                                }>Giá: thấp đến cao</option>
                                            <option value="highToLow" ${requestScope.sort=="highToLow" ? 'selected' : ''
                                                }>Giá: cao đến thấp</option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!-- Products Grid -->
                        <div class="products-grid">
                            <c:forEach items="${requestScope.listPaging}" var="product">
                                <a href="${pageContext.request.contextPath}/product-detail?id=${product.productId}"
                                    class="anifa-card-link">
                                    <div class="anifa-card" data-id="${product.productId}"
                                        data-name="${product.productName}" data-price="${product.price}"
                                        data-image="${pageContext.request.contextPath}/images/${product.image}"
                                        data-category-id="${product.categoryId}" data-discount="${product.discount}">

                                        <div class="anifa-card-img">
                                            <img src="${pageContext.request.contextPath}/images/${product.image}"
                                                alt="${product.description}">
                                            <!-- Badge: Hàng mới hoặc % giảm -->
                                            <c:choose>
                                                <c:when test="${product.discount > 0}">
                                                    <span
                                                        class="anifa-badge anifa-badge-sale">-${product.discount}%</span>
                                                </c:when>
                                            </c:choose>
                                            <!-- Cart icon bottom-right -->
                                            <button class="anifa-cart-btn" title="Thêm vào giỏ"
                                                onclick="event.preventDefault(); event.stopPropagation();">
                                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                                                    stroke="currentColor" stroke-width="1.8" stroke-linecap="round"
                                                    stroke-linejoin="round">
                                                    <rect x="3" y="3" width="18" height="18" rx="3" ry="3"></rect>
                                                    <path d="M12 8v8M8 12h8"></path>
                                                </svg>
                                            </button>
                                        </div>

                                        <!-- Color variant thumbnails -->
                                        <div class="anifa-color-row">
                                            <div class="anifa-color-thumb active">
                                                <img src="${pageContext.request.contextPath}/images/${product.image}"
                                                    alt="">
                                            </div>
                                        </div>

                                        <div class="anifa-card-info">
                                            <div class="anifa-card-name">${product.productName}</div>
                                            <div class="anifa-card-price">
                                                <c:if test="${product.discount > 0}">
                                                    <span class="anifa-price-current">
                                                        <fmt:formatNumber value="${product.getSalePrice()}"
                                                            type="number" groupingUsed="true"></fmt:formatNumber> ₫
                                                    </span>
                                                    <span class="anifa-price-old">
                                                        <fmt:formatNumber value="${product.price}" type="number"
                                                            groupingUsed="true"></fmt:formatNumber> ₫
                                                    </span>
                                                </c:if>
                                                <c:if test="${product.discount == 0}">
                                                    <span class="anifa-price-current">
                                                        <fmt:formatNumber value="${product.getSalePrice()}"
                                                            type="number" groupingUsed="true"></fmt:formatNumber> ₫
                                                    </span>
                                                </c:if>
                                            </div>
                                            <!-- Tags -->
                                            <div class="anifa-tag-row">
                                                <c:if test="${product.discount > 0}">
                                                    <span class="anifa-tag anifa-tag-exclusive">Giá độc quyền
                                                        online</span>
                                                </c:if>
                                                <c:if test="${product.discount == 0 && product.price < 399000}">
                                                    <span class="anifa-tag anifa-tag-sale">Giá tốt</span>
                                                </c:if>
                                                <c:if test="${product.price >= 399000}">
                                                    <span class="anifa-tag anifa-tag-ship">Freeship</span>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </c:forEach>
                        </div>
                        <!--Paging-->
                        <div class="pagination-SP">
                            <c:if test="${not empty requestScope.emptyProduct}">
                                <p style="color:red;text-align: center">${requestScope.emptyProduct}</p>
                            </c:if>
                            <c:if test="${startPage > 1}">
                                <a class="page-btnSP"
                                    href="${pageContext.request.contextPath}/product?page=${startPage - 1}&gender=${requestScope.gender}&cid=${requestScope.cid}&discountRange=${requestScope.discountRange}&priceRange=${requestScope.priceRange}&sort=${requestScope.sort}">
                                    <i class="fas fa-chevron-left"></i>
                                </a>
                            </c:if>
                            <c:forEach begin="${startPage}" end="${endPage}" var="no">
                                <a href="${pageContext.request.contextPath}/product?page=${no}&gender=${requestScope.gender}&cid=${requestScope.cid}&discountRange=${requestScope.discountRange}&priceRange=${requestScope.priceRange}&sort=${requestScope.sort}"
                                    class="page-btnSP paging ${no == requestScope.page ? 'active' : ''}">
                                    ${no}
                                </a>
                            </c:forEach>
                            <c:if test="${endPage < numberOfPage}">
                                <a class="page-btnSP"
                                    href="${pageContext.request.contextPath}/product?page=${endPage + 1}&gender=${requestScope.gender}&cid=${requestScope.cid}&discountRange=${requestScope.discountRange}&priceRange=${requestScope.priceRange}&sort=${requestScope.sort}">
                                    <i class="fas fa-chevron-right"></i>
                                </a>
                            </c:if>
                        </div>
                        <div
                            style="text-align: center; color: #777; font-size: 0.95rem; font-weight: 500; font-family: 'Playfair Display', serif; text-transform: uppercase; letter-spacing: 1.5px; margin-bottom: 40px;">
                            Tổng số trang: ${numberOfPage}
                        </div>
                        <!-- Load More -->
                        <!--                <div class="load-more-section">
                                    <button class="load-more-btn">
                                        <i class="fas fa-plus"></i> Xem thêm
                                    </button>
                                    <div class="product-stats">
                                        Hiển thị <strong>24</strong> trên tổng số <strong>499</strong> sản phẩm
                                    </div>
                                </div>-->
                    </div>
                </div>
            </body>
            <!-- Footer Start -->
            <jsp:include page="/chat-AI.jsp"></jsp:include>
            <jsp:include page="/footer.jsp"></jsp:include>
            <script>
                document.querySelectorAll(".filter-section").forEach(section => {
                    var header = section.querySelector("h3");
                    var content = section.querySelector(".filter-content");
                    var icon = section.querySelector(".toggle-icon");
                    header.addEventListener("click", function () {
                        content.classList.toggle("collapsed");
                        icon.classList.toggle("active");

                    });
                });
            </script>
            <!-- Footer End -->
            <script>
                document.querySelectorAll(".filter-section").forEach(section => {
                    var header = section.querySelector("h3");
                    var content = section.querySelector(".filter-content");
                    var icon = section.querySelector(".toggle-icon");
                    header.addEventListener("click", function () {
                        content.classList.toggle("collapsed");
                        icon.classList.toggle("active");

                    });
                });
            </script>

            </html>