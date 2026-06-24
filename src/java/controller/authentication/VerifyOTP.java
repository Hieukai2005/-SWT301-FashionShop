package controller.authentication;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "VerifyOTP", urlPatterns = { "/verifyotp" })
public class VerifyOTP extends HttpServlet {

    private final String VERIFY_OTP_VIEW = "login/verify-otp.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Neu chua co thong tin tu buoc forgot password
        if (session.getAttribute("forgot_email") == null || session.getAttribute("otp") == null) {
            response.sendRedirect(request.getContextPath() + "/forgotpassword");
            return;
        }
        
        request.getRequestDispatcher(VERIFY_OTP_VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        String savedOtp = (String) session.getAttribute("otp");
        
        if (savedOtp == null) {
            response.sendRedirect(request.getContextPath() + "/forgotpassword");
            return;
        }

        String inputOtp = request.getParameter("otp");

        if (inputOtp == null || !inputOtp.equals(savedOtp)) {
            request.setAttribute("error", "Mã xác nhận (OTP) không chính xác!");
            request.getRequestDispatcher(VERIFY_OTP_VIEW).forward(request, response);
            return;
        }
        
        // Neu OTP đúng
        session.setAttribute("otp_verified", true);
        response.sendRedirect(request.getContextPath() + "/resetpassword");
    }

    @Override
    public String getServletInfo() {
        return "Verify OTP Servlet";
    }
}
