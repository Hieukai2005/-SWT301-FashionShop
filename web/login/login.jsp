
<%-- 
    Document   : login
    Created on : Jan 24, 2026, 10:23:48 PM
    Author     : dotha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login - Canifa Fashion</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login2-style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>

      <body class="page-login">
        <div id="preload-block">
            <div class="square-block"></div>
        </div>

        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12 col-sm-7 col-md-5 col-lg-4 authfy-panel-left">

                    <div class="brand-logo text-center">
                        <img src="${pageContext.request.contextPath}/images/logo/vinafa.jpg" width="150" alt="brand-logo" style="border-radius: 18px; box-shadow: 0 15px 35px rgba(200, 20, 30, 0.2); border: 3px solid #fff; margin-bottom: 15px; display: inline-block;">
                        <span class="fashion-tagline">Style & Elegance</span>
                    </div>

                    <div class="authfy-login">
                        <div class="authfy-panel panel-login text-center active">

                            <div class="authfy-heading">
                                <h3 class="auth-title">Đăng nhập tài khoản</h3>
                                <div class="decorative-line"></div>
                                <p>Bạn không có tài khoản?
                                    <a href="${pageContext.request.contextPath}/signup">Đăng ký ngay!</a>
                                </p>
                            </div>

                            <div class="row loginOr">
                                <div class="col-xs-12 col-sm-12">
                                    <span class="spanOr">or</span>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12 col-sm-12">

                                    <form class="loginForm" action="${pageContext.request.contextPath}/login" method="POST">
                                        <div class="form-group input-icon-wrapper">
                                            <i class="fa-solid fa-envelope input-icon"></i>
                                            <input type="text" class="form-control email" name="username" placeholder="Địa chỉ Email" value="${requestScope.userC}">
                                        </div>

                                        <div class="form-group">
                                            <div class="pwdMask input-icon-wrapper">
                                                <i class="fa-solid fa-lock input-icon"></i>
                                                <input type="password" class="form-control password" name="password" placeholder="Mật khẩu" style="padding-left: 45px;" value="${requestScope.passC}">
                                                <span class="fa-solid fa-eye-slash pwd-toggle"></span>
                                            </div>
                                        </div>

                                        <c:if test="${not empty sessionScope.successMessage}">
                                            <div style="color:green; margin-bottom: 10px; text-align: left;">
                                                <i class="fa-solid fa-check-circle"></i> ${sessionScope.successMessage}
                                            </div>
                                            <c:remove scope="session" var="successMessage"/>
                                        </c:if>

                                        <div style="color:red; text-align: left;">${requestScope.error}</div>

                                        <div class="row remember-row">
                                            <div class="col-xs-6 col-sm-6">
                                                <label class="checkbox text-left">
                                                    <input type="checkbox" name="remember" value="ON" ${requestScope.remember}>
                                                    <span class="label-text">Ghi nhớ</span>
                                                </label>
                                            </div>
                                            <div class="col-xs-6 col-sm-6">
                                                <p class="forgotPwd">
                                                    <a href="${pageContext.request.contextPath}/forgotpassword">Quên mật khẩu?</a>
                                                </p>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <button class="btn btn-lg btn-primary btn-block" type="submit">
                                                <i class="fa-solid fa-right-to-bracket me-2"></i> Đăng nhập
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
                            <h3>Chào mừng bạn<br>đến với Vinafa</h3>
                            <div class="hero-decorative-line"></div>
                            <p style="max-width: 450px; margin: 0 auto">
                                Khám phá những bộ sưu tập thời trang hiện đại,
                                chất lượng cao và phù hợp cho mọi phong cách.
                                Trải nghiệm mua sắm tiện lợi và đẳng cấp.
                            </p>
                            <div class="fashion-quote">
                                "Fashion is the armor to survive the reality of everyday life."
                                <span>— Bill Cunningham</span>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/custom.js"></script>
    </body>
</html>