<%@ page import="Model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Quản lí người dùng || Admin</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">

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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
          integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/1.6.2/axios.min.js"
            integrity="sha512-b94Z6431JyXY14iSXwgzeZurHHRNkLt9d6bAHt7BZT38eqV+GyngIi/tVye4jBKPYQ2lBdRs0glww4fmpuLRwA=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="js/RenderDataForAdmin.js"></script>
    <%--    GET--%>
    <%--    <script>--%>
    <%--        <% User user = (User) session.getAttribute("user") == null ? null : (User) (session.getAttribute("user"));  %>--%>

    <%--        (async function getData(){--%>
    <%--            const data =await  axios.get(`http://localhost:8080/demoProject_war/user?idUser=<%= (user == null) ? null : user.getId() %>`)--%>
    <%--            if(data.data.status !=200){--%>
    <%--                window.onload =  window.location.href = 'http://localhost:8080/demoProject_war/404.jsp';--%>
    <%--            }--%>
    <%--            getDataUser(data.data.listUser)--%>
    <%--        })()--%>

    <%--    </script>--%>
</head>

<body>
<%List<User> users = (List<User>) request.getAttribute("listUser");%>

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
                <i class="fa-solid fa-boxes-stacked text-primary" title="Quản lí chủ đề"></i>
            </a>
            <a href="./orderManager" class="btn border">
                <i class="fas fa-shopping-cart text-primary" title="Quản lí đơn hàng"></i>
            </a>
            <a href="./user" class="btn border">
                <i class="fa-regular fa-user text-primary" title="Quản lí người dùng"></i>
            </a>
            <a href="./product" class="btn border" title="Quản lí sản phẩm">
                <i class="fa-brands fa-product-hunt text-primary"></i>
            </a>
            <a href="./discountAdmin" class="btn border" title="Quản lí mã giảm giá">
                <i class="fa-solid fa-tag" style="color: #D19C97"></i>
            </a>
        </div>
    </div>
</div>
<!-- Topbar End -->

<!-- Page Header Start -->
<div class="container-fluid bg-secondary mb-5">
    <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
        <h1 class="font-weight-semi-bold text-uppercase mb-3">Quản lí người dùng</h1>
    </div>
</div>
<!-- Page Header End -->

<div class="col-12 d-flex align-items-center justify-content-center">
    <form class="d-flex" style="max-width: 600px" action="" METHOD="get">
        <input class="form-control p-2" type="text" placeholder="Nhập id hoặc tên người dùng" name="q">
        <button class="btn btn-primary p-2 ml-2" style="width: 135px;">Tìm kiếm</button>
    </form>
</div>
<!-- Cart Start -->
<div class="container-fluid pt-5">
    <div class="row px-xl-5">
        <div class="col-lg-0 table-responsive mb-5">
            <table class="table table-bordered text-center mb-0">
                <thead class="bg-secondary text-dark">
                <tr>
                    <th>Id</th>
                    <th>Email người dùng</th>
                    <th>Tên người dùng</th>
                    <th>Trạng thái hoạt động</th>
                    <th>Chặn</th>
                    <th>Xóa</th>
                </tr>
                </thead>
                <tbody class="align-middle" id="renderdata-user">
                <%for (User user : users) {%>
                <tr>
                    <td class="text-center"><%=user.getId()%>
                    </td>
                    <td class="align-middle"><%=user.getEmail()%>
                    </td>
                    <td class="align-middle">
                        <p class="text-center"><%=user.getUsername()%>
                        </p>
                    </td>
                    <td class="align-middle">
                        <button class="btn btn-sm btn-primary"><%=user.isActive() ? "Đang hoạt động" : "Bị chặn"%>
                        </button>
                    </td>
                    <td class="align-middle">
                        <button id="btnBlock" data-id="<%=user.getId()%>" class="btn btn-sm btn-primary"
                                data-toggle="modal" data-target="#blockUser"
                                title=<%= user.isActive() ? "Chặn" : "Mở chặn" %>><i class="fa-solid fa-ban"></i>
                        </button>
                    </td>
                    <td class="align-middle">
                        <button data-id="<%=user.getId()%>" class="btn btn-sm btn-primary" data-toggle="modal"
                                data-target="#deleteUser" title="Xóa"><i class="fa fa-times"></i></button>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>

    </div>
</div>
<!-- Cart End -->

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

<%--    Xóa --%>
<div id="deleteUser" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xóa người dùng</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Bạn có chắc chắn muốn xóa người này không không ? </p>
            </div>
            <div class="modal-footer">
                <button id="btn-delete-user" type="button" class="btn btn-danger">Xóa</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
            </div>
        </div>
    </div>
</div>
<%--    block user--%>
<div id="blockUser" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="title-block">Chặn người dùng</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p id="body-block">Bạn có chắc chắn muốn chặn người này không không ? </p>
            </div>
            <div class="modal-footer">
                <button id="btn-block-user" type="button" class="btn btn-danger">Chặn</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
            </div>
        </div>
    </div>
</div>
<!-- Back to Top -->
<a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>
<%--    <script>--%>
<%--        const title = document.querySelector("#title-block")--%>
<%--        const body = document.querySelector('#body-block')--%>
<%--        const attr = document.querySelector("#btnBlock")--%>
<%--        console.log(attr)--%>
<%--    </script>--%>
<script src="js/Dialog.js"></script>
<script>
    Dialog('#deleteUser', '#btn-delete-user', 'user', 'idUser', "delete")
    Dialog('#blockUser', '#btn-block-user', 'user', 'idUser', "put")
</script>
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