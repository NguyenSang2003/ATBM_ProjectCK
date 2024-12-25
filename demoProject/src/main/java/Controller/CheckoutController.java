package Controller;

import DAO.*;
import Model.User;
import cart.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import cart.CartProduct;
import Model.OddImage;

import javax.servlet.RequestDispatcher;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;

@WebServlet(name = "CheckoutController", value = "/checkout")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50   // 50MB
)
//@WebServlet(name = "CheckoutController", value = "/checkout")
public class CheckoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart(); // Tạo giỏ hàng mới nếu chưa có
        }

        // Lấy các topic từ database và truyền vào request
        TopicDAO topicDAO = new TopicDAO();
        req.setAttribute("listTopic", topicDAO.getAllTopicsForClient());

        // Truyền giỏ hàng vào request để hiển thị trong checkout.jsp
        req.setAttribute("cart", cart);

        // Chuyển hướng tới trang checkout.jsp
        req.getRequestDispatcher("checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Lấy thông tin từ form
        String receiver = req.getParameter("receiver");
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");

        // Kiểm tra dữ liệu
        if (receiver == null || receiver.isEmpty() || phoneNumber == null || phoneNumber.isEmpty() || address == null || address.isEmpty()) {
            System.out.println("Invalid request: Missing form data.");
            resp.sendRedirect("checkout.jsp?error=invalid_request");
            return;
        }

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.total() == 0) {
            resp.sendRedirect("checkout.jsp?error=empty_cart");
            return;
        }

        // Kiểm tra tính hợp lệ của thông tin
        if (user == null || cart == null || cart.total() == 0 || receiver == null || phoneNumber == null || address == null) {
            System.out.println("Invalid request: User or cart is missing, or form data is incomplete.");
            resp.sendRedirect("checkout.jsp?error=invalid_request");
            return;
        }

        // Log thêm thông tin giỏ hàng
        System.out.println("Cart total: " + cart.total());

        double totalPrice = cart.totalPrice() + 30000; // Thêm phí ship
        boolean hasError = false;

        // Kiểm tra và lấy file publicKey
        Part publicKeyPart = req.getPart("publicKey");
        if (publicKeyPart == null || publicKeyPart.getSize() == 0) {
            resp.sendRedirect("checkout.jsp?error=public_key_missing");
            System.out.println("Public key file is missing.");
            return;
        }

        // Đọc nội dung file publicKey.txt
        InputStream publicKeyInputStream = publicKeyPart.getInputStream();
        String publicKey = new String(publicKeyInputStream.readAllBytes(), StandardCharsets.UTF_8).trim();
        System.out.println("Public key: " + publicKey);

        System.out.println("Receiver: " + receiver);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Address: " + address);
        System.out.println("User: " + (user != null ? user.getId() : "No user"));
        System.out.println("Cart Total: " + (cart != null ? cart.totalPrice() : "No cart"));


        // Lặp qua tất cả các sản phẩm trong giỏ hàng để lưu đơn hàng
        try {
            // Gọi OrderDAO để lưu thông tin đơn hàng
            for (Map.Entry<String, CartProduct> entry : cart.getData().entrySet()) {
                CartProduct product = entry.getValue();

                int idMaterial = product.getMaterialId();
                int idSize = product.getSizeId();

                if (idMaterial == 0 || idSize == 0) {
                    hasError = true;
                    break;
                }

                OddImage oddImage = (OddImage) product.getObject();
                OrderDAO orderDAO = new OrderDAO();
                boolean isInserted = orderDAO.insertOrderOdd(
                        oddImage.getIdOddImage(),
                        user.getId(),
                        receiver,
                        phoneNumber,
                        product.getQuantity(),
                        product.calculateTotalPrice() + 30000,
                        address,
                        idMaterial,
                        idSize,
                        oddImage.getSignature(),
                        false, // Đơn hàng chưa xác minh
                        false, // Đơn hàng chưa bị chỉnh sửa
                        publicKey // Thêm publicKey vào đây
                );

                // Kiểm tra kết quả lưu đơn hàng
                if (!isInserted) {
                    hasError = true;
                    System.out.println("Xem ra đơn hàng đã lưu được vào database ");
                    break;
                }
            }

            // Nếu có lỗi trong quá trình lưu đơn hàng, chuyển hướng về trang checkout
            if (hasError) {
                resp.sendRedirect("checkout.jsp?error=processing_error");
                return;
            }

            // Tạo bản tóm tắt đơn hàng để hiển thị cho người dùng
            StringBuilder orderDetails = new StringBuilder();
            MaterialDAO materialDAO = new MaterialDAO(); // Đảm bảo đã có DAO này
            SizeDAO sizeDAO = new SizeDAO(); // Đảm bảo đã có DAO này

            for (Map.Entry<String, CartProduct> entry : cart.getData().entrySet()) {
                CartProduct product = entry.getValue();

                if (product.getObject() instanceof OddImage) {
                    OddImage oddImage = (OddImage) product.getObject();

                    // Lấy tên chất liệu từ database
                    String materialName = materialDAO.getMaterialNameById(product.getMaterialId());

                    // Lấy tên kích cỡ từ database
                    String sizeName = sizeDAO.getSizeNameById(product.getSizeId());

                    // Thêm chi tiết sản phẩm vào orderDetails
                    orderDetails.append("Mặt hàng: ").append(oddImage.getName())
                            .append(", Số lượng: ").append(product.getQuantity())
                            .append(", Chất liệu: ").append(materialName)
                            .append(", Kích cỡ: ").append(sizeName)
                            .append(", Thành tiền: ").append(product.calculateTotalPrice()).append("\n");
                }
            }

// Tạo thông tin tóm tắt đơn hàng (bao gồm người nhận, số điện thoại, địa chỉ)
            String orderSummary = "Người nhận: " + receiver +
                    "\nSố điện thoại: " + phoneNumber +
                    "\nĐịa chỉ: " + address +
                    "\nChi tiết:\n" + orderDetails;

// Lưu thông tin đơn hàng vào session để sử dụng sau này
            session.setAttribute("orderSummary", orderSummary);

// Xóa giỏ hàng sau khi đơn hàng được xử lý thành công
            session.removeAttribute("cart");

            // Chuyển tiếp đến trang tóm tắt đơn hàng (order-summary.jsp)
            RequestDispatcher dispatcher = req.getRequestDispatcher("order-summary.jsp");
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("checkout.jsp?error=server_error");
        }
    }

}

