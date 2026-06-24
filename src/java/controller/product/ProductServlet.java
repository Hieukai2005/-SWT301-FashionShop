/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

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
import model.Product;

/**
 *
 * @author HoaNK
 * @date: 26/01/2026
 * @description: Load du lieu tu csdl len trang san pham, tim kiem,loc phan trang
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String PRODUCT = "product.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //
        String url = ERROR;
        ProductDAO daoP = new ProductDAO();
        CategoryDAO daoC = new CategoryDAO();
        //
        String page_raw = request.getParameter("page");
        String gender = request.getParameter("gender");
        String cid_raw = request.getParameter("cid");
        String discountRange_raw = request.getParameter("discountRange");
        String priceRange_raw = request.getParameter("priceRange");
        String sort = request.getParameter("sort");
        String textSearch = request.getParameter("text_search");
        
        String getSaleProduct = request.getParameter("isSale");

        //
        try {
            // convert data
            int page = (page_raw != null && !page_raw.isEmpty()) ? Integer.parseInt(page_raw) : 1;
            int cid = (cid_raw != null && !cid_raw.isEmpty()) ? Integer.parseInt(cid_raw) : 0;
            int discountRange = (discountRange_raw != null && !discountRange_raw.isEmpty()) ? Integer.parseInt(discountRange_raw) : 0;
            int priceRange = (priceRange_raw != null && !priceRange_raw.isEmpty()) ? Integer.parseInt(priceRange_raw) : 0;
            // load san pham phan trang
            if (getSaleProduct == null && !gender.trim().isEmpty()) {
                this.loadProductPaging(request, daoP, page, gender, cid, discountRange, priceRange, sort,textSearch);
            } else {
                this.loadProductSale(request, daoP, page, getSaleProduct, cid, discountRange, priceRange, sort,textSearch);
            }
            // load tat ca danh muc 
            this.loadAllCategory(request, daoC);
            //
            url = PRODUCT;
        } catch (Exception e) {
            log("Error at product" + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
    
    }

    private void loadProductPaging(HttpServletRequest request, ProductDAO daoP, int page,
            String gender, int cid, int discountRange, int priceRange, String sort,String textSearch) {
        //
        int numberOfProduct = daoP.getNumberProductUser(gender, cid, discountRange, priceRange,textSearch);
        int numberPerPage = 8;
        int numOfPage = (numberOfProduct + numberPerPage - 1) / numberPerPage;
        int numberOfPage = (numberOfProduct % numberPerPage == 0)
                ? numOfPage : numOfPage;

        int curentBlock = (page - 1) / 5; // mỗi block chứa 5 số phần tử phân trang
        int startPage = curentBlock * 5 + 1;
        int endPage = Math.min(startPage + 5 - 1, numOfPage); // làm như này để tránh lấy thừa trang

        List<Product> listProductPaging = null;
        listProductPaging = daoP.splitPagingProducts(page, numberPerPage, gender, cid, discountRange, priceRange, sort,textSearch);
        request.setAttribute("listPaging", listProductPaging);
        request.setAttribute("numberOfPage", numberOfPage);
        // gửi du lieu lên request scope cho view trang sau lấy
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        //
        request.setAttribute("numberOfProduct", numberOfProduct);
        request.setAttribute("page", page);
        request.setAttribute("gender", gender);
        request.setAttribute("cid", cid);
        request.setAttribute("discountRange", discountRange);
        request.setAttribute("priceRange", priceRange);
        request.setAttribute("sort", sort);
        request.setAttribute("textSearch", textSearch);
        //
        if (listProductPaging == null || listProductPaging.size() == 0) {
            request.setAttribute("emptyProduct",
                    "Không có sản phẩm phù hợp!");
        }
    }

    // tai tat ca category len trang
    private void loadAllCategory(HttpServletRequest request, CategoryDAO daoC) {
        List<Category> listCategory = daoC.getAllCategory();
        request.setAttribute("listAllCategory", listCategory);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void loadProductSale(HttpServletRequest request, ProductDAO daoP, int page, String saleProduct, int cid
            , int discountRange, int priceRange, String sort,String textSearch) {
        int numberOfProduct = daoP.getNumberProductSale(cid, discountRange, priceRange,textSearch);
        int numberPerPage = 8;
        int numOfPage = (numberOfProduct + numberPerPage - 1) / numberPerPage;
        int numberOfPage = (numberOfProduct % numberPerPage == 0)
                ? numOfPage : numOfPage;

        int curentBlock = (page - 1) / 5; // mỗi block chứa 5 số phần tử phân trang
        int startPage = curentBlock * 5 + 1;
        int endPage = Math.min(startPage + 5 - 1, numOfPage); // làm như này để tránh lấy thừa trang

        List<Product> listProductPaging = null;
        listProductPaging = daoP.splitPagingProductsSale(page, numberPerPage, saleProduct, cid, discountRange, priceRange, sort,textSearch);
        System.out.println(saleProduct);
        request.setAttribute("listPaging", listProductPaging);
        request.setAttribute("numberOfPage", numberOfPage);
        // gửi du lieu lên request scope cho view trang sau lấy
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        //
        request.setAttribute("numberOfProduct", numberOfProduct);
        request.setAttribute("page", page);
        request.setAttribute("cid", cid);
        request.setAttribute("discountRange", discountRange);
        request.setAttribute("priceRange", priceRange);
        request.setAttribute("sort", sort);
        request.setAttribute("textSearch", textSearch);
        //
        if (listProductPaging == null || listProductPaging.size() == 0) {
            request.setAttribute("emptyProduct",
                    "Không có sản phẩm phù hợp!");
        }
    }

}
