package controller.admin.product;

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
import java.time.LocalDateTime;
import java.util.List;
import model.Category;
import model.Color;
import model.Product;
import model.ProductVariant;
import model.Size;

/**
 * @author HoaNK
 * @date: 10/1/2026
 * @description: Sua san pham trang admin product
 */
@WebServlet(name = "UpdateProductAdminServlet", urlPatterns = {"/updateproduct"})
public class UpdateProductAdminServlet extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String UPDATE_PRODUCT_ADMIN = "admin/update-product.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // doPost

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
        String url = ERROR;
        String pid_raw = request.getParameter("pid");
        //
        ProductDAO daoP = new ProductDAO();
        CategoryDAO daoC = new CategoryDAO();
        //
        try {
            int pid = Integer.parseInt(pid_raw);
            // 1. Load dữ liệu dùng chung
            this.loadCommonProduct(request, daoP, daoC);
            // 2. Load dữ liệu chi tiết sản phẩm và biến thể
            this.loadProductAndVariants(request, pid, daoP);
            // 3. Load dữ liệu ảnh
            loadProductImageDetails(request, pid, daoP);
            //
            url = UPDATE_PRODUCT_ADMIN;
        } catch (Exception e) {
            log("Error at update admin: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    

    
    //
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        ProductDAO daoP = new ProductDAO();
        CategoryDAO daoC = new CategoryDAO();
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String pid_raw = request.getParameter("pid");
        //
        try {
            int pid = (pid_raw != null && !pid_raw.isEmpty()) ? Integer.parseInt(pid_raw) : 0;
            //
            boolean checkUpdate = this.updateCommonProduct(request, pid, daoP);
            if (checkUpdate) {
                session.setAttribute("messSuccess", "Cập nhật sản phẩm thành công!");
            } else {
                session.setAttribute("messFail", "Cập nhật thất bại. Vui lòng thử lại!");
            }
            //
            // load lại dữ liệu chung
            this.loadCommonProduct(request, daoP, daoC);
            this.loadProductAndVariants(request, pid, daoP);
            loadProductImageDetails(request, pid, daoP);
            url = UPDATE_PRODUCT_ADMIN;
        } catch (Exception e) {
            log("Error at update admin" + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    // lấy thông tin chung của 1 sản phẩm
    private void loadCommonProduct(HttpServletRequest request, ProductDAO daoP, CategoryDAO daoC) {
        request.setAttribute("listAllCategory", daoC.getAllCategory());
        request.setAttribute("listAllSize", daoP.getAllSize());
        request.setAttribute("listAllColor", daoP.getAllColor());
    }

    // lấy thông tin về biến thể của sản phẩm đó
    private void loadProductAndVariants(HttpServletRequest request, int pid, ProductDAO daoP) {
        request.setAttribute("product", daoP.getProductById(pid));
        request.setAttribute("listProductVariant", daoP.getVariantsByProductId(pid));
    }
    // lấy ảnh chi tiết của sản phẩm đó
    private void loadProductImageDetails(HttpServletRequest request, int pid, ProductDAO daoP) {
        request.setAttribute("listImageDetails", daoP.getAllProductImageDetails(pid));
    }
    // cập nhật thông tin chung cho sẩn phẩm
    private boolean updateCommonProduct(HttpServletRequest request, int pid, ProductDAO daoP) {
        // Tham số cập nhật cho thông tin chung 1 sản phẩm
        String name = request.getParameter("name");
        String price_raw = request.getParameter("price");
        String category_raw = request.getParameter("category");
        String gender = request.getParameter("gender");
        String isSale_raw = request.getParameter("is_sale");
        String discount_raw = request.getParameter("discount");
        String isActive_raw = request.getParameter("is_active");
        String image = request.getParameter("image");
        String description = request.getParameter("description");
        // convert dữ liệu cho update products
        double price = (price_raw != null && !price_raw.isEmpty()) ? Double.parseDouble(price_raw) : 1;
        int categoryId = (category_raw != null && !category_raw.isEmpty()) ? Integer.parseInt(category_raw) : 0;
        boolean isSale = (Integer.parseInt(isSale_raw) == 1) ? true : false;
        boolean isActive = (Integer.parseInt(isActive_raw) == 1) ? true : false;
        int discount = (discount_raw != null && !discount_raw.isEmpty() && isSale == true) ? Integer.parseInt(discount_raw) : 0;
        //
        Product product = new Product(pid, name, price, description, image, categoryId, gender, isSale, isActive, discount, null);
        //
        boolean checkUpdate = daoP.updateProductInfo(product);
        if (checkUpdate == true) {
            return true;
        }
        return false;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
