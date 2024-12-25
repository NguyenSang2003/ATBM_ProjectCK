package Controller;

import DAO.OrderDAO;
import DAO.ProductDAO;
import DAO.TopicDAO;
import Model.User;
import cart.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import cart.CartProduct;
import Model.OddImage;

import javax.servlet.RequestDispatcher;
import java.util.Map;

@WebServlet(name = "CheckoutController", value = "/checkout")
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

        // Lấy giỏ hàng từ session
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        // Kiểm tra tính hợp lệ của thông tin
        if (user == null || cart == null || cart.total() == 0 || receiver == null || phoneNumber == null || address == null) {
            resp.sendRedirect("checkout.jsp?error=invalid_request");
            return;
        }

        double totalPrice = cart.totalPrice() + 30000; // Thêm phí ship
        boolean hasError = false;

        try {
            // Lặp qua tất cả các sản phẩm trong giỏ hàng để lưu đơn hàng
            for (Map.Entry<String, CartProduct> entry : cart.getData().entrySet()) {
                CartProduct product = entry.getValue();

                // Kiểm tra và lấy các tham số idMaterial và idSize
                int idMaterial = product.getMaterialId();
                int idSize = product.getSizeId();

                if (idMaterial == 0 || idSize == 0) {
                    // Nếu idMaterial hoặc idSize bị sai, bạn có thể xử lý hoặc thông báo lỗi
                    System.out.println("idmaterial == 0 và id size == 0 khả năng");
                    hasError = true;
                    break;
                }

                // Gọi OrderDAO để lưu thông tin đơn hàng
                OddImage oddImage = (OddImage) product.getObject();
                OrderDAO orderDAO = new OrderDAO();
                boolean isInserted = orderDAO.insertOrderOdd(
                        oddImage.getIdOddImage(),
                        user.getId(),
                        receiver,
                        phoneNumber,
                        product.getQuantity(),
                        product.calculateTotalPrice() + 30000, // Thêm phí ship
                        address,
                        idMaterial,
                        idSize,
                        oddImage.getSignature(),
                        false, // Đơn hàng chưa xác minh
                        false  // Đơn hàng chưa bị chỉnh sửa
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
            for (Map.Entry<String, CartProduct> entry : cart.getData().entrySet()) {
                CartProduct product = entry.getValue();

                if (product.getObject() instanceof OddImage) {
                    OddImage oddImage = (OddImage) product.getObject();
                    orderDetails.append("Mặt hàng: ").append(oddImage.getName())
                            .append(", Số lượng: ").append(product.getQuantity())
                            .append(", Chất liệu: ").append(product.getMaterialId())
                            .append(", Kích cỡ: ").append(product.getSizeId())
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

