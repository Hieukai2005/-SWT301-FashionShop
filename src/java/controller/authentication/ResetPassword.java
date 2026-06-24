package controller.authentication;

import dal.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ResetPassword", urlPatterns = { "/resetpassword" })
public class ResetPassword extends HttpServlet {

    private final String RESET_PASSWORD_VIEW = "login/reset-password.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Kiem tra da qua buoc xac nhan OTP chua
        if (session.getAttribute("otp_verified") == null || !(Boolean)session.getAttribute("otp_verified")) {
            response.sendRedirect(request.getContextPath() + "/forgotpassword");
            return;
        }
        
        request.getRequestDispatcher(RESET_PASSWORD_VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("forgot_email");
        String fullName = (String) session.getAttribute("forgot_fullname");
        Boolean isVerified = (Boolean) session.getAttribute("otp_verified");
        
        if (email == null || isVerified == null || !isVerified) {
            response.sendRedirect(request.getContextPath() + "/forgotpassword");
            return;
        }

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (newPassword == null || newPassword.trim().isEmpty() || !newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp hoặc bị trống!");
            request.getRequestDispatcher(RESET_PASSWORD_VIEW).forward(request, response);
            return;
        }

        // Passwords hop le, tien hanh update
        UserDAO daoU = new UserDAO();
        String hashedPass = utility.HashPassword.toSHA1(newPassword);
        daoU.updateForgotPassword(hashedPass, email, fullName);

        // Xoa cac attribute trong session thuoc ve buoc forgot password
        session.removeAttribute("otp");
        session.removeAttribute("forgot_email");
        session.removeAttribute("forgot_fullname");
        session.removeAttribute("otp_verified");

        // Chuyen huong ve trang login voi thong bao
        session.setAttribute("successMessage", "Đổi mật khẩu thành công! Vui lòng đăng nhập lại.");
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    public String getServletInfo() {
        return "Reset Password Servlet";
    }
}
