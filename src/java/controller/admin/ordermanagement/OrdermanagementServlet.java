/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin.ordermanagement;

import dal.OrderDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import model.DashBoard;
import model.Order;

/**
 *
 * @author admin
 */
@WebServlet(name = "Ordermanagement", urlPatterns = {"/ordermanagement"})
public class OrdermanagementServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ORDER_PAGE = "/admin/order-management.jsp";
    private static final String ERROR = "error.jsp";
    private static final String ORDER_ITEM_AJAX = "/ajax/order_item_ajax.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        // 1. LẤY PARAMETER
        String keyword = request.getParameter("keyword");
        String status = request.getParameter("status");
        String dateFilter = request.getParameter("dateFilter");
        String page_raw = request.getParameter("page");
        String oid_raw = request.getParameter("oid");
        String action = request.getParameter("action");

        OrderDAO dao = new OrderDAO();

        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);

        DashBoard thisMonth = dao.getOrderDashboard(now);
        DashBoard previousMonth = dao.getOrderDashboard(lastMonth);

        try {
            if ("update".equals(action)) {
                handleUpdateOrder(request, response, dao);
                return;
            }
            if (oid_raw != null) {

                int oid = Integer.parseInt(oid_raw);
                
                java.util.List<model.OrderItem> listItems = dao.getOrderItemByOrderId(oid);
                request.setAttribute("listOrderItems", listItems);
                
                double originalTotal = 0;
                for (model.OrderItem item : listItems) {
                    originalTotal += item.getPrice() * item.getQuantity();
                }
                request.setAttribute("originalTotal", originalTotal);
                
                request.setAttribute("orderFinal", dao.getOrderById(oid));
                request.setAttribute("appliedVoucher", new dal.VoucherDAO().getVoucherByOrderId(oid));

                url = ORDER_ITEM_AJAX;

            } else {
                if (status == null || status.isEmpty()) {
                    status = "ALL";
                }

                if (dateFilter == null || dateFilter.isEmpty()) {
                    dateFilter = "ALL";
                }
// xu li page
                int page = 1;
                try {
                    if (page_raw != null) {
                        page = Integer.parseInt(page_raw);
                    }
                } catch (NumberFormatException e) {
                    page = 1;
                }
                // 3. LOAD DỮ LIỆU
                this.loadOrderPaging(request, dao, keyword, status, dateFilter, page, thisMonth, previousMonth);
                url = ORDER_PAGE;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        request.getRequestDispatcher(url).forward(request, response);

        }

    private void loadOrderPaging(HttpServletRequest request,
                                  OrderDAO dao,
                                  String keyword,
                                  String status,
                                  String dateFilter,
            int page, DashBoard thisMonth,
            DashBoard previousMonth) {

        int numberPerPage = 5;

        // Tổng số đơn hàng
        int totalRecord = dao.countOrders(keyword, status, dateFilter);

        // Tổng số trang
        int totalPage = (totalRecord + numberPerPage - 1) / numberPerPage;

        // nếu user cố tình nhập số page quá lớn vào thanh search thì xử lý user
        if ((page > totalPage || page <= 0) && page != 0) {
            page = totalPage;
        }

        int currentBlock = (page - 1) / 5; // mỗi block chứa 5 trang
        int startPage = currentBlock * 5 + 1;
        int endPage = Math.min(startPage + 5 - 1, totalPage);

        // Danh sách đơn hàng theo trang
        List<Order> list = dao.searchOrdersPaging(
                keyword,
                status,
                dateFilter,
                page,
                numberPerPage
        );
        // GỬI DỮ LIỆU SANG VIEW
        request.setAttribute("data", list);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", status);
        request.setAttribute("dateFilter", dateFilter);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("totalOrdersMonth", thisMonth.getTotalOrders());

        request.setAttribute("growthOrders",
                calculateGrowth(thisMonth.getTotalOrders(),
                        previousMonth.getTotalOrders()));

        request.setAttribute("revenueMonth", thisMonth.getTotalAmount());

        request.setAttribute("growthRevenue",
                calculateGrowth(thisMonth.getTotalAmount(),
                        previousMonth.getTotalAmount()));
        request.setAttribute("pendingMonth", thisMonth.getPendingOrders());
        request.setAttribute("completedMonth", thisMonth.getCompletedOrders());
        request.setAttribute("growthPending",
                calculateGrowth(thisMonth.getPendingOrders(),
                        previousMonth.getPendingOrders()));

        request.setAttribute("growthCompleted",
                calculateGrowth(thisMonth.getCompletedOrders(),
                        previousMonth.getCompletedOrders()));

        if (list == null || list.isEmpty()) {
            request.setAttribute("emptyOrder",
                    "Không có đơn hàng phù hợp!");
        }
    }

    private double calculateGrowth(double thisMonth, double lastMonth) {
        if (lastMonth == 0) {
            if (thisMonth == 0) {
                return 0.0;
        }
            return 100.0;
        }
        double growth = ((thisMonth - lastMonth) / lastMonth) * 100;
        return Math.round(growth * 100.0) / 100.0;
    }

    private void handleUpdateOrder(HttpServletRequest request,
                                    HttpServletResponse response,
            OrderDAO dao) throws IOException {

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String fullName = request.getParameter("customerName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String newStatus = request.getParameter("status");

        // Lấy trạng thái cũ trước khi update
        model.Order oldOrder = dao.getOrderById(orderId);
        String oldStatus = (oldOrder != null) ? oldOrder.getStatus() : "";

        dao.updateOrder(orderId, fullName, phone, address, newStatus);

        // Nếu admin hủy đơn và đơn chưa bị hủy trước đó → hoàn số lượng về kho
        if ("Đã hủy".equals(newStatus) && !"Đã hủy".equals(oldStatus)) {
            dao.updateQuantity(orderId);
        }

        request.getSession().setAttribute("successMessage",
                "Cập nhật đơn hàng thành công!");

            response.sendRedirect(request.getContextPath() + "/ordermanagement");
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
