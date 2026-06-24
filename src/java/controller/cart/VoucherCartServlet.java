/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.cart;

import dal.VoucherDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import model.User;
import model.Voucher;

/**
 *
 * @author dotha
 * @date: 13/2/2026
 * @description xử lí nghiệp vụ liên quan đến mã giảm giá, gửi request trạng
 * thái áp dụng của mã đến trang cart.jsp
 */
public class VoucherCartServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("customer");

        double totalPrice = (double) session.getAttribute("totalPrice");
        session.removeAttribute("totalPrice");

        VoucherDAO voucherDAO = new VoucherDAO();
        LocalDateTime timeNow = LocalDateTime.now();

        String voucherCode = request.getParameter("voucherCode");
        session.setAttribute("voucher", voucherCode);

        // người dùng chưa nhập mã giảm giá nhưng đã nhấn áp dụng
        if (voucherCode.trim().isEmpty()) {
            request.setAttribute("nullVoucherCode", "Voucher chưa được nhập!");
            session.removeAttribute("voucher");
            session.removeAttribute("discountValue");
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        Voucher voucher = voucherDAO.getVoucherByVoucherCode(voucherCode);

        // mã giảm giá không khớp, không hợp lệ
        if (voucher == null) {
            request.setAttribute("voucherNotFound", "Voucher không hợp lệ!");
            session.removeAttribute("voucher");
            request.setAttribute("voucher", voucherCode);
            session.removeAttribute("discountValue");
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        // mã giảm giá đúng nhưng đã hết hạn sử dụng
        if (voucher.getEndDate().isBefore(timeNow)) {
            request.setAttribute("voucherExpired", "Voucher đã hết hạn sử dụng!");
            session.removeAttribute("voucher");
            request.setAttribute("voucher", voucherCode);
            session.removeAttribute("discountValue");
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        // mã giảm giá đúng nhưng chưa được active
        if (!voucher.isIsActive()) {
            request.setAttribute("voucherNotAvailable", "Voucher hiện không khả dụng!");
            session.removeAttribute("voucher");
            request.setAttribute("voucher", voucherCode);
            session.removeAttribute("discountValue");
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        // mã giảm giá đúng nhưng chưa đến lúc dùng được
        LocalDateTime startDate = voucher.getStartDate();
        if (startDate.isAfter(timeNow)) {
            request.setAttribute("voucherEarly", "Voucher khả dụng từ lúc " + startDate.getHour() + " giờ " + startDate.getMinute() + " phút ngày " + startDate.getDayOfMonth() + " tháng " + startDate.getMonthValue() + " năm " + startDate.getYear());
            session.removeAttribute("voucher");
            request.setAttribute("voucher", voucherCode);
            session.removeAttribute("discountValue");
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        // mã giảm giá đúng nhưng đơn hàng chưa đáp ứng được giá trị đơn hàng tối thiểu
        if (totalPrice < voucher.getMinOrderValue()) {
            request.setAttribute("minOrderValueViolation", voucher.getMinOrderValue());
            session.removeAttribute("voucher");
            request.setAttribute("voucher", voucherCode);
            session.removeAttribute("discountValue");
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        // mã giảm giá đúng nhưng đã hết lượt sử dụng
        int usedCheck = voucher.getQuantity() - voucher.getUesedCount();
        if (usedCheck <= 0) {
            request.setAttribute("ExceededUsage", "Voucher đã hết lượt sử dụng!");
            session.removeAttribute("voucher");
            request.setAttribute("voucher", voucherCode);
            session.removeAttribute("discountValue");
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        // mã đúng nhưng người dùng đã dùng voucher này trước đó
        boolean checkVoucherUsage = voucherDAO.getVoucherUsageByVoucherIdAndUseId(voucher.getVoucherId(), user.getUserId());
        if (checkVoucherUsage) {
            request.setAttribute("ExceededUsage", "Bạn đã dùng Voucher này trước đó, mỗi voucher chỉ được sử dụng 1 lần!");
            session.removeAttribute("voucher");
            session.removeAttribute("discountValue");
            request.setAttribute("voucher", voucherCode);
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        // mã đúng và dùng được
        session.setAttribute("voucherApplied", true);
        session.setAttribute("voucherObj", voucher);
        session.setAttribute("discountValue", voucher.getDiscountValue());
        if ("percent".equalsIgnoreCase(voucher.getDiscountType())) {
            double maxDiscount = voucher.getMaxDiscount();
            request.setAttribute("maxDiscount", maxDiscount);
            // giảm theo % sản phẩm, nhưng giá trị đơn hàng quá cao sẽ chỉ giảm đến ngưỡng max của mã giảm giá
            double discountProductValue = totalPrice * voucher.getDiscountValue() / 100.0;
            if (discountProductValue >= maxDiscount) {
                discountProductValue = maxDiscount;
            }
            session.setAttribute("discountValue", discountProductValue);
        }
        request.getRequestDispatcher("cart?action=viewcart").forward(request, response);

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
