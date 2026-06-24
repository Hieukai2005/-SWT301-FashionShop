package dal;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Category;

public class CategoryDAO extends DBContext {

    /**
     * HoaNK - lay 1 category bang id
     */
    public Category getCategoryById(int id) {
        String query = "SELECT * FROM categories WHERE category_id = ?";
        try (PreparedStatement ps = connection.prepareCall(query)) {
            int index = 1;
            ps.setInt(index++, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category(rs.getInt("category_id"),
                            rs.getString("category_name"));
                    return category;
                }
            }
        } catch (SQLException s) {
            System.out.println(s);
        }
        return null;
    }

    /**
     * HoaNK - Lay tat ca category
     */
    public List<Category> getAllCategory() {
        String query = "SELECT * FROM categories";
        List<Category> list = new ArrayList();

        try (PreparedStatement ps = connection.prepareCall(query)) {
            int index = 1;
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category(rs.getInt("category_id"),
                            rs.getString("category_name"));
                    list.add(category);
                }
            }
        } catch (SQLException s) {
            System.out.println(s);
        }
        return list;
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT\n" +
"                c.category_id,\n" +
"                c.category_name,\n" +
"                COUNT(DISTINCT p.product_id) AS total_products,\n" +
"                SUM(CASE WHEN p.is_active = 1 THEN 1 ELSE 0 END) AS active_products,\n" +
"                SUM(CASE WHEN p.is_active = 0 THEN 1 ELSE 0 END) AS inactive_products,\n" +
"                COALESCE(SUM(oi.quantity * oi.price), 0) AS total_revenue\n" +
"            FROM categories c\n" +
"            LEFT JOIN products p ON c.category_id = p.category_id\n" +
"            LEFT JOIN product_variants pv ON p.product_id = pv.product_id\n" +
"            LEFT JOIN order_items oi ON pv.variant_id = oi.variant_id\n" +
"            LEFT JOIN orders o ON oi.order_id = o.order_id\n" +
"                AND o.status = N'Hoàn thành'\n" +
"            GROUP BY c.category_id, c.category_name\n" +
"            ORDER BY c.category_id";
        try (PreparedStatement ps = connection.prepareStatement(sql); // dùng connection từ DBContext
                 ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category cat = new Category();
                cat.setCategoryId(rs.getInt("category_id"));
                cat.setCategoryName(rs.getString("category_name"));
                cat.setTotalProducts(rs.getInt("total_products"));
                cat.setActiveProducts(rs.getInt("active_products"));
                cat.setInactiveProducts(rs.getInt("inactive_products"));
                cat.setTotalRevenue(rs.getDouble("total_revenue"));
                list.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Category> searchCategories(String keyword) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT\n" +
"                c.category_id,\n" +
"                c.category_name,\n" +
"                COUNT(DISTINCT p.product_id) AS total_products,\n" +
"                SUM(CASE WHEN p.is_active = 1 THEN 1 ELSE 0 END) AS active_products,\n" +
"                SUM(CASE WHEN p.is_active = 0 THEN 1 ELSE 0 END) AS inactive_products,\n" +
"                COALESCE(SUM(oi.quantity * oi.price), 0) AS total_revenue\n" +
"            FROM categories c\n" +
"            LEFT JOIN products p ON c.category_id = p.category_id\n" +
"            LEFT JOIN product_variants pv ON p.product_id = pv.product_id\n" +
"            LEFT JOIN order_items oi ON pv.variant_id = oi.variant_id\n" +
"            LEFT JOIN orders o ON oi.order_id = o.order_id\n" +
"                AND o.status = N'Hoàn thành'\n" +
"            WHERE c.category_name LIKE ?\n" +
"            GROUP BY c.category_id, c.category_name\n" +
"            ORDER BY c.category_id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category cat = new Category();
                cat.setCategoryId(rs.getInt("category_id"));
                cat.setCategoryName(rs.getString("category_name"));
                cat.setTotalProducts(rs.getInt("total_products"));
                cat.setActiveProducts(rs.getInt("active_products"));
                cat.setInactiveProducts(rs.getInt("inactive_products"));
                cat.setTotalRevenue(rs.getDouble("total_revenue"));
                list.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addCategory(String categoryName) {
        String sql = "INSERT INTO categories (category_name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoryName.trim());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCategory(int categoryId, String categoryName) {
        String sql = "UPDATE categories SET category_name = ? WHERE category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoryName.trim());
            ps.setInt(2, categoryId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategory(int categoryId) {
        if (categoryHasProducts(categoryId)) {
            return false;
        }
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isCategoryNameExists(String name, int excludeId) {
        String sql = "SELECT COUNT(*) FROM categories WHERE category_name = ? AND category_id != ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean categoryHasProducts(int categoryId) {
        String sql = "SELECT COUNT(*) FROM products WHERE category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // test features
    public static void main(String[] args) {
        System.out.println(new CategoryDAO().getCategoryById(1));
    }
}
