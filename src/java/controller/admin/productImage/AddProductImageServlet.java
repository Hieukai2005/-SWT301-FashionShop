/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin.productImage;

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
 * @date: 23/2/2026
 * @description: them anh chi tiet moi cho san pham
 */
@WebServlet(name="AddProductImageServlet", urlPatterns={"/addproductimage"})
public class AddProductImageServlet extends HttpServlet {
    
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
            out.println("<title>Servlet AddProductImageServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProductImageServlet at " + request.getContextPath () + "</h1>");
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
        String imageDetails = request.getParameter("image_details");
        ProductDAO daoP = new ProductDAO();
        HttpSession session = request.getSession();
        //
        try {
            int pid = Integer.parseInt(pid_raw);
            //
            boolean checkAdd = daoP.addDetailsImage(pid, imageDetails);
            if(checkAdd) {
                session.setAttribute("messSuccess", "Thêm ảnh thành công!");
            } else {
                session.setAttribute("messFail", "Thêm ảnh thất bại!");
            }
            // duong dan
            url = UPDATE_PRODUCT_ADMIN + "?pid=" + pid;
        } catch (Exception e) {
            log("Error at add product image" + e.toString());
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
