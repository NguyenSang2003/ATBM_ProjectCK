<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.Random" %>
<%@ page import="favourite.Favourite" %>
<%@ page import="cart.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.*" %>
<!DOCTYPE html>
<html lang="en">
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<head>
    <meta charset="utf-8">
    <title>Nhóm 26</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

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
    <link rel="stylesheet" href="./css/index.css">
    <link rel="stylesheet" href="./css/common.css">
    <%--    axios--%>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/1.6.1/axios.min.js"
            integrity="sha512-m9S8W3a9hhBHPFAbEIaG7J9P92dzcAWwM42VvJp5n1/M599ldK6Z2st2SfJGsX0QR4LfCVr681vyU5vW8d218w=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>

<body>
<% User user = (User) session.getAttribute("user");
    ArrayList<Topic> listTopic = request.getAttribute("listTopic") == null ? new ArrayList<>() : (ArrayList<Topic>) request.getAttribute("listTopic");
    ArrayList<OddImage> listOddNew = request.getAttribute("listOddNew") == null ? new ArrayList<>() : (ArrayList<OddImage>) request.getAttribute("listOddNew");
    ArrayList<OddImage> listOddImageOrder = request.getAttribute("listOddImageOrder") == null ? new ArrayList<>() : (ArrayList<OddImage>) request.getAttribute("listOddImageOrder");
    Random random = new Random();
    int oddRan = random.nextInt(0, listOddImageOrder.size());
    OddImage oddImageSlide = listOddImageOrder.get(oddRan);
    Locale vnLocal = new Locale("vi", "VN");
    DecimalFormat vndFormat = new DecimalFormat("#,### VNĐ");
%>
<%
    Favourite favourite = (Favourite) session.getAttribute("favourite");
    if (favourite == null) favourite = new Favourite();
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) cart = new Cart();
%>
<!-- Topbar Start -->
<div class="container-fluid">
    <div class="row align-items-center py-3 px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a href="./index" class="text-decoration-none">
                <h1 class="logo">Nhóm 26</h1>
            </a>
        </div>
        <div class="col-lg-6 col-6 text-left">
            <form action="./search" method="get">
                <div class="input-group">
                    <input type="text" name="q" class="form-control" placeholder="Tìm kiếm sản phẩm">
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
<div class="container-fluid mb-5">
    <div class="row border-top px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a class="btn shadow-none d-flex align-items-center justify-content-between bg-primary text-white w-100"
               data-toggle="collapse" href="#navbar-vertical" style="height: 65px; margin-top: -1px; padding: 0 30px;">
                <h6 class="m-0">Danh mục</h6>
                <i class="fa fa-angle-down text-dark"></i>
            </a>
            <nav class="collapse show navbar navbar-vertical navbar-light align-items-start p-0 border border-top-0 border-bottom-0"
                 id="navbar-vertical">
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
                <a href="./index" class="text-decoration-none d-block d-lg-none">
                    <h1 class="logo" style="font-size: 34px;">Nhóm 26</h1>
                </a>
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                    <div class="navbar-nav mr-auto py-0">
                        <a href="./index" class="nav-item nav-link active">Trang chủ</a>
                        <a href="./shop" class="nav-item nav-link">Cửa hàng</a>
                        <a href="./donhangcuaban" class="nav-item nav-link">Đơn hàng của bạn</a>
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Trang</a>
                            <div class="dropdown-menu rounded-0 m-0">
                                <a href="cart" class="dropdown-item">Giỏ hàng</a>
                                <a href="checkout" class="dropdown-item">Thanh toán</a>
                            </div>
                        </div>
                        <a href="contact" class="nav-item nav-link">Liên hệ</a>
                    </div>
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
                            <%}%>
                            <button class="dropdown-item" id="logout">Đăng xuất</button>
                        </div>
                    </div>
                    <%}%>
                </div>
            </nav>
            <div id="header-carousel" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active" style="height: 410px;">
                        <img class="img-fluid" src="<%=oddImageSlide.getImage()%>" alt="Image">
                        <div class="carousel-caption d-flex flex-column align-items-center justify-content-center">
                            <div class="p-3" style="max-width: 700px;">
                                <h4 class="text-light text-uppercase font-weight-medium mb-3">Ảnh bán chạy</h4>
                                <h3 class="display-4 text-white font-weight-semi-bold mb-4"><%=oddImageSlide.getName()%>
                                </h3>
                                <a href="./detail?type=odd&id=<%=oddImageSlide.getIdOddImage()%>"
                                   class="btn btn-light py-2 px-3">Xem ngay</a>
                            </div>
                        </div>
                    </div>
                </div>
                <a class="carousel-control-prev" href="#header-carousel" data-slide="prev">
                    <div class="btn btn-dark" style="width: 45px; height: 45px;">
                        <span class="carousel-control-prev-icon mb-n2"></span>
                    </div>
                </a>
                <a class="carousel-control-next" href="#header-carousel" data-slide="next">
                    <div class="btn btn-dark" style="width: 45px; height: 45px;">
                        <span class="carousel-control-next-icon mb-n2"></span>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>
<!-- Navbar End -->
<!-- Categories Start -->
<div class="container-fluid pt-5">
    <div class="row px-xl-5 pb-3">
        <%for (Topic topic : listTopic) {%>
        <div class="col-lg-4 col-md-6 pb-1">
            <div class="cat-item d-flex flex-column border mb-4" style="padding: 30px; height: 358px;">
                <p class="text-right"><%=topic.getProduct()%> sản phẩm</p>
                <a href="./pTopic?q=<%=topic.getName()%>" class="cat-img position-relative overflow-hidden mb-3">
                    <%--                    <a href="./pTopic?q=<%=topic.getName()%>" class="nav-item nav-link"><%=topic.getName()%>--%>
                    <img class="img-fluid w-100" src=<%=topic.getImageInterface()%> alt="">
                </a>
                <h5 class="font-weight-semi-bold m-0"><%=topic.getName()%>
                </h5>
            </div>
        </div>
        <%}%>
    </div>
</div>
<!-- Categories End -->


<!-- Offer Start -->
<div class="container-fluid">
    <div class="col-md-12 pt-4 pb-4 d-flex justify-content-around align-items-center  bg-secondary">
        <img style="height: 300px;" src="img/sale.jpg" alt="">
        <div class=" text-center text-md-right text-white mb-2 py-5 px-5">
            <div class="" style="z-index: 1;">
                <h5 class="text-uppercase text-primary mb-3">Mã giảm giá dành cho bạn</h5>
                <h1 class="mb-4 font-weight-semi-bold">Mùa xuân, giá sốc !</h1>
                <a href="./discount" class="btn btn-outline-primary py-md-2 px-md-3">Lấy ngay</a>
            </div>
        </div>
    </div>
    <!-- Offer End -->

    <!-- Products Start -->
    <div class="container-fluid pt-5">
        <div class="text-center mb-4">
            <h2 class="section-title px-5"><span class="px-2">Ảnh bán chạy</span></h2>
        </div>
        <div class="row px-xl-5 pb-3">
            <%if (listOddImageOrder.size() == 0) {%>
            <div></div>
            <%} else {%>
            <%for (OddImage oddImage : listOddImageOrder) {%>
            <div class="col-lg-3 col-md-6 col-sm-12 pb-1">
                <div class="card product-item border-0 mb-4">
                    <div class="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
                        <img class="img-fluid w-100 image-view" src="<%=oddImage.getImage()%>"
                             alt="<%=oddImage.getName()%>">
                    </div>
                    <div class="card-body border-left border-right text-center p-0 pt-4 pb-3">
                        <h6 class="text-truncate mb-3"><%=oddImage.getName()%>
                        </h6>
                        <div class="d-flex justify-content-center">
                            <h6><%=vndFormat.format(oddImage.getPrice() - oddImage.getDiscount())%>
                            </h6>
                            <h6 class="text-muted ml-2">
                                <del><%=vndFormat.format(oddImage.getPrice())%>
                                </del>
                            </h6>
                        </div>
                    </div>

                    <!-- Dropdown cho kích cỡ -->
                    <div class="form-group px-3">
                        <label for="sizeSelect_<%= oddImage.getIdOddImage() %>">Kích cỡ:</label>
                        <select id="sizeSelect_<%= oddImage.getIdOddImage() %>" class="form-control">
                            <% for (Size size : (List<Size>) request.getAttribute("sizes")) { %>
                            <option value="<%= size.getIdSize() %>">
                                <%= size.getNameSize() %> (<%= size.getWidth() %>x<%= size.getHeight() %>)
                            </option>
                            <% } %>
                        </select>
                    </div>

                    <!-- Dropdown cho chất liệu -->
                    <div class="form-group px-3">
                        <label for="materialSelect_<%= oddImage.getIdOddImage() %>">Chất liệu:</label>
                        <select id="materialSelect_<%= oddImage.getIdOddImage() %>" class="form-control">
                            <% for (Material material : (List<Material>) request.getAttribute("materials")) { %>
                            <option value="<%= material.getIdMaterial() %>">
                                <%= material.getNameMaterial() %> - <%= material.getDescription() %>
                            </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="card-footer d-flex justify-content-between bg-light border">
                        <a href="./detail?type=odd&id=<%=oddImage.getIdOddImage()%>" class="btn btn-sm text-dark p-0">
                            <i class="fas fa-eye text-primary mr-1"></i>Xem chi tiết</a>
                        <button title="<%=oddImage.getType()%>" value="<%=oddImage.getIdOddImage()%>"
                                class="addCart btn btn-sm text-dark p-0"
                                onclick="addToCart('<%=oddImage.getIdOddImage()%>')">
                            <i class="fas fa-shopping-cart text-primary mr-1"></i>Thêm vào giỏ
                        </button>

                    </div>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
</div>
<!-- Products End -->


<!-- Products Start 2-->
<div class="container-fluid pt-5">
    <div class="text-center mb-4">
        <h2 class="section-title px-5"><span class="px-2">Ảnh mới</span></h2>
    </div>
    <div class="row px-xl-5 pb-3">
        <%for (OddImage oddImage : listOddNew) {%>
        <div class="col-lg-3 col-md-6 col-sm-12 pb-1">
            <div class="card product-item border-0 mb-4">
                <div class="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
                    <img class="img-fluid w-100 image-view" src="<%=oddImage.getImage()%>"
                         alt="<%=oddImage.getName()%>">
                </div>
                <div class="card-body border-left border-right text-center p-0 pt-4 pb-3">
                    <h6 class="text-truncate mb-3"><%=oddImage.getName()%>
                    </h6>
                    <div class="d-flex justify-content-center">
                        <h6><%=vndFormat.format(oddImage.getPrice() - oddImage.getDiscount())%>
                        </h6>
                        <h6 class="text-muted ml-2">
                            <del><%=vndFormat.format(oddImage.getPrice())%>
                            </del>
                        </h6>
                    </div>
                </div>

                <!-- Dropdown cho kích cỡ -->
                <div class="form-group px-3">
                    <label for="sizeSelect_<%= oddImage.getIdOddImage() %>">Kích cỡ:</label>
                    <select id="sizeSelect_<%= oddImage.getIdOddImage() %>" class="form-control">
                        <% for (Size size : (List<Size>) request.getAttribute("sizes")) { %>
                        <option value="<%= size.getIdSize() %>">
                            <%= size.getNameSize() %> (<%= size.getWidth() %>x<%= size.getHeight() %>)
                        </option>
                        <% } %>
                    </select>
                </div>

                <!-- Dropdown cho chất liệu -->
                <div class="form-group px-3">
                    <label for="materialSelect_<%= oddImage.getIdOddImage() %>">Chất liệu:</label>
                    <select id="materialSelect_<%= oddImage.getIdOddImage() %>" class="form-control">
                        <% for (Material material : (List<Material>) request.getAttribute("materials")) { %>
                        <option value="<%= material.getIdMaterial() %>">
                            <%= material.getNameMaterial() %> - <%= material.getDescription() %>
                        </option>
                        <% } %>
                    </select>
                </div>

                <div class="card-footer d-flex justify-content-between bg-light border">
                    <a href="./detail?type=odd&id=<%=oddImage.getIdOddImage()%>" class="btn btn-sm text-dark p-0">
                        <i class="fas fa-eye text-primary mr-1"></i>Xem chi tiết</a>
                    <button title="<%=oddImage.getType()%>" value="<%=oddImage.getIdOddImage()%>"
                            class="addCart btn btn-sm text-dark p-0"
                            onclick="addToCart('<%=oddImage.getIdOddImage()%>')">
                        <i class="fas fa-shopping-cart text-primary mr-1"></i>Thêm vào giỏ
                    </button>
                </div>
            </div>
        </div>
        <%}%>
        <%-- ở đây --%>
    </div>
</div>
<!-- Products End -->

<!-- Footer Start -->
<div class="container-fluid bg-secondary text-dark mt-5 pt-5">
    <div class="row px-xl-5 pt-5">
        <div class="col-lg-4 col-md-12 mb-5 pr-3 pr-xl-5">
            <a href="admin.jsp" class="text-decoration-none">
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
                        <a class="text-dark mb-2" href="./index"><i class="fa fa-angle-right mr-2"></i>Trang
                            chủ</a>
                        <a class="text-dark mb-2" href="./shop"><i class="fa fa-angle-right mr-2"></i>Của
                            hàng</a>
                        <a class="text-dark mb-2" href="./donhangcuaban"><i class="fa fa-angle-right mr-2"></i>Đơn
                            hàng của bạn</a>
                        <a class="text-dark mb-2" href="./cart"><i class="fa fa-angle-right mr-2"></i>Giỏ
                            hàng</a>
                        <a class="text-dark mb-2" href="./checkout"><i class="fa fa-angle-right mr-2"></i>Thanh
                            toán</a>
                        <a class="text-dark" href="./contact"><i class="fa fa-angle-right mr-2"></i>Liên hệ</a>
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
<!-- Footer End -->


<!-- Back to Top -->
<a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>


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
<script src="js/user.js"></script>
<script src="js/addCart.js"></script>

</body>
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
</html>