/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin.productVariant;

import dal.ProductDAO;
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
 * @date: 20/2/2026
 * @description: Xoa 1 bien the cua san pham
 */
@WebServlet(name="DeleteProductVariantServlet", urlPatterns={"/deleteproductvariant"})
public class DeleteProductVariantServlet extends HttpServlet {
   
    private final String ERROR = "error.jsp";
    private final String UPDATE_PRODUCT_ADMIN = "updateproduct";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //
        
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //
        String url = ERROR;        
        ProductDAO daoP = new ProductDAO();
        HttpSession session = request.getSession();
        //
        String pvid_raw = request.getParameter("pvid");
        String pid_raw = request.getParameter("pid");
        
        try {
            int pvid = Integer.parseInt(pvid_raw);
            int pid = Integer.parseInt(pid_raw);
            boolean checkDel = daoP.deleteAProductVariantById(pvid);
        if (checkDel == true) {
            // nếu xóa hết biến thể thì cập nhật trạng thái hết sản phẩm
            int countVariant = daoP.countVariant(pid);
            if(countVariant == 0) {
                daoP.updateIsActive(pid, 0);
            }
            session.setAttribute("messSuccess", "Xóa biến thể thành công!");
        } else {
            session.setAttribute("messFail", "Biến thể đã phát sinh giao dịch. Không thể xóa!");
        }
        //
        url = UPDATE_PRODUCT_ADMIN + "?pid=" + pid;
        } catch (Exception e) {
            log("Error at Delete product variant" + e.toString());
        } finally {
            response.sendRedirect(request.getContextPath() + "/" + url);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
