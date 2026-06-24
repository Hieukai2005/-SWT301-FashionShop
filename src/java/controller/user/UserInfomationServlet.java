/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.CustomerDAO;
import dal.UserDAO;
import dal.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Customer;
import model.User;

/**
 *
 * @author dotha
 */
public class UserInfomationServlet extends HttpServlet {

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

        String action = request.getParameter("action") == null ? "viewInfomation" : request.getParameter("action");

        switch (action) {
            case "viewInfomation":
                viewInfomation(session, user, request, response);
                break;
            case "edit":
                editInfomation(session, user, request, response);
                break;
            case "viewVouchers":
                viewVouchers(session, user, request, response);
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

    private void viewInfomation(HttpSession session, User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = new Customer();
        customer.setUserId(user.getUserId());
        customer.setAddress(user.getAddress());
        customer.setEmail(user.getEmail());
        customer.setPhoneNumber(user.getPhoneNumber());
        customer.setFullName(user.getFullName());
        customer.setCreatedAt(user.getCreatedDateFormat());
        customer.setTotalSpending(customerDAO.getTotalSpendingByUserId(user.getUserId()));
        customer.setTotalOrders(customerDAO.getTotalOrderByUserId(user.getUserId()));
        String tier = customer.getTier();
        int numberVocher = new VoucherDAO().getNumberVoucherForNewUser(user, customer);
        request.setAttribute("numberVocher", numberVocher);
        double totalAmout = customer.getTotalSpending();
        if (tier.equalsIgnoreCase("Green")) {
            request.setAttribute("neededAmountTopromoted", 1000000 - totalAmout);
            request.setAttribute("nextRank", "Bronze");
        } else if (tier.equalsIgnoreCase("Bronze")) {
            request.setAttribute("neededAmountTopromoted", 2000000 - totalAmout);
            request.setAttribute("nextRank", "Silver");
        } else if (tier.equalsIgnoreCase("Silver")) {
            request.setAttribute("neededAmountTopromoted", 3000000 - totalAmout);
            request.setAttribute("nextRank", "Gold");
        }

        request.setAttribute("totalSpending", customer.getTotalSpending());
        request.setAttribute("user", customer);

        request.getRequestDispatcher("user/user-profile.jsp").forward(request, response);
    }

    private void editInfomation(HttpSession session, User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String fullname = request.getParameter("fullname");
        if (fullname != null && fullname.trim().isEmpty()) fullname = null;
        
        String phoneNumber = request.getParameter("phonenumber");
        if (phoneNumber != null && phoneNumber.trim().isEmpty()) phoneNumber = null;
        
        // Validate phone number: must be exactly 10 digits, starting with 0
        if (phoneNumber != null && !phoneNumber.matches("^0\\d{9}$")) {
            request.setAttribute("message", "Số điện thoại không hợp lệ! Vui lòng nhập đúng 10 số, bắt đầu bằng số 0.");
            request.getRequestDispatcher("userinfomation?action=viewInfomation").forward(request, response);
            return;
        }
        
        String address = request.getParameter("address");
        if (address != null && address.trim().isEmpty()) address = null;
        
        String currentpass = request.getParameter("currentpass");
        String newpass = request.getParameter("newpass");
        String newpasscheck = request.getParameter("newpasscheck");
        if (!currentpass.trim().isEmpty()) {
            if (newpass == null || newpass.trim().length() < 6) {
                request.setAttribute("message", "Đổi thất bại. Mật khẩu mới tối thiểu 6 ký tự!");
                request.getRequestDispatcher("userinfomation?action=viewInfomation").forward(request, response);
                return;
            }
            if (!newpass.equals(newpasscheck)) {
                request.setAttribute("message", "Đổi thất bại. Mật khẩu phải khớp!");
                request.getRequestDispatcher("userinfomation?action=viewInfomation").forward(request, response);
                return;
            }
        }
        boolean checkUpdate = userDAO.updateUserInfomationById(user.getUserId(), fullname, phoneNumber, address);
        if (!currentpass.trim().isEmpty()) {
            currentpass = utility.HashPassword.toSHA1(currentpass);
            newpass = utility.HashPassword.toSHA1(newpass);
            checkUpdate = userDAO.updatePassByUserId(user.getUserId(), newpass, currentpass);
        }
        if (!checkUpdate) {
            request.setAttribute("message", "Cập nhật không thành công, vui lòng kiểm tra lại mật khẩu!");
            request.getRequestDispatcher("userinfomation?action=viewInfomation").forward(request, response);
            return;
        }
        user = userDAO.getUserByUserId(user.getUserId());
        request.setAttribute("message", "Cập nhật thành công!");
        session.setAttribute("customer", user);
        request.getRequestDispatcher("userinfomation?action=viewInfomation").forward(request, response);
    }

    private void viewVouchers(HttpSession session, User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = new Customer();
        customer.setUserId(user.getUserId());
        customer.setTotalSpending(customerDAO.getTotalSpendingByUserId(user.getUserId()));
        customer.setTotalOrders(customerDAO.getTotalOrderByUserId(user.getUserId()));
        customer.setCreatedAt(user.getCreatedDateFormat());
        customer.setEmail(user.getEmail());
        
        VoucherDAO voucherDAO = new VoucherDAO();
        java.util.List<model.Voucher> listVouchers = voucherDAO.getVoucherForNewUser(user, customer);
        int numberVocher = listVouchers.size();
        
        request.setAttribute("user", customer);
        request.setAttribute("totalSpending", customer.getTotalSpending());
        request.setAttribute("numberVocher", numberVocher);
        request.setAttribute("listVouchers", listVouchers);
        
        String tier = customer.getTier();
        double totalAmout = customer.getTotalSpending();
        if (tier.equalsIgnoreCase("Green")) {
            request.setAttribute("neededAmountTopromoted", 1000000 - totalAmout);
            request.setAttribute("nextRank", "Bronze");
        } else if (tier.equalsIgnoreCase("Bronze")) {
            request.setAttribute("neededAmountTopromoted", 2000000 - totalAmout);
            request.setAttribute("nextRank", "Silver");
        } else if (tier.equalsIgnoreCase("Silver")) {
            request.setAttribute("neededAmountTopromoted", 3000000 - totalAmout);
            request.setAttribute("nextRank", "Gold");
        }

        request.getRequestDispatcher("user/user-voucher.jsp").forward(request, response);
    }
}
