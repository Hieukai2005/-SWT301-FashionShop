package utility;

import model.*;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Email {
    //
    private final String from = "kimmita963@gmail.com";
    private final String password = "tasq ujie aimv lyhh";
    
    private Session session;
    
    public Email() {
    // Cấu hình mail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // bật gửi dữ liệu qua cổng tls 587 ssl 465...
        props.put("mail.smtp.auth", "true"); // bắt buộc đăng nhập khi tới gmail
        props.put("mail.smtp.starttls.enable", "true"); // mã hóa dữ liệu
        //
        // Cấu hình phiên
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        //
    }
    // Nhận email từ phía client ném qua đây
    public boolean sendMail(String to, String otp,String title,String mess) {
        
        // Cấu hình nội dung mail
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            msg.setSubject(title,"UTF-8");
 
            msg.setSentDate(new Date());

            msg.setContent(mess,"text/html; charset=UTF-8");
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
