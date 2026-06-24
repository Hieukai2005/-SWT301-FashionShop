/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HieuND khách hàng ở admin
 */
public class CustomerDAO extends DBContext {

    public List<Customer> getCustomers(String keyword, String tier, String sort) {
        List<Customer> list = new ArrayList<>();

        String sql = "SELECT u.user_id, u.full_name, u.email, u.phone_number, u.address, u.created_at, "
                + "ISNULL(SUM(o.total_amount), 0) AS total_spending, "
                + "COUNT(o.order_id) AS total_orders "
                + "FROM users u "
                + "LEFT JOIN orders o ON u.user_id = o.user_id AND o.status = N'Hoàn thành' "
                + "WHERE u.role_id = 2 ";

        if (!keyword.isEmpty()) {
            sql += "AND (u.full_name LIKE ? OR u.phone_number LIKE ?) ";
        }

        sql += "GROUP BY u.user_id, u.full_name, u.email, u.phone_number, u.address, u.created_at ";

        // SORT
        switch (sort) {
            case "name_asc":
                sql += "ORDER BY u.full_name ASC";
                break;
            case "name_desc":
                sql += "ORDER BY u.full_name DESC";
                break;
            case "newest":
                sql += "ORDER BY u.created_at DESC";
                break;
            case "oldest":
                sql += "ORDER BY u.created_at ASC";
                break;
            case "spending_desc":
                sql += "ORDER BY total_spending DESC";
                break;
            default:
                sql += "ORDER BY u.user_id ASC";
                break;
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int index = 1;
            if (!keyword.isEmpty()) {
                ps.setString(index++, "%" + keyword + "%");
                ps.setString(index++, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setUserId(rs.getInt("user_id"));
                c.setFullName(rs.getString("full_name"));
                c.setEmail(rs.getString("email"));
                c.setPhoneNumber(rs.getString("phone_number"));
                c.setAddress(rs.getString("address"));
                c.setCreatedAt(rs.getString("created_at"));
                c.setTotalSpending(rs.getDouble("total_spending"));
                c.setTotalOrders(rs.getInt("total_orders"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Filter tier ở Java (vì tier tính từ totalSpending)
        if (!tier.equals("ALL")) {
            List<Customer> filtered = new ArrayList<>();
            for (Customer c : list) {
                if (c.getTier().equals(tier)) {
                    filtered.add(c);
                }
            }
            return filtered;
        }

        return list;
    }

    public int countTotalCustomers() {
        String sql = "SELECT COUNT(*) FROM users WHERE role_id = 2";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countNewCustomers() {
        String sql = "SELECT COUNT(*) FROM users "
                + "WHERE role_id = 2 AND created_at >= DATEADD(DAY, -10, GETDATE())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countVipCustomers() {
        String sql = "SELECT COUNT(*) FROM ("
                + "SELECT u.user_id, ISNULL(SUM(o.total_amount),0) AS total_spending "
                + "FROM users u "
                + "LEFT JOIN orders o ON u.user_id = o.user_id AND o.status = N'Hoàn thành' "
                + "WHERE u.role_id = 2 "
                + "GROUP BY u.user_id "
                + "HAVING ISNULL(SUM(o.total_amount),0) >= 3000000"
                + ") AS vip";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Customer> getCustomersPaging(String keyword, String tier,
            String sort, String dateJoined, int page, int pageSize) {

        List<Customer> list = new ArrayList<>();

        String orderBy = "u.user_id ASC";

        switch (sort) {
            case "name_asc":
                orderBy = "u.full_name ASC";
                break;
            case "name_desc":
                orderBy = "u.full_name DESC";
                break;
            case "newest":
                orderBy = "u.created_at DESC";
                break;
            case "oldest":
                orderBy = "u.created_at ASC";
                break;
            case "spending_desc":
                orderBy = "ISNULL(SUM(o.total_amount),0) DESC";
                break;
        }

        String sql
                = "SELECT * FROM ( "
                + " SELECT u.user_id, u.full_name, u.email, u.phone_number, u.address, u.created_at, "
                + " u.status, "
                + " ISNULL(SUM(o.total_amount),0) AS total_spending, "
                + " COUNT(o.order_id) AS total_orders, "
                + " ROW_NUMBER() OVER (ORDER BY " + orderBy + ") AS row_num "
                + " FROM users u "
                + " LEFT JOIN orders o ON u.user_id = o.user_id AND o.status = N'Hoàn thành' "
                + " WHERE u.role_id = 2 ";

        if (!keyword.isEmpty()) {
            sql += " AND (u.full_name LIKE ? OR u.phone_number LIKE ?) ";
        }

        // Lọc theo ngày tham gia
        if (dateJoined != null && !dateJoined.equals("ALL")) {
            switch (dateJoined) {
                case "today":
                    sql += " AND CAST(u.created_at AS DATE) = CAST(GETDATE() AS DATE) ";
                    break;
                case "week":
                    sql += " AND DATEPART(YEAR, u.created_at) = DATEPART(YEAR, GETDATE()) "
                         + " AND DATEPART(WEEK, u.created_at) = DATEPART(WEEK, GETDATE()) ";
                    break;
                case "month":
                    sql += " AND DATEPART(YEAR, u.created_at) = DATEPART(YEAR, GETDATE()) "
                         + " AND DATEPART(MONTH, u.created_at) = DATEPART(MONTH, GETDATE()) ";
                    break;
                case "lastMonth":
                    sql += " AND DATEPART(YEAR, u.created_at) = DATEPART(YEAR, DATEADD(MONTH,-1,GETDATE())) "
                         + " AND DATEPART(MONTH, u.created_at) = DATEPART(MONTH, DATEADD(MONTH,-1,GETDATE())) ";
                    break;
            }
        }

        sql += " GROUP BY u.user_id, u.full_name, u.email, u.phone_number, u.address, u.created_at, u.status ";

        // FILTER TIER (dùng HAVING vì có SUM)
        if (!tier.equals("ALL")) {

            sql += " HAVING ";

            switch (tier) {
                case "Gold":
                    sql += " ISNULL(SUM(o.total_amount),0) >= 3000000 ";
                    break;
                case "Silver":
                    sql += " ISNULL(SUM(o.total_amount),0) BETWEEN 2000000 AND 2999999 ";
                    break;
                case "Bronze":
                    sql += " ISNULL(SUM(o.total_amount),0) BETWEEN 1000000 AND 1999999 ";
                    break;
                case "Green":
                    sql += " ISNULL(SUM(o.total_amount),0) < 1000000 ";
                    break;
            }
        }

        sql += ") AS temp WHERE row_num BETWEEN ? AND ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int index = 1;

            if (!keyword.isEmpty()) {
                ps.setString(index++, "%" + keyword + "%");
                ps.setString(index++, "%" + keyword + "%");
            }

            int start = (page - 1) * pageSize + 1;
            int end = page * pageSize;

            ps.setInt(index++, start);
            ps.setInt(index++, end);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer c = new Customer();
                c.setUserId(rs.getInt("user_id"));
                c.setFullName(rs.getString("full_name"));
                c.setEmail(rs.getString("email"));
                c.setPhoneNumber(rs.getString("phone_number"));
                c.setAddress(rs.getString("address"));
                c.setCreatedAt(rs.getString("created_at"));
                c.setTotalSpending(rs.getDouble("total_spending"));
                c.setTotalOrders(rs.getInt("total_orders"));
                c.setActive(rs.getBoolean("status"));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countCustomers(String keyword, String tier, String dateJoined) {

        String sql
                = "SELECT COUNT(*) FROM ( "
                + " SELECT u.user_id, ISNULL(SUM(o.total_amount),0) AS total_spending "
                + " FROM users u "
                + " LEFT JOIN orders o ON u.user_id = o.user_id AND o.status = N'Hoàn thành' "
                + " WHERE u.role_id = 2 ";

        if (!keyword.isEmpty()) {
            sql += " AND (u.full_name LIKE ? OR u.phone_number LIKE ?) ";
        }

        // Lọc theo ngày tham gia
        if (dateJoined != null && !dateJoined.equals("ALL")) {
            switch (dateJoined) {
                case "today":
                    sql += " AND CAST(u.created_at AS DATE) = CAST(GETDATE() AS DATE) ";
                    break;
                case "week":
                    sql += " AND DATEPART(YEAR, u.created_at) = DATEPART(YEAR, GETDATE()) "
                         + " AND DATEPART(WEEK, u.created_at) = DATEPART(WEEK, GETDATE()) ";
                    break;
                case "month":
                    sql += " AND DATEPART(YEAR, u.created_at) = DATEPART(YEAR, GETDATE()) "
                         + " AND DATEPART(MONTH, u.created_at) = DATEPART(MONTH, GETDATE()) ";
                    break;
                case "lastMonth":
                    sql += " AND DATEPART(YEAR, u.created_at) = DATEPART(YEAR, DATEADD(MONTH,-1,GETDATE())) "
                         + " AND DATEPART(MONTH, u.created_at) = DATEPART(MONTH, DATEADD(MONTH,-1,GETDATE())) ";
                    break;
            }
        }

        sql += " GROUP BY u.user_id ";

        if (!tier.equals("ALL")) {

            sql += " HAVING ";

            switch (tier) {
                case "Gold":
                    sql += " ISNULL(SUM(o.total_amount),0) >= 3000000 ";
                    break;
                case "Silver":
                    sql += " ISNULL(SUM(o.total_amount),0) BETWEEN 2000000 AND 2999999 ";
                    break;
                case "Bronze":
                    sql += " ISNULL(SUM(o.total_amount),0) BETWEEN 1000000 AND 1999999 ";
                    break;
                case "Green":
                    sql += " ISNULL(SUM(o.total_amount),0) < 1000000 ";
                    break;
            }
        }

        sql += ") AS temp";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            if (!keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /** Khóa tài khoản (status = 0) */
    public boolean lockUser(int userId) {
        String sql = "UPDATE users SET status = 0 WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Mở khóa tài khoản (status = 1) */
    public boolean unlockUser(int userId) {
        String sql = "UPDATE users SET status = 1 WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertUser(String fullName, String email,
            String phone, String address, int roleId) {

        String sql = "INSERT INTO Users (full_name, email, phone_number, address, role_id) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setInt(5, roleId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(int userId, String fullName, String email,
            String phone, String address, int roleId) {

        String sql = "UPDATE Users SET full_name=?, email=?, phone_number=?, "
                + "address=?, role_id=? WHERE user_id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setInt(5, roleId);
            ps.setInt(6, userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getTotalSpendingByUserId(int userId) {
        String sql = "SELECT SUM(total_amount) FROM orders \n" +
"                     WHERE user_id = ? AND status = N'Hoàn thành'";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     *
     * @param userId user id
     * @return tổng các sản phẩm đã đặt cuaur user đó theo user id
     */
    public int getTotalOrderByUserId(int userId) {
        String sql = "SELECT count(*) FROM orders \n" +
"                     WHERE user_id = ? AND status = N'Hoàn thành'";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
