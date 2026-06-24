<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Chính sách quyền riêng tư</title>

<style>

body{
    font-family: Arial, sans-serif;
    background: url('https://media-cdn-v2.laodong.vn/storage/newsportal/2025/2/20/1465868/Rose-Blackpink-2.jpg') center center / cover no-repeat;
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    background-attachment: fixed;

    margin:0;
    min-height:100vh;

    display:flex;
    justify-content:center; 
    align-items:center;
}

/* Card */
.container{
    max-width:900px;
    margin:auto;
    background:white;
    padding:45px;
    border-radius:14px;
    box-shadow:0 10px 30px rgba(0,0,0,0.1);
}

/* Title */
h1{
    color:#c48b00;
    text-align:center;
    margin-bottom:30px;
}

/* Section title */
h2{
    color:#333;
    margin-top:30px;
    border-left:4px solid #c48b00;
    padding-left:10px;
}

/* Paragraph */
p{
    color:#555;
    line-height:1.7;
}

/* List */
ul{
    padding-left:25px;
}

li{
    margin:10px 0;
    color:#444;
}

/* Contact box */
.contact{
    background:#f9fbff;
    padding:18px;
    border-left:4px solid #c48b00;
    border-radius:6px;
    margin-top:10px;
}

/* Button */
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

<h1>Chính sách quyền riêng tư</h1>

<p>Chúng tôi cam kết bảo vệ thông tin cá nhân của người dùng khi sử dụng website của chúng tôi.</p>

<h2>1. Thông tin thu thập</h2>

<ul>
<li>Họ và tên</li>
<li>Email</li>
<li>Số điện thoại</li>
<li>Địa chỉ giao hàng</li>
</ul>

<h2>2. Mục đích sử dụng</h2>

<p>
Thông tin của bạn được sử dụng để xử lý đơn hàng, cung cấp dịch vụ và hỗ trợ khách hàng tốt hơn.
</p>

<h2>3. Bảo mật</h2>

<p>
Chúng tôi cam kết bảo mật thông tin cá nhân và không chia sẻ cho bên thứ ba khi chưa có sự đồng ý của bạn.
</p>

<h2>4. Liên hệ</h2>

<div class="contact">
<p>Email: hello@canifa.com</p>
<p>Điện thoại: 0227303.0222</p>
</div>

<div class="btn-container">
<a class="back-btn" href="${pageContext.request.contextPath}/signup">
← Quay lại đăng ký
</a>
</div>

</div>

</body>
</html>