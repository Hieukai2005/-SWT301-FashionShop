<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chỉnh sửa sản phẩm - Admin Dashboard</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com" rel="preconnect"/>
        <link crossorigin="" href="https://fonts.gstatic.com" rel="preconnect"/>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght@100..700,0..1&display=swap" rel="stylesheet"/>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/update-page.css"/>
        <script src="${pageContext.request.contextPath}/js/update-page.js" defer></script>
    </head>
    <body>

        <jsp:include page="taskbar.jsp"></jsp:include>

            <div class="main-containerDB">
                <div class="content-wrapper-custom">

                    <!--                    <nav aria-label="breadcrumb">
                                            <ol class="breadcrumb">
                                                <li class="breadcrumb-item"><a class="text-decoration-none text-muted" href="#">Quay lại</a></li>                            
                                            </ol>
                                        </nav>-->

                    <div class="card">
                        <div class="card-header">
                            <span class="material-symbols-outlined section-title-icon">Thông tin chung</span> 
                        </div>
                        <form action="updateproduct" method="post" id="updateForm">

                            <div class="card-body">
                                <div class="row g-4">
                                <c:set var="product" value="${requestScope.product}"/>
                                <input type="hidden" name="pid" value="${product.productId}">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label class="form-label">Tên sản phẩm</label>
                                        <input class="form-control" type="text" value="${product.productName}" name="name"/>
                                        <span class="error-info"></span>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Giá tiền (VNĐ)</label>
                                            <input class="form-control" type="number" value="${product.price}" name="price"/>
                                            <span class="error-info"></span>                                       
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Danh mục</label>
                                            <select class="form-select" name="category">
                                                <c:forEach items="${requestScope.listAllCategory}" var="cate">
                                                    <c:if test="${cate.categoryId == product.categoryId}">
                                                        <option value="${cate.categoryId}" selected>${cate.categoryName}</option>
                                                    </c:if>
                                                    <option value="${cate.categoryId}">${cate.categoryName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="gendis mb-3">
                                        <div class="gender">
                                            <label class="form-label">Giới tính</label>
                                            <select name="gender" class="form-select" id="gd">
                                                <option value="Nam" ${product.gender eq "Nam" ? 'selected' : ''}>Nam</option>
                                                <option value="Nữ" ${product.gender eq "Nữ" ? 'selected' : ''}>Nữ</option>
                                                <option value="Unisex" ${product.gender eq "Unisex" ? 'selected' : ''}>Unisex</option>
                                            </select>
                                        </div>
                                        <div class="discount">
                                            <label class="form-label">Giảm giá</label><br>
                                            <input type="radio" class="is_sale" name="is_sale" value="1" ${product.isSale == true ? 'checked' : ''}>Bật
                                            <input type="radio" class="is_sale" name="is_sale" value="0" ${product.isSale == false ? 'checked' : ''}>Tắt
                                        </div>                        
                                    </div>
                                    <div class="gendis">
                                        <div class="dis mb-3 ${product.isSale == true ? 'vis' : ''}">
                                            <label class="form-label">% Giảm giá</label>
                                            <input class="form-control" type="number" id="discount" name="discount" value="${product.discount}"/>
                                            <span class="error-info"></span>                                       
                                        </div>
                                        <div class="active">
                                            <label class="form-label">Ẩn hiện</label><br>
                                            <input type="radio" class="is_active" name="is_active" value="1" ${product.isActive == true ? 'checked' : ''}>Bật
                                            <input type="radio" class="is_active" name="is_active" value="0" ${product.isActive == false ? 'checked' : ''}>Tắt
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label class="form-label">Hình ảnh</label>
                                        <input class="form-control" name="image" type="text" value="${product.image}"/>
                                        <span class="error-info"></span>                                    
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Mô tả sản phẩm</label>
                                        <textarea class="form-control" id="description" rows="4" name="description">${product.description}</textarea>
                                        <span class="error-info"></span>                                    
                                    </div>
                                </div>
                            </div>
                            <div class="text-end mt-3 border-top pt-3">
                                <button class="btn btn-primary btn-update" type="submit" name="action" value="editProductInfo">
                                    <span class="material-symbols-outlined align-bottom me-1">Cập nhật thông tin chung</span> 
                                </button>
                                <div>
                                    <c:if test="${not empty requestScope.updateSucc}">
                                        <span style="color:green;font-size: 0.7rem">* ${requestScope.updateSucc} </span>
                                    </c:if> 
                                    <c:if test="${not empty requestScope.updateFail}">
                                        <span style="color:red;font-size: 0.7rem">* ${requestScope.updateFail} </span>
                                    </c:if>
                                </div>                                                               
                            </div>
                        </div>
                    </form>

                </div>

                <div class="card">
                    <div class="card-header">
                        <span class="material-symbols-outlined section-title-icon">Quản lý biến thể</span> 
                    </div>
                    <div class="card-body">
                        <div class="bg-light p-4 rounded-3 mb-4">
                            <h6 class="text-muted text-uppercase mb-3 fw-bold" style="font-size: 0.75rem;">Thêm biến thể mới</h6>
                            <form action="addproductvariant" method="post" id="addVariantForm">
                                <input type="hidden" name="pid" value="${product.productId}">
                                <div class="row g-3 align-items-end">
                                    <div class="col-md-2">
                                        <label class="form-label small">Kích thước</label>
                                        <select class="form-select form-select-sm" name="size">
                                            <c:forEach items="${requestScope.listAllSize}" var="size">
                                                <option value="${size.sizeId}">${size.sizeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <label class="form-label small">Màu sắc</label>
                                        <select class="form-select form-select-sm" name="color">
                                            <c:forEach items="${requestScope.listAllColor}" var="color">
                                                <option value="${color.colorId}">${color.colorName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <label class="form-label small">Số lượng</label>
                                        <input class="form-control form-control-sm" placeholder="0" type="number" name="quantity"/>
                                        <span class="error-info"></span>                                    
                                    </div>
                                    <div class="col-md-3">
                                        <label class="form-label small">Nhập ảnh</label>
                                        <input class="form-control img" placeholder="URL ảnh..." type="text" name="image_main" />
                                        <span class="error-info"></span>                                        
                                    </div>

                                    <div class="col-md-2">
                                        <button class="btn btn-primary btn-sm w-100" type="submit" name="action" value="addVariant" >
                                            <span class="material-symbols-outlined align-bottom me-1">Thêm</span>                                           
                                        </button>                                       
                                    </div>
                                </div>
                            </form>

                        </div>

                        <div class="table-responsive">
                            <table class="table align-middle">
                                <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Kích thước</th>
                                        <th>Màu sắc</th>
                                        <th>Số lượng</th>
                                        <th>Ảnh biến thể</th>
                                        <th class="text-end">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listProductVariant}" var="productVariant" >
                                        <tr>
                                            <td>${productVariant.variantId}</td>
                                            <td><span class="variant-badge">${productVariant.size.sizeName}</span></td>
                                            <td><span></span>${productVariant.color.colorName}</td>
                                            <td class="fw-medium">${productVariant.quantity}</td>
                                            <td class="img-variant"> <img src="${pageContext.request.contextPath}/images/${productVariant.image}" alt="alt" width="136px" height="150px"/></td>
                                            <td id="operation" data-pvid="${productVariant.variantId}" data-size="${productVariant.size.sizeName}" data-color="${productVariant.color.colorName}" data-quantity="${productVariant.quantity}" data-image="${productVariant.image}">                                               
                                                <button class="action-iconDH edit-iconDH" onclick="editProductVariant(this, '.updatePopup')">
                                                    <i class="fas fa-edit"></i>
                                                </button>

                                                <button class="btn btn-link text-danger p-1" onclick="openConfirmPopup('.deleteVariantPopup', '${productVariant.variantId}', '${productVariant.productId}')">
                                                    <span class="material-symbols-outlined"><i class="fa-solid fa-trash-can"></i></span></button>
                                            </td>

                                        </tr>
                                    </c:forEach>                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div> 
                <!--popup sửa sản phẩm-->
                <div class="updatePopup">
                    <h3 id="idVariant"></h3>
                    <form action="updateproductvariant" method="post" id="updateVariantForm">
                        <div class="frame">
                            <input type="hidden" name="pid" value="${product.productId}">
                            <input type="hidden" name="pvid" value="">
                            <div class="details-info">
                                <label>Kích thước sản phẩm:</label>
                                <input type="text" id="size" name="size" value="" readonly>
                            </div>
                            <div class="details-info">
                                <label>Màu sản phẩm: </label>
                                <input type="text" id="color" name="color" value="" readonly>
                            </div>
                            <div class="details-info">
                                <label>Số lượng: </label>
                                <input type="number" name="quantity" placeholder="Số lượng" value="">
                                <span class="error-info"></span>
                            </div>
                            <div class="details-info">
                                <label>Ảnh chính:</label>
                                <input type="text" name="image_main" placeholder="Ảnh chính" value="" readonly>
                                <!--<span class="error-info"></span>-->                                        
                            </div> 
                            <!--                                    <div class="details-info">
                                                                    <label>Ảnh chi tiết:</label>
                                                                    <input type="text" name="image_details" placeholder="Ảnh chi tiết" value="">
                                                                    <span class="error-info"></span>
                                                                </div>-->

                            <button type="submit" id="updateBtn"><i class="fa-solid fa-plus"></i>Sửa biến thể</button>
                            <span id="error-submit"></span>
                        </div>
                    </form>
                    <button type="button" id="closeBtn" onclick="closeConfirmPopup('.updatePopup')"><i class="fa-solid fa-x"></i></button>
                </div>
                <!--Chi tiết ảnh-->
                <div class="card">
                    <div class="card-header">
                        <span class="material-symbols-outlined section-title-icon">Quản lí ảnh chi tiết</span> 
                    </div>
                    <div class="card-body">
                        <div class="bg-light p-4 rounded-3 mb-4">
                            <h6 class="text-muted text-uppercase mb-3 fw-bold" style="font-size: 0.75rem;">Thêm ảnh chi tiết mới</h6>
                            <form action="addproductimage" method="post" id="addImagesForm">
                                <input type="hidden" name="pid" value="${product.productId}">
                                <div class="row g-3 align-items-end">                                                                   
                                    <div class="col-md-10">
                                        <label class="form-label small">Nhập ảnh chi tiết</label>
                                        <input class="form-control" placeholder="URL ảnh..." type="text" name="image_details"/>
                                        <span class="error-info"></span>
                                    </div>
                                    <div class="col-md-2">
                                        <button class="btn btn-primary btn-sm w-100" type="submit">
                                            <span class="material-symbols-outlined align-bottom me-1">Thêm</span>                                           
                                        </button>                                       
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="table-responsive">
                            <table class="table align-middle">
                                <thead>
                                    <tr>
                                        <th>Mã ảnh</th>
                                        <th>Ảnh chi tiết</th>
                                        <th>Đường dẫn ảnh</th>                                        
                                        <th class="text-end">Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listImageDetails}" var="imageDetails" >
                                        <tr>
                                            <td>#${imageDetails.imageId}</td>
                                            <td id="imageDetails"><img src="images/${imageDetails.imagePath}" alt="Ảnh chi tiết sản phẩm"/></td>
                                            <td><span class="variant-badge">${imageDetails.imagePath}</span></td>                                           
                                            <td id="deleteImages">                                                                                             
                                                <button class="btn btn-link text-danger p-1" onclick="openConfirmPopup('.deleteImagePopup', '${imageDetails.imageId}', '${imageDetails.productId}')">
                                                    <span class="material-symbols-outlined"><i class="fa-solid fa-trash-can"></i></span></button>
                                            </td>
                                        </tr>
                                    </c:forEach>                                    
                                </tbody>
                            </table>
                        </div>                        
                    </div>
                </div> 
                <!--popup xóa biến thể-->
                <div class="deletePopup deleteVariantPopup">
                    <div class="deletePopup-header">
                        <i class="fa-regular fa-trash-can style-trash"></i> Xác nhận xóa?
                    </div>
                    <h2>Bạn có muốn xóa biến thể <span class="id_num"></span> không?</h2>
                    <div class="btnConfirm">
                        <form action="deleteproductvariant" method="post">
                            <input type="hidden" name="pvid" value='' class="data_id">
                            <input type="hidden" name="pid" value='' class='pidDelete'>
                            <button type="submit" class="btac agreeBtn">Đồng ý</button>
                        </form>
                        <button type="button" class="btac cancelBtn" onclick="closeConfirmPopup('.deleteVariantPopup')">Hủy bỏ</button>
                    </div>
                </div>
                <!--popup xóa ảnh-->
                <div class="deletePopup deleteImagePopup">
                    <div class="deletePopup-header">
                        <i class="fa-regular fa-trash-can style-trash"></i> Xác nhận xóa?
                    </div>
                    <h2>Bạn có muốn xóa ảnh <span class="id_num"></span> không?</h2>
                    <div class="btnConfirm">
                        <form action="deleteproductimage" method="post">
                            <input type="hidden" name="image_id" value='' class="data_id">
                            <input type="hidden" name="pid" value='' class="pidDelete">
                            <button type="submit" class="btac agreeBtn">Đồng ý</button>
                        </form>
                        <button type="button" class="btac cancelBtn" onclick="closeConfirmPopup('.deleteImagePopup')">Hủy bỏ</button>
                    </div>
                </div>
                <!--.-->
                <!--lớp phủ-->
                <div class="all"></div>
                <!--Thông báo-->
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
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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