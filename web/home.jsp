<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>VINAFA HOME</title>
        <link rel=" stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat-ai.css">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>

    <body>
    <!-- Header (giống product.html) -->
    <jsp:include page="/header.jsp"></jsp:include>
        <!--         header end-->

            <!-- Banner lớn ưu đãi nổi bật (slider) -->
            <div class="main-banner">
                <div class="main-banner-slider">
                    <img src="https://2885371169.e.cdneverest.net/Simiconnector/BannerSlider/a/r/artboard-web-190126.webp"
                         alt="Banner 2" class="main-banner-img" />
                    <img src="https://2885371169.e.cdneverest.net/Simiconnector/BannerSlider/a/o/aophong-desk-210326.webp"
                         alt="Banner 3" class="main-banner-img" />
                    <img src="https://2885371169.e.cdneverest.net/Simiconnector/BannerSlider/s/a/sales_topbanner_desktop-210126.webp"
                         alt="Banner 3" class="main-banner-img" />
                    <img src="https://2885371169.e.cdneverest.net/Simiconnector/BannerSlider/m/d/mddt_topbanner_desktop-050226.webp"
                         alt="Banner 3" class="main-banner-img" />
                </div>
            </div>
            <script src="${pageContext.request.contextPath}/js/banner-slider.js"></script>

        <!-- Ưu đãi nổi bật -->
        <c:if test="${not empty listVouchers}">
            <section class="section-highlight">
                <h2>Voucher Dành Cho Bạn</h2>
                <div class="highlight-list">
                    <c:if test="${sessionScope.customer == null}">
                        <a href="${pageContext.request.contextPath}/login" style="text-decoration: none">
                            <p id="login-voucher">Đăng nhập để xem các ưu đãi dành cho bạn!</p>
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.customer != null}">
                        <c:forEach items="${listVouchers}" var="voucher">
                            <div class="highlight-item">
                                <div class="voucher-box">
                                    <div class="voucher-code">${voucher.code}</div>
                                    <div class="voucher-discount">Giảm
                                        <c:choose>
                                            <c:when
                                                test="${fn:toLowerCase(voucher.discountType) == 'percent'}">
                                                <fmt:formatNumber value="${voucher.discountValue}"
                                                                  type="number" groupingUsed="true"></fmt:formatNumber>%
                                                (Tối đa <fmt:formatNumber value="${voucher.maxDiscount}"
                                                                  type="number" groupingUsed="true"></fmt:formatNumber>đ)
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatNumber value="${voucher.discountValue}"
                                                                  type="number" groupingUsed="true"></fmt:formatNumber>đ
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="voucher-condition">Đơn tối thiểu <fmt:formatNumber
                                            value="${voucher.minOrderValue}"></fmt:formatNumber>đ</div>
                                    <div class="voucher-expiry">HSD: ${voucher.endDateFormat}</div>
                                    <button class="voucher-btn" onclick="copyCode(this)"
                                            data-code="${voucher.code}">Copy Mã</button>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
            </section>
        </c:if>

        <section class="uniform-section">
            <div class="uniform-card"
                 onclick="window.location.href = '${pageContext.request.contextPath}/product?gender=Nam'">
                <img src="https://media.canifa.com/wysiwyg/cms/cmspage2-02072025-1.webp"
                     alt="Đồng phục học sinh" class="uniform-img">
                <div class="uniform-content">
                    <h2>Nam</h2>
                    <p>Chất liệu cao cấp, thoáng mát, mang lại cảm giác thoải mái khi mặc cả ngày.</p>
                </div>
            </div>
            <div class="uniform-card"
                 onclick="window.location.href = '${pageContext.request.contextPath}/product?gender=Nữ'">
                <img src="https://media.canifa.com/wysiwyg/cms/cmspage2-02072025-2.webp"
                     alt="Đồng phục giáo viên" class="uniform-img">
                <div class="uniform-content">
                    <h2>Nữ</h2>
                    <p>Thiết kế nữ tính, thanh lịch, phù hợp với nhiều phong cách thời trang khác nhau.</p>
                </div>
            </div>
            <div class="uniform-card"
                 onclick="window.location.href = '${pageContext.request.contextPath}/product?gender=Unisex'">
                <img src="https://media.canifa.com/wysiwyg/cms/cmspage2-02072025-3.webp"
                     alt="Đồng phục theo bối cảnh" class="uniform-img">
                <div class="uniform-content">
                    <h2>Unisex</h2>
                    <p>Phong cách trẻ trung, năng động, dễ dàng thể hiện cá tính riêng.</p>
                </div>
            </div>
        </section>

        <!-- Flash Sale -->
        <div class="tshirt-banner-wrapper">
            <h2 class="section-title tshirt-banner-text" style="color: #fff; z-index: 2; position: relative;">Flash Sale</h2>
            <img src="https://2885371169.e.cdneverest.net/Simiconnector/1_NAM_T-SHIRT_desktop-110326.webp" alt="Flash Sale Background" class="tshirt-banner-img">
        </div>
        <section class="section-flash-sale" style="padding-top: 0; margin-top: 0;">
            <div class="products-grid best-grid-overlap">
                <!-- Sản phẩm flash sale mẫu -->
                <c:forEach items="${requestScope.listFlashSaleProducts}" var="saleProduct" begin="0"
                           end="6">
                    <div class="product-card"
                         onclick="window.location.href = '${pageContext.request.contextPath}/product-detail?id=${saleProduct.productId}'">
                        <div class="product-image">
                            <img src="${pageContext.request.contextPath}/images/${saleProduct.image}"
                                 alt="Flash Sale" />
                            <div class="discount-badge">${saleProduct.discount}%</div>
                        </div>
                        <div class="product-info">
                            <div class="product-name">${saleProduct.productName}</div>
                            <div class="product-price">
                                <span class="price-sale">
                                    <fmt:formatNumber value="${saleProduct.getSalePrice()}">
                                    </fmt:formatNumber>₫
                                </span>
                                <span class="price-original">
                                    <fmt:formatNumber value="${saleProduct.price}"></fmt:formatNumber>₫
                                    </span>
                                </div>
                            </div>
                        </div>
                </c:forEach>
                <div class="product-card"
                     onclick="window.location.href = '${pageContext.request.contextPath}/product?isSale=true'">
                    <div class="product-image">
                        <img src="http://png.pngtree.com/png-clipart/20210829/original/pngtree-flash-sales-promotion-banner-with-lightning-illustration-png-image_6684312.jpg"
                             alt="Flash Sale" />
                    </div>
                    <div class="product-info">
                        <div class="product-name" style="color: red">Xem Thêm!</div>
                    </div>
                </div>
            </div>
        </section>
        <script src="../js/clock.js"></script>

        <!-- Sản phẩm mới -->
        <div class="tshirt-banner-wrapper video-banner-wrapper">
            <h2 class="section-title tshirt-banner-text">Sản phẩm mới</h2>
            <iframe id="youtube-banner-video" class="tshirt-banner-iframe" 
                    src="https://www.youtube.com/embed/S674dnY3vP0?enablejsapi=1&autoplay=1&mute=1&controls=0&showinfo=0&rel=0&loop=1&playlist=S674dnY3vP0&start=2" 
                    frameborder="0" 
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" 
                    allowfullscreen>
            </iframe>
        </div>
        <section class="section-new" style="padding-top: 0; margin-top: 0;">
            <div class="products-grid best-grid-overlap">
                <!--sản phẩm mới-->
                <c:forEach items="${requestScope.listNewestProducts}" var="newProduct">
                    <div class="product-card"
                         onclick="window.location.href = '${pageContext.request.contextPath}/product-detail?id=${newProduct.productId}'">
                        <div class="product-image">
                            <img src="${pageContext.request.contextPath}/images/${newProduct.image}"
                                 alt="Sản phẩm mới" />
                        </div>
                        <div class="product-info">
                            <div class="product-name">${newProduct.productName}</div>
                            <div class="product-price">
                                <span class="price-sale">
                                    <fmt:formatNumber value="${newProduct.price}"></fmt:formatNumber>₫
                                    </span>
                                </div>
                            </div>
                        </div>
                </c:forEach>
                <!--.-->
        </section>

        <!-- Sản phẩm bán chạy -->
        <div class="tshirt-banner-wrapper">
            <h2 class="section-title tshirt-banner-text">Sản phẩm bán chạy</h2>
            <img src="https://2885371169.e.cdneverest.net/Simiconnector/1_T-SHIRT_DESKTOP.webp" alt="T-Shirt Collection" class="tshirt-banner-img">
        </div>
        <section class="best-seller" style="padding-top: 0; margin-top: 0;">

            <div class="best-grid best-grid-overlap">
                <c:forEach items="${bestSellerList}" var="p">
                    <a class="best-card" href="product-detail?id=${p.productId}">

                        <div class="best-img-wrap">
                            <img class="best-img" src="${pageContext.request.contextPath}/images/${p.image}"
                                 alt="${p.productName}">
                        </div>

                        <div class="best-name">${p.productName}</div>

                        <div class="best-price">
                            <div class="best-price">
                                <c:choose>
                                    <c:when test="${p.price != null && p.price > 0}">
                                        <fmt:formatNumber value="${p.price}" maxFractionDigits="0" /> đ
                                    </c:when>
                                    <c:otherwise>
                                        Liên hệ
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                    </a>
                </c:forEach>
            </div>
        </section>
    </body>
    <!-- Footer (giống product.html) -->
    <jsp:include page="/chat-AI.jsp"></jsp:include>
    <jsp:include page="/footer.jsp"></jsp:include>
    <!-- Footer End -->

    <script>
        function copyCode(t) {
            var voucherCode = t.dataset.code;
            navigator.clipboard.writeText(voucherCode)
                    .then(() => {
                        alert("Đã copy: " + voucherCode);
                    })
                    .catch(err => {
                        console.error("Lỗi copy:", err);
                    });
        }
        ;
    </script>

</html>