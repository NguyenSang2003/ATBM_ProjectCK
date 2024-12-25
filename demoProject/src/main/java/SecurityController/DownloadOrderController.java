package SecurityController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

@WebServlet(name = "DownloadOrderController", value = "/download-order")
public class DownloadOrderController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String orderSummary = (String) session.getAttribute("orderSummary");

        if (orderSummary == null) {
            resp.sendRedirect("/checkout.jsp?error=no_order");
            return;
        }

        // Set the content type and the content disposition header for file download
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=order-summary.txt");

        // Set the correct character encoding for the output file (UTF-8)
        resp.setCharacterEncoding("UTF-8");

        // Write the order summary to the response output stream
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"))) {
            writer.write(orderSummary);
        }
        System.out.println("Tải đơn hàng thành công");
    }
}