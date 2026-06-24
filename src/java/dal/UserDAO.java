package dal;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import model.Category;
import model.User;

public class UserDAO extends DBContext {

    //SQL liên quan đến người dùng
    public int check(String username, String password) {
        String sql = "SELECT role_id FROM [users] WHERE email = ? AND password = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt("role_id"); // trả về role
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // không tìm thấy user
    }

    public boolean insert(String email, String name, String pasword) {
        String sql = "INSERT INTO [users]\n"
                + "           ([email]\n"
                + "           ,[full_name]\n"
                + "           ,[password])\n"
                + "     VALUES\n"
                + "           (?,?,?)\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, name);
            st.setString(3, pasword);
            st.executeUpdate();
            return true;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM Users WHERE (full_name= ? OR email = ?) AND password = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, username); // lấy làm email luôn
            st.setObject(3, password);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt(1));
                u.setEmail(rs.getString(3));
                u.setPhoneNumber(rs.getString(4));
                u.setRoleId(rs.getInt(7));
                u.setStatus(rs.getBoolean(8));
                u.setFullName(rs.getString(2));
                u.setAddress(rs.getString(5));
                u.setCreatedAt(rs.getTimestamp(9).toLocalDateTime());
                // set thêm các field khác nếu có
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserInfomationById(int userId, String fullname, String phoneNumber, String address) {
        String sql = "                     UPDATE [dbo].[users]\n" +
"                        SET [full_name] = ?\n" +
"                           ,[phone_number] = ?\n" +
"                           ,[address] = ?\n" +
"                      WHERE user_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int index = 1;
            st.setObject(index++, fullname);
            st.setObject(index++, phoneNumber);
            st.setObject(index++, address);
            st.setObject(index++, userId);
            int row = st.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // test method 
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        //System.out.println(new UserDAO().getUserByUsernameAndPassword("thanhdoAdmin", "123"));
        System.out.println(userDAO.check("thanhdoAdmin", "123"));
    }

    public boolean updatePassByUserId(int userId, String newpass, String currentpass) {
        String sql = "              UPDATE [dbo].[users]\n" +
"                      SET password = ?\n" +
"                    WHERE user_id = ? AND password = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int index = 1;
            st.setObject(index++, newpass);
            st.setObject(index++, userId);
            st.setObject(index++, currentpass);
            int row = st.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByUserId(int userId) {
        String sql = "                     SELECT * FROM users WHERE user_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt(1));
                u.setEmail(rs.getString(3));
                u.setPhoneNumber(rs.getString(4));
                u.setRoleId(rs.getInt(7));
                u.setFullName(rs.getString(2));
                u.setAddress(rs.getString(5));
                u.setCreatedAt(rs.getTimestamp(9).toLocalDateTime());
                // set thêm các field khác nếu có
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HoaNK - kiem tra email va ten nguoi dung (lay lai mat khau)
     */
    public boolean checkEmailAndFullName(String email, String fullName) {
        String query = "SELECT 1 FROM users WHERE email = ? AND full_name = ?";
        
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, fullName);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return true;
                }
            }
        }catch(Exception e){
            System.err.println("Error at userDAO");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * HoaNK - Cap nhat mat khau moi khi lay lai mat khau
     */
    public void updateForgotPassword(String newPassword, String email,String fullName) {
        String query = "UPDATE users SET password = ? WHERE email = ? AND full_name = ?";
        
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, newPassword);
            ps.setString(2, email);
            ps.setString(3, fullName);
            ps.executeUpdate();
        } catch (Exception e) {
             System.err.println("Error at userDAO");
            e.printStackTrace();
        }
    }
}
