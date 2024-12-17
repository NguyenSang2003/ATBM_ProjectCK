<%@ page import="java.util.ArrayList" %>
<%@ page import="DAO.ProductDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="DAO.OrderDAO" %>
<%@ page import="favourite.Favourite" %>
<%@ page import="cart.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.*" %>
<!DOCTYPE html>
<%--Dòng dưới để hiện lên theo charset UTF-8--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <%--Dòng dưới để hiện lên theo charset UTF-8--%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <title>Nhóm 26</title>
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
    <link rel="stylesheet" href="./css/logo.css">
    <link rel="stylesheet" href="./css/common.css">
</head>

<body id="listProduct">

<% User user = (User) session.getAttribute("user");
    ArrayList<Topic> listTopic = request.getAttribute("listTopic") == null ? new ArrayList<>() :
            (ArrayList<Topic>) request.getAttribute("listTopic");

    ArrayList<OddImage> listOddImage = request.getAttribute("listOddImage") == null ? new ArrayList<>() :
            (ArrayList<OddImage>) request.getAttribute("listOddImage");
%>
<%
    Locale vnLocal = new Locale("vi", "VN");
    DecimalFormat vndFormat = new DecimalFormat("#,### VND");
%>
<%
    Favourite favourite = (Favourite) session.getAttribute("favourite");
    if (favourite == null) favourite = new Favourite();

    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) cart = new Cart();
%>

<%
    // Phân trang
    int totalPage = 0;
    int currentPage = 1;

    if (request.getAttribute("totalPage") != null) {
        totalPage = (Integer) request.getAttribute("totalPage");
    }
    if (request.getAttribute("currentPage") != null) {
        currentPage = (Integer) request.getAttribute("currentPage");
    }
%>


<!-- Topbar Start -->
<div class="container-fluid">
    <div class="row align-items-center py-3 px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a href="index" class="text-decoration-none">
                <h1 class="logo">Nhóm 26</h1>
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
            <a href="./favourite" class="btn border">
                <i class="fas fa-heart text-primary"></i>
                <span class="badge"><%=favourite.total()%></span>
            </a>
            <a href="./cart" class="btn border">
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

                <div class="navbar-nav w-100 overflow" style="height: 410px">
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
                <a href="" class="text-decoration-none d-block d-lg-none">
                    <h1 class="logo">Nhóm 26</h1>
                </a>
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                    <div class="navbar-nav mr-auto py-0">
                        <a href="index" class="nav-item nav-link">Trang chủ</a>
                        <a href="shop" class="nav-item nav-link active">Cửa hàng</a>
                        <a href="donhangcuaban" class="nav-item nav-link ">Đơn hàng của bạn</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle " data-toggle="dropdown">Trang</a>
                            <div class="dropdown-menu rounded-0 m-0">
                                <a href="cart" class="dropdown-item ">Giỏ hàng</a>
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
                            <a href="./order" class="dropdown-item">Quản lí đơn hàng</a>
                            <a href="./user" class="dropdown-item">Quản lí người dùng</a>
                            <a href="./discountAdmin" class="dropdown-item">Quản lí mã giảm giá</a>
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
        <h1 class="font-weight-semi-bold text-uppercase mb-3">Cửa Hàng Của Chúng Tôi</h1>
        <div class="d-inline-flex">
            <p class="m-0"><a href="index">Trang Chủ</a></p>
            <p class="m-0 px-2">-</p>
            <p class="m-0">Cửa Hàng</p>
        </div>
    </div>
</div>
<!-- Page Header End -->


<!-- Shop Start -->
<div class="container-fluid pt-5">
    <div class="row px-xl-5">
        <!-- Shop Sidebar Start -->
        <div class="col-lg-3 col-md-12">
            <!-- Price Start -->
            <%
                //Check cho các phần lọc được chọn
                String selectedPriceRange = (String) request.getAttribute("selectedPriceRange");
                if (selectedPriceRange == null) {
                    selectedPriceRange = "all";
                }
            %>

            <%-- Chức năng lọc sản phẩm theo giá tiền --%>
            <form action="shop" method="GET">
                <%-- Xét value:  100 -> 100 000 vnd --%>
                <input type="hidden" name="filterShop" value="filterShop">
                <div class="border-bottom mb-4 pb-4">
                    <h5 class="font-weight-semi-bold mb-4">Lọc theo giá</h5>
                    <div class="custom-control custom-radio d-flex align-items-center justify-content-between mb-3">
                        <input type="radio" class="custom-control-input" name="priceRange" value="all"
                               id="price-all" <%= "all".equals(selectedPriceRange) ? "checked" : "" %>>
                        <label class="custom-control-label" for="price-all">Tất cả loại giá</label>
                    </div>
                    <div class="custom-control custom-radio d-flex align-items-center justify-content-between mb-3">
                        <input type="radio" class="custom-control-input" name="priceRange" value="0-100"
                               id="price-1" <%= "0-100".equals(selectedPriceRange) ? "checked" : "" %>>
                        <label class="custom-control-label" for="price-1">Dưới 100.000 vnđ</label>
                    </div>
                    <div class="custom-control custom-radio d-flex align-items-center justify-content-between mb-3">
                        <input type="radio" class="custom-control-input" name="priceRange" value="100-300"
                               id="price-2" <%= "100-300".equals(selectedPriceRange) ? "checked" : "" %>>
                        <label class="custom-control-label" for="price-2">100k đến 300k vnđ</label>
                    </div>
                    <div class="custom-control custom-radio d-flex align-items-center justify-content-between mb-3">
                        <input type="radio" class="custom-control-input" name="priceRange" value="300-600"
                               id="price-3" <%= "300-600".equals(selectedPriceRange) ? "checked" : "" %>>
                        <label class="custom-control-label" for="price-3">300k đến 600k vnđ</label>
                    </div>
                    <div class="custom-control custom-radio d-flex align-items-center justify-content-between mb-3">
                        <input type="radio" class="custom-control-input" name="priceRange" value="600-1000000"
                               id="price-4" <%= "600-1000000".equals(selectedPriceRange) ? "checked" : "" %>>
                        <label class="custom-control-label" for="price-4">600k vnđ trở lên</label>
                    </div>
                    <button type="submit" class="btn btn-primary">Lọc</button>
                </div>
            </form>
            <!-- Price End -->
        </div>

        <!-- Shop Product Start -->
        <div class="col-lg-9 col-md-12">
            <div class="row pb-3">
                <div class="col-12 pb-1">
                    <div class="d-flex align-items-center justify-content-between mb-4">
                        <!-- Tìm kiếm sản phẩm -->
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
                </div>

                <%
                    List<OddImage> filteredOddImages = (List<OddImage>) request.getAttribute("filteredOddImages");
                    List<OddImage> displayedOddImages = (filteredOddImages != null) ? filteredOddImages : listOddImage;
                    if (displayedOddImages != null && displayedOddImages.size() > 0) {
                        for (OddImage oddImage : displayedOddImages) {
                %>
                <div class="col-lg-4 col-md-6 col-sm-12 pb-1">
                    <div class="card product-item border-0 mb-4">
                        <div class="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
                            <img class="img-fluid w-100 image-view" src="<%=oddImage.getImage()%>"
                                 alt="<%=oddImage.getName()%>">
                        </div>
                        <div class="card-body border-left border-right text-center p-0 pt-4 pb-3">
                            <h6 class="text-truncate mb-3"><%=oddImage.getName()%>
                            </h6>
                            <div class="d-flex justify-content-center">
                                <h6><%=vndFormat.format((oddImage.getPrice() - oddImage.getDiscount()))%>
                                </h6>
                                <h6 class="text-muted ml-2">
                                    <del><%=vndFormat.format(oddImage.getPrice())%>
                                    </del>
                                </h6>
                            </div>
                        </div>

                        <!-- Dropdown chọn kích cỡ -->
                        <div class="form-group">
                            <label for="sizeSelect_<%=oddImage.getIdOddImage()%>">Kích cỡ:</label>
                            <select id="sizeSelect_<%=oddImage.getIdOddImage()%>" class="form-control">
                                <% List<Size> sizes = (List<Size>) request.getAttribute("sizes"); %>
                                <% for (Size size : sizes) { %>
                                <option value="<%=size.getIdSize()%>"><%=size.getNameSize()%> (<%=size.getWidth()%>
                                    x<%=size.getHeight()%>)
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <!-- Dropdown chọn chất liệu -->
                        <div class="form-group">
                            <label for="materialSelect_<%=oddImage.getIdOddImage()%>">Chất liệu:</label>
                            <select id="materialSelect_<%=oddImage.getIdOddImage()%>" class="form-control">
                                <% List<Material> materials = (List<Material>) request.getAttribute("materials"); %>
                                <% for (Material material : materials) { %>
                                <option value="<%=material.getIdMaterial()%>"><%=material.getNameMaterial()%>
                                    - <%=material.getDescription()%>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <div class="card-footer d-flex justify-content-between bg-light border">
                            <a href="./detail?type=odd&id=<%=oddImage.getIdOddImage()%>"
                               class="btn btn-sm text-dark p-0">
                                <i class="fas fa-eye text-primary mr-1"></i>Xem chi tiết
                            </a>
                            <button title="<%=oddImage.getType()%>" value="<%=oddImage.getIdOddImage()%>"
                                    class="btn btn-sm text-dark p-0 addCart"
                                    onclick="addToCart('<%=oddImage.getIdOddImage()%>')">
                                <i class="fas fa-shopping-cart text-primary mr-1"></i>Thêm vào giỏ
                            </button>
                        </div>
                    </div>
                </div>
                <% }
                } %>

            </div>
        </div>
    </div>
</div>

<%
%>
<div class="col-12 pb-1">
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center mb-3">
            <li class="page-item disabled">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                    <span class="sr-only">Quay lại</span>
                </a>
            </li>

            <%-- Phân trang cho cả khi lọc và ko lọc sản phẩm --%>
            <%for (int i = 1; i <= totalPage; i++) {%>
            <% String s = currentPage == i ? "active" : ""; %>
            <li class="page-item ml-1 <%= s %>">
                <a class="page-link"
                   href="./shop?page=<%= i %>&filterShop=filterShop&priceRange=<%= selectedPriceRange %>"><%= i %>
                </a>
            </li>
            <%}%>

            <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                    <span class="sr-only">Tiếp</span>
                </a>
            </li>

        </ul>
    </nav>
</div>
</div>
</div>
<!-- Shop Product End -->
</div>
</div>
<!-- Shop End -->


<!-- Footer chung cho các trang - Start -->
<div class="container-fluid bg-secondary text-dark mt-5 pt-5">
    <div class="row px-xl-5 pt-5">
        <div class="col-lg-4 col-md-12 mb-5 pr-3 pr-xl-5">
            <a href="" class="text-decoration-none">
                <h1 class="logo" style="height: 60px; text-align: start; margin-top: -16px;">Nhóm 26</h1>
            </a>
            <p>Shop Nhóm 26 - Điểm đến đáng tin cậy cho các loại ảnh bản quyền, với sự đa dạng và phong phú trong
                tất cả các thể loại. Khi bạn cần ảnh bản quyền. Hãy nhớ "Cần ảnh bản quyền đến với Shop Nhóm 26".
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
                        <a class="text-dark mb-2" href="index.jsp"><i class="fa fa-angle-right mr-2"></i>Trang
                            chủ</a>
                        <a class="text-dark mb-2" href="shop.html"><i class="fa fa-angle-right mr-2"></i>Cửa
                            hàng</a>
                        <a class="text-dark mb-2" href="donhangcuaban.jsp"><i
                                class="fa fa-angle-right mr-2"></i>Đơn hàng của bạn</a>
                        <a class="text-dark mb-2" href="cart.jsp"><i class="fa fa-angle-right mr-2"></i>Giỏ
                            hàng</a>
                        <a class="text-dark mb-2" href="checkout.jsp"><i class="fa fa-angle-right mr-2"></i>Thanh
                            toán</a>
                        <a class="text-dark" href="contact.jsp"><i class="fa fa-angle-right mr-2"></i>Liên hệ</a>
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
</div>
<!-- Footer chung cho các trang - End -->


<!-- Back to Top -->
<a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>

<!-- Add cart js -->
<script src="./js/addCart.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>


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
<script src="js/shop.js"></script>

<script>
    function addToCart(productId) {
        const sizeId = document.getElementById(`sizeSelect_${productId}`).value;
        const materialId = document.getElementById(`materialSelect_${productId}`).value;

        if (!sizeId || !materialId) {
            alert('Vui lòng chọn kích cỡ và chất liệu.');
            return;
        }

        fetch('./cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `type=odd&idProduct=${productId}&sizeId=${sizeId}&materialId=${materialId}`
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 200) {
                    alert(data.message);
                } else {
                    alert('Thêm vào giỏ không thành công');
                }
            })
            .catch(error => {
                console.error('Lỗi:', error);
                alert('Có lỗi xảy ra!');
            });
    }
</script>
</body>

</html>