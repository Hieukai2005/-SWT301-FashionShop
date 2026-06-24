package dal;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import java.sql.Timestamp;
import java.time.LocalDate;
import model.Color;
import model.DashBoard;
import model.OrderItem;
import model.Product;
import model.ProductVariant;
import model.Size;
import model.User;

public class OrderDAO extends DBContext {

    private ProductDAO daoP = new ProductDAO();

    /**
     * HoaNK - Lay 1 don hang bang id
     */
    public Order getOrderById(int id) {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareCall(query)) {
            int index = 1;
            ps.setInt(index++, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp time = rs.getTimestamp("order_date");
                    LocalDateTime date = (time != null) ? time.toLocalDateTime() : null;
                    Order order = new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                            rs.getString("full_name"), rs.getString("phone_number"),
                            rs.getString("shipping_address"), rs.getDouble("total_amount"),
                            date, rs.getString("status"));
                    return order;
                }
            }
        } catch (SQLException s) {
            System.out.println(s);
        }
        return null;
    }

    /**
     * HoaNK - Lay ra 10 don hang gan nhat
     */
    public List<Order> getRecentOrders(int number) {
        String query = "SELECT TOP (?) * FROM orders ORDER BY order_date DESC";
        List<Order> list = new ArrayList();
        int index = 1;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(index++, number);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp time = rs.getTimestamp("order_date");
                    LocalDateTime date = (time != null) ? time.toLocalDateTime() : null;
                    Order order = new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                            rs.getString("full_name"), rs.getString("phone_number"),
                            rs.getString("shipping_address"), rs.getDouble("total_amount"),
                            date, rs.getString("status"));
                    order.setItems(getOrderItemByOrderId(order.getOrderId()));
                    order.setVoucher(new VoucherDAO().getVoucherByOrderId(order.getOrderId()));
                    list.add(order);
                }
                return list;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    /**
     * @HoaNK - Lay ra cac mat hang da dat
     */
    public List<OrderItem> getOrderItemByOrderId(int orderId) {
        String query = "SELECT p.product_id,oi.* FROM orders o \n"
                + " JOIN order_items oi ON o.order_id = oi.order_id\n"
                + " JOIN product_variants pv ON oi.variant_id = pv.variant_id\n"
                + " JOIN products p ON p.product_id = pv.product_id\n"
                + " WHERE o.order_id = ?";
        List<OrderItem> list = new ArrayList();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = daoP.getProductById(rs.getInt("product_id"));
                    Order order = this.getOrderById(rs.getInt("order_id"));
                    ProductVariant productVariant = daoP.getVariantById(rs.getInt("variant_id"));
                    Size size = daoP.getSizeById(productVariant.getSizeId());
                    Color color = daoP.getColorById(productVariant.getColorId());
                    Timestamp time = rs.getTimestamp("created_at");
                    LocalDateTime date = (time != null) ? time.toLocalDateTime() : null;

                    OrderItem orderItem = new OrderItem(rs.getInt("order_item_id"), rs.getInt("order_id"),
                            rs.getInt("variant_id"), rs.getInt("quantity"), rs.getInt("price"),
                            date);

                    productVariant.setProduct(product);
                    productVariant.setSize(size);
                    productVariant.setColor(color);
                    orderItem.setProductVariant(productVariant);
                    orderItem.setOrder(order);
                    list.add(orderItem);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    /**
     * @author dotha
     * @date 22/02/2026
     * @param userId id của user khi đăng nhập
     * @return list các đơn được user đó đặt hàng sắp xếp theo ngày được tạo
     * giảm dần
     */
    public List<Order> getOrderByUserId(int userId) {
        String sql = "                SELECT * FROM orders WHERE user_id = ? order by order_date desc";
        List<Order> listOrders = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt(1);
                    String fullName = rs.getString(3);
                    String phoneNumber = rs.getString(4);
                    String shippingAddress = rs.getString(5);
                    double totalAmount = rs.getDouble(6);
                    LocalDateTime orderDate = rs.getTimestamp(7).toLocalDateTime();
                    String status = rs.getString(8);
                    Order order = new Order(orderId, userId, fullName, phoneNumber, shippingAddress, totalAmount, orderDate, status);
                    listOrders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOrders;
    }

    public List<Order> searchOrders(String keyword, String status, String dateFilter) {
        List<Order> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("                    SELECT o.order_id, o.user_id, o.full_name, o.phone_number,\n" +
"                           o.shipping_address, o.total_amount, o.order_date, o.status\n" +
"                    FROM orders o\n" +
"                    WHERE 1 = 1");

        // keyword
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("                        AND (\n" +
"                            CAST(o.order_id AS VARCHAR) LIKE ?\n" +
"                            OR o.full_name LIKE ?\n" +
"                            OR o.phone_number LIKE ?\n" +
"                        )");
        }

        // status
        if (status != null && !status.equals("ALL")) {
            sql.append(" AND o.status = ? ");
        }

        if (dateFilter != null && !dateFilter.equals("ALL")) {
            switch (dateFilter) {
                case "today":
                    sql.append("                                AND CAST(o.order_date AS DATE) = CAST(GETDATE() AS DATE)");
                    break;
                case "week":
                    sql.append("                                AND DATEPART(YEAR, o.order_date) = DATEPART(YEAR, GETDATE())\n" +
"                                AND DATEPART(WEEK, o.order_date) = DATEPART(WEEK, GETDATE())");
                    break;
                case "month":
                    sql.append("                                AND DATEPART(YEAR, o.order_date) = DATEPART(YEAR, GETDATE())\n" +
"                                AND DATEPART(MONTH, o.order_date) = DATEPART(MONTH, GETDATE())");
                    break;
                case "lastMonth":
                    sql.append("                                AND DATEPART(YEAR, o.order_date) = DATEPART(YEAR, DATEADD(MONTH, -1, GETDATE()))\n" +
"                                AND DATEPART(MONTH, o.order_date) = DATEPART(MONTH, DATEADD(MONTH, -1, GETDATE()))");
                    break;
            }
        }

        sql.append(" ORDER BY o.order_date DESC ");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String key = "%" + keyword + "%";
                ps.setString(index++, key);
                ps.setString(index++, key);
                ps.setString(index++, key);
            }

            if (status != null && !status.equals("ALL")) {
                ps.setString(index++, status);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("shipping_address"),
                        rs.getDouble("total_amount"),
                        rs.getTimestamp("order_date").toLocalDateTime(),
                        rs.getString("status")
                );
                model.Voucher voucher = new VoucherDAO().getVoucherByOrderId(o.getOrderId());
                o.setVoucher(voucher);
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Order> searchOrdersPaging(String keyword, String status,
            String dateFilter, int page, int pageSize) {

        List<Order> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("                    SELECT o.order_id, o.user_id, o.full_name, o.phone_number,\n" +
"                           o.shipping_address, o.total_amount, o.order_date, o.status\n" +
"                    FROM orders o\n" +
"                    WHERE 1 = 1");

        // keyword
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("                        AND (\n" +
"                            CAST(o.order_id AS VARCHAR) LIKE ?\n" +
"                            OR o.full_name LIKE ?\n" +
"                            OR o.phone_number LIKE ?\n" +
"                        )");
        }

        // status
        if (status != null && !status.equals("ALL")) {
            sql.append(" AND o.status = ? ");
        }

        // date filter
        if (dateFilter != null && !dateFilter.equals("ALL")) {
            switch (dateFilter) {
                case "today":
                    sql.append(" AND CAST(o.order_date AS DATE) = CAST(GETDATE() AS DATE) ");
                    break;
                case "week":
                    sql.append("                                AND DATEPART(YEAR, o.order_date) = DATEPART(YEAR, GETDATE())\n" +
"                                AND DATEPART(WEEK, o.order_date) = DATEPART(WEEK, GETDATE())");
                    break;
                case "month":
                    sql.append("                                AND DATEPART(YEAR, o.order_date) = DATEPART(YEAR, GETDATE())\n" +
"                                AND DATEPART(MONTH, o.order_date) = DATEPART(MONTH, GETDATE())");
                    break;
                case "lastMonth":
                    sql.append("                                AND DATEPART(YEAR, o.order_date) = DATEPART(YEAR, DATEADD(MONTH, -1, GETDATE()))\n" +
"                                AND DATEPART(MONTH, o.order_date) = DATEPART(MONTH, DATEADD(MONTH, -1, GETDATE()))");
                    break;
            }
        }

        sql.append("                    ORDER BY o.order_date DESC\n" +
"                    OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String key = "%" + keyword + "%";
                ps.setString(index++, key);
                ps.setString(index++, key);
                ps.setString(index++, key);
            }

            if (status != null && !status.equals("ALL")) {
                ps.setString(index++, status);
            }

            ps.setInt(index++, (page - 1) * pageSize);
            ps.setInt(index++, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("shipping_address"),
                        rs.getDouble("total_amount"),
                        rs.getTimestamp("order_date").toLocalDateTime(),
                        rs.getString("status")
                );
                List<OrderItem> items = getOrderItemByOrderId(o.getOrderId());
                o.setItems(items);
                model.Voucher voucher = new VoucherDAO().getVoucherByOrderId(o.getOrderId());
                o.setVoucher(voucher);
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countOrders(String keyword, String status, String dateFilter) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM orders o WHERE 1=1 ");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("                        AND (\n" +
"                            CAST(o.order_id AS VARCHAR) LIKE ?\n" +
"                            OR o.full_name LIKE ?\n" +
"                            OR o.phone_number LIKE ?\n" +
"                        )");
        }

        if (status != null && !status.equals("ALL")) {
            sql.append(" AND o.status = ? ");
        }

        // date filter
        if (dateFilter != null && !dateFilter.equals("ALL")) {
            switch (dateFilter) {
                case "today":
                    sql.append(" AND CAST(o.order_date AS DATE) = CAST(GETDATE() AS DATE) ");
                    break;
                case "week":
                    sql.append("                                AND DATEPART(YEAR, o.order_date) = DATEPART(YEAR, GETDATE())\n" +
"                                AND DATEPART(WEEK, o.order_date) = DATEPART(WEEK, GETDATE())");
                    break;
                case "month":
                    sql.append("                                AND DATEPART(YEAR, o.order_date) = DATEPART(YEAR, GETDATE())\n" +
"                                AND DATEPART(MONTH, o.order_date) = DATEPART(MONTH, GETDATE())");
                    break;
                case "lastMonth":
                    sql.append("                                AND DATEPART(YEAR, o.order_date) = DATEPART(YEAR, DATEADD(MONTH, -1, GETDATE()))\n" +
"                                AND DATEPART(MONTH, o.order_date) = DATEPART(MONTH, DATEADD(MONTH, -1, GETDATE()))");
                    break;
            }
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String key = "%" + keyword + "%";
                ps.setString(index++, key);
                ps.setString(index++, key);
                ps.setString(index++, key);
            }

            if (status != null && !status.equals("ALL")) {
                ps.setString(index++, status);
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

    public DashBoard getOrderDashboard(LocalDate date) {
        String sql = "                    SELECT\n" +
"                        COUNT(order_id) as totalOrders,\n" +
"                        SUM(CASE WHEN status = N'Hoàn thành' THEN total_amount ELSE 0 END) as totalAmount,\n" +
"                        SUM(CASE WHEN status = N'Chờ xác nhận' THEN 1 ELSE 0 END) as pendingOrders,\n" +
"                        SUM(CASE WHEN status = N'Hoàn thành' THEN 1 ELSE 0 END) as completedOrders\n" +
"                    FROM Orders\n" +
"                    WHERE MONTH(order_date) = ?\n" +
"                    AND YEAR(order_date) = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, date.getMonthValue());
            ps.setInt(2, date.getYear());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                DashBoard d = new DashBoard();
                d.setTotalOrders(rs.getInt("totalOrders"));
                d.setTotalAmount(rs.getDouble("totalAmount"));
                d.setPendingOrders(rs.getInt("pendingOrders"));
                d.setCompletedOrders(rs.getInt("completedOrders"));
                return d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DashBoard();
    }

    public void updateOrder(int id, String name, String phone,
            String address, String status) {

        String sql = "                    UPDATE orders\n" +
"                    SET full_name = ?,\n" +
"                        phone_number = ?,\n" +
"                        shipping_address = ?,\n" +
"                        status = ?\n" +
"                    WHERE order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, status);
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author dotha
     * @date 25/02/2026
     * @param user user obj
     * @param total tổng tiền của đơn hàng trong giỏ hàng của user đó
     * @return id của đơn hàng vừa tạo
     */
    public int createOrderByUser(User user, double total) {
        String sql = "                INSERT INTO [dbo].[orders]\n" +
"                           ([user_id]\n" +
"                           ,[full_name]\n" +
"                           ,[phone_number]\n" +
"                           ,[shipping_address]\n" +
"                           ,[total_amount])\n" +
"\n" +
"                     VALUES\n" +
"                           (?\n" +
"                           ,?\n" +
"                           ,?\n" +
"                           ,?\n" +
"                           ,?)";
        try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            st.setObject(index++, user.getUserId());
            st.setObject(index++, user.getFullName());
            st.setObject(index++, user.getPhoneNumber());
            st.setObject(index++, user.getAddress());
            st.setObject(index++, total);
            int row = st.executeUpdate();
            if (row > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     *
     * @param orderId order id
     * @param variantId variant id
     * @param quantity số lượng sản phẩm trong một item
     * @param price giá tiền sản phẩm đó đã được giảm giá nếu có
     * @return true nếu thêm thành công, false nếu ngược lại
     */
    public boolean addOrderItem(int orderId, int variantId, int quantity, double price) {
        String sql = "                INSERT INTO [dbo].[order_items]\n" +
"                           ([order_id]\n" +
"                           ,[variant_id]\n" +
"                           ,[quantity]\n" +
"                           ,[price])\n" +
"                     VALUES\n" +
"                           (?\n" +
"                           ,?\n" +
"                           ,?\n" +
"                           ,?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int index = 1;
            st.setObject(index++, orderId);
            st.setObject(index++, variantId);
            st.setObject(index++, quantity);
            st.setObject(index++, price);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cancelOrder(int orderId) {
        String sql = "UPDATE orders SET status = N'Đã hủy' WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @author dotha
     * @date 25/02/2026
     * @param orderId order id
     * @return giá tiền của một order chưa tính giá tiền được áp dụng voucher
     */
    public double getPriceByOrderId(int orderId) {
        String sql = "                SELECT SUM(price * quantity) FROM order_items WHERE order_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, orderId);
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

    public boolean updateQuantity(int orderId) {
        String sql = "                      SELECT * FROM order_items\n" +
"                       WHERE order_id = ?";
        List<OrderItem> orderItems = new ArrayList<>();

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setObject(1, orderId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int index = 1;
                    int orderItemId = rs.getInt(index++);
                    int orderId1 = rs.getInt(index++);
                    int productVariantId = rs.getInt(index++);
                    int quantity = rs.getInt(index++);
                    double price = rs.getDouble(index++);
                    OrderItem oi = new OrderItem();
                    oi.setOrderId(orderId);
                    oi.setPrice(price);
                    oi.setProductVariantId(productVariantId);
                    oi.setOrderId(orderId1);
                    oi.setQuantity(quantity);
                    orderItems.add(oi);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        for (OrderItem orderItem : orderItems) {
            String sqlUpdate = "                               UPDATE [dbo].[product_variants]\n" +
"                                  SET [quantity] = quantity + ?\n" +
"                                WHERE variant_id = ?";
            try (PreparedStatement st = connection.prepareStatement(sqlUpdate)) {
                st.setInt(1, orderItem.getQuantity());
                st.setInt(2, orderItem.getProductVariantId());
                st.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    
    public List<Double> getMonthlyRevenue(int year) {
        List<Double> monthlyRevenue = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            monthlyRevenue.add(0.0);
        }

        String sql = "SELECT MONTH(order_date) as [month], SUM(total_amount) as revenue "
                + "FROM orders "
                + "WHERE YEAR(order_date) = ? AND status = N'Hoàn thành' "
                + "GROUP BY MONTH(order_date) "
                + "ORDER BY MONTH(order_date)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int month = rs.getInt("month");
                    double revenue = rs.getDouble("revenue");
                    monthlyRevenue.set(month - 1, revenue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyRevenue;
    }

    public List<Integer> getOrderStatusCounts() {
        List<Integer> counts = new ArrayList<>();
        String[] statuses = {"Hoàn thành", "Chờ xác nhận", "Đang giao", "Đã hủy"};

        for (String status : statuses) {
            String sql = "SELECT COUNT(*) FROM orders WHERE status = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setNString(1, status);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        counts.add(rs.getInt(1));
                    } else {
                        counts.add(0);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return counts;
    }

    public List<Integer> getAvailableYears() {
        List<Integer> years = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(order_date) as [year] FROM orders ORDER BY [year] DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                years.add(rs.getInt("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return years;
    }
}
