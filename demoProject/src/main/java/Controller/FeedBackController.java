package Controller;

import DAO.FeedbackDAO;
import DAO.OrderDAO;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "FeedbackController", value = "/feedback")
public class FeedBackController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset= UTF-8");
        String type = req.getParameter("type");
        int id = Integer.parseInt(req.getParameter("id"));
        String content = req.getParameter("content").trim();
        String star = req.getParameter("star");
        System.out.println(type + id + content);
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        OrderDAO orderDAO = new OrderDAO();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String URL = "/demoProject_war/detail?type=" + type + "&id=" + id;
//        xử lí các case có thể xảy ra
//        người dùng chưa đăng nhập -> OK
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        //  Xử lí mua hàng rồi mới được bình luận
        if( "odd".equals(type )&&!orderDAO.checkUserOrderOddImage(user.getId(), id) ){
            HttpSession session1 = req.getSession();
            session1.setAttribute("errMess", "Bạn chưa mua sản phẩm này ");
            session1.setMaxInactiveInterval(60);
            resp.sendRedirect(URL);
            return;
        }

//        Xử lí value rỗng
        if (content.trim().length() == 0 && star == null) {

            HttpSession session1 = req.getSession();
            session1.setAttribute("errMess", "Vui lòng nhập trường này");
            session1.setMaxInactiveInterval(60);
            resp.sendRedirect(URL);
            return;
        }

//        thỏa điều kiện thì mới cho insert
//
        if (type.equals("album")) {
            if(!feedbackDAO.checkUserFeedbackForAlbum(user.getId(),id)){

                feedbackDAO.insertFeedbackForAlbum(id, user.getId(), content.trim(), star);
            }
            if(content.trim().length() == 0 || content.isEmpty()){
                feedbackDAO.updateStarForAlbum(star, user.getId(),id);
            }
            feedbackDAO.updateAlbumFeedback(user.getId(), id,star, content);
        } else if (type.equals("odd")) {
            if(!feedbackDAO.checkUserFeedbackForOddImage(user.getId(), id)){

                feedbackDAO.insertFeedbackForOddImage(id, user.getId(), content.trim(),star);
            }
            if(content.trim().length() == 0){
                feedbackDAO.updateStarForOddImage(star,user.getId(), id);
                System.out.println("ex");
            }
            feedbackDAO.updateOddImageFeedback(user.getId(),id,star,content);
        }
        resp.sendRedirect("http://localhost:8080/demoProject_war/detail?type="+type+"&id="+ id);
    }

}
