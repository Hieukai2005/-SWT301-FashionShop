/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.order;

import dal.CartDAO;
import dal.OrderDAO;
import dal.ProductDAO;
import dal.VoucherDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import model.CartItem;
import model.Order;
import model.OrderItem;
import model.CartItemView;
import model.User;

/**
 *
 * @author dotha
 * @date 21/02/2026
 */
public class OrderServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        OrderDAO orderDAO = new OrderDAO();

        User user = (User) session.getAttribute("customer");
        // nếu người dùng chưa đăng nhập mà vẫn vô xem order
        if (user == null) {
            response.sendError(401, "Lỗi không tìm thấy User!");
            return;
        }

        String action = request.getParameter("action") == null ? "viewOrder" : request.getParameter("action");
        switch (action) {
            case "viewOrder":
                viewOrder(request, response, session, orderDAO, user);
                break;
            case "pay":
                payOrder(request, response, session, orderDAO, user);
                break;
            case "checkout":
                checkoutOrder(request, response, session, orderDAO, user);
                break;
            case "cancelOrder":
                cancelOrder(request, response, session, orderDAO, user);
                break;
            default:
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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

    // THÊM MỚI: map key tab (tiếng Anh) sang status tiếng Việt lưu trong DB
    private String getStatusByTab(String tab) {
        switch (tab) {
            case "pending":
                return "Chờ xác nhận";
            case "shipping":
                return "Đang giao";
            case "done":
                return "Hoàn thành";
            case "cancelled":
                return "Đã hủy";
            default:
                return null; // "all" hoặc không hợp lệ → không filter
        }
    }

    private void viewOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            OrderDAO orderDAO, User user) throws IOException, ServletException {
        List<Order> listOrders = orderDAO.getOrderByUserId(user.getUserId());
        if (listOrders.isEmpty()) {
            request.setAttribute("listOrdersEmpty", true);
            request.getRequestDispatcher("order.jsp").forward(request, response);
            return;
        }
        // THÊM MỚI: lấy tab key từ request, mặc định "all"
        String selectedTab = request.getParameter("tab") == null ? "all" : request.getParameter("tab");
        String statusFilter = getStatusByTab(selectedTab); // null nghĩa là hiện tất cả
        request.setAttribute("selectedTab", selectedTab);
        LinkedHashMap<Order, List<OrderItem>> orderItemHashMap = new LinkedHashMap<>();

        for (Order o : listOrders) {
            // THÊM MỚI: bỏ qua order không khớp status nếu đang filter theo tab
            if (statusFilter != null && !o.getStatus().equals(statusFilter)) {
                continue;
            }
            List<OrderItem> listOrderItems = orderDAO.getOrderItemByOrderId(o.getOrderId());
            orderItemHashMap.put(o, listOrderItems);
        }

        // THÊM MỚI: không có đơn hàng nào sau khi filter
        if (orderItemHashMap.isEmpty()) {
            request.setAttribute("tabOrdersEmpty", true);
        }

        request.setAttribute("orderItemsMap", orderItemHashMap);
        request.getRequestDispatcher("order.jsp").forward(request, response);
    }

    private void payOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            OrderDAO orderDAO, User user) throws ServletException, IOException {
        // trước tiên kiểm tra user đã có set địa chỉ để nhận hàng chưa, nếu chưa thì
        // không được đặt hàng
        if (user.getAddress() == null || user.getPhoneNumber() == null || user.getFullName() == null) {
            request.setAttribute("message",
                    "Bạn hãy điền đầy đủ thông tin (Tên, Địa chỉ, Số điện thoại) trước khi đặt hàng!");
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        VoucherDAO voucherDAO = new VoucherDAO();
        ProductDAO productDAO = new ProductDAO();

        // lấy cart id
        CartDAO cartDAO = new CartDAO();
        int cartId = cartDAO.getCartIdByUserId(user.getUserId());

        // nếu không tìm thấy cart id thì báo lỗi
        if (cartId == -1) {
            response.sendError(500, "Không tìm thấy cart!");
            return;
        }

        // lấy cart item bằng cart id nếu cart id được tìm thấy
        List<CartItem> listCartItems = cartDAO.getCartItemByCartId(cartId);
        if (listCartItems.isEmpty()) {
            request.setAttribute("message", "Giỏ hàng trống!");
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        // lấy tổng tiền của tất cả sản phẩm và tính cả voucher nếu được áp dụng
        String voucherCode = request.getParameter("vouchercode");
        double total = 0.0, discount = 0.0;
        try {
            total = (double) session.getAttribute("totalPriceFinal");
            if (session.getAttribute("discountValue") != null) {
                discount = (double) session.getAttribute("discountValue");
            }
        } catch (Exception e) {
            response.sendError(500, "Lỗi chuyển đổi số tiền!");
            return;
        }

        // kiểm tra xem voucher tại thời điểm đặt hàng có dùng được hay không
        int voucherId = -1;
        if (!voucherCode.trim().isEmpty()) {
            int checkVoucherIsValid = voucherDAO.checkVoucherByVoucherCode(voucherCode);
            if (checkVoucherIsValid == -1) {

                request.setAttribute("message",
                        "Voucher hiện không khả dụng, vui lòng áp dụng lại voucher hoặc dùng voucher khác!");


                request.setAttribute("message", "Voucher hiện không khả dụng, vui lòng áp dụng lại voucher hoặc dùng voucher khác!");
                session.removeAttribute("voucher");
                session.removeAttribute("discountValue");
                request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
                return;
            }
            voucherId = checkVoucherIsValid;
        }
        System.out.println("VOucherid: " + voucherId);

        // kiểm tra xem số lượng của các đơn đặt hàng xem có quá số lượng trong kho
        // không
        for (CartItem listCartItem : listCartItems) {
            int quantity = listCartItem.getQuantity();
            int variantId = listCartItem.getVariantId();
            int quantityVariant = productDAO.getQuanityByVariantId(variantId);
            // nếu số lượng sản phẩm trong kho nhỏ hơn số lượng hàng trong giỏ hàng
            if (quantityVariant - quantity < 0) {
                int cartItemId = listCartItem.getCartItemId();
                request.setAttribute("message", "Cart Item với ID " + cartItemId + " chỉ còn lại " + quantityVariant
                        + " chiếc trong kho, vui lòng chọn lại!");
                request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
                return;
            }
        }

        // đơn hàng trong giỏ thỏa mãn được điều kiện và có thể thêm vào trang order
        // tạo order cho đơn hàng và trả về id của đơn hàng vừa tạo
        int orderIdCreated = orderDAO.createOrderByUser(user, total);

        // copy đơn hàng ở trong phần cart item cho vô phần order item
        for (CartItem listCartItem : listCartItems) {
            int orderId = orderIdCreated;
            int variantId = listCartItem.getVariantId();
            int quantity = listCartItem.getQuantity();
            double price = productDAO.getPriceByVariantId(variantId);
            boolean checkAddOrderItem = orderDAO.addOrderItem(orderId, variantId, quantity, price);
            if (!checkAddOrderItem) {
                response.sendError(500, "Lỗi không thêm được order item!");
                return;
            }

            // sau khi thêm thành công thì xóa nó ở trong phần cart đi
            boolean checkDelateCartItem = cartDAO.deleteCartItemByCartItemId(listCartItem.getCartItemId());
            if (!checkDelateCartItem) {
                response.sendError(400, "Lỗi không xóa được cart item!");
                return;
            }

            // cập nhật lại quantity của biến thể sau khi đã thêm thành công sản phẩm đó vào
            // order item
            boolean checkUpdateQuantity = productDAO.setProductVariantQuantityByVariantId(variantId, quantity);
            if (!checkUpdateQuantity) {
                response.sendError(500, "Lỗi không update được số lượng trong biến thể!");
                return;
            }
        }

        // set lại session cart count
        session.setAttribute("cartCount", cartDAO.getNumberCartByUserId(user.getUserId()));

        // chèn thêm voucher usage nếu voucher đó được dùng và dùng được
        if (voucherId != -1) {
            int userID = user.getUserId();
            int orderId = orderIdCreated;
            boolean checkAddVoucherUsages = voucherDAO.addVoucherUsage(voucherId, userID, orderId);
            if (!checkAddVoucherUsages) {
                response.sendError(500, "Lỗi không update được voucher usage!");
                return;
            }
            // cập nhật thêm used count trong bảng voucher tăng lên 1
            boolean checkAscVoucher = voucherDAO.updateUsedCountByVoucherId(voucherId);
            if (!checkAscVoucher) {
                response.sendError(500, "Lỗi không update được used count!");
                return;
            }
        }

        // Hoàn tất đặt hàng - xóa voucher (nếu có) và các thông tin liên quan khỏi
        // phiên làm việc (session)

        // Hoàn tất đặt hàng - xóa voucher (nếu có) và các thông tin liên quan khỏi phiên làm việc (session)


        session.removeAttribute("voucher");
        session.removeAttribute("voucherObj");
        session.removeAttribute("voucherApplied");
        session.removeAttribute("discountValue");

        request.getRequestDispatcher("order?action=viewOrder").forward(request, response);
    }

    // THÊM MỚI: hàm xử lý hủy đơn hàng
    private void cancelOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            OrderDAO orderDAO, User user) throws ServletException, IOException {
        String orderIdRaw = request.getParameter("orderId");
        if (orderIdRaw == null || orderIdRaw.isEmpty()) {
            response.sendError(400, "Không tìm thấy mã đơn hàng!");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdRaw);
        } catch (NumberFormatException e) {
            response.sendError(400, "Mã đơn hàng không hợp lệ!");
            return;
        }

        boolean success = orderDAO.cancelOrder(orderId);
        if (success) {
            // Hủy thành công: redirect về tab "Đã hủy" để thấy đơn vừa hủy
            response.sendRedirect(request.getContextPath() + "/order?action=viewOrder&tab=cancelled");
            boolean retQuantity = orderDAO.updateQuantity(orderId);
        } else {
            response.sendError(500, "Hủy đơn hàng thất bại, vui lòng thử lại!");
        }
    }

    private void checkoutOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session,
            OrderDAO orderDAO, User user) throws ServletException, IOException {
        // Kiểm tra thông tin người dùng
        if (user.getAddress() == null || user.getPhoneNumber() == null || user.getFullName() == null) {
            request.setAttribute("message",
                    "Bạn hãy điền đầy đủ thông tin (Tên, Địa chỉ, Số điện thoại) trước khi đặt hàng!");
            request.getRequestDispatcher("cart?action=viewcart").forward(request, response);
            return;
        }

        CartDAO cartDAO = new CartDAO();
        int cartId = cartDAO.getCartIdByUserId(user.getUserId());
        if (cartId == -1) {
            response.sendRedirect("cart?action=viewcart");
            return;
        }

        List<CartItemView> listCartItemViews = cartDAO.getCartItemViewsByCartId(cartId);
        if (listCartItemViews.isEmpty()) {
            response.sendRedirect("cart?action=viewcart");
            return;
        }

        // Tính toán lại các giá trị (tương tự CartServlet)
        double totalPrice = 0;
        for (CartItemView item : listCartItemViews) {
            double price = item.getPrice();
            double discount = item.getDiscount();
            int quantity = item.getQuantity();
            double priceAfterDiscount = price * (1 - discount / 100.0);
            totalPrice += priceAfterDiscount * quantity;
        }

        double discountProductValue = 0;
        if (session.getAttribute("discountValue") != null) {
            discountProductValue = (double) session.getAttribute("discountValue");
        }

        double totalPriceFinal = Math.max(0, (totalPrice < 399000) ? totalPrice + 30000 - discountProductValue
                : totalPrice - discountProductValue);

        request.setAttribute("listCartItemViews", listCartItemViews);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("totalPriceFinal", totalPriceFinal);
        request.setAttribute("discountValue", discountProductValue);
        request.setAttribute("voucherCode", session.getAttribute("voucher"));

        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

}
