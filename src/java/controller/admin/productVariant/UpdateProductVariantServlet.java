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
 *
 * @author HoaNK
 * @date: 21/2/2026
 * @description: Cap nhat 1 bien the: so luong, anh chinh cua bien the
 */
@WebServlet(name="UpdateProductVariantServlet", urlPatterns={"/updateproductvariant"})
public class UpdateProductVariantServlet extends HttpServlet {
   
    private final String ERROR = "error.jsp";
    private final String UPDATE_PRODUCT_ADMIN = "updateproduct";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateProductVariantServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProductVariantServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        String pid_raw = request.getParameter("pid");
        ProductDAO daoP = new ProductDAO();
        HttpSession session = request.getSession();
        //
        try{
            int pid = Integer.parseInt(pid_raw);
            // check update
            boolean checkUpdate = this.updateVariant(request, daoP);
            // thong bao update
            if(checkUpdate) {
               session.setAttribute("messSuccess", "Cập nhật biến thể thành công!");
            } else {
               session.setAttribute("messFail", "Cập nhật biến thể thất bại!");
            }
            // duong dan
            url = UPDATE_PRODUCT_ADMIN + "?pid=" + pid;
        }catch(Exception e) {
            log("Error at update proudct variant!" + e.toString());
        }finally{
            response.sendRedirect(request.getContextPath() + "/" + url);
        } 
    }

    private boolean updateVariant(HttpServletRequest request, ProductDAO daoP) {
        String quantity_raw = request.getParameter("quantity");
        String image = request.getParameter("image_main");
        String variantId_raw = request.getParameter("pvid");
        
        int quantity = (quantity_raw != null && !quantity_raw.isEmpty()) ? Integer.parseInt(quantity_raw) : 1;
        int variantId = Integer.parseInt(variantId_raw);
        
        return daoP.updateAVariant(quantity, image, variantId);   
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
