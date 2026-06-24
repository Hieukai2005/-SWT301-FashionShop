
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
        <title>Login</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login2-style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    </head>

    <body class="page-forgot">
        <div id="preload-block">
            <div class="square-block"></div>
        </div>

        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12 col-sm-7 col-md-5 col-lg-4 authfy-panel-left">

                    <div class="brand-logo text-center">
                        <img src="${pageContext.request.contextPath}/images/logo/vinafa.jpg" width="150" alt="brand-logo" style="border-radius: 18px; box-shadow: 0 15px 35px rgba(200, 20, 30, 0.2); border: 3px solid #fff; margin-bottom: 15px; display: inline-block;">
                    </div>

                    <div class="authfy-login">
                        <div class="authfy-panel panel-login text-center active">

                            <div class="authfy-heading">
                                <h3 class="auth-title">Lấy lại mật khẩu bằng email</h3>
                                <p>Bạn Không có tài khoản?
                                    <a href="${pageContext.request.contextPath}/signup">Đăng ký ngay!</a>
                                </p>
                            </div>
                            <!--
                                                        <div class="row loginOr">
                                                            <div class="col-xs-12 col-sm-12">
                                                                <span class="spanOr">or</span>
                                                            </div>
                                                        </div>-->

                            <div class="row">
                                <div class="col-xs-12 col-sm-12">

                                    <form class="loginForm" action="forgotpassword" method="POST">
                                        <div class="form-group">
                                            <input type="text" class="form-control email" name="email" placeholder="Email address">
                                        </div>

                                        <div class="form-group">
                                            <div class="pwdMask">
                                                <input type="text" class="form-control password" name="fullname" placeholder="Full Name">
                                                <span class="fa-solid fa-eye-slash pwd-toggle"></span>
                                            </div>
                                        </div>
                                        <div>
                                            <c:if test="${not empty sessionScope.mailSuccess}">
                                                <p style="color:green;text-align: start">* ${sessionScope.mailSuccess}</p>
                                                <c:remove scope="session" var="mailSuccess"/>
                                            </c:if>
                                            <c:if test="${not empty sessionScope.mailFail}">
                                                <p style="color:red;text-align: start">* ${sessionScope.mailFail}</p>
                                                <c:remove scope="session" var="mailFail"/>
                                            </c:if>
                                        </div>
                                        <div style="color:red">${requestScope.error}</div>

                                        <!--                                        <div class="row remember-row">
                                                                                    <div class="col-xs-6 col-sm-6">
                                                                                        <label class="checkbox text-left">
                                                                                            <input type="checkbox" value="remember-me">
                                                                                            <span class="label-text">Remember me</span>
                                                                                        </label>
                                                                                    </div>
                                                                                    <div class="col-xs-6 col-sm-6">
                                                                                        <p class="forgotPwd">
                                                                                             nếu bạn có trang forgot.jsp thì đổi link 
                                                                                            <a href="#">Quên mật khẩu?</a>
                                                                                        </p>
                                                                                    </div>
                                                                                </div>-->

                                        <div class="form-group">
                                            <button class="btn btn-lg btn-primary btn-block" type="submit">
                                                Gửi đến Email 
                                            </button>
                                        </div>
                                    </form>

                                    <a href="${pageContext.request.contextPath}/userinfomation" class="back-to-home" style="display:block;margin-top:16px;">
                                        &larr; Quay về đăng nhập
                                    </a>

                                </div>
                            </div>

                        </div>
                    </div>

                </div>

                <div class="col-md-7 col-lg-8 authfy-panel-right hidden-xs hidden-sm">
                    <div class="hero-heading">
                        <div class="headline">
                            <h3>Chào mừng bạn đến với Vinafa</h3>
                            <p>
                                Đăng nhập vào Vinafa để khám phá những bộ sưu tập thời trang hiện đại,
                                chất lượng cao và phù hợp cho mọi lứa tuổi. Trải nghiệm mua sắm tiện lợi,
                                an toàn và nhanh chóng ngay hôm nay.
                            </p>
                        </div>

                    </div>
                </div>

            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/custom.js"></script>
    </body>
</html>
