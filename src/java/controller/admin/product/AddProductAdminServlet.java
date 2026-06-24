package controller.admin.product;

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
 * @date: 10/2/2026
 * @description: Them san pham moi vao cua hang (CREATE)
 */
@WebServlet(name = "AddProductAdminServlet", urlPatterns = {"/addproductadmin"})
public class AddProductAdminServlet extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String PRODUCT_ADMIN_SERVLET = "productadmin";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        // Các dữ liệu insert cho bẳng product
        String name = request.getParameter("name");
        String price_raw = request.getParameter("price");
        String image = request.getParameter("image");
        String description = request.getParameter("description");
        String category_raw = request.getParameter("category");
        String gender = request.getParameter("gender");
        String isSale_raw = request.getParameter("is_sale");
        String isActive_raw = request.getParameter("is_active");
        String discount_raw = request.getParameter("discount");
        // insert cho product variant
        String size_raw = request.getParameter("size");
        String color_raw = request.getParameter("color");
        String quantity_raw = request.getParameter("quantity");
        // dữ liệu chuyển về trang đang đứng dó
        String page_raw = request.getParameter("page");
        String text = request.getParameter("text");
        String cid_raw = request.getParameter("cid");
        //
        ProductDAO daoP = new ProductDAO();
        HttpSession session = request.getSession();
        // Thêm sản phẩm trang product
        try {// convert dữ liệu cho trang products
            double price = (price_raw != null && !price_raw.isEmpty()) ? Double.parseDouble(price_raw) : 1;
            int categoryId = (category_raw != null && !category_raw.isEmpty()) ? Integer.parseInt(category_raw) : 0;
            int isSale = (isSale_raw != null && !isSale_raw.isEmpty()) ? Integer.parseInt(isSale_raw) : 0;
            int isActive = (isActive_raw != null && !isActive_raw.isEmpty()) ? Integer.parseInt(isActive_raw) : 0;
            int discount = (discount_raw != null && !discount_raw.isEmpty()) ? Integer.parseInt(discount_raw) : 0;
            // convert cho trang product_variant
            int size = (size_raw != null && !size_raw.isEmpty()) ? Integer.parseInt(size_raw) : 0;
            int color = (color_raw != null && !color_raw.isEmpty()) ? Integer.parseInt(color_raw) : 0;
            int quantity = (quantity_raw != null && !quantity_raw.isEmpty()) ? Integer.parseInt(quantity_raw) : 0;
            // covert hco trang đang đứng
            int page = (page_raw != null && !page_raw.isEmpty()) ? Integer.parseInt(page_raw) : 1;
            int cid =  (cid_raw != null  && !cid_raw.isEmpty()) ? Integer.parseInt(cid_raw) : 0;
            
            // thêm mới sản phẩm chính đồng thời lấy id của sản phẩm ddos 
            int productId = daoP.addProductAdmin(name, price, description, image, categoryId, gender, isSale, isActive, discount);
            // lấy đc id,thêm chi tiết sản phẩm vào product variant
            if (productId > 0) { // nếu <=0 thì dao trên bị sai
                daoP.addProductVariantAdmin(productId, size, color, quantity, image);
                session.setAttribute("messSuccess", "Thêm thành công!");
            } else {
                session.setAttribute("messFail", "Thêm thất bại!");
            }
            //
            String query = "?page="+page+"&text="+text+"&cid="+cid;
            url = PRODUCT_ADMIN_SERVLET + query;
        } catch (Exception e) {
            log("Error add product admin" + e.toString());
        } finally {
            //request.getRequestDispatcher(url).forward(request, response);
            response.sendRedirect(request.getContextPath() +"/"+ url);
        }
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
