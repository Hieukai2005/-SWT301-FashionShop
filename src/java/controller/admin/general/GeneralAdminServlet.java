package controller.admin.general;

import dal.OrderDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import model.DashBoard;
import model.Order;
import model.OrderItem;

/**
 *
 * @author HoaNK
 * @date: 4/2/2026
 * @description: Load va xu li du lieu lien quan den trang dashboard admin
 */
@WebServlet(name = "GeneralAdminServlet", urlPatterns = {"/generaladmin"})
public class GeneralAdminServlet extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String DASHBOARD = "/admin/dashboard.jsp";
    private final String ORDER_ITEM_AJAX = "/ajax/order_item_ajax.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // 
        String url = ERROR;
        String oid_raw = request.getParameter("oid");
        // Lấy ra tháng năm hiện tại
        LocalDate time = LocalDate.now(); // thời gian hiện tại
        LocalDate lastTime = time.minusMonths(1); // tính toán lùi 1 tháng so với tháng hiện tại nếu xuống 12 lùi 1 năm
        //
        try {
            ProductDAO daoP = new ProductDAO();
            OrderDAO daoO = new OrderDAO();
            // ấn mắt có oid với vào này để đọc popup ko thì load info như bthuongw
            if (oid_raw != null) {
                this.allOrderItem(request, daoO, oid_raw);
                url = ORDER_ITEM_AJAX;
            } else {
                // lấy dữ liệu tháng trước và tháng này 
                DashBoard thisMonth = daoP.getDashboard(time);
                DashBoard lastMonth = daoP.getDashboard(lastTime);
                // Thống kê báo cáo
                this.totalOrder(request, thisMonth, lastMonth);
                this.totalAmount(request, thisMonth, lastMonth);
                this.totalProductNew(request, thisMonth, lastMonth);
                this.totalUserNew(request, thisMonth, lastMonth);
                // Đơn hàng gần nhất
                this.recentOrder(request, daoO);
                // Dữ liệu biểu đồ
                String year_raw = request.getParameter("year");
                int year = time.getYear(); // Mặc định là năm hiện tại
                try {
                    if (year_raw != null) {
                        year = Integer.parseInt(year_raw);
                    }
                } catch (Exception e) {}
                
                List<Double> monthlyRevenue = daoO.getMonthlyRevenue(year);
                List<Integer> orderStatusCounts = daoO.getOrderStatusCounts();
                List<Integer> listYears = daoO.getAvailableYears();
                
                request.setAttribute("monthlyRevenue", monthlyRevenue);
                request.setAttribute("orderStatusCounts", orderStatusCounts);
                request.setAttribute("listYears", listYears);
                request.setAttribute("selectedYear", year);
                //
                url = DASHBOARD;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // Tổng sản phẩm tháng
    private void totalOrder(HttpServletRequest request, DashBoard thisMonth, DashBoard lastMonth) {
        // Load số đơn hàng tháng này
        int orderThisMonth = thisMonth.getTotalOrders();
        // Load số đơn hàng tháng trước
        int orderLastMonth = lastMonth.getTotalOrders();
        // tính toán % tăng trưởng
        double growth = calculateGrowth(orderThisMonth, orderLastMonth);

        // 
        request.setAttribute("orderTotalMonth", orderThisMonth);
        request.setAttribute("growthOrder", growth);
    }

    // Tổng tiền tháng
    private void totalAmount(HttpServletRequest request, DashBoard thisMonth, DashBoard lastMonth) {
        // Load tổng tiền tháng này
        double totalAmountThisMonth = thisMonth.getTotalAmount();
        // Load tổng tiền tháng trước
        double totalAmountLastMonth = lastMonth.getTotalAmount();
        // tính toán % tăng trưởng
        double growth = calculateGrowth(totalAmountThisMonth, totalAmountLastMonth);
        // format dữ liệu
        Locale vn = new Locale("vi", "VN");
        NumberFormat formater = NumberFormat.getInstance(vn);
        String totalAmountThisMonthVn = formater.format(totalAmountThisMonth);
        // 
        request.setAttribute("totalAmountMonth", totalAmountThisMonthVn);
        request.setAttribute("growthTotalAmount", growth);
    }

    // Tổng số sản phẩm thêm mới
    private void totalProductNew(HttpServletRequest request, DashBoard thisMonth, DashBoard lastMonth) {
        // load sản phẩm mới tháng này
        int totalProductThisMonth = thisMonth.getNewProducts();
        // load sản phẩm tháng trước
        int totalProductLastMonth = lastMonth.getNewProducts();
        // tính toán só sản phẩm mới thêm thán này so với tháng trước
        int newProducts = totalProductThisMonth - totalProductLastMonth;
        //
        request.setAttribute("totalProductNew", totalProductThisMonth);
        request.setAttribute("newProducts", newProducts);
    }

    // Tổng số người dùng
    private void totalUserNew(HttpServletRequest request, DashBoard thisMonth, DashBoard lastMonth) {
        // load sản phẩm mới tháng này
        int totalUserThisMonth = thisMonth.getNewUsers();
        // load sản phẩm tháng trước
        int totalUserLastMonth = lastMonth.getNewUsers();
        // tính toán só sản phẩm mới thêm thán này so với tháng trước
        int newUsers = totalUserThisMonth - totalUserLastMonth;
        //
        request.setAttribute("totalUserNew", totalUserThisMonth);
        request.setAttribute("newUser", newUsers);
    }

    //  
    // tính toán tăng trưởng
    private double calculateGrowth(double thisMonth, double lastMonth) {
        if (lastMonth == 0) {
            if (thisMonth == 0) {
                return 0.00;// Nếu tháng trước 0 tháng này cũng 0 thì 0 tăng
            } else {
                return 100.00; // Nếu tháng trước 0 tháng này lớn hơn 0 thì tăng 100%
            }
        }
        // nếu tháng trước #0 thì tính toán theo công thức tăng trưởng = (tháng này - tháng trước) / tháng trước ) * 100
        double growth = ((thisMonth - lastMonth) / lastMonth) * 100;
        return Math.round(growth * 100.0) / 100.0; // format 2 só sau . động
    }

    // 10 đơn hàng gần nhất
    private void recentOrder(HttpServletRequest request, OrderDAO daoO) {
        List<Order> listRecentOrder = daoO.getRecentOrders(10);
        //
        request.setAttribute("listRecentOrder", listRecentOrder);
    }

    private void allOrderItem(HttpServletRequest request, OrderDAO daoO, String oid_raw) {
        try {
            int oid = Integer.parseInt(oid_raw);
            List<OrderItem> listOrderItem = daoO.getOrderItemByOrderId(oid);
            request.setAttribute("listOrderItems", listOrderItem);
            
            // Thêm thông tin đơn hàng và voucher cho popup
            request.setAttribute("orderFinal", daoO.getOrderById(oid));
            request.setAttribute("appliedVoucher", new dal.VoucherDAO().getVoucherByOrderId(oid));
            
            // Tính toán tổng tiền gốc cho popup
            double originalTotal = 0;
            for (model.OrderItem item : listOrderItem) {
                originalTotal += item.getPrice() * item.getQuantity();
            }
            request.setAttribute("originalTotal", originalTotal);
        } catch (Exception e) {
            System.out.println(e);
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
