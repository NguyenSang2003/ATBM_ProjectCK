package Controller;

import DAO.UserDAO;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Đặt encoding cho request và response
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmailAndPass(email, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId()); // Lưu userId vào session
            session.setMaxInactiveInterval(10 * 60);// Session tồn tại trong 10 phút
            resp.sendRedirect("index");
        } else {
            req.setAttribute("err", "Email hoặc mật khẩu không đúng");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
