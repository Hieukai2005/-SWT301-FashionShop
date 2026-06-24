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
import java.util.List;
import model.Category;
import model.Color;
import model.Product;
import model.Size;

/**
 * @author HoaNK
 * @date: 08/02/2026
 * @description: Load san pham, tim kiem, loc cate,
 *               phan trang tren trang san pham admin (READ)
 */
@WebServlet(name = "ProductAdminServlet", urlPatterns = {"/productadmin"})
public class ProductAdminServlet extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String PRODUCT_ADMIN = "admin/product-page.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //
        String url = ERROR;
        // trang duoc chon
        String page_raw = request.getParameter("page");
        String text = request.getParameter("text");
        String cid_raw = request.getParameter("cid");
        String statusSale = request.getParameter("statusSale");
        String statusActive = request.getParameter("statusActive");
        
        ProductDAO daoP = new ProductDAO();
        CategoryDAO daoC = new CategoryDAO();
        //
        try {
            // chuyen doi du lieu
            int cid = (cid_raw != null && !cid_raw.isEmpty()) ? Integer.parseInt(cid_raw) : 0;
            int page = (page_raw != null && !page_raw.isEmpty()) ? Integer.parseInt(page_raw) : 1;           
            // danh sach san pham da phan trang
            this.loadProductPaging(request, daoP,page,text,cid,statusSale,statusActive);
            // danh sach chi tiet san pham
            this.loadAllCategory(request, daoC); // danh muc
            this.loadAllSize(request, daoP); // size
            this.loadAllColor(request, daoP); // color
            //
            url = PRODUCT_ADMIN;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
        //
    }
    
    // Load du lieu theo trang, tim kiem va loc cate
    private void loadProductPaging(HttpServletRequest request, ProductDAO daoP,
            int page, String text,int cid,String statusSale,String statusActive) {
        // tinh toan phan trang
        int numberOfProduct = daoP.getNumberOfProduct(text, cid,statusSale,statusActive); // số sản phẩm
        int numberPerPage = 8; // số sản phẩm 1 trang
        int numOfPage = (numberOfProduct + numberPerPage - 1) / numberPerPage;
        int numberOfPage = (numberOfProduct % numberPerPage == 0)
                ? numOfPage : numOfPage; // số trang cần để chứa n sản phẩm
        // danh sanh n san pham tren 1 trang da chon
        
        int curentBlock = (page - 1) / 5; // mỗi block chứa 5 số phần tử phân trang
        int startPage = curentBlock * 5 + 1;
        int endPage = Math.min(startPage + 5 - 1, numOfPage); // làm như này để tránh lấy thừa trang
        
        List<Product> listProductPaging = null;
        listProductPaging = daoP.splitPaging(page, numberPerPage, text, cid,statusSale,statusActive);
        request.setAttribute("listPagingAdmin", listProductPaging);
        request.setAttribute("numberOfPage", numberOfPage);
        // gửi du lieu lên request scope cho view trang sau lấy
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        //

        request.setAttribute("page", page);
        request.setAttribute("text", text);
        request.setAttribute("cid", cid);
        request.setAttribute("statusSale", statusSale);
        request.setAttribute("statusActive", statusActive);
        //
        if (listProductPaging == null || listProductPaging.size() == 0) {
            request.setAttribute("emptyProductAdmin",
                    "Kho hàng chưa có sản phẩm phù hợp!");
        }
    }

    // Load du lieu danh muc san pham
    private void loadAllCategory(HttpServletRequest request, CategoryDAO daoC) {
        List<Category> listAllCategory = daoC.getAllCategory();
        request.setAttribute("listAllCategory", listAllCategory);
    }
    // Load du lieu size
    private void loadAllSize(HttpServletRequest request, ProductDAO daoP) {
        List<Size> listAllSize = daoP.getAllSize();
        request.setAttribute("listAllSize", listAllSize);
    } 
    // Load du lieu color
    private void loadAllColor(HttpServletRequest request, ProductDAO daoP) {
        List<Color> listAllColor = daoP.getAllColor();
        request.setAttribute("listAllColor", listAllColor);
    } 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
