<%@ page import="Model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Discount" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.PublicKeyDAO" %>
<%@ page import="favourite.Favourite" %>
<%@ page import="cart.Cart" %>
<%@ page import="Model.Topic" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="vi">

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp"); // Chuyển hướng người dùng chưa đăng nhập
        return;
    }
%>
<%
    ArrayList<Topic> listTopic = request.getAttribute("listTopic") == null ? new ArrayList<>() :
            (ArrayList<Topic>) request.getAttribute("listTopic");

    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) {
        cart = new Cart();
    }

    Favourite favourite = (Favourite) session.getAttribute("favourite");
    if (favourite == null) favourite = new Favourite();
%>

<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Tạo key</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/1.6.1/axios.min.js"
            integrity="sha512-m9S8W3a9hhBHPFAbEIaG7J9P92dzcAWwM42VvJp5n1/M599ldK6Z2st2SfJGsX0QR4LfCVr681vyU5vW8d218w=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/logo.css">
    <link rel="stylesheet" href="./css/common.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
          integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>

<body>
<%
    PublicKeyDAO dao = new PublicKeyDAO();
    List<String> publicKeys = dao.getAllPublicKeys(); // Lấy danh sách public key từ DAO
%>

<!-- Start - Phần dùng chung cho các trang dành cho user -->
<!-- Topbar Start -->
<div class="container-fluid">
    <div class="row align-items-center py-3 px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a href="index" class="text-decoration-none">
                <h1 class="logo">Nhóm 63</h1>
            </a>
        </div>
        <div class="col-lg-6 col-6 text-left">
            <form action="">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Tìm kiếm sản phẩm">
                    <div class="input-group-append">
                            <span class="input-group-text bg-transparent text-primary" title="Tìm kiếm">
                                <i class="fa fa-search"></i>
                            </span>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-lg-3 col-6 text-right">
            <a href="./favourite" class="btn border" title="Yêu thích">
                <i class="fas fa-heart text-primary"></i>
                <span class="badge"><%=favourite.total()%></span>
            </a>
            <a href="cart" class="btn border" title="Giỏ hàng">
                <i class="fas fa-shopping-cart text-primary"></i>
                <span class="badge"><%=cart.total()%></span>
            </a>
        </div>
    </div>
</div>
<!-- Topbar End -->

<!-- Navbar Start -->
<div class="container-fluid">
    <div class="row border-top px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a class="btn shadow-none d-flex align-items-center justify-content-between bg-primary text-white w-100"
               data-toggle="collapse" href="#navbar-vertical"
               style="height: 65px; margin-top: -1px; padding: 0 30px;">
                <h6 class="m-0">Danh mục</h6>
                <i class="fa fa-angle-down text-dark"></i>
            </a>
            <nav class="collapse position-absolute navbar navbar-vertical navbar-light align-items-start p-0 border border-top-0 border-bottom-0 bg-light"
                 id="navbar-vertical" style="width: calc(100% - 30px); z-index: 1;">
                <div class="navbar-nav w-100 overflow-hidden" style="height: 410px">
                    <%if (listTopic.size() == 0) {%>
                    <p>Chưa có topic nào</p>
                    <%} else {%>
                    <%for (Topic topic : listTopic) {%>
                    <a href="./pTopic?q=<%=topic.getName()%>" class="nav-item nav-link"><%=topic.getName()%>
                    </a>
                    <%}%>
                    <%}%>
                </div>
            </nav>
        </div>
        <div class="col-lg-9">
            <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 py-lg-0 px-0">
                <a href="index" class="text-decoration-none d-block d-lg-none">
                    <h1 class="logo">Nhóm 63</h1>
                </a>
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                    <div class="navbar-nav mr-auto py-0">
                        <a href="index" class="nav-item nav-link">Trang chủ</a>
                        <a href="shop" class="nav-item nav-link">Cửa hàng</a>
                        <a href="donhangcuaban" class="nav-item nav-link ">Đơn hàng của bạn</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Trang</a>
                            <div class="dropdown-menu rounded-0 m-0">
                                <a href="cart" class="dropdown-item active">Giỏ hàng</a>
                                <a href="checkout" class="dropdown-item">Thanh toán</a>
                            </div>
                        </div>
                        <a href="contact" class="nav-item nav-link ">Liên hệ</a>
                    </div>

                    <%--Phần login--%>
                    <%if (user == null) {%>
                    <div class="navbar-nav ml-auto py-0">
                        <a href="login.jsp" class="nav-item nav-link">Đăng nhập</a>
                        <a href="register.jsp" class="nav-item nav-link">Đăng ký</a>
                    </div>
                    <%} else { %>
                    <div class="navbar-nav ml-auto py-0 position-relative">
                        <p class="nav-link dropdown-toggle m-0" data-toggle="dropdown">Hi, <%= user.getUsername()%>
                        </p>
                        <div class="dropdown-menu rounded-0 m-0">
                            <%if (!user.isVerifyEmail()) {%>
                            <a href="./verify" class="dropdown-item">Xác thực email của bạn</a>
                            <%}%>
                            <% if (user.isAdmin()) {%>
                            <a href="./topic" class="dropdown-item">Quản lí chủ đề</a>
                            <a href="./product" class="dropdown-item">Quản lí sản phẩm</a>
                            <a href="./orderManager" class="dropdown-item">Quản lí đơn hàng</a>
                            <a href="./user" class="dropdown-item">Quản lí người dùng</a>
                            <a href="./discountAdmin" class="dropdown-item">Quản lí mã giảm giá</a>
                            <a href="./generateKey" class="dropdown-item active">Tạo cặp khóa RSA</a>
                            <%}%>
                            <button class="dropdown-item" id="logout">Đăng xuất</button>
                        </div>
                    </div>
                    <%}%>

                </div>
            </nav>
        </div>
    </div>
</div>
<!-- Navbar End -->

<!-- Page Header Start -->
<div class="container-fluid bg-secondary mb-5">
    <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
        <h1 class="font-weight-semi-bold text-uppercase mb-3">Trình tạo Key</h1>
        <div class="d-inline-flex">
            <p class="m-0"><a href="index">Trang Chủ</a></p>
            <p class="m-0 px-2">-</p>
            <p class="m-0">Trình tạo Key</p>
        </div>
    </div>
</div>
<!-- Page Header End -->

<!-- Keygen UI Start -->
<div class="container" style="margin: auto; width: 90%;">
    <%--    <h3 style="text-align: center; margin-bottom: 20px;">Nơi phát sinh cặp khóa RSA</h3>--%>

    <div class="row" style="display: flex; justify-content: center; margin-bottom: 20px">
        <!-- Buttons Section -->
        <div class="col-md-4 d-flex flex-column align-items-start">
            <button class="btn btn-secondary mb-2" onclick="generateKeyPair()">Tạo key DSA</button>
            <button class="btn btn-secondary mb-2" onclick="loadKey()">Tải key</button>
        </div>

        <div class="col-md-4">
            <div style="background-color: #f8d7da; padding: 10px; border-radius: 5px;">
                <h6>Privatekey được tải lên ở đây</h6>
                <div id="privateKeyLoad"
                     style="word-wrap: break-word; white-space: pre-wrap; background-color: white; padding: 10px; border: 1px solid #ccc; border-radius: 5px; height: 150px; overflow-y: auto;">
                    <!-- Public key content will be shown here -->
                </div>
                <%--                <h5 style="margin-top: 10px; font-weight: bold">Public key đã được cập nhật xuống database tự động</h5>--%>
            </div>
        </div>

        <div class="col-md-4">
            <div style="background-color: #f8d7da; padding: 10px; border-radius: 5px;">
                <h6>Public key của bạn ở đây</h6>
                <div id="userPublicKey"
                     style="word-wrap: break-word; white-space: pre-wrap; background-color: white; padding: 10px; border: 1px solid #ccc; border-radius: 5px; height: 150px; overflow-y: auto;">
                    <!-- Public key content will be shown here -->
                </div>
            </div>
        </div>

    </div>

    <div class="row" style="display: flex; justify-content: center">
        <!-- Public Key Display Section -->
        <div class="col-md-4">
            <div style="background-color: #f8d7da; padding: 10px; border-radius: 5px;">
                <h6>Publickey mới tạo ra ở đây</h6>
                <div id="publicKey"
                     style="word-wrap: break-word; white-space: pre-wrap; background-color: white; padding: 10px; border: 1px solid #ccc; border-radius: 5px; height: 150px; overflow-y: auto;">
                    <!-- Public key content will be shown here -->
                </div>
                <button class="btn btn-secondary mt-2" onclick="savePublicKey()">Lưu Public Key</button>
                <h5 style="margin-top: 10px; font-weight: bold">Public key đã được cập nhật xuống database tự động</h5>
            </div>
        </div>

        <!-- Private Key Display Section -->
        <div class="col-md-4">
            <div style="background-color: #f8d7da; padding: 10px; border-radius: 5px;">
                <h6>PrivateKey mới tạo ra ở đây</h6>
                <div id="privateKey"
                     style="word-wrap: break-word; white-space: pre-wrap; background-color: white; padding: 10px; border: 1px solid #ccc; border-radius: 5px; height: 150px; overflow-y: auto;">
                    <!-- Private key content will be shown here -->
                </div>
                <h5 style="margin-top: 10px; color: red; font-weight: bold">Hãy lưu xuống vì bạn sẽ không thấy nó lần
                    nữa tại đây.</h5>
                <button class="btn btn-secondary mt-2" onclick="savePrivateKey()">Lưu Private Key</button>
            </div>
        </div>
    </div>
</div>
<!-- Keygen UI End -->

<script>
    <%-- Tự động lấy public key từ DB lên trùng với iduser --%>

    function fetchUserPublicKey() {
        axios.post('/demoProject_war/getUserPublicKey')
            .then(response => {
                const publicKey = response.data; // Lấy dữ liệu từ API
                const publicKeyDisplay = document.getElementById('userPublicKey'); // Vị trí hiển thị

                // Kiểm tra kết quả trả về và hiển thị
                publicKeyDisplay.innerText = publicKey && publicKey.trim() !== ""
                    ? publicKey
                    : "No public key found for the user.";
            })
            .catch(error => {
                console.error("Error fetching public key:", error.response || error.message);
                document.getElementById('userPublicKey').innerText =
                    `Error fetching public key: ${error.response ? error.response.data : error.message}`;
            });
    }

    document.addEventListener('DOMContentLoaded', function () {
        // Lấy context path từ URL hiện tại
        const contextPath = window.location.pathname.split('/')[1];
        const apiPath = `/${contextPath}/getUserPublicKey`;

        axios.post(apiPath)
            .then(response => {
                const publicKey = response.data;
                const publicKeyDisplay = document.getElementById('userPublicKey');

                publicKeyDisplay.innerText = publicKey && publicKey.trim() !== ""
                    ? publicKey
                    : "No public key found for the user.";
            })
            .catch(error => {
                console.error("Error fetching public key:", error.response || error.message);
                document.getElementById('userPublicKey').innerText =
                    `Error fetching public key: ${error.response ? error.response.data : error.message}`;
            });
    });

</script>


<script>
    // function generateKeyPair() {
    //     if (isGeneratingKey) return; // Ngăn chặn việc gọi API nhiều lần
    //     isGeneratingKey = true;
    //
    //     axios.post('/generateKey')  // Gọi API tạo cặp khóa DSA
    //         .then(response => {
    //             const [privateKey, publicKey] = response.data.split(';'); // Tách chuỗi trả về thành PrivateKey và PublicKey
    //             document.getElementById('privateKey').innerText = privateKey;
    //             document.getElementById('publicKey').innerText = publicKey;
    //         })
    //         .catch(error => {
    //             alert("Error generating keys: " + error.message);
    //         })
    //         .finally(() => {
    //             isGeneratingKey = false; // Reset trạng thái sau khi xử lý xong
    //         });
    // }

    // Lưu PrivateKey xuống local với định dạng PEM
    function savePrivateKey() {
        const privateKeyElement = document.getElementById('privateKey');
        const privateKey = privateKeyElement.innerText.trim();

        if (!privateKey) {
            alert("No PrivateKey found to save.");
            return;
        }

        const fileName = prompt("Nhập tên file để lưu PrivateKey:", "DSA_privateKey.txt");
        if (fileName) {
            const blob = new Blob([`-----BEGIN PRIVATE KEY-----\n${privateKey}\n-----END PRIVATE KEY-----\n`], {type: "text/plain"});
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = fileName;
            link.click();
        }
    }

    // Lưu PublicKey xuống local với định dạng PEM
    function savePublicKey() {
        const publicKeyElement = document.getElementById('publicKey');
        const publicKey = publicKeyElement.innerText.trim();

        if (!publicKey) {
            alert("No PublicKey found to save.");
            return;
        }

        const fileName = prompt("Nhập tên file để lưu PublicKey:", "DSA_publicKey.txt");
        if (fileName) {
            const blob = new Blob([`-----BEGIN PUBLIC KEY-----\n${publicKey}\n-----END PUBLIC KEY-----\n`], {type: "text/plain"});
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = fileName;
            link.click();
        }
    }
</script>


<%--<script>--%>
<%--    // Lưu PrivateKey xuống local--%>
<%--    function saveKey() {--%>
<%--        const privateKeyElement = document.getElementById('privateKey');--%>
<%--        const privateKey = privateKeyElement.innerText.trim();--%>

<%--        if (!privateKey) {--%>
<%--            alert("No PrivateKey found to save.");--%>
<%--            return;--%>
<%--        }--%>

<%--        const fileName = prompt("Nhập tên file để lưu PrivateKey:", "DSA_private_key.txt");--%>
<%--        if (fileName) {--%>
<%--            const blob = new Blob([privateKey], {type: 'text/plain'});--%>
<%--            const url = URL.createObjectURL(blob);--%>

<%--            // Tạo thẻ a để tải file--%>
<%--            const a = document.createElement('a');--%>
<%--            a.href = url;--%>
<%--            a.download = fileName;--%>
<%--            a.click();--%>

<%--            // Giải phóng URL sau khi tải xong--%>
<%--            URL.revokeObjectURL(url);--%>

<%--            // Xóa private key khỏi giao diện sau khi lưu--%>
<%--            privateKeyElement.innerText = "";--%>
<%--        }--%>
<%--    }--%>
<%--</script>--%>


<script>
    // Trạng thái để ngăn gọi API tạo key nhiều lần
    let isGeneratingKey = false;

    // Hàm tạo cặp khóa
    function generateKeyPair() {
        if (isGeneratingKey) return; // Ngăn chặn việc gọi API nhiều lần
        isGeneratingKey = true;

        axios.post('generateKey')
            .then(response => {
                const [privateKey, publicKey] = response.data.split(';'); // Tách chuỗi trả về thành PrivateKey và PublicKey
                document.getElementById('privateKey').innerText = privateKey;
                document.getElementById('publicKey').innerText = publicKey;
            })
            .catch(error => {
                alert("Error generating keys: " + error.message);
            })
            .finally(() => {
                isGeneratingKey = false; // Reset trạng thái sau khi xử lý xong
            });
    }
</script>

<!-- Footer Start -->
<div class="container-fluid bg-secondary text-dark mt-5 pt-5">
    <!-- Footer chung cho các trang -->
    <div class="row px-xl-5 pt-5">
        <div class="col-lg-4 col-md-12 mb-5 pr-3 pr-xl-5">
            <a href="" class="text-decoration-none">
                <h1 class="logo" style="height: 60px; text-align: start; margin-top: -16px;">Nhóm 63</h1>
            </a>
            <p>Shop Nhóm 63 - Điểm đến đáng tin cậy cho các loại ảnh treo tường, poster, với sự đa dạng và phong phú
                trong
                tất cả các thể loại. Khi bạn cần ảnh treo tường. Hãy nhớ "Cần ảnh poster đến với Shop Nhóm 63".
            </p>
            <p class="mb-2"><i class="fa fa-map-marker-alt text-primary mr-3"></i>ĐH Nông Lâm HCM, Tp.Thủ Đức</p>
            <p class="mb-2"><i class="fa fa-envelope text-primary mr-3"></i>Model@gmail.com</p>
            <p class="mb-0"><i class="fa fa-phone-alt text-primary mr-3"></i>+010 345 67890</p>
        </div>
        <div class="col-lg-8 col-md-12">
            <div class="row">
                <div class="col-md-6 mb-5" style="padding-left: 70px;">
                    <h5 class="font-weight-bold text-dark mb-4">Di Chuyển Nhanh</h5>
                    <div class="d-flex flex-column justify-content-start">
                        <a class="text-dark mb-2" href="index"><i class="fa fa-angle-right mr-2"></i>Trang
                            chủ</a>
                        <a class="text-dark mb-2" href="shop"><i class="fa fa-angle-right mr-2"></i>Của
                            hàng</a>
                        <a class="text-dark mb-2" href="donhangcuaban"><i
                                class="fa fa-angle-right mr-2"></i>Đơn hàng của bạn</a>
                        <a class="text-dark mb-2" href="cart"><i class="fa fa-angle-right mr-2"></i>Giỏ
                            hàng</a>
                        <a class="text-dark mb-2" href="checkout"><i class="fa fa-angle-right mr-2"></i>Thanh
                            toán</a>
                        <a class="text-dark" href="contact"><i class="fa fa-angle-right mr-2"></i>Liên hệ</a>
                    </div>
                </div>
                <div class="col-md-6 mb-5">
                    <h5 class="font-weight-bold text-dark mb-4">Đăng ký mới</h5>
                    <form action="">
                        <div class="form-group">
                            <input type="text" class="form-control border-0 py-4" placeholder="Tên của bạn"
                                   required="required"/>
                        </div>
                        <div class="form-group">
                            <input type="email" class="form-control border-0 py-4" placeholder="Email của bạn"
                                   required="required"/>
                        </div>
                        <div>
                            <button class="btn btn-primary btn-block border-0 py-3" type="submit">Đăng ký
                                ngay!
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Footer chung cho các trang -->

</div>
<!-- Footer End -->

<!-- Back to Top -->
<a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>

<%-- Mã js để xóa mã giảm giá --%>
<script src="./js/discountDelete.js"></script>

<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
<script src="lib/easing/easing.min.js"></script>
<script src="lib/owlcarousel/owl.carousel.min.js"></script>


<!-- Contact Javascript File -->
<script src="mail/jqBootstrapValidation.min.js"></script>
<script src="mail/contact.js"></script>

<!-- Template Javascript -->
<script src="js/main.js"></script>
<script src="js/topic.js"></script>
</body>

</html>