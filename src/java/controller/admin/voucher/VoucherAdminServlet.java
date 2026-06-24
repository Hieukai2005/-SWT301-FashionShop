/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin.voucher;

import dal.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import model.Voucher;

/**
 *
 * @author dotha
 * @date: 15/2/2026
 * @description thêm sửa xóa mã giảm giá
 */
public class VoucherAdminServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs xử lý việc nhận action từ
     * request, phân các action thành các case khác nhau để xử lý từng action
     * một
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        VoucherDAO voucherDAO = new VoucherDAO();

        String action = request.getParameter("action") == null ? "viewVoucher" : request.getParameter("action").trim();
        switch (action) {
            case "viewVoucher":
                viewVoucher(request, response, voucherDAO);
                break;
            case "addVoucher":
                addOrEditVoucher(request, response, voucherDAO, "add");
                break;
            case "editVoucher":
                addOrEditVoucher(request, response, voucherDAO, "edit");
                break;
            case "deleteVoucher":
                deleteVoucher(request, response, voucherDAO);
                break;
            default:
                break;
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

    /**
     * @author dotha
     * @param request từ discount-code.jsp
     * @param response discount-code.jsp
     * @param voucherDAO đối tượng để truy cập vào lớp DAO của Voucher
     * @throws IOException
     * @throws ServletException Xử lý việc hiển thị các voucher và phân trang,
     * hiển thị các thông tin tổng quan từ bảng voucher trong dtb
     */
    private void viewVoucher(HttpServletRequest request, HttpServletResponse response, VoucherDAO voucherDAO) throws IOException, ServletException {
        int numberOfVoucherSession = voucherDAO.getNumberOfVoucher("", "", "");
        int currentlyOperating = voucherDAO.getNumberOfVoucher("", "currentlyOperating", "");
        int expired = voucherDAO.getNumberOfVoucher("", "expired", "");
        int numberVoucherUsed = voucherDAO.getNumberOfVoucherUsed();
        int notYet = voucherDAO.getNumberOfVoucher("", "notYet", "");
        int notActive = voucherDAO.getNumberOfVoucher("", "notActive", "");
        int percent = voucherDAO.getNumberOfVoucher("", "", "percent");
        int fixed = voucherDAO.getNumberOfVoucher("", "", "fixed");

        request.setAttribute("numberOfVoucherSession", numberOfVoucherSession);
        request.setAttribute("currentlyOperating", currentlyOperating);
        request.setAttribute("expired", expired);
        request.setAttribute("numberVoucherUsed", numberVoucherUsed);
        request.setAttribute("notYet", notYet);
        request.setAttribute("notActive", notActive);
        request.setAttribute("percent", percent);
        request.setAttribute("fixed", fixed);

        String pageRaw = request.getParameter("page") == null ? "1" : request.getParameter("page");
        int page = 1;
        try {
            page = Integer.parseInt(pageRaw);
        } catch (Exception e) {
            response.sendError(400, "Lỗi không chuyển đổi được số trang");
            return;
        }
        // tránh việc user nhập page vào thanh search gây lỗi truy vấn
        if (page < 1) {
            page = 1;
        }
        String searchValue = request.getParameter("searchValue") == null ? "" : request.getParameter("searchValue");
        String status = request.getParameter("status") == null ? "" : request.getParameter("status");
        String typeOfDiscount = request.getParameter("typeOfDiscount") == null ? "" : request.getParameter("typeOfDiscount");

        request.setAttribute("searchValue", searchValue);
        request.setAttribute("status", status);
        request.setAttribute("typeOfDiscount", typeOfDiscount);

        int numberOfVoucher = voucherDAO.getNumberOfVoucher(searchValue, status, typeOfDiscount);
        int numberVoucherPerPage = 6;
        int totalOfPage = (numberOfVoucher + numberVoucherPerPage - 1) / numberVoucherPerPage;
        // tránh việc user nhập page vào thanh search gây lỗi truy vấn
        if (page > totalOfPage && totalOfPage != 0) {
            page = totalOfPage;
        }

        int currentBlock = (page - 1) / 5;
        int startPage = currentBlock * 5 + 1;
        int endPage = Math.min(startPage + 5 - 1, totalOfPage);

        List<Voucher> listVouchers = voucherDAO.getVoucherSplitPage(searchValue, status, typeOfDiscount, page, numberVoucherPerPage);

        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("totalOfPage", totalOfPage);
        request.setAttribute("listVouchers", listVouchers);
        request.setAttribute("page", page);

        if (listVouchers.isEmpty()) {
            request.setAttribute("emptyVoucherList", "Không tìm thấy voucher phù hợp");
        }
        request.getRequestDispatcher("admin/discount-code.jsp").forward(request, response);
    }

    /**
     * @author dotha
     * @param request discount-code.jsp
     * @param response discount-code.jsp
     * @param voucherDAO đối tượng để truy cập vào lớp DAO của Voucher
     * @throws IOException
     * @throws ServletException Xử lí việc xóa Voucher bằng voucher id
     */
    private void deleteVoucher(HttpServletRequest request, HttpServletResponse response, VoucherDAO voucherDAO) throws IOException, ServletException {
        String voucherIdRaw = request.getParameter("id");
        int voucherId;
        try {
            voucherId = Integer.parseInt(voucherIdRaw);
        } catch (NumberFormatException e) {
            response.sendError(400, "Lỗi không chuyển đổi được id");
            return;
        }
        boolean checkDelete = voucherDAO.deleteVoucherById(voucherId);
        if (checkDelete) {
            request.setAttribute("voucherMessgae", "Xóa Voucher thành công!");
        } else {
            request.setAttribute("voucherMessgae", "Xóa Voucher thất bại, hãy kiểm tra lại!");
        }
        viewVoucher(request, response, voucherDAO);
    }

    /**
     * @author dotha
     * @param request discount-code.jsp
     * @param response discount-code.jsp
     * @param voucherDAO đối tượng để truy cập vào lớp DAO của Voucher
     * @param action action để biết user muốn thêm mới voucher hay muốn sửa
     * voucher
     * @throws IOException
     * @throws ServletException Xử lí việc thêm hoặc chỉnh sửa voucher
     */
    private void addOrEditVoucher(HttpServletRequest request, HttpServletResponse response, VoucherDAO voucherDAO, String action) throws IOException, ServletException {
        String code = request.getParameter("code").trim();
        String discountType = request.getParameter("discount_type");
        String discountValueRaw = request.getParameter("discount_value");
        String minOrderValueRaw = request.getParameter("min_order_value");
        String maxDiscountRaw = request.getParameter("max_discount");
        String quantityRaw = request.getParameter("quantity");
        String startDateRaw = request.getParameter("start_date");
        String endDateRaw = request.getParameter("end_date");
        String isActiveRaw = request.getParameter("is_active") == null ? "0" : "1";

        if (code.trim().isEmpty() || discountType.trim().isEmpty() || discountValueRaw.trim().isEmpty() || minOrderValueRaw.trim().isEmpty()
                || (discountType.equals("percent") && maxDiscountRaw.trim().isEmpty()) || quantityRaw.trim().isEmpty() || startDateRaw.trim().isEmpty() || endDateRaw.trim().isEmpty()) {
            request.setAttribute("voucherMessgae", "Trường dữ liệu không được để trống (trừ \"Giảm tối đa\" nếu là loại giảm giá là \"Số tiền cố định\")!");
            viewVoucher(request, response, voucherDAO);
            return;
        }

        double discountValue, minOrderValue, maxDiscount;
        int isActive, quantity;
        LocalDateTime startDate, endDate;

        try {
            isActive = Integer.parseInt(isActiveRaw);
            discountValue = Double.parseDouble(discountValueRaw);
            minOrderValue = Double.parseDouble(minOrderValueRaw);
            quantity = Integer.parseInt(quantityRaw);
            maxDiscount = 0;
            if ("percent".equalsIgnoreCase(discountType)
                    && maxDiscountRaw != null
                    && !maxDiscountRaw.trim().isEmpty()) {
                maxDiscount = Double.parseDouble(maxDiscountRaw);
            }
            startDate = (startDateRaw != null && !startDateRaw.trim().isEmpty())
                    ? LocalDateTime.parse(startDateRaw)
                    : null;
            endDate = (endDateRaw != null && !endDateRaw.trim().isEmpty())
                    ? LocalDateTime.parse(endDateRaw)
                    : null;
        } catch (NumberFormatException | DateTimeParseException e) {
            response.sendError(400);
            return;
        }

        // Validate percent discount: must be > 0 and <= 100
        if ("percent".equalsIgnoreCase(discountType) && (discountValue <= 0 || discountValue > 100)) {
            request.setAttribute("voucherMessgae", "Giá trị giảm theo phần trăm phải lớn hơn 0 và không quá 100%!");
            viewVoucher(request, response, voucherDAO);
            return;
        }

        if ("add".equalsIgnoreCase(action)) {
            boolean checkAdd = voucherDAO.addVoucher(code, discountType, discountValue, minOrderValue, maxDiscount, quantity, startDate, endDate, isActive);
            if (checkAdd) {
                request.setAttribute("voucherMessgae", "Thêm Voucher thành công!");
            } else {
                request.setAttribute("voucherMessgae", "Thêm Voucher thất bại, hãy kiểm tra lại!");
            }
        } else {
            String voucherIdRaw = request.getParameter("voucher_id");
            int voucherId;
            try {
                voucherId = Integer.parseInt(voucherIdRaw);
            } catch (NumberFormatException e) {
                response.sendError(400, "Lỗi không chuyển đổi được id");
                return;
            }
            boolean checkEdit = voucherDAO.editVoucher(code, discountType, discountValue, minOrderValue, maxDiscount, quantity, startDate, endDate, isActive, voucherId);
            if (checkEdit) {
                request.setAttribute("voucherMessgae", "Chỉnh sửa Voucher thành công!");
            } else {
                request.setAttribute("voucherMessgae", "Chỉnh sửa Voucher thất bại, hãy kiểm tra lại!");
            }
        }
        viewVoucher(request, response, voucherDAO);
    }

}
