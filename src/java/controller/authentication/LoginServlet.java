/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import dal.CartDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import model.User;

/**
 *
 * @author Tien Hoang
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        Cookie[] arr = request.getCookies();
        if (arr != null) {
            for (Cookie o : arr) {
                if (o.getName().equals("userC")) {
                    request.setAttribute("userC", o.getValue());
                    request.setAttribute("remember", "checked");
                }
                if (o.getName().equals("passC")) {
                    request.setAttribute("passC", o.getValue());
                }
            }
        }
        request.getRequestDispatcher("/login/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @author Tien Hoang
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String u = request.getParameter("username");
        String p = request.getParameter("password");
        String originalP = p; // Lưu lại pass chưa mã hóa để bỏ vào Cookie nếu cần

        // Trim để tránh trường hợp nhập toàn dấu cách
        if (u != null) {
            u = u.trim();
        }
        if (p != null) {
            p = p.trim();
        }

        // 1. Chưa nhập gì cả
        if ((u == null || u.isEmpty()) && (p == null || p.isEmpty())) {
            request.setAttribute("error", "* Bạn phải nhập username và password!");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }

        // 2. Bỏ trống username
        if (u == null || u.isEmpty()) {
            request.setAttribute("error", "* Không được để trống username!");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }

        // 3. Bỏ trống password
        if (p == null || p.isEmpty()) {
            request.setAttribute("error", "* Không được để trống password!");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }

        p = utility.HashPassword.toSHA1(p);

        UserDAO d = new UserDAO();
        int role = d.check(u, p);

        if (role == -1) {
            request.setAttribute("error", "* Tài khoản hoặc mật khẩu không chính xác!");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }
        // Lấy thông tin user để lưu vào session (khuyến nghị lưu cả object User)
        User customer = d.getUserByUsernameAndPassword(u, p);
        if (!customer.isStatus()) {
            request.setAttribute("error", "* Tài khoản của bạn không hoạt động!");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }
        // ==== ĐĂNG NHẬP THÀNH CÔNG -> TẠO SESSION customer ====

        // Nếu chưa lấy được object User, tối thiểu vẫn set username
        if (customer == null) {
            customer = new User();
            customer.setEmail(u);
            customer.setRoleId(role); // nếu User có field role
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("customer", customer);

        // ------- REMEMBER ME -------
        String remember = request.getParameter("remember");
        Cookie cUser = new Cookie("userC", u);
        Cookie cPass = new Cookie("passC", originalP);

        if (remember != null) {
            cUser.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
            cPass.setMaxAge(60 * 60 * 24 * 7);
        } else {
            cUser.setMaxAge(0);
            cPass.setMaxAge(0);
        }
        response.addCookie(cUser);
        response.addCookie(cPass);
        // ---------------------------

        // lấy luôn sô đơn hàng đã thêm vào giỏ trước khi vao trang đăng nhập
        CartDAO cartDAO = new CartDAO();
        int cartCount = cartDAO.getNumberCartByUserId(customer.getUserId());
        if (cartCount != -1) {
            session.setAttribute("cartCount", cartCount);
        }

        // lấy cart id
        int cartID = cartDAO.getCartIdByUserId(customer.getUserId());

        if (cartID != -1) {
            session.setAttribute("cartId", cartID);
        }
        System.out.println("cart id = " + cartID);

        // (tuỳ chọn) set timeout session 1 ngày
        session.setMaxInactiveInterval(60 * 60 * 24);

        // nếu trước đó người dùng có thêm sản phẩm vô giỏ hàng thì sẽ thêm sản phẩm vô
        // giỏ và redirect đến phần xem giỏ luôn
        String redirectUrl = (String) session.getAttribute("redirectyUrl");
        if (redirectUrl != null) {
            response.sendRedirect(redirectUrl);
            return;
        }
        // Redirect theo role
        if (role == 1) {
            response.sendRedirect(request.getContextPath() + "/generaladmin"); // admin
        } else {
            response.sendRedirect(request.getContextPath() + "/home"); // customer
        }
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
