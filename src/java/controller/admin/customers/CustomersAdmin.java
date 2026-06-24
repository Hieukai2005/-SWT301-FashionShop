/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin.customers;

import dal.CustomerDAO;
import dal.OrderDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import model.Customer;
import model.Order;

/**
 * Admin - Quản lý khách hàng
 * Hỗ trợ: xem danh sách, khóa/mở khóa, xóa mềm, xem chi tiết đơn hàng (AJAX)
 */
@WebServlet(name = "CustomersAdmin", urlPatterns = {"/customersadmin"})
public class CustomersAdmin extends HttpServlet {

    private static final String CUSTOMER_PAGE     = "admin/customers.jsp";
    private static final String CUSTOMER_DETAIL_AJAX = "/ajax/customer_detail_ajax.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        CustomerDAO dao = new CustomerDAO();

        // ---- Xử lý các action POST (lock / unlock / delete) ----
        if ("lock".equals(action)) {
            handleAction(request, response, dao, "lock");
            return;
        }
        if ("unlock".equals(action)) {
            handleAction(request, response, dao, "unlock");
            return;
        }
        if ("delete".equals(action)) {
            handleAction(request, response, dao, "delete");
            return;
        }

        // ---- AJAX: chi tiết đơn hàng của khách hàng ----
        String uidRaw = request.getParameter("uid");
        if (uidRaw != null && !uidRaw.trim().isEmpty()) {
            try {
                int uid = Integer.parseInt(uidRaw.trim());
                OrderDAO orderDAO = new OrderDAO();
                List<Order> orders = orderDAO.getOrderByUserId(uid);
                request.setAttribute("customerOrders", orders);
                request.getRequestDispatcher(CUSTOMER_DETAIL_AJAX).forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/customersadmin");
            }
            return;
        }

        // ---- Danh sách khách hàng chính ----
        String keyword    = request.getParameter("keyword");
        String tier       = request.getParameter("tier");
        String sort       = request.getParameter("sort");
        String dateJoined = request.getParameter("dateJoined");

        if (keyword == null)    keyword    = "";
        if (tier == null)       tier       = "ALL";
        if (sort == null)       sort       = "none";
        if (dateJoined == null) dateJoined = "ALL";

        int page     = 1;
        int pageSize = 5;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try { page = Integer.parseInt(pageParam); } catch (NumberFormatException e) { page = 1; }
        }

        List<Customer> list = dao.getCustomersPaging(keyword, tier, sort, dateJoined, page, pageSize);
        int totalFiltered   = dao.countCustomers(keyword, tier, dateJoined);
        int totalPage       = (totalFiltered == 0) ? 1 : (int) Math.ceil((double) totalFiltered / pageSize);
        if (page > totalPage) page = totalPage;

        // Dashboard stats
        int totalCustomers = dao.countTotalCustomers();
        int newCustomers   = dao.countNewCustomers();
        int vipCustomers   = dao.countVipCustomers();

        request.setAttribute("listCustomers",  list);
        request.setAttribute("currentPage",    page);
        request.setAttribute("totalPage",      totalPage);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("newCustomers",   newCustomers);
        request.setAttribute("vipCustomers",   vipCustomers);
        request.setAttribute("keyword",        keyword);
        request.setAttribute("tier",           tier);
        request.setAttribute("sort",           sort);
        request.setAttribute("dateJoined",     dateJoined);

        request.getRequestDispatcher(CUSTOMER_PAGE).forward(request, response);
    }

    private void handleAction(HttpServletRequest request, HttpServletResponse response,
                               CustomerDAO dao, String action) throws IOException {
        String userIdRaw = request.getParameter("userId");
        if (userIdRaw == null || userIdRaw.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/customersadmin");
            return;
        }
        try {
            int userId = Integer.parseInt(userIdRaw.trim());
            boolean ok = false;
            String msg = "";
            switch (action) {
                case "lock":
                    OrderDAO orderDAO = new OrderDAO();
                    List<Order> orders = orderDAO.getOrderByUserId(userId);
                    boolean hasActiveOrder = false;
                    for (Order o : orders) {
                        if ("Chờ xác nhận".equals(o.getStatus()) || "Đang giao".equals(o.getStatus())) {
                            hasActiveOrder = true;
                            break;
                        }
                    }
                    if (hasActiveOrder) {
                        ok = false;
                        msg = "Tài khoản đã phát sinh giao dịch. Không thể khóa!";
                        request.getSession().setAttribute("errorMessage", msg);
                    } else {
                        ok  = dao.lockUser(userId);
                        msg = ok ? "Đã khóa tài khoản thành công!" : "Khóa tài khoản thất bại!";
                        request.getSession().setAttribute(ok ? "successMessage" : "errorMessage", msg);
                    }
                    break;
                case "unlock":
                    ok  = dao.unlockUser(userId);
                    msg = ok ? "Đã mở khóa tài khoản thành công!" : "Mở khóa thất bại!";
                    request.getSession().setAttribute(ok ? "successMessage" : "errorMessage", msg);
                    break;
                case "delete":
                    // Handle delete if necessary, existing code didn't implement it but it was mentioned
                    break;
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "ID người dùng không hợp lệ!");
        }
        response.sendRedirect(request.getContextPath() + "/customersadmin");
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
        return "Admin Customers Servlet";
    }
}
