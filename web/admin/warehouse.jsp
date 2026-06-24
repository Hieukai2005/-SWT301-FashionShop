<%-- 
    Document   : warehouse
    Created on : Jan 31, 2026, 6:29:10 PM
    Author     : dotha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>
    <body>
        <jsp:include page="taskbar.jsp"></jsp:include>
        <div id="warehouseKH">
            <h1 class="page-titleWH"><i class="fas fa-warehouse"></i> Quản lý Kho Hàng</h1>

            <!-- Stats -->
            <div class="stats-rowWH">
                <div class="stat-boxWH">
                    <div class="stat-headerWH">
                        <div>
                            <div class="stat-numberWH">12,456</div>
                            <div class="stat-textWH">Tổng tồn kho</div>
                        </div>
                        <div class="stat-iconWH" style="background: linear-gradient(135deg, #667eea, #764ba2);">
                            <i class="fas fa-boxes"></i>
                        </div>
                    </div>
                </div>
                <div class="stat-boxWH">
                    <div class="stat-headerWH">
                        <div>
                            <div class="stat-numberWH">856</div>
                            <div class="stat-textWH">Sản phẩm trong kho</div>
                        </div>
                        <div class="stat-iconWH" style="background: linear-gradient(135deg, #f093fb, #f5576c);">
                            <i class="fas fa-box"></i>
                        </div>
                    </div>
                </div>
                <div class="stat-boxWH">
                    <div class="stat-headerWH">
                        <div>
                            <div class="stat-numberWH">45</div>
                            <div class="stat-textWH">Sắp hết hàng</div>
                        </div>
                        <div class="stat-iconWH" style="background: linear-gradient(135deg, #4facfe, #00f2fe);">
                            <i class="fas fa-exclamation-triangle"></i>
                        </div>
                    </div>
                </div>
                <div class="stat-boxWH">
                    <div class="stat-headerWH">
                        <div>
                            <div class="stat-numberWH">23</div>
                            <div class="stat-textWH">Hết hàng</div>
                        </div>
                        <div class="stat-iconWH" style="background: linear-gradient(135deg, #43e97b, #38f9d7);">
                            <i class="fas fa-ban"></i>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Toolbar -->
            <div class="toolbar-WH">
                <div class="search-boxWH">
                    <i class="fas fa-search"></i>
                    <input type="text" placeholder="Tìm kiếm sản phẩm trong kho...">
                </div>
                <div class="filter-groupWH">
                    <select class="filter-selectWH">
                        <option>Tất cả danh mục</option>
                        <option>Áo nam</option>
                        <option>Áo nữ</option>
                        <option>Quần nam</option>
                        <option>Quần nữ</option>
                        <option>Phụ kiện</option>
                    </select>
                    <select class="filter-selectWH">
                        <option>Tất cả trạng thái</option>
                        <option>Còn nhiều</option>
                        <option>Sắp hết</option>
                        <option>Hết hàng</option>
                    </select>
                    <button class="export-btnWH">
                        <i class="fas fa-download"></i> Xuất báo cáo
                    </button>
                </div>
            </div>

            <!-- Table -->
            <div class="table-containerWH">
                <table class="tableWH">
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>SKU</th>
                            <th>Danh mục</th>
                            <th>Tồn kho</th>
                            <th>Đã bán</th>
                            <th>Giá nhập</th>
                            <th>Giá bán</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <div class="product-cellWH">
                                    <div class="product-thumbWH" style="background: linear-gradient(135deg, #667eea, #764ba2);"><i class="fas fa-tshirt"></i></div>
                                    <span class="product-nameWH">Áo sơ mi nam cao cấp</span>
                                </div>
                            </td>
                            <td>SKU001</td>
                            <td>Áo nam</td>
                            <td>
                                <span class="stock-levelWH stock-highWH">125</span>
                                <div class="progress-barWH"><div class="progress-fillWH" style="width:85%; background:#27ae60;"></div></div>
                            </td>
                            <td>245</td>
                            <td>300,000₫</td>
                            <td>450,000₫</td>
                            <td><span class="status-badgeWH status-successWH">Còn nhiều</span></td>
                            <td>
                                <div class="action-cellWH">
                                    <button class="action-iconWH view-iconWH"><i class="fas fa-eye"></i></button>
                                    <button class="action-iconWH edit-iconWH"><i class="fas fa-edit"></i></button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="product-cellWH">
                                    <div class="product-thumbWH" style="background: linear-gradient(135deg, #f093fb, #f5576c);"><i class="fas fa-female"></i></div>
                                    <span class="product-nameWH">Đầm nữ thanh lịch</span>
                                </div>
                            </td>
                            <td>SKU002</td>
                            <td>Áo nữ</td>
                            <td>
                                <span class="stock-levelWH stock-highWH">89</span>
                                <div class="progress-barWH"><div class="progress-fillWH" style="width:70%; background:#27ae60;"></div></div>
                            </td>
                            <td>178</td>
                            <td>380,000₫</td>
                            <td>580,000₫</td>
                            <td><span class="status-badgeWH status-successWH">Còn nhiều</span></td>
                            <td>
                                <div class="action-cellWH">
                                    <button class="action-iconWH view-iconWH"><i class="fas fa-eye"></i></button>
                                    <button class="action-iconWH edit-iconWH"><i class="fas fa-edit"></i></button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="product-cellWH">
                                    <div class="product-thumbWH" style="background: linear-gradient(135deg, #4facfe, #00f2fe);"><i class="fas fa-male"></i></div>
                                    <span class="product-nameWH">Quần jean nam slim fit</span>
                                </div>
                            </td>
                            <td>SKU003</td>
                            <td>Quần nam</td>
                            <td>
                                <span class="stock-levelWH stock-mediumWH">15</span>
                                <div class="progress-barWH"><div class="progress-fillWH" style="width:25%; background:#f39c12;"></div></div>
                            </td>
                            <td>412</td>
                            <td>450,000₫</td>
                            <td>680,000₫</td>
                            <td><span class="status-badgeWH status-pendingWH">Sắp hết</span></td>
                            <td>
                                <div class="action-cellWH">
                                    <button class="action-iconWH view-iconWH"><i class="fas fa-eye"></i></button>
                                    <button class="action-iconWH edit-iconWH"><i class="fas fa-edit"></i></button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="product-cellWH">
                                    <div class="product-thumbWH" style="background: linear-gradient(135deg, #fa709a, #fee140);"><i class="fas fa-user-tag"></i></div>
                                    <span class="product-nameWH">Áo khoác bomber unisex</span>
                                </div>
                            </td>
                            <td>SKU004</td>
                            <td>Áo nam</td>
                            <td>
                                <span class="stock-levelWH stock-highWH">67</span>
                                <div class="progress-barWH"><div class="progress-fillWH" style="width:60%; background:#27ae60;"></div></div>
                            </td>
                            <td>134</td>
                            <td>600,000₫</td>
                            <td>890,000₫</td>
                            <td><span class="status-badgeWH status-successWH">Còn nhiều</span></td>
                            <td>
                                <div class="action-cellWH">
                                    <button class="action-iconWH view-iconWH"><i class="fas fa-eye"></i></button>
                                    <button class="action-iconWH edit-iconWH"><i class="fas fa-edit"></i></button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="product-cellWH">
                                    <div class="product-thumbWH" style="background: linear-gradient(135deg, #a8edea, #fed6e3);"><i class="fas fa-shopping-bag"></i></div>
                                    <span class="product-nameWH">Quần short nữ thời trang</span>
                                </div>
                            </td>
                            <td>SKU005</td>
                            <td>Quần nữ</td>
                            <td>
                                <span class="stock-levelWH stock-highWH">142</span>
                                <div class="progress-barWH"><div class="progress-fillWH" style="width:90%; background:#27ae60;"></div></div>
                            </td>
                            <td>89</td>
                            <td>210,000₫</td>
                            <td>320,000₫</td>
                            <td><span class="status-badgeWH status-successWH">Còn nhiều</span></td>
                            <td>
                                <div class="action-cellWH">
                                    <button class="action-iconWH view-iconWH"><i class="fas fa-eye"></i></button>
                                    <button class="action-iconWH edit-iconWH"><i class="fas fa-edit"></i></button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="product-cellWH">
                                    <div class="product-thumbWH" style="background: linear-gradient(135deg, #d299c2, #fef9d7);"><i class="fas fa-hat-cowboy"></i></div>
                                    <span class="product-nameWH">Mũ snapback thời trang</span>
                                </div>
                            </td>
                            <td>SKU006</td>
                            <td>Phụ kiện</td>
                            <td>
                                <span class="stock-levelWH stock-lowWH">0</span>
                                <div class="progress-barWH"><div class="progress-fillWH" style="width:0%; background:#e74c3c;"></div></div>
                            </td>
                            <td>256</td>
                            <td>120,000₫</td>
                            <td>180,000₫</td>
                            <td><span class="status-badgeWH status-cancelledWH">Hết hàng</span></td>
                            <td>
                                <div class="action-cellWH">
                                    <button class="action-iconWH view-iconWH"><i class="fas fa-eye"></i></button>
                                    <button class="action-iconWH edit-iconWH"><i class="fas fa-edit"></i></button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="product-cellWH">
                                    <div class="product-thumbWH" style="background: linear-gradient(135deg, #667eea, #764ba2);"><i class="fas fa-socks"></i></div>
                                    <span class="product-nameWH">Tất nam cao cấp (3 đôi)</span>
                                </div>
                            </td>
                            <td>SKU007</td>
                            <td>Phụ kiện</td>
                            <td>
                                <span class="stock-levelWH stock-highWH">234</span>
                                <div class="progress-barWH"><div class="progress-fillWH" style="width:95%; background:#27ae60;"></div></div>
                            </td>
                            <td>445</td>
                            <td>60,000₫</td>
                            <td>95,000₫</td>
                            <td><span class="status-badgeWH status-successWH">Còn nhiều</span></td>
                            <td>
                                <div class="action-cellWH">
                                    <button class="action-iconWH view-iconWH"><i class="fas fa-eye"></i></button>
                                    <button class="action-iconWH edit-iconWH"><i class="fas fa-edit"></i></button>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="product-cellWH">
                                    <div class="product-thumbWH" style="background: linear-gradient(135deg, #ff9a9e, #fecfef);"><i class="fas fa-vest"></i></div>
                                    <span class="product-nameWH">Áo polo nam classic</span>
                                </div>
                            </td>
                            <td>SKU008</td>
                            <td>Áo nam</td>
                            <td>
                                <span class="stock-levelWH stock-mediumWH">22</span>
                                <div class="progress-barWH"><div class="progress-fillWH" style="width:30%; background:#f39c12;"></div></div>
                            </td>
                            <td>367</td>
                            <td>250,000₫</td>
                            <td>380,000₫</td>
                            <td><span class="status-badgeWH status-pendingWH">Sắp hết</span></td>
                            <td>
                                <div class="action-cellWH">
                                    <button class="action-iconWH view-iconWH"><i class="fas fa-eye"></i></button>
                                    <button class="action-iconWH edit-iconWH"><i class="fas fa-edit"></i></button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
