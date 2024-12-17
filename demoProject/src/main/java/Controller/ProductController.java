package Controller;

import DAO.ProductDAO;
import DAO.TopicDAO;
import Upload.UploadFile;
import Model.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB)
@WebServlet(name = "ProductController", value = "/product/*")
public class ProductController extends HttpServlet {
    TopicDAO topicDAO = new TopicDAO();
    ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user") == null ? null : (User) session.getAttribute("user");
//        // Kiểm tra quyền và chuyển hướng
        if (user == null || !user.isAdmin()) {
            System.out.println("redirect");
            resp.sendRedirect("404.jsp");
            return;
        } else if (user.isAdmin()) {
            System.out.println("GET");
            int page = 1;
            int recSize = 5;
            if (req.getParameter("page") != null) {

                page = Integer.parseInt(req.getParameter("page"));
            }
            int totalOdd = productDAO.totalOdd();
            int totalPage = 0;
            totalPage = (int) Math.ceil((double) totalOdd / recSize);
            req.setAttribute("listOddImage", productDAO.getAllOddImage(page, recSize));
            req.setAttribute("listNamesTopic", topicDAO.getAllNamesTopic());
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPage", totalPage);
            req.getRequestDispatcher("quanlisanpham.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String type = req.getPathInfo();
        System.out.println(type);
        TopicDAO topicDAO = new TopicDAO();
        UploadFile uploadFile = new UploadFile();
        if (type.equals("/addImg")) {

        } else if (type.equals("/addAlbum")) {
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String path = req.getPathInfo();
        System.out.println(path);
        ProductDAO productDAO = new ProductDAO();
        JSONObject jsonObjectResults = new JSONObject();
        if (path.equals("/deleteOddImage")) {
            int idOddImage = Integer.parseInt(req.getParameter("idOddImage"));
            if (productDAO.deleteOddImage(idOddImage)) {
                jsonObjectResults.put("status", 200);
                jsonObjectResults.put("message", "Đã xóa ảnh thành công");

            } else {
                jsonObjectResults.put("status", 500);
                jsonObjectResults.put("message", "Xóa ảnh thất bại");
            }
            resp.setContentType("application/json");
            resp.getWriter().write(jsonObjectResults.toString());

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String path = req.getPathInfo();
        System.out.println(path);
        ProductDAO productDAO = new ProductDAO();
        JSONObject jsonObject = new JSONObject();
        String status = "false";
        if ("/editShowOddImage".equals(path)) {
            String idOddImage = req.getParameter("idOddImage");
            String beforeChange = String.valueOf(productDAO.getShowOddImage(idOddImage));
            if ("false".equals(beforeChange)) {
                status = "true";
            }
            if (productDAO.updateShowOddImage(Integer.parseInt(idOddImage), status)) {
                jsonObject.put("status", 200);
                jsonObject.put("message", "Cập nhật thành công");
                resp.setContentType("application/json");
                resp.getWriter().write(jsonObject.toString());
                System.out.println("update thành công");
            } else {
                jsonObject.put("status", 500);
                jsonObject.put("message", "Cập nhật thất bại thất bại. Vui lòng thử lại");
                resp.setContentType("application/json");
                resp.getWriter().write(jsonObject.toString());

            }
        }
    }
}
