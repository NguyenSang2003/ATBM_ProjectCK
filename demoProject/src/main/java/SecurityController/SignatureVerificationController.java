package SecurityController;

import DAO.OrderDAO;
import SecurityModel.DigitalSigLogic;

import java.io.*;
import java.security.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Base64;

@WebServlet("/SignatureVerificationController")
public class SignatureVerificationController extends HttpServlet {

    // Phương thức GET để hiển thị form tải chữ ký
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/addSignature.jsp");
        dispatcher.forward(request, response);
    }

    // Phương thức POST để xử lý tải lên chữ ký và xác thực
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy idOrder và chữ ký từ form
        String signature = request.getParameter("signature");
        String idOrderStr = request.getParameter("idOrder");

        System.out.println("Signature là: " + signature);
        System.out.println("ID Order là: " + idOrderStr);

        if (signature == null || signature.isEmpty()) {
            response.sendRedirect("/addSignature.jsp?error=no_signature");
            System.out.println("Không tồn tại chữ ký");
            return;
        }

        if (idOrderStr == null || idOrderStr.isEmpty()) {
            response.sendRedirect("/addSignature.jsp?error=no_order_id");
            System.out.println("Không tồn tại ID Order");
            return;
        }

        try {
            int idOrder = Integer.parseInt(idOrderStr);

            // Lấy public key từ cơ sở dữ liệu dựa trên idOrder
            OrderDAO orderDAO = new OrderDAO();
            String publicKeyStr = orderDAO.getPublicKeyByOrderId(idOrder);

            if (publicKeyStr == null) {
                response.sendRedirect("/addSignature.jsp?error=public_key_not_found");
                System.out.println("Không tìm thấy public key");
                return;
            }

            // Chuyển đổi public key từ string
            PublicKey publicKey = DigitalSigLogic.stringToPublicKey(publicKeyStr);
            System.out.println("Public key là: " + publicKey);

            // Lấy nội dung tệp từ cơ sở dữ liệu dựa trên idOrder
            String fileContent = orderDAO.getOrderDetailsById(idOrder);
            if (fileContent == null || fileContent.isEmpty()) {
                response.sendRedirect("/addSignature.jsp?error=file_not_found");
                System.out.println("Nội dung tệp không tìm thấy");
                return;
            }

            // Tạm thời lưu nội dung vào một tệp để xác minh chữ ký
            File tempFile = File.createTempFile("order-details", ".txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(fileContent);
            }

            // Xác thực chữ ký
            DigitalSigLogic digitalSigLogic = new DigitalSigLogic("DSA", "SUN");
            boolean isVerified = digitalSigLogic.verifyFile(tempFile.getAbsolutePath(), signature, publicKey);

            // Xóa tệp tạm sau khi xác minh
            tempFile.delete();

            if (isVerified) {
                System.out.println("Chữ ký hợp lệ!");

                // Cập nhật trạng thái xác thực vào cơ sở dữ liệu
                boolean isUpdated = orderDAO.updateOrderVerificationStatus(idOrder, signature);
                if (isUpdated) {
                    request.setAttribute("verificationResult", "Chữ ký hợp lệ và trạng thái đơn hàng đã được cập nhật!");
                } else {
                    request.setAttribute("verificationResult", "Chữ ký hợp lệ nhưng không thể cập nhật trạng thái đơn hàng!");
                }
            } else {
                request.setAttribute("verificationResult", "Chữ ký không hợp lệ!");
            }

            // Chuyển đến trang addSignature.jsp và hiển thị kết quả
            RequestDispatcher dispatcher = request.getRequestDispatcher("/addSignature.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/addSignature.jsp?error=verification_failed");
        }
    }
}