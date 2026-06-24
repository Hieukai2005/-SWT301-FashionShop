<%-- 
    Document   : choose-size
    Created on : Feb 20, 2026, 10:35:14 AM
    Author     : dotha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Hướng Dẫn Chọn Size</title>
        <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,400;0,600;1,400&family=DM+Sans:wght@300;400;500&display=swap" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/choose-size.css" rel="stylesheet"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat-ai.css">
    </head>
    <jsp:include page="/header.jsp"></jsp:include>
        <body>
            <div class="page-wrapS">

                <!-- ── Hero ── -->
                <div class="heroS">
                    <h1>Hướng Dẫn Chọn Size</h1>
                    <p>Đơn vị: cm &amp; kg</p>
                    <div class="dividerS"></div>
                </div>

                <!-- ════════════════ NỮ ════════════════ -->
                <div class="gender-blockS" id="nuS">
                    <div class="gender-titleS">
                        <h2>Nữ</h2>
                        <div class="lineS"></div>
                        <span class="badge-labelS">Áo &amp; Quần</span>
                    </div>
                    <div class="sectionS" id="sectionAoNuS">
                        <div class="section-labelS">
                            <h3>Áo / Đồ trên người</h3>
                            <div class="sub-lineS"></div>
                        </div>
                        <p class="unit-noteS">* Đơn vị tính: cm, kg</p>
                        <div class="tbl-wrapS" id="tblAoNuS">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Số đo</th>
                                        <th class="th-sS">S</th>
                                        <th class="th-mS">M</th>
                                        <th class="th-lS">L</th>
                                        <th class="th-xlS">XL</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr><td>Chiều cao</td><td>150–155</td><td>155–163</td><td>160–165</td><td>162–166</td></tr>
                                    <tr><td>Cân nặng</td><td>41–46 kg</td><td>47–52 kg</td><td>53–58 kg</td><td>59–64 kg</td></tr>
                                    <tr><td>Vòng ngực</td><td>79–82</td><td>82–87</td><td>88–94</td><td>94–99</td></tr>
                                    <tr><td>Vòng mông</td><td>88–90</td><td>90–94</td><td>94–98</td><td>98–102</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="sectionS" id="sectionQuanNuS">
                        <div class="section-labelS">
                            <h3>Quần Jeans / Khaki</h3>
                            <div class="sub-lineS"></div>
                        </div>
                        <p class="unit-noteS">* Đơn vị tính: cm</p>
                        <div class="tbl-wrapS" id="tblQuanNuS">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Số đo</th>
                                        <th class="th-sS">27 (S)</th>
                                        <th class="th-mS">28 (M)</th>
                                        <th class="th-lS">29 (L)</th>
                                        <th class="th-xlS">30 (XL)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr><td>Vòng eo</td><td>67.5</td><td>70</td><td>72.5</td><td>75</td></tr>
                                    <tr><td>Vòng mông (slim)</td><td>81.5</td><td>84</td><td>86.5</td><td>89</td></tr>
                                    <tr><td>Vòng mông (regular)</td><td>89.46</td><td>92</td><td>94.5</td><td>97.1</td></tr>
                                    <tr><td>Chiều dài quần</td><td>94</td><td>95</td><td>96</td><td>97</td></tr>
                                    <tr><td>Rộng gấu (slim)</td><td>13</td><td>13.5</td><td>14</td><td>14.5</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="tipsS" id="tipsNuS">
                        <h4>Lưu ý khi chọn size nữ</h4>
                        <ul>
                            <li>Nếu số đo ở ranh giới 2 size, ưu tiên chọn size lớn hơn để thoải mái hơn.</li>
                            <li>Với áo ôm body, bạn nên chọn đúng hoặc nhỏ hơn 1 size.</li>
                            <li>Với áo khoác có lớp lót dày, nên chọn thêm 1 size.</li>
                            <li>Vòng ngực và vòng mông là 2 thông số quan trọng nhất để chọn cỡ quần áo.</li>
                        </ul>
                    </div>
                </div>

                <div class="block-sepS"></div>

                <!-- ════════════════ NAM ════════════════ -->
                <div class="gender-blockS" id="namS">
                    <div class="gender-titleS">
                        <h2>Nam</h2>
                        <div class="lineS"></div>
                        <span class="badge-labelS">Áo &amp; Quần</span>
                    </div>
                    <div class="sectionS" id="sectionAoNamS">
                        <div class="section-labelS">
                            <h3>Áo / Đồ trên người</h3>
                            <div class="sub-lineS"></div>
                        </div>
                        <p class="unit-noteS">* Đơn vị tính: cm, kg</p>
                        <div class="tbl-wrapS" id="tblAoNamS">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Số đo</th>
                                        <th class="th-sS">S</th>
                                        <th class="th-mS">M</th>
                                        <th class="th-lS">L</th>
                                        <th class="th-xlS">XL</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr><td>Chiều cao</td><td>162–168</td><td>169–173</td><td>171–175</td><td>173–177</td></tr>
                                    <tr><td>Cân nặng</td><td>57–62 kg</td><td>63–67 kg</td><td>68–72 kg</td><td>73–77 kg</td></tr>
                                    <tr><td>Vòng ngực</td><td>84–88</td><td>88–94</td><td>94–98</td><td>98–104</td></tr>
                                    <tr><td>Vòng mông</td><td>85–89</td><td>90–94</td><td>95–99</td><td>100–104</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="sectionS" id="sectionQuanNamS">
                        <div class="section-labelS">
                            <h3>Quần Jeans / Khaki</h3>
                            <div class="sub-lineS"></div>
                        </div>
                        <p class="unit-noteS">* Đơn vị tính: cm</p>
                        <div class="tbl-wrapS" id="tblQuanNamS">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Số đo</th>
                                        <th class="th-sS">29 (S)</th>
                                        <th class="th-mS">30 (M)</th>
                                        <th class="th-lS">31 (L)</th>
                                        <th class="th-xlS">32 (XL)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr><td>Vòng eo</td><td>79.5</td><td>82</td><td>84.5</td><td>87</td></tr>
                                    <tr><td>Vòng mông</td><td>96.5</td><td>99</td><td>101.5</td><td>104</td></tr>
                                    <tr><td>Chiều dài quần</td><td>99.8</td><td>100.5</td><td>101.2</td><td>101.2</td></tr>
                                    <tr><td>Rộng ống (slim)</td><td>15.4</td><td>16</td><td>16.6</td><td>17.2</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="tipsS" id="tipsNamS">
                        <h4>Lưu ý khi chọn size nam</h4>
                        <ul>
                            <li>Chiều cao và vòng ngực là 2 thông số quan trọng nhất với áo nam.</li>
                            <li>Áo polo, áo sơ mi: ưu tiên chọn đúng size hoặc size nhỏ hơn để gọn gàng.</li>
                            <li>Áo hoodie, áo khoác: có thể chọn thêm 1 size nếu muốn mặc rộng hơn.</li>
                            <li>Với quần jeans, đo vòng eo và vòng mông để chọn số quần chính xác.</li>
                        </ul>
                    </div>
                </div>

                <div class="block-sepS"></div>

                <!-- ════════════════ UNISEX ════════════════ -->
                <div class="gender-blockS" id="unisexS">
                    <div class="gender-titleS">
                        <h2>Unisex</h2>
                        <div class="lineS"></div>
                        <span class="badge-labelS">Người lớn</span>
                    </div>
                    <div class="sectionS" id="sectionUnisexS">
                        <div class="section-labelS">
                            <h3>Bảng size chung</h3>
                            <div class="sub-lineS"></div>
                        </div>
                        <p class="unit-noteS">* Đơn vị tính: cm, kg</p>
                        <div class="tbl-wrapS" id="tblUnisexS">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Số đo</th>
                                        <th class="th-sS">S</th>
                                        <th class="th-mS">M</th>
                                        <th class="th-lS">L</th>
                                        <th class="th-xlS">XL</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr><td>Chiều cao</td><td>162–168</td><td>169–173</td><td>171–175</td><td>173–177</td></tr>
                                    <tr><td>Cân nặng</td><td>57–62 kg</td><td>63–67 kg</td><td>68–72 kg</td><td>73–77 kg</td></tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="tipsS" id="tipsUnisexS">
                        <h4>Lưu ý khi chọn size Unisex</h4>
                        <ul>
                            <li>Sản phẩm unisex thường có form rộng hơn, nữ nên chọn nhỏ hơn 1 size so với thông thường.</li>
                            <li>Áo unisex ưu tiên theo chiều cao hơn cân nặng.</li>
                            <li>Nếu muốn mặc oversize, hãy lên thêm 1–2 size so với gợi ý.</li>
                        </ul>
                    </div>
                </div>

            </div>
        </body>
    <jsp:include page="/chat-AI.jsp"></jsp:include>
    <jsp:include page="/footer.jsp"></jsp:include>
</html>