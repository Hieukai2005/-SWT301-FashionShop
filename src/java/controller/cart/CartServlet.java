/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.cart;

import dal.CartDAO;
import dal.ProductDAO;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import model.Cart;
import model.CartItem;
import model.CartItemView;
import model.ProductVariant;
import model.User;
import model.Voucher;

/**
 * @author dotha
 * @date: 12/2/2026
 * @description xử lí phần cart của user, user bắt buộc phải đăng nhập để có thể
 * vô được phần này
 */
public class CartServlet extends HttpServlet {

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
        doGet(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        CartDAO cartDAO = new CartDAO();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("customer");
        // đăng nhập đã được kiểm tra bằng filter nên phần này sẽ không kiểm tra lại

        String action = request.getParameter("action");
        if (action == null) {
            action = "viewcart";
        }

        switch (action) {
            case "viewcart":
                viewCart(request, response, user, cartDAO);
                break;
            case "addtocart":
                addToCart(request, response, user, cartDAO);
                break;
            case "delete":
                deleteCartItem(request, response, user, cartDAO);
                break;
            case "desc":
                descending(request, response, cartDAO);
                break;
            case "asc":
                ascending(request, response, cartDAO);
                break;
            case "updateQuantity":
                updateQuantity(request, response, cartDAO);
                break;
            default:
                break;
        }

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
     * @param request
     * @param response
     * @param user
     * @param cartDAO
     */
    private void viewCart(HttpServletRequest request, HttpServletResponse response, User user, CartDAO cartDAO) throws ServletException, IOException {
        String cartIdRaw = request.getParameter("cartId");
        int cartId;
        ProductDAO productDAO = new ProductDAO();
        LocalDateTime timeNow = LocalDateTime.now();
        HttpSession session = request.getSession();

        if (cartIdRaw == null || cartIdRaw.matches("[^0-9]+")) {
            int cartIDCheck = cartDAO.getCartIdByUserId(user.getUserId());
            if (cartIDCheck != -1) {
                session.setAttribute("cartId", cartIDCheck);
                cartId = cartIDCheck;
            } else {
                // Nếu user thực sự chưa có giỏ hàng trong DB
                request.getRequestDispatcher("cart.jsp").forward(request, response);
                return;
            }
        } else {
            cartId = Integer.parseInt(cartIdRaw);
            session.setAttribute("cartId", cartId); // Cập nhật lại session id để các request sau dùng được
        }

        List<CartItemView> listCartItemViews = cartDAO.getCartItemViewsByCartId(cartId);

        // cart rỗng thì tới trang cart luôn và không set tham số nữa
        if (listCartItemViews.isEmpty()) {
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        // lấy tổng sô tiền sau khi cộng dồn (chưa giảm giá)
        double totalPrice = 0;
        for (CartItemView item : listCartItemViews) {
            double price = item.getPrice();
            double discount = item.getDiscount();
            int quantity = item.getQuantity();
            double priceAfterDiscount = price * (1 - discount / 100.0);
            totalPrice += priceAfterDiscount * quantity;
        }
        
        request.setAttribute("totalPrice", totalPrice);

        // tự áp dụng mã giảm giá nếu có
        Voucher voucher;
        String voucherCode = (String) session.getAttribute("voucher");
        double discountProductValue = 0;
        boolean voucherApplied = session.getAttribute("voucherApplied") != null;
        if (voucherApplied && voucherCode != null) {
            voucher = (Voucher) session.getAttribute("voucherObj");
            session.setAttribute("discountValue", voucher.getDiscountValue());
            discountProductValue = voucher.getDiscountValue();
            if (voucher.getMinOrderValue() > totalPrice || voucher.getEndDate().isBefore(timeNow)) {
                session.setAttribute("discountValue", 0);
                discountProductValue = 0; // set lại giá trị của discount nếu khong áp dụng được voucher
            } else if ("percent".equalsIgnoreCase(voucher.getDiscountType())) {
                double maxDiscount = voucher.getMaxDiscount();
                request.setAttribute("maxDiscount", maxDiscount);
                // giảm theo % sản phẩm, nhưng giá trị đơn hàng quá cao sẽ chỉ giảm đến ngưỡng max của mã giảm giá
                discountProductValue = totalPrice * voucher.getDiscountValue() / 100.0;
                if (discountProductValue >= maxDiscount) {
                    discountProductValue = maxDiscount;
                }
                session.setAttribute("discountValue", discountProductValue);
            }
        }

        // tính tổng giá tiền sau khi đã có voucher
        // nếu đơn hàng dưới 399k thì tính thêm 30k tiền vận chuyển
        double totalPriceFinal = Math.max(0, (totalPrice < 399000) ? totalPrice + 30000 - discountProductValue : totalPrice - discountProductValue);
        session.setAttribute("totalPriceFinal", totalPriceFinal);

        request.setAttribute("listCartItemViews", listCartItemViews);
       
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    /**
     * @author dotha
     * @param request request của trang
     * @param response respond của trang
     * @param user user được lấy từ trong Session
     * @param cartDAO đối tượng được tạo đêr truy cập các phương thức liên quan
     * đến database để truy xuất dữ liệu
     * @description thêm cart cho người dùng nếu họ chưa có cart, thêm sản phẩm
     * người dùng chọn trong phần product details
     */
    private void addToCart(HttpServletRequest request, HttpServletResponse response, User user, CartDAO cartDAO) throws IOException {
        Cart cart = cartDAO.getCartByUserId(user.getUserId());
        HttpSession session = request.getSession();

        //nếu người dùng này chưa có trong dtb carts
        if (cart == null) {
            int cartForUserId = cartDAO.createCartForUserId(user.getUserId());
            if (cartForUserId == -1) {
                // Lỗi có thể do race condition (nhấn nhiều lần làm nhiều request gửi tới cùng lúc tạo nhiều giỏ hàng)
                // Do đó sẽ kiểm tra lại 1 lần nữa xem thread khác đã tạo giỏ chưa
                cart = cartDAO.getCartByUserId(user.getUserId());
                if (cart == null) {
                    System.err.println("Can not create cart for user id " + user.getUserId());
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
                    return;
                }
            } else {
                // tạo cart khi người dùng chưa có cart
                cart = new Cart();
                cart.setCartId(cartForUserId);
                cart.setUserId(user.getUserId());
                System.out.println("Cart ID " + cartForUserId);
            }
        }

        // lấy dữ liệu trên thanh search
        String idRaw = request.getParameter("id");
        String colorRaw = request.getParameter("color");
        String sizeRaw = request.getParameter("size");
        String quantityRaw = request.getParameter("quantity");

        int id, color, size, quantity;

        if (idRaw == null || colorRaw == null || sizeRaw == null || quantityRaw == null) {
            System.err.println("Null Parameter at CartServlet");
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return;
        }

        // bắt lỗi nếu chuỗi query không phải các số integer
        try {

            id = Integer.parseInt(idRaw);
            color = Integer.parseInt(colorRaw);
            size = Integer.parseInt(sizeRaw);
            quantity = Integer.parseInt(quantityRaw);

        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException at CartServlet");
            e.printStackTrace();
            return;
        }

        ProductDAO productDAO = new ProductDAO();

        int cartId = cart.getCartId();
        int variantId = productDAO.getVariantIdByProductIdAndSizeIdAndColorId(id, color, size);
        // quantity đã được parse ở trên

        if (variantId == -1) {
            System.err.println("Invalid Variant Id");
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return;
        }

        int addCheck = cartDAO.addToCart(cartId, variantId, quantity);

        // cập nhật trạng thái của session cart count sau khi add xong để luôn đảm bảo tính chính xác
        if (addCheck > 0) {
            int cartCount = cartDAO.getNumberCartByUserId(user.getUserId());
            session.setAttribute("cartCount", cartCount);
        }

        response.sendRedirect(request.getContextPath() + "/cart?action=viewcart&cartId=" + cartId);
    }

    private void deleteCartItem(HttpServletRequest request, HttpServletResponse response, User user, CartDAO cartDAO) throws IOException {
        String cartItemIdRaw = request.getParameter("cartItemId");
        int cartItemId;
        HttpSession session = request.getSession();

        if (cartItemIdRaw == null) {
            response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
            return;
        }

        try {
            cartItemId = Integer.parseInt(cartItemIdRaw);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
            return;
        }

        boolean deleteCheck = cartDAO.deleteCartItemByCartItemId(cartItemId);

        // nếu xóa thành công thì tính lại và cập nhật chính xác số lượng trong cart
        if (deleteCheck) {
            int cartCount = cartDAO.getNumberCartByUserId(user.getUserId());
            session.setAttribute("cartCount", cartCount);
        }

        response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
    }

    private void descending(HttpServletRequest request, HttpServletResponse response, CartDAO cartDAO) throws IOException {
        String cartItemIdRaw = request.getParameter("cartItemId");
        int cartItemId;

        if (cartItemIdRaw == null) {
            response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
            return;
        }

        try {
            cartItemId = Integer.parseInt(cartItemIdRaw);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
            return;
        }

        boolean descCheck = cartDAO.descQuantityByCartItemId(cartItemId);
        // nếu giảm thành công thì cập nhật trạng thái số lượng của quantity
        response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
    }

    private void ascending(HttpServletRequest request, HttpServletResponse response, CartDAO cartDAO) throws IOException {
        String cartItemIdRaw = request.getParameter("cartItemId");
        int cartItemId;

        if (cartItemIdRaw == null) {
            response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
            return;
        }

        try {
            cartItemId = Integer.parseInt(cartItemIdRaw);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
            return;
        }

        boolean check = cartDAO.ascQuantityByCartItemId(cartItemId);
        // nếu tăng thành công thì cập nhật trạng thái số lượng của quantity
        response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
    }

    private void updateQuantity(HttpServletRequest request, HttpServletResponse response, CartDAO cartDAO) throws IOException {
        String cartItemIdRaw = request.getParameter("cartItemId");
        String quantityRaw = request.getParameter("quantity");

        if (cartItemIdRaw == null || quantityRaw == null) {
            response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
            return;
        }

        try {
            int cartItemId = Integer.parseInt(cartItemIdRaw);
            int quantity = Integer.parseInt(quantityRaw);
            cartDAO.updateQuantityByCartItemId(cartItemId, quantity);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/cart?action=viewcart");
    }

}
