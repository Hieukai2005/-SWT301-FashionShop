<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Điều khoản dịch vụ</title>

<style>

body{
    font-family: Arial, sans-serif;
    background: linear-gradient(135deg,#f6f8fb,#e9eef6);
    margin:0;
    padding:40px;
    background: url('https://www.elleman.vn/app/uploads/2020/03/26/174307/nu-ca-sy-lisa-blackpink-1.jpg') center center / cover no-repeat;
}

.container{
    max-width:900px;
    margin:auto;
    background:white;
    padding:45px;
    border-radius:14px;
    box-shadow:0 10px 30px rgba(0,0,0,0.1);
}

h1{
    color:#c48b00;
    text-align:center;
    margin-bottom:30px;
}

h2{
    color:#333;
    margin-top:30px;
    border-left:4px solid #c48b00;
    padding-left:10px;
}

p{
    color:#555;
    line-height:1.7;
}

ul{
    padding-left:25px;
}

li{
    margin:10px 0;
    color:#444;
}

.note{
    background:#f9fbff;
    padding:18px;
    border-left:4px solid #c48b00;
    border-radius:6px;
    margin-top:10px;
}

.back-btn{
    display:inline-block;
    margin-top:35px;
    padding:12px 28px;
    background:#c48b00;
    color:white;
    text-decoration:none;
    border-radius:8px;
    font-size:16px;
    transition:0.3s;
}

.back-btn:hover{
    background:#a87400;
    transform:translateY(-2px);
}

.btn-container{
    text-align:center;
}

</style>
</head>

<body>

<div class="container">

<h1>Điều khoản dịch vụ</h1>

<p>Khi sử dụng website của chúng tôi, bạn đồng ý với các điều khoản sau:</p>

<h2>1. Tài khoản</h2>

<p>Người dùng phải cung cấp thông tin chính xác khi đăng ký tài khoản.</p>

<h2>2. Sử dụng website</h2>

<ul>
<li>Không sử dụng website cho mục đích bất hợp pháp</li>
<li>Không truy cập trái phép vào hệ thống</li>
<li>Không gây ảnh hưởng đến hoạt động website</li>
</ul>

<h2>3. Đơn hàng</h2>

<p>Chúng tôi có quyền từ chối hoặc hủy đơn hàng nếu phát hiện gian lận.</p>

<h2>4. Thay đổi điều khoản</h2>

<div class="note">
<p>Điều khoản có thể được cập nhật để phù hợp với hoạt động của website.</p>
</div>

<div class="btn-container">
<a class="back-btn" href="${pageContext.request.contextPath}/signup">
← Quay lại đăng ký
</a>
</div>

</div>

</body>
</html>