package Controller;

import DAO.ProductDAO;
import DAO.TopicDAO;
import DAO.MaterialDAO;
import DAO.SizeDAO;
import Model.OddImage;
import Model.Material;
import Model.Size;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShopController", value = "/shop")
public class ShopController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isFiltering = false;

        // Kiểm tra có đang lọc không
        if (req.getParameter("filterShop") != null) {
            isFiltering = true;
        }

        // Lấy giá trị lọc ra để gửi qua yêu cầu phân trang
        String priceRange = req.getParameter("priceRange");
        if (priceRange == null) {
            priceRange = "all";
        }
        req.setAttribute("selectedPriceRange", priceRange);

        // Xử lý hiển thị thông thường
        if (isFiltering) {
            doPost(req, resp);
        } else {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");

            // Khởi tạo các DAO
            ProductDAO productDAO = new ProductDAO();
            TopicDAO topicDAO = new TopicDAO();
            MaterialDAO materialDAO = new MaterialDAO();
            SizeDAO sizeDAO = new SizeDAO();

            // Lấy thông tin sản phẩm
            int recSize = 6;
            int page = 1;
            int totalOdd = productDAO.totalOdd();
            int max = totalOdd;
            int totalPage = (int) Math.ceil((double) (max) / recSize);
            String type = req.getParameter("type");

            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }

            if ("odd".equals(type)) {
                req.setAttribute("listOddImage", productDAO.getAllOddImageForClient(page, recSize));
                req.getRequestDispatcher("shop.jsp").forward(req, resp);
                return;
            }

            // Lấy danh sách chất liệu và kích cỡ
            List<Material> materials = materialDAO.getAllMaterials();
            List<Size> sizes = sizeDAO.getAllSizes();

            // Truyền danh sách chất liệu và kích cỡ vào request
            req.setAttribute("listOddImage", productDAO.getAllOddImageForClient(page, recSize));
            req.setAttribute("listTopic", topicDAO.getAllTopicsForClient());
            req.setAttribute("totalPage", totalPage);
            req.setAttribute("currentPage", page);
            req.setAttribute("materials", materials);  // Chất liệu
            req.setAttribute("sizes", sizes);  // Kích cỡ

            req.getRequestDispatcher("shop.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String priceRange = request.getParameter("priceRange");
            int minPrice = 0;
            int maxPrice = Integer.MAX_VALUE;

            // Nhân với 1000 để chuyển sang tiền VNĐ
            if (priceRange != null && !priceRange.equals("all")) {
                String[] prices = priceRange.split("-");
                minPrice = Integer.parseInt(prices[0]) * 1000;
                maxPrice = Integer.parseInt(prices[1]) * 1000;
            }

            // Lấy thông tin số trang hiện tại
            String pageStr = request.getParameter("page");
            String recSizeStr = request.getParameter("recSize");

            // Xét số sản phẩm hiển thị trên mỗi trang
            int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;
            int recSize = (recSizeStr != null) ? Integer.parseInt(recSizeStr) : 6;

            ProductDAO productDAO = new ProductDAO();

            // Lấy danh sách sản phẩm sau khi lọc
            List<OddImage> filteredOddImages = productDAO.getFilteredOddImages(page, recSize, minPrice, maxPrice);

            // Tính tổng số trang dựa trên kết quả lọc
            int totalFilteredItems = productDAO.totalFilteredItems(minPrice, maxPrice);
            int totalPage = (int) Math.ceil((double) totalFilteredItems / recSize);

            // Lấy danh sách chất liệu và kích cỡ
            MaterialDAO materialDAO = new MaterialDAO();
            SizeDAO sizeDAO = new SizeDAO();
            List<Material> materials = materialDAO.getAllMaterials();
            List<Size> sizes = sizeDAO.getAllSizes();

            // Set các thuộc tính cho request
            request.setAttribute("filteredOddImages", filteredOddImages);
            request.setAttribute("selectedPriceRange", priceRange);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("currentPage", page);
            request.setAttribute("materials", materials);  // Chất liệu
            request.setAttribute("sizes", sizes);  // Kích cỡ

            RequestDispatcher dispatcher = request.getRequestDispatcher("shop.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}