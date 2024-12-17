package Controller;

import DAO.BelongDAO;
import DAO.ProductDAO;
import DAO.TopicDAO;
import Model.OddImage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ProductForTopicController", value = "/pTopic")
public class ProductForTopicController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        TopicDAO topicDAO = new TopicDAO();
        BelongDAO belongDAO = new BelongDAO();
        ProductDAO productDAO = new ProductDAO();
        String nameTopic = req.getParameter("q");
        String type = req.getParameter("type");
        String path = req.getServletPath();
        int page = 1;
        int recSize = 5;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        int idTopic = topicDAO.getIdTopicByName(nameTopic);
        int totalOdd = belongDAO.getCountOddByIdTopic(idTopic);
        int max = totalOdd;
        int totalPage = (int) Math.ceil((double) max / recSize);
        ArrayList<Integer> listIdOdd = belongDAO.listIdOddImageBelongTopicPagination(String.valueOf(idTopic), page, recSize);
        ArrayList<OddImage> listOdd = new ArrayList<>();
        for (int id : listIdOdd) {
            listOdd.add(productDAO.getOddImageById(id));
        }
        if ("odd".equals(type)) {
            req.setAttribute("listOddImage", listOdd);
            req.setAttribute("listTopic", topicDAO.getAllTopicsForClient());
            req.getRequestDispatcher("Topic.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("listOddImage", listOdd);
        req.setAttribute("listTopic", topicDAO.getAllTopicsForClient());
        System.out.println("Line 65: " + page);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("nameTopic", nameTopic);
        req.getRequestDispatcher("Topic.jsp").forward(req, resp);
    }
}