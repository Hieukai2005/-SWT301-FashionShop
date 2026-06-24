/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Timestamp;
import model.Voucher;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.User;

/**
 *
 * @author dotha
 * @date: 15/2/2026 class liên quan đến việc thêm, xóa, sửa mã giảm giá cho sản
 * phẩm
 */
public class VoucherDAO extends DBContext {

    /**
     * @author dotha
     * @param voucherCode code của voucher
     * @return voucher obj Lấy Voucher bằng voucher code
     */
    public Voucher getVoucherByVoucherCode(String voucherCode) {
        String sql = "SELECT * FROM vouchers WHERE code = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, voucherCode);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int voucherId = rs.getInt(1);
                    String code = voucherCode;
                    String discountType = rs.getString(3);
                    double discountValue = rs.getDouble(4);
                    double minOrderValue = rs.getDouble(5);
                    double maxDiscount = rs.getDouble(6);
                    int quantity = rs.getInt(7);
                    int uesedCount = rs.getInt(8);
                    LocalDateTime startDate = rs.getTimestamp(9).toLocalDateTime();
                    LocalDateTime endDate = rs.getTimestamp(10).toLocalDateTime();
                    boolean isActive = rs.getBoolean(11);
                    LocalDateTime createAt = rs.getTimestamp(12).toLocalDateTime();
                    Voucher voucher = new Voucher(voucherId, code, discountType, discountValue, minOrderValue, maxDiscount, quantity, uesedCount, startDate, endDate, isActive, createAt);
                    return voucher;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author dotha
     * @param searchParameter dữ liệu từ ô search user nhập vào
     * @param status trạng thái của voucher
     * @param typeOfDiscount loại giảm giá
     * @return số lượng voucher được lọc sau khi truy vấn
     */
    public int getNumberOfVoucher(String searchParameter, String status, String typeOfDiscount) {
        String sql = "                     SELECT COUNT(*) FROM vouchers\n" +
"                     WHERE 1=1";
        if (!searchParameter.isEmpty()) {
            sql += "                   AND code LIKE ?";
        }
        if (!typeOfDiscount.isEmpty() && !"allDiscount".equalsIgnoreCase(typeOfDiscount)) {
            sql += "                   AND discount_type = ?";
        }
        if (!status.isEmpty() && !"allStatus".equalsIgnoreCase(status)) {
            if ("currentlyOperating".equalsIgnoreCase(status)) {
                sql += "                       AND is_active = 1 AND ? BETWEEN start_date AND end_date";
            } else if ("expired".equalsIgnoreCase(status)) {
                sql += "                       AND is_active = 1 AND end_date < ?";
            } else if ("notYet".equalsIgnoreCase(status)) {
                sql += "                       AND is_active = 1 AND start_date > ?";
            } else {
                sql += "                       AND is_active = 0";
            }
        }
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int index = 1;
            if (!searchParameter.isEmpty()) {
                st.setObject(index++, "%" + searchParameter + "%");
            }
            if (!typeOfDiscount.isEmpty() && !"allDiscount".equalsIgnoreCase(typeOfDiscount)) {
                st.setObject(index++, typeOfDiscount);
            }
            if (!status.isEmpty() && !"allStatus".equalsIgnoreCase(status) && !"notActive".equalsIgnoreCase(status)) {
                LocalDateTime timeNow = LocalDateTime.now();
                st.setObject(index++, Timestamp.valueOf(timeNow));
            }
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

    /**
     * @author dotha
     * @param searchParameter dữ liệu từ ô search user nhập vào
     * @param status trạng thái của voucher
     * @param typeOfDiscount loại giảm giá
     * @param page index trang muốn chuyển đến
     * @param numberPerPage số lượng voucher hiển thị mỗi trang
     * @return list các voucher được truy vấn kế tiếp
     */
    public List<Voucher> getVoucherSplitPage(String searchParameter, String status, String typeOfDiscount, int page, int numberPerPage) {
        List<Voucher> listVouchers = new ArrayList<>();
        String sql = "                     SELECT * FROM vouchers\n" +
"                     WHERE 1=1";
        if (!searchParameter.isEmpty()) {
            sql += "                   AND code LIKE ?";
        }
        if (!typeOfDiscount.isEmpty() && !"allDiscount".equalsIgnoreCase(typeOfDiscount)) {
            sql += "                   AND discount_type = ?";
        }
        if (!status.isEmpty() && !"allStatus".equalsIgnoreCase(status)) {
            if ("currentlyOperating".equalsIgnoreCase(status)) {
                sql += "                       AND is_active = 1 AND ? BETWEEN start_date AND end_date";
            } else if ("expired".equalsIgnoreCase(status)) {
                sql += "                       AND is_active = 1 AND end_date < ?";
            } else if ("notYet".equalsIgnoreCase(status)) {
                sql += "                       AND is_active = 1 AND start_date > ?";
            } else {
                sql += "                       AND is_active = 0";
            }
        }
        sql += "               ORDER BY voucher_id ASC\n" +
"               OFFSET ? ROWS\n" +
"               FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int index = 1;
            if (!searchParameter.isEmpty()) {
                st.setObject(index++, "%" + searchParameter + "%");
            }
            if (!typeOfDiscount.isEmpty() && !"allDiscount".equalsIgnoreCase(typeOfDiscount)) {
                st.setObject(index++, typeOfDiscount);
            }
            if (!status.isEmpty() && !"allStatus".equalsIgnoreCase(status) && !"notActive".equalsIgnoreCase(status)) {
                LocalDateTime timeNow = LocalDateTime.now();
                st.setObject(index++, Timestamp.valueOf(timeNow));
            }
            st.setObject(index++, (page - 1) * numberPerPage);
            st.setObject(index++, numberPerPage);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int getData = 1;
                    int voucherId = rs.getInt(getData++);
                    String code = rs.getString(getData++);
                    String discountType = rs.getString(getData++);
                    double discountValue = rs.getDouble(getData++);
                    double minOrderValue = rs.getDouble(getData++);
                    double maxDiscount = rs.getDouble(getData++);
                    int quantity = rs.getInt(getData++);
                    int uesedCount = rs.getInt(getData++);
                    LocalDateTime startDate = rs.getTimestamp(getData++).toLocalDateTime();
                    LocalDateTime endDate = rs.getTimestamp(getData++).toLocalDateTime();
                    boolean isActive = rs.getBoolean(getData++);
                    LocalDateTime createAt = rs.getTimestamp(getData++).toLocalDateTime();
                    Voucher voucher = new Voucher(voucherId, code, discountType, discountValue, minOrderValue, maxDiscount, quantity, uesedCount, startDate, endDate, isActive, createAt);
                    listVouchers.add(voucher);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVouchers;
    }

    /**
     *
     * @return số lượng voucher đã được sử dụng
     */
    public int getNumberOfVoucherUsed() {
        String sql = "SELECT SUM(used_count) FROM vouchers";
        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @author dotha
     * @param voucherId id của voucher
     * @return true nếu xóa thành công voucher theo id false nếu ngược lại
     */
    public boolean deleteVoucherById(int voucherId) {
        String sql = "DELETE FROM [dbo].[vouchers] WHERE voucher_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, voucherId);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @author dotha
     * @param code code của voucher
     * @param discountType loại giảm giá
     * @param discountValue giá trị giảm
     * @param minOrderValue giá trị đơn hàng tối thiểu có thể áp dụng
     * @param maxDiscount giá trị giảm tối đa đơn hàng có thể áp dụng
     * @param quantity số lượng voucher
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @param active trạng thái bật tắt của voucher
     * @return true nếu thêm mới voucher thành công false nếu ngược lại
     */
    public boolean addVoucher(String code, String discountType, double discountValue, double minOrderValue, double maxDiscount, int quantity, LocalDateTime startDate, LocalDateTime endDate, int active) {
        String sql = "                 INSERT INTO [dbo].[vouchers]\n" +
"                            ([code]\n" +
"                            ,[discount_type]\n" +
"                            ,[discount_value]\n" +
"                            ,[min_order_value]\n" +
"                            ,[max_discount]\n" +
"                            ,[quantity]\n" +
"                            ,[start_date]\n" +
"                            ,[end_date]\n" +
"                            ,[is_active])\n" +
"                 VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int i = 1;
            st.setString(i++, code);
            st.setString(i++, discountType);
            st.setDouble(i++, discountValue);
            st.setDouble(i++, minOrderValue);
            if ("percent".equalsIgnoreCase(discountType)) {
                st.setDouble(i++, maxDiscount);
            } else {
                st.setNull(i++, java.sql.Types.DOUBLE);
            }
            st.setInt(i++, quantity);
            if (startDate != null) {
                st.setTimestamp(i++, Timestamp.valueOf(startDate));
            } else {
                st.setNull(i++, java.sql.Types.TIMESTAMP);
            }
            if (endDate != null) {
                st.setTimestamp(i++, Timestamp.valueOf(endDate));
            } else {
                st.setNull(i++, java.sql.Types.TIMESTAMP);
            }
            st.setInt(i++, active);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @author dotha
     * @param code code của voucher
     * @param discountType loại giảm giá
     * @param discountValue giá trị giảm
     * @param minOrderValue giá trị đơn hàng tối thiểu có thể áp dụng
     * @param maxDiscount giá trị giảm tối đa đơn hàng có thể áp dụng
     * @param quantity số lượng voucher
     * @param startDate ngày bắt đầu
     * @param endDate ngày kết thúc
     * @param active trạng thái bật tắt của voucher
     * @param voucherId id của voucher
     * @return true nếu sửa voucher theo id thành công false nếu ngược lại
     */
    public boolean editVoucher(String code, String discountType, double discountValue, double minOrderValue, double maxDiscount, int quantity, LocalDateTime startDate, LocalDateTime endDate, int active, int voucherId) {
        String sql = "                     UPDATE [dbo].[vouchers]\n" +
"                        SET [code] = ?\n" +
"                           ,[discount_type] = ?\n" +
"                           ,[discount_value] = ?\n" +
"                           ,[min_order_value] = ?\n" +
"                           ,[max_discount] = ?\n" +
"                           ,[quantity] = ?\n" +
"                           ,[start_date] = ?\n" +
"                           ,[end_date] = ?\n" +
"                           ,[is_active] = ?\n" +
"                      WHERE voucher_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int index = 1;
            st.setObject(index++, code);
            st.setObject(index++, discountType);
            st.setObject(index++, discountValue);
            st.setObject(index++, minOrderValue);
            if ("percent".equalsIgnoreCase(discountType)) {
                st.setDouble(index++, maxDiscount);
            } else {
                st.setNull(index++, java.sql.Types.DOUBLE);
            }
            st.setObject(index++, quantity);
            st.setObject(index++, Timestamp.valueOf(startDate));
            st.setObject(index++, Timestamp.valueOf(endDate));
            st.setObject(index++, active);
            st.setObject(index++, voucherId);

            st.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @author dotha
     * @param voucherCode mã code của voucher
     * @return id của voucher nếu voucher dùng được, -1 nếu không dùng được
     */
    public int checkVoucherByVoucherCode(String voucherCode) {
        String sql = "                     SELECT voucher_id FROM vouchers\n" +
"                     WHERE quantity - used_count > 0\n" +
"                     AND GETDATE() BETWEEN start_date AND end_date\n" +
"                     AND is_active = 1 AND CODE = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, voucherCode);
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

    // test feature
    public static void main(String[] args) {
        VoucherDAO voucherDAO = new VoucherDAO();
        System.out.println(voucherDAO.addVoucher("HAPPYNEWYEAR2027", "percent", 300000, 20000, 30, 20, LocalDateTime.now(), LocalDateTime.parse("2026-12-12 12:12:12.000", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), 1));
    }

    public boolean addVoucherUsage(int voucherId, int userID, int orderId) {
        String sql = "                     INSERT INTO [dbo].[voucher_usages]\n" +
"                                ([voucher_id]\n" +
"                                ,[user_id]\n" +
"                                ,[order_id])\n" +
"                          VALUES\n" +
"                                (?\n" +
"                                ,?\n" +
"                                ,?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int index = 1;
            st.setObject(index++, voucherId);
            st.setObject(index++, userID);
            st.setObject(index++, orderId);
            int row = st.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lấy thông tin voucher đã được áp dụng cho một đơn hàng cụ thể
     * @param orderId id của đơn hàng
     * @return voucher object nếu tồn tại, null nếu không
     */
    public Voucher getVoucherByOrderId(int orderId) {
        String sql = "SELECT v.* FROM vouchers v JOIN voucher_usages vu ON v.voucher_id = vu.voucher_id WHERE vu.order_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, orderId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int voucherId = rs.getInt(1);
                    String code = rs.getString(2);
                    String discountType = rs.getString(3);
                    double discountValue = rs.getDouble(4);
                    double minOrderValue = rs.getDouble(5);
                    double maxDiscount = rs.getDouble(6);
                    int quantity = rs.getInt(7);
                    int uesedCount = rs.getInt(8);
                    java.time.LocalDateTime startDate = rs.getTimestamp(9) != null ? rs.getTimestamp(9).toLocalDateTime() : null;
                    java.time.LocalDateTime endDate = rs.getTimestamp(10) != null ? rs.getTimestamp(10).toLocalDateTime() : null;
                    boolean isActive = rs.getBoolean(11);
                    java.time.LocalDateTime createAt = rs.getTimestamp(12) != null ? rs.getTimestamp(12).toLocalDateTime() : null;
                    return new Voucher(voucherId, code, discountType, discountValue, minOrderValue, maxDiscount, quantity, uesedCount, startDate, endDate, isActive, createAt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author dotha
     * @param voucherId voucher id
     * @param userId user id
     * @return kiểm tra xem voucher đã được user đó dùng lần nào chưa, nếu rồi
     * trả về true, chưa thì false
     */
    public boolean getVoucherUsageByVoucherIdAndUseId(int voucherId, int userId) {
        String sql = "                     select 1 from voucher_usages\n" +
"                     where voucher_id = ? and user_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int index = 1;
            st.setInt(index++, voucherId);
            st.setInt(index++, userId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @author dotha
     * @param voucherId voucher id
     * @return true nếu update used count thành công false nếu ngược lại
     */
    public boolean updateUsedCountByVoucherId(int voucherId) {
        String sql = "                     UPDATE [dbo].[vouchers]\n" +
"                        SET [used_count] = [used_count] + 1\n" +
"                      WHERE voucher_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, voucherId);
            int row = st.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @author dotha
     * @return list voucher được đề xuất cho người dùng mới
     */
    public List<Voucher> getVoucherForNewUser(User user, Customer customer) {
        List<Voucher> listVouchers = new ArrayList<>();
        String sql = "                     SELECT * FROM vouchers V\n" +
"                     LEFT JOIN voucher_usages VU\n" +
"                     ON V.voucher_id = VU.voucher_id AND VU.user_id = ?\n" +
"                     WHERE (code LIKE '%NEW%' OR code LIKE ?)\n" +
"                     AND is_active = 1 \n" +
"                     AND quantity - used_count > 0\n" +
"                     AND GETDATE() BETWEEN start_date AND end_date\n" +
"                     AND VU.voucher_id IS NULL";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, user.getUserId());
            st.setString(2, "%" + customer.getTier() + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int voucherId = rs.getInt(1);
                    String code = rs.getString(2);
                    String discountType = rs.getString(3);
                    double discountValue = rs.getDouble(4);
                    double minOrderValue = rs.getDouble(5);
                    double maxDiscount = rs.getDouble(6);
                    int quantity = rs.getInt(7);
                    int uesedCount = rs.getInt(8);
                    LocalDateTime startDate = rs.getTimestamp(9).toLocalDateTime();
                    LocalDateTime endDate = rs.getTimestamp(10).toLocalDateTime();
                    boolean isActive = rs.getBoolean(11);
                    LocalDateTime createAt = rs.getTimestamp(12).toLocalDateTime();
                    Voucher voucher = new Voucher(voucherId, code, discountType, discountValue, minOrderValue, maxDiscount, quantity, uesedCount, startDate, endDate, isActive, createAt);
                    listVouchers.add(voucher);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listVouchers;
    }
    
    /**
     * 
     * @param user lấy user id
     * @return số lượng voucher user đó có và có thể sử dụng
     */
     public int getNumberVoucherForNewUser(User user, Customer customer) {
        String sql = "                     SELECT COUNT(*) FROM vouchers V\n" +
"                     LEFT JOIN voucher_usages VU\n" +
"                     ON V.voucher_id = VU.voucher_id AND VU.user_id = ?\n" +
"                     WHERE (code LIKE '%NEW%' OR code LIKE ?)\n" +
"                     AND is_active = 1 \n" +
"                     AND quantity - used_count > 0\n" +
"                     AND GETDATE() BETWEEN start_date AND end_date\n" +
"                     AND VU.voucher_id IS NULL";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, user.getUserId());
            st.setString(2, "%" + customer.getTier() + "%");
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
