package Controller;

import DAO.*;
import Model.Material;
import Model.Size;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeController", value = "/index")
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        TopicDAO topicDAO = new TopicDAO();
        ProductDAO productDAO = new ProductDAO();
        OrderDAO orderDAO = new OrderDAO();
        MaterialDAO materialDAO = new MaterialDAO();
        SizeDAO sizeDAO = new SizeDAO();

        // Lấy danh sách chất liệu và kích thước
        List<Material> materials = materialDAO.getAllMaterials();
        List<Size> sizes = sizeDAO.getAllSizes();

        // Truyền dữ liệu vào request
        req.setAttribute("listTopic", topicDAO.getAllTopicsForClient());
        req.setAttribute("listOddNew", productDAO.getTop8ddImageNew());
        req.setAttribute("listOddImageOrder", orderDAO.getTop8OddImageOrder());
        req.setAttribute("materials", materials);  // Chất liệu
        req.setAttribute("sizes", sizes);  // Kích thước

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}

