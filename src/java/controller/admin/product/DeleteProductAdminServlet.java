package controller.admin.product;

import dal.ProductDAO;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author HoaNK
 * @date: 10/1/2026
 * @description :Xoa 1 san pham o trang admin
 */
@WebServlet(name = "DeleteProductAdminServlet", urlPatterns = {"/deleteproductadmin"})
public class DeleteProductAdminServlet extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String PRODUCT_ADMIN_SERVLET = "productadmin";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // 
        String url = ERROR;
        HttpSession session = request.getSession();
        // dữ liệu để xóa trang nào ở yên trang đó
        String page_raw = request.getParameter("page");
        String text = request.getParameter("text");
        String cid_raw = request.getParameter("cid");
        //
        String pid_raw = request.getParameter("pid");
        //
        try {
            int pid = (pid_raw != null && !pid_raw.isEmpty()) ? Integer.parseInt(pid_raw) : 0;
            int page = (page_raw != null && !page_raw.isEmpty()) ? Integer.parseInt(page_raw) : 1;
            int cid = (cid_raw != null && !cid_raw.isEmpty()) ? Integer.parseInt(cid_raw) : 0;
            // xóa 1 product bằng id
            ProductDAO daoP = new ProductDAO();
            boolean check = daoP.deleteProductAdmin(pid);
            if (check == true) {
                session.setAttribute("messSuccess", "Xóa thành công!");
            } else {
                session.setAttribute("messFail", "Sản phẩm đã phát sinh giao dịch.Không thể xóa!");
            }
            // XỬ LÝ TEXT TIẾNG VIỆT TRƯỚC KHI ĐƯA LÊN URL
            String endcodedText = "";
            if (text != null && !text.isEmpty()) {
                endcodedText = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
            }
            //
            String query = "?page=" + page + "&text=" + endcodedText + "&cid=" + cid;
            url = PRODUCT_ADMIN_SERVLET + query; // về trang vừa xóa
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            //request.getRequestDispatcher(url).forward(request, response);
            response.sendRedirect(request.getContextPath() + "/" + url); // dùng response tránh load lặp lại
        }
        //
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
