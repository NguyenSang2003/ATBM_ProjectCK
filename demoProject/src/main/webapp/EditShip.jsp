<%@ page import="Model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.Topic" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Order" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Quản lí chủ đề || Admin</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/1.6.1/axios.min.js" integrity="sha512-m9S8W3a9hhBHPFAbEIaG7J9P92dzcAWwM42VvJp5n1/M599ldK6Z2st2SfJGsX0QR4LfCVr681vyU5vW8d218w==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/logo.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <%--    Xử lí --%>
    <script src="js/RenderDataForAdmin.js"></script>

</head>

<body>
<%Order order = (Order) request.getAttribute("order");
    Locale vnLocal = new Locale("vi", "VN");
    DecimalFormat vndFormat = new DecimalFormat("#,### VNĐ");
%>
<!-- Topbar Start -->
<div class="container-fluid">

    <div class="row align-items-center py-3 px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a href="./index" class="text-decoration-none">
                <h1 class="logo">Nhóm 63</h1>
            </a>
        </div>
        <div class="col-lg-6 col-6 text-left">
            <form action="">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Tìm kiếm sản phẩm">
                    <div class="input-group-append">
                            <span class="input-group-text bg-transparent text-primary">
                                <i class="fa fa-search"></i>
                            </span>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-lg-3 col-6 text-right">
            <a href="./topic" class="btn border">
                <i class="fa-solid fa-boxes-stacked text-primary"></i>
            </a>
            <a href="./orderManager" class="btn border">
                <i class="fas fa-shopping-cart text-primary"></i>
            </a>
            <a href="./user" class="btn border">
                <i class="fa-regular fa-user text-primary"></i>

            </a>
            <a href="./product" class="btn border">
                <i class="fa-brands fa-product-hunt text-primary"></i>
            </a>
        </div>
    </div>
</div>
<!-- Topbar End -->



<!-- Page Header Start -->
<div class="container-fluid bg-secondary mb-5">
    <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
        <h1 class="font-weight-semi-bold text-uppercase mb-3">Chỉnh sửa trạng thái giao</h1>
    </div>
</div>
<!-- Page Header End -->


<!-- Cart Start -->

<div class="container-fluid pt-5">
    <form action="./editShip" method="post">
        <div class="form-row">
            <div>
                <input type="hidden" name="type" value="<%=order.getType()%>">
                <input type="hidden" name="idOrder" value="<%=order.getIdOrder()%>">
            </div>
            <div class="form-group col-md-6">
                <label for="inputEmail4">Tên người nhận</label>
                <input disabled type="email" class="form-control" id="inputEmail4" value="<%=order.getReceiver()%>" placeholder="Email">
            </div>
            <div class="form-group col-md-6">
                <label for="inputPassword4">Số điện thoại</label>
                <input disabled type="text" value="<%=order.getPhoneNumber()%>" class="form-control" id="inputPassword4" placeholder="Password">
            </div>
        </div>
        <div class="form-group">
            <label for="inputAddress">Địa chỉ nhận</label>
            <input type="text" disabled value="<%=order.getAddress()%>" class="form-control" id="inputAddress" placeholder="1234 Main St">
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputCity">Tên sản phẩm</label>
                <input  type="text" disabled class="form-control" value="<%=order.getNameProduct()%>" id="inputCity">
            </div>
            <div class="form-group col-md-4">
                <label for="inputState">Trạng thái</label>
                <select name="newStatus" id="inputState" class="form-control">
                    <option value="<%=order.getStatus()%>"><%=order.getStatus()%></option>
                    <option value="Đang chuẩn bị">Đang chuẩn bị</option>
                    <option value="Đã giao cho đơn vị vận chuyển">Đã giao cho đơn vị vận chuyển</option>
                    <option value="Đang vận chuyển">Đang vận chuyển</option>
                    <option value="Đã giao">Đã giao</option>
                    <option value="Đã hủy">Đã hủy</option>
                </select>
            </div>
            <div class="form-group col-md-2">
                <label for="inputCity">Tiền thu hộ </label>
                <input type="text" class="form-control" value="<%=vndFormat.format(order.getTotalPrice())%>"  id="inputPrice">
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Cập nhật</button>
    </form>
</div>
<!-- Cart End -->


<!-- Footer Start -->
<div class="container-fluid bg-secondary text-dark mt-5 pt-5">
    <div class="row px-xl-5 pt-5">
        <div class="col-lg-4 col-md-12 mb-5 pr-3 pr-xl-5">
            <a href="./index" class="text-decoration-none">
                <h1 class="logo" style="height: 60px; text-align: start; margin-top: -16px;">Nhóm 63</h1>
            </a>
            <p>Shop Nhóm 63 - Điểm đến đáng tin cậy cho các loại ảnh bản quyền, với sự đa dạng và phong phú trong
                tất cả các thể loại. Khi bạn cần ảnh bản quyền. Hãy nhớ "Cần ảnh bản quyền đến với Shop Nhóm 63".
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
                        <a class="text-dark mb-2" href="admin.jsp"><i class="fa fa-angle-right mr-2"></i>Trang
                            chủ</a>
                        <a class="text-dark mb-2" href="shop.html"><i class="fa fa-angle-right mr-2"></i>Của
                            hàng</a>
                        <a class="text-dark mb-2" href="albumnew.html"><i class="fa fa-angle-right mr-2"></i>Bộ sưu
                            tập mới</a>
                        <a class="text-dark mb-2" href="cart.html"><i class="fa fa-angle-right mr-2"></i>Giỏ
                            hàng</a>
                        <a class="text-dark mb-2" href="checkout.html"><i class="fa fa-angle-right mr-2"></i>Thanh
                            toán</a>
                        <a class="text-dark" href="contact.html"><i class="fa fa-angle-right mr-2"></i>Liên hệ</a>
                    </div>
                </div>
                <div class="col-md-6 mb-5">
                    <h5 class="font-weight-bold text-dark mb-4">Đăng ký mới</h5>
                    <form action="">
                        <div class="form-group">
                            <input type="text" class="form-control border-0 py-4" placeholder="Tên của bạn"
                                   required="required" />
                        </div>
                        <div class="form-group">
                            <input type="email" class="form-control border-0 py-4" placeholder="Email của bạn"
                                   required="required" />
                        </div>
                        <div>
                            <button class="btn btn-primary btn-block border-0 py-3" type="submit">Đăng ký
                                ngay!</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer End -->


<!-- Back to Top -->
<a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>

<%--Confirm delete--%>
<div id="deleteTopic" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xóa chủ đề</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Bạn có chắc chắn muốn xóa nó không ? </p>
            </div>
            <div class="modal-footer">
                <button id="btn-delete-topic" type="button" class="btn btn-danger">Xóa</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
            </div>
        </div>
    </div>
</div>
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

</body>

</html>