<%@ page import="Model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.Topic" %>
<%@ page import="java.util.List" %>
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
    <script>
        <% User user = (User) session.getAttribute("user") == null ? null : (User) (session.getAttribute("user"));  %>

        <%--(async function getData(){--%>
        <%-- &lt;%&ndash;const data =  await  axios.get(`http://localhost:8080/demoProject_war/topic?idUser=<%= (user == null) ? null : user.getId() %>`)&ndash;%&gt;--%>
        <%-- &lt;%&ndash;   if(data.data.status !=200){&ndash;%&gt;--%>
        <%-- &lt;%&ndash;       window.onload =  window.location.href = 'http://localhost:8080/demoProject_war/404.jsp';&ndash;%&gt;--%>
        <%-- &lt;%&ndash;   }&ndash;%&gt;--%>
        <%-- &lt;%&ndash;   getDataTopic(data.data.listTopic)&ndash;%&gt;--%>
        <%--    const userId = <%= (user == null) ? null : user.getId() %>;--%>
        <%--    const url = `http://localhost:8080/demoProject_war/topic?idUser=${userId}`;--%>

        <%--    const xhr = new XMLHttpRequest();--%>
        <%--    xhr.open('GET', url);--%>

        <%--    xhr.onreadystatechange = function () {--%>
        <%--        if (xhr.readyState === 4) {--%>
        <%--            if (xhr.status === 200) {--%>
        <%--                const data = JSON.parse(xhr.responseText);--%>

        <%--                if (data.status === 404) {--%>
        <%--                    window.onload = window.location.href = 'http://localhost:8080/demoProject_war/404.jsp';--%>
        <%--                }--%>
        <%--                --%>
        <%--            } else {--%>
        <%--                console.error('Error:', xhr.status);--%>
        <%--            }--%>
        <%--        }--%>
        <%--    };--%>

        <%--    xhr.send();--%>
        <%--})()--%>

    </script>
</head>

<body>
<%
    List<Topic> listTopic = (List<Topic>) request.getAttribute("listTopic");
%>
<!-- Topbar Start -->
<div class="container-fluid">

    <div class="row align-items-center py-3 px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a href="./index.html" class="text-decoration-none">
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
            <a href="/topic" class="btn border">
                <i class="fa-solid fa-boxes-stacked text-primary"></i>
            </a>
            <a href="/order" class="btn border">
                <i class="fas fa-shopping-cart text-primary"></i>
            </a>
            <a href="/user" class="btn border">
                <i class="fa-regular fa-user text-primary"></i>

            </a>
            <a href="/product/" class="btn border">
                <i class="fa-brands fa-product-hunt text-primary"></i>
            </a>
        </div>
    </div>
</div>
<!-- Topbar End -->



<!-- Page Header Start -->
<div class="container-fluid bg-secondary mb-5">
    <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
        <h1 class="font-weight-semi-bold text-uppercase mb-3">Quản lí chủ đề</h1>
    </div>
</div>
<!-- Page Header End -->


<!-- Cart Start -->

<div class="container-fluid pt-5">
    <div class="row px-xl-5">
        <div class="col-lg-8 table-responsive mb-5">
            <table class="table table-bordered text-center mb-0">
                <thead class="bg-secondary text-dark">
                <tr>
                    <th>ID</th>
                    <th>Tên chủ đề</th>
                    <th>Số sản phẩm</th>
                    <th>Xóa</th>
                </tr>
                </thead>
                <tbody class="align-middle" id="renderdata">
                <%for(Topic topic : listTopic){%>
                <tr>
                    <td class="align-middle"><%=topic.getIdTopic()%></td>
                    <td class="text-left"><img src=<%=topic.getImageInterface()%> alt="" style="width: 50px; margin-right: 5px"><%=topic.getName()%></td>
                    <td class="align-middle">
                        <p class="text-center"><%=topic.getProduct()%></p>
                    </td>
                    <td class="align-middle"><a class="btn btn-sm btn-primary" data-id=<%=topic.getIdTopic()%> data-toggle="modal" data-target="#deleteTopic" ><i class="fa fa-times"></i></a></td>
                </tr>
                <%}%>>
                </tbody>
            </table>
        </div>
        <div class="col-lg-4">
            <div class="card-header bg-secondary border-0">
                <h6 class="font-weight-semi-bold m-0">Thêm chủ đề</h6>
            </div>
            <form class="mb-5  mt-4" action="" id="formTopic" enctype="multipart/form-data" accept-charset="UTF-8">
                <div class="input-group d-flex justify-content-between mt-4">
                    <input type="text" id="nameTopic" class="form-control p-3" placeholder="Tên chủ đề" name="nameTopic">
                    <span class="show-message"></span>
                </div>
                <div class="input-group d-flex justify-content-between mt-3">
                    <input style="height: 100%;" type="file" accept="image/*" id="interfaceImage" class="form-control p-3" placeholder="Link ảnh đại diện" name="interfaceImage">
                    <span class="show-message"></span>
                </div>
                <img src="" class="show-image" alt="" style="width: 360px; height: auto; margin-top: 10px;">
                <div class="input-group-append mt-4">
                    <button class="btn btn-primary">Thêm</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Cart End -->


<!-- Footer Start -->
<div class="container-fluid bg-secondary text-dark mt-5 pt-5">
    <div class="row px-xl-5 pt-5">
        <div class="col-lg-4 col-md-12 mb-5 pr-3 pr-xl-5">
            <a href="./index.html" class="text-decoration-none">
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
                        <a class="text-dark mb-2" href="index.html"><i class="fa fa-angle-right mr-2"></i>Trang
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
<script>

    document.addEventListener("DOMContentLoaded", ()=>{
        var idTopic;
        $('#deleteTopic').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget) // Button that triggered the modal
            idTopic = button.data('id') // Extract info from data-* attributes
        })
        const btnDelete = document.querySelector("#btn-delete-topic")
        console.log(btnDelete)
        btnDelete.addEventListener('click', async ()=>{
            <%--const {data} = await  axios.delete(`http://localhost:8080/demoProject_war/topic?idTopic=${idTopic}`)--%>
            <%--console.log(data)--%>
            <%--if(data.status ===200){--%>
            <%--    alert(data.message)--%>
            <%--    location.reload()--%>
            <%--}--%>
            <%--else  if (data.status ===500){--%>
            <%--    alert(data.message)--%>
            <%--}--%>
            const xhr = new XMLHttpRequest();
            const url =`http://localhost:8080/demoProject_war/topic?idTopic=${idTopic}`
            xhr.open("DELETE", url)
            xhr.onreadystatechange = ()=>{
                if(xhr.readyState === 4){
                    if(xhr.status === 200){
                        const data = JSON.parse(xhr.responseText)
                        if (data.status === 200) {
                            alert(data.message);
                            location.reload();
                        } else if (data.status === 500) {
                            alert(data.message);
                        }
                    }
                }
                else {
                    console.error("err :" + xhr.status)
                }
            }
            xhr.send()
        })
    })
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
<script src="js/topic.js"></script>
</body>

</html>