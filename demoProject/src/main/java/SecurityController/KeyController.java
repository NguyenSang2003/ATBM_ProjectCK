package SecurityController;

import DAO.PublicKeyDAO;
import SecurityModel.KeyPairGeneratorUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "KeyController", urlPatterns = {"/generateKey", "/saveKey", "/loadKeys", "/getUserPublicKey"})
public class KeyController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in.");
            return;
        }

        if ("/generateKey".equals(action)) {
            // Hiển thị giao diện tạo khóa
            request.getRequestDispatcher("keyGen.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action: " + action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        PublicKeyDAO keyDAO = new PublicKeyDAO();

        switch (action) {
            case "/generateKey":
                try {
                    int userId = (int) request.getSession().getAttribute("userId"); // Lấy userId từ session
                    String privateKeyAndPublicKey = KeyPairGeneratorUtil.generateAndSaveKeyPair(userId); // Tạo cặp khóa
                    response.getWriter().write(privateKeyAndPublicKey); // Trả về cả PrivateKey và PublicKey
                } catch (Exception e) {
                    response.getWriter().write("Error generating keys: " + e.getMessage());
                }
                break;

            case "/getUserPublicKey":
                try {
                    Integer userId = (Integer) request.getSession().getAttribute("userId");
                    if (userId == null) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("User is not logged in.");
                        return;
                    }
                    String publicKey = keyDAO.getPublicKeyByUserId(userId); // Lấy public key từ database
                    response.setContentType("text/plain");
                    response.getWriter().write(publicKey != null ? publicKey : "No public key found.");
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Error fetching public key: " + e.getMessage());
                }
                break;


            case "/loadKeys":
                List<String> keys = keyDAO.getAllPublicKeys();
                response.getWriter().write(String.join(",", keys)); // Trả danh sách public keys dưới dạng chuỗi
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action: " + action);
                break;
        }
    }
}