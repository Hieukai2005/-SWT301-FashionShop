/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CustomerDAO;
import dal.ProductDAO;
import dal.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import model.Customer;
import model.Product;
import model.User;
import model.Voucher;

/**
 *
 * @author HoaNK
 * @date: 26/1/2026
 * @description: Load san pham tu csdl len trang home
 */
@WebServlet(name = "Home", urlPatterns = {"/home"})
public class Home extends HttpServlet {

    private final String HOME = "home.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //
        List<Product> listNewestProducts = null;
        List<Product> listFlashSaleProducts = null;
        List<Voucher> listVouchers = null;
        User user = (User) request.getSession().getAttribute("customer");
        //
        try {
            //8 sản phẩm mới nhất
            ProductDAO daoP = new ProductDAO();
            VoucherDAO voucherDAO = new VoucherDAO();
            if (user != null) {
                // chuyển user sang customer để lấy thông tin về tier
                Customer customer = new Customer();
                CustomerDAO customerDAO = new CustomerDAO();
                customer.setUserId(user.getUserId());
                customer.setAddress(user.getAddress());
                customer.setEmail(user.getEmail());
                customer.setPhoneNumber(user.getPhoneNumber());
                customer.setFullName(user.getFullName());
                customer.setCreatedAt(user.getCreatedDateFormat());
                customer.setTotalSpending(customerDAO.getTotalSpendingByUserId(user.getUserId()));
                customer.setTotalOrders(customerDAO.getTotalOrderByUserId(user.getUserId()));
                
                listVouchers = voucherDAO.getVoucherForNewUser(user, customer);
                request.setAttribute("listVouchers", listVouchers);
            }
            listNewestProducts = daoP.get8NewestProducts();
            request.setAttribute("listNewestProducts", listNewestProducts);
            //LinhNH
            //sản phẩm đang flash sale
            ProductDAO dao = new ProductDAO();
            List<Product> flashSaleList = dao.getFlashSaleProducts();
            request.setAttribute("listFlashSaleProducts", flashSaleList);
            //sản phẩm best seller
            ProductDAO daoB = new ProductDAO();
            List<Product> bestSellerList = daoB.getTop8BestSeller();
            request.setAttribute("bestSellerList", bestSellerList);

        } catch (Exception e) {
            log("Home_Error", e);
        } finally {
            if (listFlashSaleProducts == null) {
                request.setAttribute("emptyFlashSale",
                        "Hiện chưa có chương trình Flash Sale nào. Bạn hãy quay lại sau nhé!");
            }
            request.getRequestDispatcher(HOME).forward(request, response);
        }
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
