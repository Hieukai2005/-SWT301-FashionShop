<%-- 
    Document   : signup
    Created on : Jan 26, 2026, 11:20:57 PM
    Author     : Tien Hoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sign Up - Canifa Fashion</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login2-style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>

    <body class="page-signup">
        <div id="preload-block">
            <div class="square-block"></div>
        </div>

        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12 col-sm-7 col-md-5 col-lg-4 authfy-panel-left">

                    <div class="brand-logo text-center">
                        <img src="${pageContext.request.contextPath}/images/logo/vinafa.jpg" width="150" alt="brand-logo" style="border-radius: 18px; box-shadow: 0 15px 35px rgba(200, 20, 30, 0.2); border: 3px solid #fff; margin-bottom: 15px; display: inline-block;">
                        <span class="fashion-tagline">Join Our Fashion Family</span>
                    </div>

                    <div class="authfy-login">
                        <div class="authfy-panel panel-signup text-center active">

                            <div class="authfy-heading">
                                <h3 class="auth-title">Đăng ký miễn phí!</h3>
                                <div class="decorative-line"></div>
                                <p>Đã có tài khoản?
                                    <a href="${pageContext.request.contextPath}/dispatchcontroller?action=login">Đăng nhập</a>
                                </p>
                            </div>

                            <div class="row">
                                <div class="col-xs-12 col-sm-12">

                                    <form class="signupForm" action="${pageContext.request.contextPath}/signup" method="POST">
                                        <div class="form-group input-icon-wrapper">
                                            <i class="fa-solid fa-envelope input-icon"></i>
                                            <input type="email" class="form-control" name="username" placeholder="Địa chỉ Email" required>
                                        </div>

                                        <div class="form-group input-icon-wrapper">
                                            <i class="fa-solid fa-user input-icon"></i>
                                            <input type="text" class="form-control" name="fullname" placeholder="Họ và tên" required>
                                        </div>

                                        <div class="form-group">
                                            <div class="pwdMask input-icon-wrapper">
                                                <i class="fa-solid fa-lock input-icon"></i>
                                                <input type="password" class="form-control" name="password" placeholder="Mật khẩu" style="padding-left: 45px;" required>
                                                <span class="fa-solid fa-eye-slash pwd-toggle"></span>
                                            </div>
                                        </div>
                                        <c:if test="${requestScope.messageSuccess ne null}">
                                            <div style="color:green;text-align: start; margin-bottom: 18px">* ${requestScope.messageSuccess}</div>
                                        </c:if>
                                        <c:if test="${requestScope.messageFail ne null}">
                                            <div style="color:red;text-align: start;margin-bottom: 18px">* ${requestScope.messageFail}</div>
                                        </c:if>
                                        <div class="form-group">
                                            <p class="term-policy text-muted small">
                                                <label class="policy-check">
                                                    <input type="checkbox" required>
                                                    Tôi đồng ý với 
                                                    <a href="${pageContext.request.contextPath}/login/privacy.jsp">Chính sách quyền riêng tư</a> 
                                                    và 
                                                    <a href="${pageContext.request.contextPath}/login/terms.jsp">Điều khoản dịch vụ</a>.
                                                </label>
                                            </p>
                                        </div>

                                        <div class="form-group">
                                            <button class="btn btn-lg btn-primary btn-block" type="submit">
                                                <i class="fa-solid fa-user-plus me-2"></i> Đăng ký
                                            </button>
                                        </div>
                                    </form>

                                    <a href="${pageContext.request.contextPath}/home" class="back-to-home" style="display:block;margin-top:16px;">
                                        &larr; Quay về trang chủ
                                    </a>

                                </div>
                            </div>

                        </div>
                    </div>

                </div>

                <div class="col-md-7 col-lg-8 authfy-panel-right hidden-xs hidden-sm">
                    <div class="hero-heading">
                        <div class="headline">
                            <h3>Gia nhập cộng đồng<br>thời trang Vinafa</h3>
                            <div class="hero-decorative-line"></div>
                            <p style="max-width: 450px; margin: 0 auto">
                                Tạo tài khoản để nhận ưu đãi độc quyền, theo dõi đơn hàng
                                và khám phá bộ sưu tập mới nhất từ Vinafa.
                            </p>
                            <div class="fashion-quote">
                                "Elegance is not standing out, but being remembered."
                                <span>— Giorgio Armani</span>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/custom.js"></script>
    </body>
</html>