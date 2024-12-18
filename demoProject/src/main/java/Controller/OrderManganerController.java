package Controller;

import DAO.OrderDAO;
import Model.Order;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "OrderManganerController", value = "/orderManager")
public class OrderManganerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect("404.jsp");
            return;
        }
        OrderDAO orderDAO = new OrderDAO();
        String optionValue = "all";
        if (req.getParameter("option") != null) {
            optionValue = req.getParameter("option");
        }
        System.out.println(optionValue);
        int page = 1;
        int recSize = 5;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        int totalOddOrder = orderDAO.totalOddOrder("oddImageOrder");
        int totalCartOrder = orderDAO.totalOddOrder("cartOrder");
        int max = Math.max(totalCartOrder, totalOddOrder);
        int totalPage = (int) Math.ceil((double) max / recSize);
        if (optionValue == null || "all".equals(optionValue)) {
            ArrayList<Order> orders = new ArrayList<>(orderDAO.getAllOrderOddImageForAdmin(page, recSize));
            orders.addAll(orderDAO.getAllCartOrderForAdmin(page, recSize));
            req.setAttribute("listOrder", orders);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPage", totalPage);
            req.setAttribute("type", optionValue);
            req.getRequestDispatcher("quanlidonhang.jsp").forward(req, resp);
            return;
        } else {
            ArrayList<Order> orders = new ArrayList<>(orderDAO.getAllOrderOddImageForByStatus(optionValue));
            orders.addAll(orderDAO.getAllCartOrderForByStatus(optionValue));
            req.setAttribute("listOrder", orders);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPage", totalPage);
            req.setAttribute("type", optionValue);
            req.getRequestDispatcher("quanlidonhang.jsp").forward(req, resp);
            return;
        }

    }
}
