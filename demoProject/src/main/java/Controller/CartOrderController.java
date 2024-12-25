//package Controller;
//
//import DAO.OrderDAO;
//import Regex.Regex;
//import cart.Cart;
//import cart.CartProduct;
//import Model.OddImage;
//import Model.User;
//import org.json.JSONObject;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Map;
//
//@WebServlet(name = "CartOrderController", urlPatterns = {"/cart-order"})
//public class CartOrderController extends HttpServlet {
//
//    @Override
////    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////        req.setCharacterEncoding("UTF-8");
////        resp.setCharacterEncoding("UTF-8");
////
////        // Lấy thông tin từ request
////        String receiver = req.getParameter("receiver");
////        String phoneNumber = req.getParameter("phoneNumber");
////        String address = req.getParameter("address");
////
////        HttpSession session = req.getSession();
////        Cart cart = (Cart) session.getAttribute("cart");
////        User user = (User) session.getAttribute("user");
////
////        OrderDAO orderDAO = new OrderDAO();
////        Regex regex = new Regex();
////        JSONObject jsonObject = new JSONObject();
////
////        if (user == null) {
////            jsonObject.put("status", 401);
////            jsonObject.put("message", "Vui lòng đăng nhập trước khi mua hàng.");
////        } else if (cart == null || cart.total() == 0) {
////            jsonObject.put("status", 400);
////            jsonObject.put("message", "Giỏ hàng của bạn đang trống.");
////        } else if (!user.isActive()) {
////            jsonObject.put("status", 403);
////            jsonObject.put("message", "Tài khoản của bạn không được phép mua hàng.");
////        } else if (receiver == null || receiver.isEmpty() || phoneNumber == null || phoneNumber.isEmpty() || address == null || address.isEmpty()) {
////            jsonObject.put("status", 400);
////            jsonObject.put("message", "Vui lòng nhập đầy đủ thông tin người nhận.");
////        } else if (!regex.isValidPhoneNumber(phoneNumber)) {
////            jsonObject.put("status", 400);
////            jsonObject.put("message", "Số điện thoại không hợp lệ.");
////        } else {
////            try {
////                StringBuilder orderDetails = new StringBuilder();
////                int totalQuantity = 0;
////                double totalPrice = cart.totalPrice() + 30000; // Thêm phí ship
////
////                for (Map.Entry<String, CartProduct> entry : cart.getData().entrySet()) {
////                    CartProduct cartProduct = entry.getValue();
////                    totalQuantity += cartProduct.getQuantity();
////
////                    if (cartProduct.getObject() instanceof OddImage) {
////                        OddImage oddImage = (OddImage) cartProduct.getObject();
////                        orderDetails.append("Sản phẩm: ").append(oddImage.getName())
////                                .append(", Số lượng: ").append(cartProduct.getQuantity())
////                                .append(", Giá: ").append(cartProduct.calculateTotalPrice())
////                                .append("\n");
////
////                        // Gọi hàm insertOrderOdd cho từng sản phẩm
////                        if (!orderDAO.insertOrderOdd(
////                                oddImage.getIdOddImage(),
////                                user.getId(),
////                                receiver,
////                                phoneNumber,
////                                cartProduct.getQuantity(),
////                                cartProduct.calculateTotalPrice(),
////                                address,
////                                oddImage.getIdMaterial(),
////                                oddImage.getIdSize(),
////                                oddImage.getSignature(),
////                                false,  // giả định đơn hàng được xác minh ngay
////                                false // giả định đơn hàng không bị chỉnh sửa
////                        )) {
////                            throw new RuntimeException("Không thể lưu đơn hàng của sản phẩm: " + oddImage.getName());
////                        }
////                    }
////                }
////
////                // Xóa giỏ hàng sau khi xử lý
////                session.removeAttribute("cart");
////
////                // Chuẩn bị file nội dung đơn hàng
////                String fileContent = "ĐƠN HÀNG\n\nNgười nhận: " + receiver +
////                        "\nSố điện thoại: " + phoneNumber +
////                        "\nĐịa chỉ: " + address +
////                        "\nChi tiết đơn hàng:\n" + orderDetails +
////                        "\nTổng tiền: " + totalPrice;
////
////                // Trả thông tin file dưới dạng JSON
////                jsonObject.put("status", 200);
////                jsonObject.put("message", "Đặt hàng thành công.");
////                jsonObject.put("fileContent", fileContent);
////
////            } catch (Exception e) {
////                jsonObject.put("status", 500);
////                jsonObject.put("message", "Đã xảy ra lỗi trong quá trình xử lý đơn hàng: " + e.getMessage());
////            }
////        }
////
////        resp.setContentType("application/json");
////        resp.sendRedirect("/index");
////        try (PrintWriter out = resp.getWriter()) {
////            out.write(jsonObject.toString());
////        }
////    }
//
//
////    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////        req.setCharacterEncoding("UTF-8");
////        resp.setCharacterEncoding("UTF-8");
////
////        String receiver = req.getParameter("receiver");
////        String phoneNumber = req.getParameter("phoneNumber");
////        String address = req.getParameter("address");
////
////        HttpSession session = req.getSession();
////        Cart cart = (Cart) session.getAttribute("cart");
////        User user = (User) session.getAttribute("user");
////
////        if (user == null || cart == null || cart.total() == 0) {
////            resp.sendRedirect("/checkout.jsp?error=invalid_request");
////            return;
////        }
////        int totalQuantity = 0;
////        double totalPrice = cart.totalPrice() + 30000; // Thêm phí ship
////        try {
////            StringBuilder orderDetails = new StringBuilder();
////            boolean hasError = false;
////
////            for (Map.Entry<String, CartProduct> entry : cart.getData().entrySet()) {
////                CartProduct product = entry.getValue();
////                if (product.getObject() instanceof OddImage) {
////                    OddImage oddImage = (OddImage) product.getObject();
////                    orderDetails.append("Mặt hàng: ").append(oddImage.getName())
////                            .append(", Số lượng: ").append(product.getQuantity())
////                            .append(", Chất liệu: ").append(oddImage.getIdMaterial())
////                            .append(", Kích cỡ: ").append(oddImage.getIdSize())
////                            .append(", Thành tiền: ").append(product.calculateTotalPrice()).append("\n");
////
////                    OrderDAO orderDAO = new OrderDAO();
////                    boolean isInserted = orderDAO.insertOrderOdd(
////                            oddImage.getIdOddImage(),
////                            user.getId(),
////                            receiver,
////                            phoneNumber,
////                            product.getQuantity(),
////                            product.calculateTotalPrice(),
////                            address,
////                            oddImage.getIdMaterial(),
////                            oddImage.getIdSize(),
////                            oddImage.getSignature(),
////                            false,  // giả định đơn hàng được xác minh ngay
////                            false   // giả định đơn hàng không bị chỉnh sửa
////                    );
////
////                    if (!isInserted) {
////                        hasError = true;
////                        break;
////                    }
////                }
////            }
////
////            if (hasError) {
////                resp.sendRedirect("/checkout.jsp?error=processing_error");
////                return;
////            }
////
////// Lưu thông tin đơn hàng trong session
////            String orderSummary = "Người nhận: " + receiver +
////                    "\nSố điện thoại: " + phoneNumber +
////                    "\nĐịa chỉ: " + address +
////                    "\nChi tiết:\n" + orderDetails;
////
////            session.setAttribute("orderSummary", orderSummary);
////            session.removeAttribute("cart");
////            resp.sendRedirect("/order-summary.jsp");
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////    }
////}
//
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//
//        // Lấy dữ liệu từ request
//        String receiver = req.getParameter("receiver");
//        String phoneNumber = req.getParameter("phoneNumber");
//        String address = req.getParameter("address");
//
//        String idMaterial = req.getParameter("products[<%=id%>][idMaterial]");
//        String idSize = req.getParameter("products[<%=id%>][idSize]");
//
//        HttpSession session = req.getSession();
//        Cart cart = (Cart) session.getAttribute("cart");
//        User user = (User) session.getAttribute("user");
//
//        // Kiểm tra tính hợp lệ của thông tin
//        if (user == null || cart == null || cart.total() == 0 || receiver == null || phoneNumber == null || address == null) {
//            resp.sendRedirect("/checkout.jsp?error=invalid_request");
//            return;
//        }
//
//        double totalPrice = cart.totalPrice() + 30000; // Thêm phí ship
//        boolean hasError = false;
//
//        try {
//            // Lặp qua tất cả các sản phẩm trong giỏ hàng để lưu đơn hàng
//            for (Map.Entry<String, CartProduct> entry : cart.getData().entrySet()) {
//                CartProduct product = entry.getValue();
//                if (product.getObject() instanceof OddImage) {
//                    OddImage oddImage = (OddImage) product.getObject();
//                    System.out.println("Material ID: " + product.getMaterialId());
//                    System.out.println("Size ID: " + product.getSizeId());
//
//                    // Gọi DAO để lưu thông tin đơn hàng
//                    OrderDAO orderDAO = new OrderDAO();
//                    boolean isInserted = orderDAO.insertOrderOdd(
//                            oddImage.getIdOddImage(),
//                            user.getId(),
//                            receiver,
//                            phoneNumber,
//                            product.getQuantity(),
//                            product.calculateTotalPrice(),
//                            address,
//                            product.getMaterialId(),
//                            product.getSizeId(),
//                            oddImage.getSignature(),
//                            false, // Đơn hàng không được xác minh
//                            false  // Đơn hàng không bị chỉnh sửa
//                    );
//
//                    if (!isInserted) {
//                        hasError = true;
//                        break;
//                    }
//                }
//            }
//
//            // Kiểm tra xem đơn hàng đã được lưu chưa
//            if (hasError) {
//                resp.sendRedirect("/checkout.jsp?error=processing_error");
//                return;
//            }
//
//            // Lưu thông tin đơn hàng vào session
//            StringBuilder orderDetails = new StringBuilder();
//            for (Map.Entry<String, CartProduct> entry : cart.getData().entrySet()) {
//                CartProduct product = entry.getValue();
//                if (product.getObject() instanceof OddImage) {
//                    OddImage oddImage = (OddImage) product.getObject();
//                    orderDetails.append("Mặt hàng: ").append(oddImage.getName())
//                            .append(", Số lượng: ").append(product.getQuantity())
//                            .append(", Chất liệu: ").append(product.getMaterialId())
//                            .append(", Kích cỡ: ").append(product.getSizeId())
//                            .append(", Thành tiền: ").append(product.calculateTotalPrice()).append("\n");
//                }
//            }
//
//            // Tạo bản tóm tắt đơn hàng
//            String orderSummary = "Người nhận: " + receiver +
//                    "\nSố điện thoại: " + phoneNumber +
//                    "\nĐịa chỉ: " + address +
//                    "\nChi tiết:\n" + orderDetails;
//
//            // Lưu thông tin đơn hàng vào session
//            session.setAttribute("orderSummary", orderSummary);
//
//            // Xóa giỏ hàng sau khi tạo đơn hàng thành công
//            session.removeAttribute("cart");
//
//            // Chuyển tiếp tới trang tóm tắt đơn hàng
//            RequestDispatcher dispatcher = req.getRequestDispatcher("/order-summary.jsp");
//            dispatcher.forward(req, resp);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resp.sendRedirect("/checkout.jsp?error=server_error");
//        }
//    }
//}