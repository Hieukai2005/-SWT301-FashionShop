package controller.authentication;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import utility.Email;

/**
 *
 * @author DELL P5530
 */
@WebServlet(name = "ForgotPassword", urlPatterns = { "/forgotpassword" })
public class ForgotPassword extends HttpServlet {

    private final String FORGOT_PASSWORD_VIEW = "login/forgot-password.jsp";
    private final String FORGOT_PASSWORD__CONTROLLER = "forgotpassword";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ForgotPassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPassword at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher(FORGOT_PASSWORD_VIEW).forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        //
        UserDAO daoU = new UserDAO();
        Email mail = new Email();
        HttpSession session = request.getSession();
        // lay tu form forgot
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullname");
        // kiem tra email va full name co ton tai trong bang user hay khog
        boolean checkInfo = daoU.checkEmailAndFullName(email, fullName);
        //
        if (email != null && !email.isEmpty() && fullName != null && !fullName.isEmpty()) {
            if (checkInfo) {
                String otp = this.createOTP();
                
                // Luu thong tin vao session
                session.setAttribute("otp", otp);
                session.setAttribute("forgot_email", email);
                session.setAttribute("forgot_fullname", fullName);
                
                String title = "Mã xác nhận (OTP) lấy lại mật khẩu!";
                String mess = this.messForgotPassword(otp);
                mail.sendMail(email, otp, title, mess);
                session.setAttribute("mailSuccess", "Thành công! Vui lòng kiểm tra email để lấy mã OTP.");
                response.sendRedirect(request.getContextPath() + "/verifyotp");
                return;
            } else {
                session.setAttribute("mailFail", "Thất bại! Sai email hoặc họ và tên!");
            }
        } else {
            session.setAttribute("mailFail", "Thất bại! Vui lòng điền đầy đủ thông tin!");
        }
        response.sendRedirect(request.getContextPath() + "/" + FORGOT_PASSWORD__CONTROLLER);
    }

    // Ham tao OTP ngau nhien 6 chu so
    private String createOTP() {
        String otp = "";
        String chars = "0123456789";
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            int index = rand.nextInt(chars.length()); // chon 1 vi tri trong chuoi
            otp += chars.charAt(index);
        }
        return otp;
    }

    // Tin nhan gui den email
    private String messForgotPassword(String otp) {
        String mess = "<div style=\"max-width: 600px; margin: 0 auto; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; border: 1px solid #e0e0e0; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 8px rgba(0,0,0,0.1);\">\n"
                + "    \n"
                + "    <div style=\"width: 100%; text-align: center; background-color: #f8f9fa;\">\n"
                + "        <img src=\"https://tse3.mm.bing.net/th/id/OIP.7eeVNiEdJqmajjPoPRx0GAHaFL?rs=1&pid=ImgDetMain&o=7&rm=3\" alt=\"Banner Quên Mật Khẩu\" style=\"width: 600px; height: 235px; display: block;\">\n"
                + "    </div>\n"
                + "\n"
                + "    <div style=\"padding: 30px; background-color: #ffffff; color: #333333; line-height: 1.6;\">\n"
                + "        <h2 style=\"color: #2c3e50; text-align: center; margin-top: 0;\">Mã Xác Nhận (OTP)</h2>\n"
                + "        \n"
                + "        <p>Chào bạn,</p>\n"
                + "        <p>Hệ thống đã nhận được yêu cầu lấy lại mật khẩu cho tài khoản của bạn. Dưới đây là mã xác nhận (OTP) để bạn đặt lại mật khẩu:</p>\n"
                + "        \n"
                + "        <div style=\"background-color: #f1f8e9; border: 1px dashed #8bc34a; padding: 15px; text-align: center; margin: 25px 0; border-radius: 5px;\">\n"
                + "            <span style=\"font-size: 24px; font-weight: bold; color: #d32f2f; letter-spacing: 2px;\">\n"
                + otp
                + "            </span>\n"
                + "        </div>\n"
                + "        \n"
                + "        <p style=\"font-size: 14px; color: #7f8c8d;\">\n"
                + "            <strong>Lưu ý an toàn:</strong> Vui lòng không chia sẻ mã này cho bất kỳ ai. Bạn hãy nhập mã này vào trang khôi phục để tạo mật khẩu mới.\n"
                + "        </p>\n"
                + "        \n"
                + "        <hr style=\"border: none; border-top: 1px solid #eeeeee; margin: 25px 0;\">\n"
                + "        \n"
                + "        <p style=\"font-style: italic; color: #555555; text-align: center;\">\n"
                + "            Chúc bạn một ngày học tập và làm việc thật vui vẻ, tràn đầy năng lượng! <br>\n"
                + "            Cảm ơn bạn đã luôn đồng hành cùng chúng tôi.\n"
                + "        </p>\n"
                + "        \n"
                + "        <p style=\"text-align: right; margin-bottom: 0;\">\n"
                + "            Trân trọng,<br>\n"
                + "            <strong>Đội ngũ Hỗ trợ</strong>\n"
                + "        </p>\n"
                + "    </div>\n"
                + "</div>";
        return mess;
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
