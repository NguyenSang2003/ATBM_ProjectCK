package Controller;

import DAO.ProductDAO;
import DAO.TopicDAO;
import cart.Cart;
import Model.User;
import cart.CartProduct;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CartController", value = "/cart")
public class CartController extends HttpServlet {

    //    Lấy ra xem phần topic từ lớp productdao
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        ProductDAO productDAO = new ProductDAO();

        TopicDAO topicDAO = new TopicDAO();
        req.setAttribute("listTopic", topicDAO.getAllTopicsForClient());

        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }


    //Xử lí các chức năng của giỏ như thêm, sửa, xóa
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String type = req.getParameter("type");
        String id = req.getParameter("idProduct");
        int sizeId = Integer.parseInt(req.getParameter("sizeId"));
        int materialId = Integer.parseInt(req.getParameter("materialId"));

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        JSONObject jsonObject = new JSONObject();

        if (cart == null) {
            cart = new Cart();
        }

        // Gọi hàm add với cả sizeId và materialId
        if (cart.add(type, type + id, Integer.parseInt(id), materialId, sizeId)) {
            session.setAttribute("cart", cart);
            jsonObject.put("status", 200);
            jsonObject.put("message", "Thêm thành công");
        } else {
            jsonObject.put("status", 500);
            jsonObject.put("message", "Thêm không thành công");
        }

        resp.setContentType("application/json");
        resp.getWriter().write(jsonObject.toString());
    }

//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//
//        String type = req.getParameter("type");
//        String id = req.getParameter("idProduct");
//        int change = Integer.parseInt(req.getParameter("change")); // Lấy giá trị thay đổi (+1/-1)
//
//        HttpSession session = req.getSession();
//        Cart cart = (Cart) session.getAttribute("cart");
//        JSONObject jsonObject = new JSONObject();
//
//        if (cart == null || !cart.getData().containsKey(type + id)) {
//            jsonObject.put("status", 500);
//            jsonObject.put("message", "Sản phẩm không tồn tại trong giỏ hàng.");
//        } else {
//            CartProduct cartProduct = cart.getData().get(type + id);
//            int newQuantity = cartProduct.getQuantity() + change;
//
//            if (newQuantity <= 0) {
//                cart.remove(type + id); // Xóa sản phẩm nếu số lượng bằng 0
//                jsonObject.put("newQuantity", 0);
//            } else {
//                cartProduct.setQuantity(newQuantity); // Cập nhật số lượng
//                jsonObject.put("newQuantity", newQuantity);
//            }
//
//            session.setAttribute("cart", cart);
//            jsonObject.put("status", 200);
//            jsonObject.put("message", "Cập nhật thành công.");
//        }
//
//        resp.setContentType("application/json");
//        resp.getWriter().write(jsonObject.toString());
//    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String body = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        JSONObject json = new JSONObject(body);

        String type = json.getString("type");
        String id = json.getString("idProduct");
        int change = json.getInt("change");

        // Phần còn lại giữ nguyên như cũ
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        JSONObject jsonObject = new JSONObject();

        if (cart == null || !cart.getData().containsKey(type + id)) {
            jsonObject.put("status", 500);
            jsonObject.put("message", "Sản phẩm không tồn tại trong giỏ hàng.");
        } else {
            CartProduct cartProduct = cart.getData().get(type + id);
            int newQuantity = cartProduct.getQuantity() + change;

            if (newQuantity <= 0) {
                cart.remove(type + id); // Xóa sản phẩm nếu số lượng <= 0
                jsonObject.put("newQuantity", 0);
            } else {
                cartProduct.setQuantity(newQuantity); // Cập nhật số lượng
                jsonObject.put("newQuantity", newQuantity);
            }

            session.setAttribute("cart", cart);
            jsonObject.put("status", 200);
            jsonObject.put("message", "Cập nhật số lượng thành công.");
        }

        resp.setContentType("application/json");
        resp.getWriter().write(jsonObject.toString());
    }


//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//        HttpSession session = req.getSession();
//        Cart cart = (Cart) session.getAttribute("cart");
//        JSONObject jsonObject = new JSONObject();
//        if (cart.removeAll()) {
//            session.setAttribute("cart", cart);
//            jsonObject.put("status", 200);
//            jsonObject.put("message", "Xóa thành công");
//            resp.setContentType("application/json");
//            resp.getWriter().write(jsonObject.toString());
//        } else {
//            session.setAttribute("cart", cart);
//            jsonObject.put("status", 500);
//            jsonObject.put("message", "Xóa không thành công");
//            resp.setContentType("application/json");
//            resp.getWriter().write(jsonObject.toString());
//        }
//    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        JSONObject jsonObject = new JSONObject();

        // Kiểm tra xóa toàn bộ hay chỉ xóa một sản phẩm
        String id = req.getParameter("idProduct");
        String type = req.getParameter("type");

        if (id == null && type == null) { // Xóa toàn bộ sản phẩm
            if (cart != null) {
                cart.getData().clear();
                session.setAttribute("cart", cart);
                jsonObject.put("status", 200);
                jsonObject.put("message", "Giỏ hàng đã được làm trống.");
            } else {
                jsonObject.put("status", 500);
                jsonObject.put("message", "Giỏ hàng trống, không có gì để xóa.");
            }
        } else { // Xóa một sản phẩm
            if (cart != null && cart.remove(type + id)) {
                session.setAttribute("cart", cart);
                jsonObject.put("status", 200);
                jsonObject.put("message", "Xóa sản phẩm thành công.");
            } else {
                jsonObject.put("status", 500);
                jsonObject.put("message", "Xóa sản phẩm không thành công.");
            }
        }

        resp.setContentType("application/json");
        resp.getWriter().write(jsonObject.toString());
    }


}
