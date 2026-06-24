package dal;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.Cart;
import model.CartItem;
import model.CartItemView;
import model.ProductVariant;
import model.User;
import java.sql.Connection;

/**
 * @author: dotha
 * @date: 12/2/2026 lấy giỏ hàng theo id người dùng được set trong session, thêm
 *        vào giỏ hàng,....
 */
public class CartDAO extends DBContext {

    private Connection connectionTransaction;

    public CartDAO(Connection connectionTransaction) {
        this.connectionTransaction = connectionTransaction;
    }

    public CartDAO() {
    }

    // SQL liên quan giỏ hàng,...
    // Lấy Cart bằng đối tượng User, khi lấy xong thì lấy chính tham số user đó làm
    // đối số cho constructor của class Cart
    public Cart getCartByUserId(int userId) {
        String query = "SELECT * FROM carts WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int cartId = rs.getInt(1);
                    Timestamp createdAt = rs.getTimestamp(3);
                    return new Cart(cartId, userId, createdAt.toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error at: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author dotha
     * @param userId id người dùng khi đã dăngd nhập
     * @return id của cart khi cart được chèn thành công, -1 nếu chèn không
     *         thành công
     */
    public int createCartForUserId(int userId) {
        String query = "INSERT INTO [dbo].[carts]\n"
                + "           ([user_id])   \n"
                + "       VALUES\n"
                + "           (?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1, userId);
            int row = statement.executeUpdate();
            if (row > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    try {
                        return rs.getInt(1);
                    } catch (SQLException ex) {
                        try {
                            return rs.getBigDecimal(1).intValue();
                        } catch (Exception ex2) {
                            return getCartIdByUserId(userId);
                        }
                    }
                } else {
                    return getCartIdByUserId(userId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error at: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    // Lấy các Cart Item bằng Cart Id
    public List<CartItem> getCartItemByCartId(int cartId) {
        String query = "SELECT * FROM cart_items WHERE cart_id = ?";
        List<CartItem> listCartItems = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, cartId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    int cartItemId = rs.getInt(1);
                    int variantId = rs.getInt(3);
                    int quantity = rs.getInt(4);
                    Timestamp createdAt = rs.getTimestamp(5);
                    CartItem cartItem = new CartItem(cartItemId, cartId, variantId, quantity,
                            createdAt.toLocalDateTime());
                    listCartItems.add(cartItem);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error at: " + e.getMessage());
            e.printStackTrace();
        }
        return listCartItems;
    }

    /**
     * @author dotha
     * @param cartId    id của cart
     * @param variantId id của variant
     * @param quantity  số lượng của đơn hàng
     * @return id cart item nếu chèn thành công, -1 nếu chèn thất bại
     */
    public int createCartItemByProductVariant(int cartId, int variantId, int quantity) {
        String sql = "INSERT INTO [dbo].[cart_items]\n" +
"                           ([cart_id]\n" +
"                           ,[variant_id]\n" +
"                           ,[quantity])\n" +
"                     VALUES\n" +
"                           (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, cartId);
            statement.setInt(2, variantId);
            statement.setInt(3, quantity);
            int row = statement.executeUpdate();
            if (row > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        // trả về id vừa chèn của cột identity trong table cart_items
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error at: " + e.getMessage());
            e.printStackTrace();
        }
        // không chèn được thì trả về -1
        return -1;
    }

    /**
     * @author dotha
     * @param cartId    id của cart
     * @param variantId id của biến thể sản phẩm đã chọn
     * @param quantity  số lượng sản phẩm đã chọn
     * @return true nếu add thành công, false nếu không thành công Hàm này cũng
     *         sẽ kiểm tra việc nếu cùng cart id và trùng variant id thì sẽ cập nhật
     *         số
     *         lượng cho đơn hàng tương ứng trong giỏ hàng Nếu không trùng thì sẽ
     *         tạo
     *         một cart item mới
     */
    public int addToCart(int cartId, int variantId, int quantity) {
        String checkSql = "SELECT quantity FROM cart_items WHERE cart_id = ? AND variant_id = ?";
        try (PreparedStatement checkSt = connection.prepareStatement(checkSql)) {
            checkSt.setInt(1, cartId);
            checkSt.setInt(2, variantId);
            ResultSet rs = checkSt.executeQuery();
            if (rs.next()) {
                // đã tồn tại thì update
                int oldQuantity = rs.getInt("quantity");
                String updateSql = "UPDATE cart_items SET quantity = ? WHERE cart_id = ? AND variant_id = ?";
                try (PreparedStatement updateSt = connection.prepareStatement(updateSql)) {
                    updateSt.setInt(1, oldQuantity + quantity);
                    updateSt.setInt(2, cartId);
                    updateSt.setInt(3, variantId);
                    updateSt.executeUpdate();
                    return 1;
                }
            } else {
                // chưa tồn tại thì insert
                int addResult = createCartItemByProductVariant(cartId, variantId, quantity);
                if (addResult == -1) {
                    // Xảy ra lỗi (có thể do race condition, Duplicate Unique Key Constraint)
                    // Một luồng khác đã chèn variant này trước, ta sẽ thử update thay vì chèn
                    String updateSql = "UPDATE cart_items SET quantity = quantity + ? WHERE cart_id = ? AND variant_id = ?";
                    try (PreparedStatement updateSt = connection.prepareStatement(updateSql)) {
                        updateSt.setInt(1, quantity);
                        updateSt.setInt(2, cartId);
                        updateSt.setInt(3, variantId);
                        updateSt.executeUpdate();
                        return 1;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error at: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
        return 2;
    }

    /**
     * @author dotha
     * @param userId id của người dùng
     * @return cart id bằng user id
     */
    public int getCartIdByUserId(int userId) {
        String sql = "SELECT cart_id FROM carts WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @author dotha
     * @param cartId id của cart
     * @return list các CartItemView, obj CartItemView lưu các trường liên quan
     *         đến sản phẩm trong phần giỏ hàng mỗi người dùng sẽ chỉ có 1 cart id
     *         riêng
     *         biệt nên việc truy xuất các sản phẩm chỉ cần cart Id và join bảng
     */
    public List<CartItemView> getCartItemViewsByCartId(int cartId) {
        List<CartItemView> listCartItemViews = new ArrayList<>();
        String sql = "SELECT CI.cart_item_id, PV.image, P.gender, P.product_name, P.price, P.discount, P.product_id, C.color_name, S.size_name, ci.quantity, c.color_id, s.size_id FROM cart_items CI\n" +
"                JOIN product_variants PV ON CI.variant_id = PV.variant_id\n" +
"                JOIN products P ON P.product_id = PV.product_id\n" +
"                JOIN sizes S ON S.size_id = PV.size_id\n" +
"                JOIN colors C ON C.color_id = PV.color_id\n" +
"                WHERE CI.cart_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cartId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    int cartItemId = rs.getInt(1);
                    String image = rs.getString(2);
                    String gender = rs.getString(3);
                    String productName = rs.getString(4);
                    double price = rs.getDouble(5);
                    int discount = rs.getInt(6);
                    int productId = rs.getInt(7);
                    String color = rs.getString(8);
                    String size = rs.getString(9);
                    int quantity = rs.getInt(10);
                    int colorId = rs.getInt(11);
                    int sizeId = rs.getInt(12);
                    CartItemView cartItemView = new CartItemView(cartItemId, image, gender, productName, price,
                            discount, productId, color, size, quantity, colorId, sizeId);
                    listCartItemViews.add(cartItemView);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCartItemViews;
    }

    /**
     * @author dotha
     * @param userId id của người dùng
     * @return số lượng đon hàng được người dùng thêm vào giỏ
     */
    public int getNumberCartByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM carts C JOIN cart_items CI ON CI.cart_id = C.cart_id WHERE C.user_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @author dotha
     * @param cartItemId id của cart Item
     * @return true nếu xóa thành công cart item bằng cart item id false nếu xóa
     *         không thành công
     */
    public boolean deleteCartItemByCartItemId(int cartItemId) {
        String sql = "DELETE FROM [dbo].[cart_items] WHERE cart_item_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, cartItemId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @author dotha
     * @param cartItemId id của cart item
     * @return true nếu việc tăng quantity của sản phẩm trong giỏ hàng thành
     *         công lên 1 số lượng false nếu tăng thất bại
     */
    public boolean descQuantityByCartItemId(int cartItemId) {
        String sql = "UPDATE [dbo].[cart_items]\n" +
"                   SET\n" +
"                      [quantity] = quantity - 1\n" +
"                WHERE cart_item_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, cartItemId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @author dotha
     * @param cartItemId id của cart item
     * @return true nếu việc giảm quantity của sản phẩm trong giỏ hàng thành
     *         công xuống 1 số lượng false nếu giảm thất bại
     */
    public boolean ascQuantityByCartItemId(int cartItemId) {
        String sql = "UPDATE [dbo].[cart_items]\n" +
"                   SET\n" +
"                      [quantity] = quantity + 1\n" +
"                WHERE cart_item_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, cartItemId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateQuantityByCartItemId(int cartItemId, int quantity) {
        if (quantity <= 0) {
            return deleteCartItemByCartItemId(cartItemId);
        }
        String sql = "UPDATE [dbo].[cart_items]\n" +
"                   SET\n" +
"                      [quantity] = ?\n" +
"                WHERE cart_item_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, quantity);
            st.setInt(2, cartItemId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // test features
    public static void main(String[] args) {
        new CartDAO().ascQuantityByCartItemId(4);
    }

}
