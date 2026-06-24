package controller.admin.productVariant;

import dal.CategoryDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ProductVariant;

/**
 * @author HoaNK
 * @date: 20/2/2026
 * @description: Them 1 bien the cua san pham
 */
@WebServlet(name = "AddProductVariantServlet", urlPatterns = {"/addproductvariant"})
public class AddProductVariantServlet extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String UPDATE_PRODUCT_ADMIN = "updateproduct";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //
        String url = ERROR;
        String pid_raw = request.getParameter("pid");
        //
        ProductDAO daoP = new ProductDAO();
        CategoryDAO daoC = new CategoryDAO();
        HttpSession session = request.getSession();
        //
        try {
            int pid = Integer.parseInt(pid_raw);
            // add 1 bien the thanh cong hoac that bai gui thong bao len session scorpe
            boolean checkAdd = this.addAVariant(request, daoP, pid);
            if (checkAdd) {
                int countVariant = daoP.countVariant(pid);
                if(countVariant == 1) {
                    daoP.updateIsActive(pid, 1);
                }
                session.setAttribute("messSuccess", "Thêm biến thể thành công!");
            } else {
                session.setAttribute("messFail", "Trùng biến thể!");
            }
            //
            url = UPDATE_PRODUCT_ADMIN + "?pid=" + pid;
        } catch (Exception e) {
            log("Error in add product variant!" + e.toString());
        } finally {
            //request.getRequestDispatcher(url).forward(request, response);
            response.sendRedirect(request.getContextPath() + "/" + url);
        }
    }

    // thêm mới 1 biến thể
    private boolean addAVariant(HttpServletRequest request, ProductDAO daoP, int pid) {
        // Tham số cho 1 biến thể
        String size_raw = request.getParameter("size");
        String color_raw = request.getParameter("color");
        String quantity_raw = request.getParameter("quantity");
        String image = request.getParameter("image_main");
        // convert cho edit product_variant
        int size = (size_raw != null && !size_raw.isEmpty()) ? Integer.parseInt(size_raw) : 0;
        int color = (color_raw != null && !color_raw.isEmpty()) ? Integer.parseInt(color_raw) : 0;
        int quantity = (quantity_raw != null && !quantity_raw.isEmpty()) ? Integer.parseInt(quantity_raw) : 0;

        //
        ProductVariant variant = new ProductVariant(0, pid, size, color, quantity, image, 0);
        //
        boolean checkVariant = daoP.addAProductVariant(variant);
        if(checkVariant == true){
        boolean checkImage = daoP.addDetailsImage(pid, variant.getImage());
        }
        //
        return checkVariant;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
