package dal;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import java.sql.Timestamp;
import java.time.LocalDate;
import model.Category;
import model.Color;
import model.DashBoard;
import model.OrderItem;
import model.ProductImage;
import model.ProductVariant;
import model.Size;

public class ProductDAO extends DBContext {

    private CategoryDAO categoryDao = new CategoryDAO();

    /**
     * HoaNK - lay 1 product bang id
     */
    public Product getProductById(int id) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareCall(query)) {
            int index = 1;
            ps.setInt(index++, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp time = rs.getTimestamp("created_at");
                    LocalDateTime date = (time != null) ? time.toLocalDateTime() : null;
                    Product product = new Product(rs.getInt("product_id"), rs.getString("product_name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("category_id"), rs.getString("gender"), rs.getBoolean("is_sale"),
                            rs.getBoolean("is_active"), rs.getInt("discount"), date);
                    return product;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HoaNK - lay 1 size bang id
     */
    public Size getSizeById(int sid) {
        String query = "SELECT * FROM sizes WHERE size_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Size size = new Size(rs.getInt("size_id"), rs.getString("size_name"));
                    return size;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * HoaNK - lay 1 size bang id
     */
    public Color getColorById(int coid) {
        String query = "SELECT * FROM colors WHERE color_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, coid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Color color = new Color(rs.getInt("color_id"), rs.getString("color_name"));
                    return color;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * HoaNK - Loc danh sach 8 san pham moi nhat tu csdl
     */
    public List<Product> get8NewestProducts() {
        String query = "SELECT TOP 8 * FROM products \n"
                + "WHERE product_id IN (\n"
                + "    SELECT DISTINCT product_id \n"
                + "    FROM product_variants \n"
                + "    WHERE quantity > 0\n"
                + ")\n"
                + "ORDER BY created_at DESC";
        List<Product> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareCall(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category category = categoryDao.getCategoryById(rs.getInt("category_id"));
                    Timestamp time = rs.getTimestamp("created_at");
                    LocalDateTime date = (time != null) ? time.toLocalDateTime() : null;
                    Product product = new Product(rs.getInt("product_id"), rs.getString("product_name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("category_id"), rs.getString("gender"), rs.getBoolean("is_sale"),
                            rs.getBoolean("is_active"), rs.getInt("discount"), date);
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    /**
     * HoaNK - Loc danh sach 5 cac san pham dang co flash - sale
     */
    public List<Product> get5FlashSaleProducts() {
        String query = "SELECT TOP 5 *\n"
                + "FROM products\n"
                + "WHERE product_id IN (\n"
                + "    SELECT DISTINCT product_id\n"
                + "    FROM product_variants\n"
                + "    WHERE quantity > 0\n"
                + ")\n"
                + "ORDER BY created_at DESC;";
        List<Product> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareCall(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp time = rs.getTimestamp("created_at");
                    LocalDateTime date = (time != null) ? time.toLocalDateTime() : null;
                    Product product = new Product(rs.getInt("product_id"), rs.getString("product_name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("category_id"), rs.getString("gender"), rs.getBoolean("is_sale"),
                            rs.getBoolean("is_active"), rs.getInt("discount"), date);
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    /**
     * HoaNK - Loc tat ca san pham danh cho nam hoặc nữ
     */
    public List<Product> getAllProductsByGender(String gender) {
        String query = "SELECT * FROM products \n"
                + "    WHERE gender = ? ";
//        query += " ORDER BY product_id ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        List<Product> list = new ArrayList();

        try (PreparedStatement ps = connection.prepareCall(query)) {
            int index = 1;
            ps.setString(index++, gender);
//            ps.setInt(index++, (page - 1) * numberPerPage);
//            ps.setInt(index++,numberPerPage);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp time = rs.getTimestamp("created_at");
                    LocalDateTime date = (time != null) ? time.toLocalDateTime() : null;
                    Product product = new Product(rs.getInt("product_id"), rs.getString("product_name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("category_id"), rs.getString("gender"), rs.getBoolean("is_sale"),
                            rs.getBoolean("is_active"), rs.getInt("discount"), date);
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    /**
     * LinhNH - Phan trang cho trang san pham
     */
    public List<Product> splitPagingProducts(int page, int numberPerPage, String gender, int cid, int discount, int price, String sort, String textSearch) {
        String query = "SELECT p.*,c.category_name FROM products p\n"
                + "JOIN categories c ON p.category_id = c.category_id\n"
                + "WHERE p.is_active = 1\n"
                + this.addConditionPagingUser(gender, cid, discount, price, textSearch)
                + this.orderCondition(sort)
                + " OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY";

        List<Product> list = new ArrayList();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            if (textSearch != null && !textSearch.isEmpty()) {
                ps.setString(index++, "%" + textSearch + "%");
                ps.setString(index++, "%" + textSearch + "%");
                ps.setString(index++, "%" + textSearch + "%");
            }
            ps.setInt(index++, (page - 1) * numberPerPage);
            ps.setInt(index++, numberPerPage);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp time = rs.getTimestamp("created_at");
                    LocalDateTime date = (time != null) ? time.toLocalDateTime() : null;
                    Product product = new Product(rs.getInt("product_id"), rs.getString("product_name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("category_id"), rs.getString("gender"), rs.getBoolean("is_sale"),
                            rs.getBoolean("is_active"), rs.getInt("discount"), date);
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error at product DAO");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * LinhNH - Dem san pham cua trang san pham nguoi dung
     */
    public int getNumberProductUser(String gender, int cid, int discount, int price, String textSearch) {
        String query = "SELECT COUNT(*) FROM products p "
                + "JOIN categories c ON p.category_id = c.category_id "
                + "WHERE p.is_active = 1 "
                + this.addConditionPagingUser(gender, cid, discount, price, textSearch);

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            if (textSearch != null && !textSearch.isEmpty()) {
                ps.setString(index++, "%" + textSearch + "%");
                ps.setString(index++, "%" + textSearch + "%");
                ps.setString(index++, "%" + textSearch + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error at product DAO");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * LinhLH - kiem tra va noi dieu kien cho query
     */
    private String addConditionPagingUser(String gender, int cid, int discountRange, int priceRange, String textSearch) {
        String query = "";
        if (gender != null && !gender.isEmpty()) {
            query += " AND p.gender = " + "N'" + gender + "'";
        }
        // category
        if (cid > 0) {
            query += " AND p.category_id = " + cid;
        }
        // discount 
        if (discountRange == 1) {
            query += " AND p.discount BETWEEN 1 AND 19";
        } else if (discountRange == 2) {
            query += " AND p.discount BETWEEN 20 AND 29";
        } else if (discountRange == 3) {
            query += " AND p.discount BETWEEN 30 AND 39";
        } else if (discountRange == 4) {
            query += " AND p.discount BETWEEN 40 AND 49";
        } else if (discountRange == 5) {
            query += " AND p.discount >= 50";
        }
        // price 
        String actualPrice = "(CASE WHEN p.is_sale = 1 AND p.discount > 0 "
                + "THEN (p.price * (100 - p.discount) / 100) "
                + "ELSE p.price END)";

        if (priceRange == 1) {
            query += " AND " + actualPrice + " BETWEEN 1 AND 199999";
        } else if (priceRange == 2) {
            query += " AND " + actualPrice + " BETWEEN 200000 AND 499999";
        } else if (priceRange == 3) {
            query += " AND " + actualPrice + " BETWEEN 500000 AND 999999";
        } else if (priceRange == 4) {
            query += " AND " + actualPrice + " >= 1000000";
        }
        // search
        if (textSearch != null && !textSearch.isEmpty()) {
            query += " AND (p.product_name LIKE ? OR p.description LIKE ? OR c.category_name LIKE ?)";
        }
        //
        return query;
    }

    private String orderCondition(String sort) {
        String orderQuery = " ORDER BY p.product_id";
        if ("lowToHigh".equals(sort)) {
            orderQuery = " ORDER BY p.price";
        } else if ("highToLow".equals(sort)) {
            orderQuery = " ORDER BY p.price DESC";
        } else if ("newest".equals(sort)) {
            orderQuery = " ORDER BY p.created_at DESC";
        }
        return orderQuery;
    }

    // ADMIN
    /**
     * HoaNK - Lay ra so don hang thang
     */
    public DashBoard getDashboard(LocalDate date) {
        int month = date.getMonthValue();

        int year = date.getYear();
        DashBoard dashBoard = new DashBoard();
        String query = "SELECT "
                + "(SELECT COUNT(*) FROM orders WHERE MONTH(order_date) = ? AND YEAR(order_date) = ?) AS totalOrders, "
                + "(SELECT SUM(total_amount) FROM orders WHERE MONTH(order_date) = ? AND YEAR(order_date) = ? AND status = N'Hoàn thành') AS totalAmount  , "
                + "(SELECT COUNT(*) FROM products WHERE MONTH(created_at) = ? AND YEAR(created_at) = ?) AS newProducts, "
                + "(SELECT COUNT(*) FROM users WHERE MONTH(created_at) = ? AND YEAR(created_at) = ?) AS newUsers";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (int index = 1; index <= 8; index += 2) {
                ps.setInt(index, month);
                ps.setInt(index + 1, year);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dashBoard.setTotalOrders(rs.getInt("totalOrders"));
                    dashBoard.setTotalAmount(rs.getDouble("totalAmount"));
                    dashBoard.setNewProducts(rs.getInt("newProducts"));
                    dashBoard.setNewUsers(rs.getInt("newUsers"));
                    return dashBoard;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * HoaNK - Dem so san pham co trong shop, tim kiem, loc cate
     */
    public int getNumberOfProduct(String text, int categoryId, String statusSale, String statusActive) {
        String query = "SELECT COUNT(*) FROM products p"
                + " JOIN categories c ON p.category_id = c.category_id"
                + " WHERE 1=1 "
                + this.addConditionPagingAdmin(text, categoryId, statusSale, statusActive);

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            if (text != null && text.equals("") == false) {
                ps.setString(index++, "%" + text + "%");
                ps.setString(index++, "%" + text + "%");
                ps.setString(index++, "%" + text + "%");
            }
            if (categoryId > 0) {
                ps.setInt(index++, categoryId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    /**
     * HoaNK - Phan trang san pham hien thi, tim kiem, loc cate trang admin
     */
    public List<Product> splitPaging(int page, int numberPerPage, String text,
            int categoryId, String statusSale, String statusActive) {
        String query = "SELECT p.product_id,p.product_name,p.price,p.description,p.image,"
                + "p.category_id,p.is_sale,p.is_active,p.discount, SUM(pv.quantity) as total_stock\n"
                + "FROM products p \n"
                + "LEFT JOIN product_variants pv ON p.product_id = pv.product_id\n"
                + "JOIN categories c ON p.category_id = c.category_id\n"
                + "WHERE 1=1 "
                + this.addConditionPagingAdmin(text, categoryId, statusSale, statusActive)
                + " GROUP BY p.product_id,p.product_name,p.price,p.description,p.image,p.category_id,p.is_sale,p.is_active,p.discount\n"
                + "ORDER BY p.product_id\n"
                + "OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY";
        List<Product> list = new ArrayList();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            if (text != null && text.equals("") == false) {
                ps.setString(index++, "%" + text + "%");
                ps.setString(index++, "%" + text + "%");
                ps.setString(index++, "%" + text + "%");
            }
            if (categoryId > 0) {
                ps.setInt(index++, categoryId);
            }

            ps.setInt(index++, (page - 1) * numberPerPage);
            ps.setInt(index++, numberPerPage);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category category = categoryDao.getCategoryById(rs.getInt("category_id"));
                    Product product = new Product(rs.getInt("product_id"), rs.getString("product_name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("category_id"), null, rs.getBoolean("is_sale"),
                            rs.getBoolean("is_active"), rs.getInt("discount"), null);
                    product.setCategory(category);
                    product.setTotalStock(rs.getInt("total_stock"));
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    // nối điều kiện
    private String addConditionPagingAdmin(String text, int categoryId, String statusSale, String statusActive) {
        String query = "";
        if (text != null && text.equals("") == false) {
            query += " AND (p.product_name LIKE ? OR p.description LIKE ? OR c.category_name LIKE ?) ";
        }
        if (categoryId > 0) {
            query += " AND (c.category_id = ?)";
        }
        if (statusSale != null && statusSale.equals("") == false) {
            if ("on".equals(statusSale)) {
                query += " AND is_sale = 1";
            } else if ("off".equals(statusSale)) {
                query += " AND is_sale = 0";
            } else if ("under30".equals(statusSale)) {
                query += " AND (is_sale = 1 AND discount < 30)";
            } else if ("30to50".equals(statusSale)) {
                query += " AND (is_sale = 1 AND discount BETWEEN 30 AND 50)";
            } else if ("over50".equals(statusSale)) {
                query += " AND (is_sale = 1 AND discount > 50)";
            }
        }
        if (statusActive != null && statusActive.equals("") == false) {
            if ("on".equals(statusActive)) {
                query += " AND is_active = 1";
            } else if ("off".equals(statusActive)) {
                query += " AND is_active = 0";
            }
        }
        return query;
    }

    public List<ProductVariant> getVariantsByProductId(int productId) {
        String sql = "            SELECT variant_id, product_id, size_id, color_id, quantity, image, sold_quantity\n" +
"            FROM product_variants\n" +
"            WHERE product_id = ?";

        List<ProductVariant> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Size size = this.getSizeById(rs.getInt("size_id"));
                    Color color = this.getColorById(rs.getInt("color_id"));
                    ProductVariant v = new ProductVariant();
                    v.setVariantId(rs.getInt("variant_id"));
                    v.setProductId(rs.getInt("product_id"));
                    v.setSizeId(rs.getInt("size_id"));
                    v.setColorId(rs.getInt("color_id"));
                    v.setQuantity(rs.getInt("quantity"));
                    v.setImage(rs.getString("image"));
                    v.setSold_quantity(rs.getInt("sold_quantity"));
                    v.setSize(size);
                    v.setColor(color);
                    list.add(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // bạn có thể log tốt hơn
        }
        return list;
    }

    // B) Lấy danh sách Color theo productId (distinct)
    public List<Color> getColorsByProductId(int productId) {
        String sql = "            SELECT DISTINCT c.color_id, c.color_name\n" +
"            FROM product_variants v\n" +
"            JOIN colors c ON v.color_id = c.color_id\n" +
"            WHERE v.product_id = ?\n" +
"            ORDER BY c.color_name";

        List<Color> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Color c = new Color();
                    c.setColorId(rs.getInt("color_id"));
                    c.setColorName(rs.getString("color_name"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // C) Lấy danh sách Size theo productId (distinct)
    public List<Size> getSizesByProductId(int productId) {
        String sql = "            SELECT DISTINCT s.size_id, s.size_name\n" +
"            FROM product_variants v\n" +
"            JOIN sizes s ON v.size_id = s.size_id\n" +
"            WHERE v.product_id = ?\n" +
"            ORDER BY s.size_name";

        List<Size> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Size s = new Size();
                    s.setSizeId(rs.getInt("size_id"));
                    s.setSizeName(rs.getString("size_name"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // D) Lấy variants theo productId + colorId (để render size theo màu)
    public List<ProductVariant> getVariantsByProductAndColor(int productId, int colorId) {
        String sql = "            SELECT variant_id, product_id, size_id, color_id, quantity, image, sold_quantity\n" +
"            FROM product_variants\n" +
"            WHERE product_id = ? AND color_id = ?\n" +
"            ORDER BY size_id";

        List<ProductVariant> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ps.setInt(2, colorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductVariant v = new ProductVariant();
                    v.setVariantId(rs.getInt("variant_id"));
                    v.setProductId(rs.getInt("product_id"));
                    v.setSizeId(rs.getInt("size_id"));
                    v.setColorId(rs.getInt("color_id"));
                    v.setQuantity(rs.getInt("quantity"));
                    v.setImage(rs.getString("image"));
                    v.setSold_quantity(rs.getInt("sold_quantity"));
                    list.add(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // E) Lấy 1 variant cụ thể (dùng khi Add to cart theo variantId)
    public ProductVariant getVariantById(int variantId) {
        String sql = "            SELECT variant_id, product_id, size_id, color_id, quantity, image, sold_quantity\n" +
"            FROM product_variants\n" +
"            WHERE variant_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, variantId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ProductVariant v = new ProductVariant();
                    v.setVariantId(rs.getInt("variant_id"));
                    v.setProductId(rs.getInt("product_id"));
                    v.setSizeId(rs.getInt("size_id"));
                    v.setColorId(rs.getInt("color_id"));
                    v.setQuantity(rs.getInt("quantity"));
                    v.setImage(rs.getString("image"));
                    v.setSold_quantity(rs.getInt("sold_quantity"));
                    v.setColorName(this.getColorNameByColorId(rs.getInt("color_id")));
                    v.setProductName(this.getProductNameByProductId(rs.getInt("product_id")));
                    v.setSizeName(this.getSizeNameBySizeId(rs.getInt("size_id")));
                    return v;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getColorNameByColorId(int colorId) {
        String sql = "                     select color_name from colors where color_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, colorId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getQtyByProductColorSize(int productId, int colorId, int sizeId) {
        String sql = "            SELECT quantity\n" +
"            FROM product_variants\n" +
"            WHERE product_id = ? AND color_id = ? AND size_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setInt(2, colorId);
            ps.setInt(3, sizeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // không có variant => null
    }

    /**
     * @HoaNK - Lay tat ca size cua san pham
     */
    public List<Size> getAllSize() {
        String query = "SELECT * FROM sizes";
        //
        List<Size> list = new ArrayList();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Size size = new Size(rs.getInt("size_id"), rs.getString("size_name"));
                    list.add(size);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    /**
     * @HoaNK - Lay tat ca color cua san pham
     */
    public List<Color> getAllColor() {
        String query = "SELECT * FROM colors";
        //
        List<Color> list = new ArrayList();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Color color = new Color(rs.getInt("color_id"), rs.getString("color_name"));
                    list.add(color);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    /**
     * HoaNK - Them san pham o trang admin va lay key cua san pham vua them
     */
    public int addProductAdmin(String name, double price, String description,
            String image, int categoryId, String gender, int sale, int active, int discount)
            throws SQLException {
        String query = "INSERT INTO products (product_name, price, description, image, category_id, gender, is_sale, is_active,discount) "
                + " VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            ps.setString(index++, name);
            ps.setDouble(index++, price);
            ps.setString(index++, description);
            ps.setString(index++, image);
            ps.setInt(index++, categoryId);
            ps.setString(index++, gender);
            ps.setInt(index++, sale);
            ps.setInt(index++, active);
            ps.setInt(index++, discount);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { // lấy key vừa tạo ra
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * HoaNK - Them chi tiet san pham vao bang productvariant
     */
    public void addProductVariantAdmin(int productId, int size, int color, int quantity, String image)
            throws SQLException {
        String query = "INSERT INTO product_variants (product_id,size_id,color_id,quantity,image)"
                + " VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            ps.setInt(index++, productId);
            ps.setInt(index++, size);
            ps.setInt(index++, color);
            ps.setInt(index++, quantity);
            ps.setString(index++, image);
            ps.executeUpdate();
        }
    }

    /**
     * HoaNK - xoa 1 san pham bang id
     */
    public boolean deleteProductAdmin(int pid) {
        String query = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            ps.setInt(index++, pid);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * HoaNK-Cap nhat thong tin chung san pham
     */
    public boolean updateProductInfo(Product product) {
        String query = "UPDATE [dbo].[products]\n"
                + "   SET [product_name] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[image] = ?\n"
                + "      ,[category_id] = ?\n"
                + "      ,[gender] = ?\n"
                + "      ,[is_sale] = ?\n"
                + "      ,[is_active] = ?\n"
                + "      ,[discount] = ?\n"
                + " WHERE [product_id] = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            ps.setString(index++, product.getProductName());
            ps.setDouble(index++, product.getPrice());
            ps.setString(index++, product.getDescription());
            ps.setString(index++, product.getImage());
            ps.setInt(index++, product.getCategoryId());
            ps.setString(index++, product.getGender());
            ps.setBoolean(index++, product.isIsSale());
            ps.setBoolean(index++, product.isIsActive());
            ps.setInt(index++, product.getDiscount());
            ps.setInt(index++, product.getProductId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * HoaNK - them 1 bien the cua san pham
     */
    public boolean addAProductVariant(ProductVariant variant) {
        String query = "INSERT INTO [dbo].[product_variants] ([product_id],[size_id]\n"
                + " ,[color_id],[quantity],[image])\n"
                + "   VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            ps.setInt(index++, variant.getProductId());
            ps.setInt(index++, variant.getSizeId());
            ps.setInt(index++, variant.getColorId());
            ps.setInt(index++, variant.getQuantity());
            ps.setString(index++, variant.getImage());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Error at ProductDAO");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * HoaNK - add ảnh vào bảng ảnh
     */
    public boolean addDetailsImage(int pid, String imageDetails) {
        String query = "INSERT INTO product_images(product_id,image_path)"
                + " VALUES (?,?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, pid);
            ps.setString(2, imageDetails);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Error ProductDAO");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * HoaNK - Lay danh sach anh cua 1 san pham bang product id
     */
    public List<ProductImage> getAllProductImageDetails(int pid) {
        String query = "SELECT * FROM product_images WHERE product_id = ?";

        List<ProductImage> list = new ArrayList();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            ps.setInt(index++, pid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductImage productImage = new ProductImage(rs.getInt("image_id"),
                            rs.getString("image_path"), rs.getInt("image_order"),
                            rs.getInt("product_id"));
                    list.add(productImage);
                }
            }
        } catch (Exception e) {
            System.err.println("Error at ProductDAO");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * HoaNK - Xoa 1 bien the bang id
     */
    public boolean deleteAProductVariantById(int variantId) {
        String query = "DELETE FROM product_variants where variant_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, variantId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error at Product DAO");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * HoaNK - Xoa 1 anh the bang id
     */
    public boolean deleteAImageDetailsById(int imageDetailsId) {
        String query = "DELETE FROM product_images where image_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, imageDetailsId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error at Product DAO");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * HoaNK - Cap nhat so luong va anh chinh cua bien the
     */
    public boolean updateAVariant(int quantity, String image, int variantId) {
        String query = "UPDATE product_variants SET quantity = ?, image = ? WHERE variant_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setString(2, image);
            ps.setInt(3, variantId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error at Product DAO");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * HoaNK - dem so bien the cua 1 san pham
     */
    public int countVariant(int productId) {
        String query = "SELECT COUNT(*) FROM product_variants WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error at ProductDAO");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * HoaNK-update trang thai hoat dong cua san pham khi xoa het bien the
     */
    public void updateIsActive(int productId, int status) {
        String query = "UPDATE products SET is_active = ? WHERE product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, status);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error at ProductDAO");
            e.printStackTrace();
        }
    }

    /* 
     * @author dotha 
     * Lấy 5 sản phẩm mới nhất có liên quán đến sản
     * phẩm trong tham số
     *
     * @param product đối tượng để lấy tham số
     * @return 1 list các product được lọc cùng category, gender và giá của sản
     * phẩm được lọc không lớn hơn hay nhỏ hơn 20% giá của product parameter
     */
    public List<Product> getRelatedProducts(Product product) {
        List<Product> listProducts = new ArrayList<>();
        String sql = "                     SELECT TOP 4 * FROM products\n" +
"                     WHERE category_id = ? AND gender = ? AND price BETWEEN ? AND ? AND product_id != ?\n" +
"                     ORDER BY created_at DESC";
        double lowPrice = product.getSalePrice() - (product.getSalePrice() * 20 / 100.0);
        double highPrice = product.getSalePrice() + (product.getSalePrice() * 20 / 100.0);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, product.getCategoryId());
            statement.setObject(2, product.getGender());
            statement.setObject(3, lowPrice);
            statement.setObject(4, highPrice);
            statement.setObject(5, product.getProductId()); // tránh lấy trùng sản phẩm với sản phẩm đang xem

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt(1);
                    String productName = rs.getString(2);
                    double price = rs.getDouble(3);
                    String description = rs.getString(4);
                    String image = rs.getString(5);
                    int categoryId = rs.getInt(6);
                    String gender = rs.getString(7);
                    boolean isSale = rs.getBoolean(8);
                    boolean isActive = rs.getBoolean(9);
                    int discount = rs.getInt(10);
                    LocalDateTime createdAt = rs.getTimestamp(11).toLocalDateTime();
                    Product p = new Product(productId, productName, price, description, image, categoryId, gender, isSale, isActive, discount, createdAt);
                    listProducts.add(p);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error at ProductDAO");
            e.printStackTrace();
        }
        return listProducts;
    }

    /**
     * @author dotha
     * @param productId id sản phẩm
     * @param productColorId color id sản phầm
     * @param productSizeId size id sản phẩm
     * @return variant id được tìm thấy, -1 nếu không thấy
     */
    public int getVariantIdByProductIdAndSizeIdAndColorId(int productId, int productColorId, int productSizeId) {
        String sql = "SELECT variant_id FROM product_variants WHERE product_id = ? AND size_id = ? AND color_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            statement.setInt(2, productSizeId);
            statement.setInt(3, productColorId);

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
     * @param aInt product id
     * @return tên của product tìm thấy bằng product id
     */
    public String getProductNameByProductId(int aInt) {
        String sql = "                     select product_name from products where product_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, aInt);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author dotha
     * @param aInt size id
     * @return tên của size được tìm thấy bằng size id
     */
    private String getSizeNameBySizeId(int aInt) {
        String sql = "                     select size_name from sizes where size_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, aInt);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author dotha
     * @param productId product id
     * @return phần trăm giảm của product được tìm thấy bằng product id
     */
    public double getProductSalePriceByProductId(int productId) {
        String sql = "                     SELECT discount FROM products WHERE product_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, productId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @author dotha
     * @param variantId variant id
     * @return số lượng của variant được tìm thấy bằng variant id
     */
    public int getQuanityByVariantId(int variantId) {
        String sql = "                     SELECT quantity FROM product_variants \n" +
"                     WHERE variant_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, variantId);
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
     * @date 25/02/2026
     * @param variantId variant id
     * @return giá tiền (trừ đi cả giá tiền nếu nó được sale) của một sản phẩm
     * bằng variant id
     */
    public double getPriceByVariantId(int variantId) {
        String sql = "                       SELECT P.price * (1 - p.discount / 100.0) FROM product_variants PV\n" +
"                       JOIN products P ON P.product_id = PV.product_id\n" +
"                       WHERE PV.variant_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, variantId);
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

    public boolean setProductVariantQuantityByVariantId(int variantId, int quantityOrder) {
        String sql = "                     UPDATE product_variants\n" +
"                     SET quantity = quantity - ?\n" +
"                     WHERE variant_id = ?\n" +
"                     AND quantity >= ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int index = 1;
            st.setObject(index++, quantityOrder);
            st.setObject(index++, variantId);
            st.setObject(index++, quantityOrder);
            int row = st.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @author LinhNH
     * @param lọc hiển thị ra 4 sản phẩm flash sale
     */
    public List<Product> getFlashSaleProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 7 * FROM products WHERE is_active = 1 AND is_sale = 1 AND discount > 0 ORDER BY created_at DESC";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setImage(rs.getString("image"));
                p.setDiscount(rs.getInt("discount"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @author LinhNH
     * @param hiển thị ra 8 sản phẩm best seller
     */
    public List<Product> getTop8BestSeller() {
        List<Product> list = new ArrayList<>();

        String sql
                = "SELECT TOP 8 p.product_id, p.product_name, p.price, p.image, p.discount, "
                + "       SUM(oi.quantity) AS total_sold "
                + "FROM order_items oi "
                + "JOIN product_variants pv ON oi.variant_id = pv.variant_id "
                + "JOIN products p ON pv.product_id = p.product_id "
                + "JOIN orders o ON oi.order_id = o.order_id "
                + "WHERE o.status = N'Hoàn thành' AND p.is_active = 1 "
                + "GROUP BY p.product_id, p.product_name, p.price, p.image, p.discount "
                + "ORDER BY total_sold DESC";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setImage(rs.getString("image"));
                p.setDiscount(rs.getInt("discount"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getNumberProductSale(int cid, int discountRange, int priceRange, String textSearch) {

        String query = "SELECT COUNT(*) FROM products p "
                + "JOIN categories c ON p.category_id = c.category_id "
                + "WHERE p.is_active = 1 AND p.is_sale = 1 AND p.discount > 0 "
                + this.addConditionPagingUser("", cid, discountRange, priceRange, textSearch);

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            if (textSearch != null && !textSearch.isEmpty()) {
                ps.setString(index++, "%" + textSearch + "%");
                ps.setString(index++, "%" + textSearch + "%");
                ps.setString(index++, "%" + textSearch + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error at product DAO");
            e.printStackTrace();
        }
        return 0;
    }

    public List<Product> splitPagingProductsSale(int page, int numberPerPage, String saleProduct, int cid, int discountRange, int priceRange, String sort, String textSearch) {
        String query = "SELECT p.*,c.category_name FROM products p\n"
                + "JOIN categories c ON p.category_id = c.category_id\n"
                + " WHERE p.is_active = 1 AND p.is_sale = 1 AND p.discount > 0 \n"
                + this.addConditionPagingUser("", cid, discountRange, priceRange, textSearch)
                + this.orderCondition(sort)
                + " OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY";

        List<Product> list = new ArrayList();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            if (textSearch != null && !textSearch.isEmpty()) {
                ps.setString(index++, "%" + textSearch + "%");
                ps.setString(index++, "%" + textSearch + "%");
                ps.setString(index++, "%" + textSearch + "%");
            }
            ps.setInt(index++, (page - 1) * numberPerPage);
            ps.setInt(index++, numberPerPage);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp time = rs.getTimestamp("created_at");
                    LocalDateTime date = (time != null) ? time.toLocalDateTime() : null;
                    Product product = new Product(rs.getInt("product_id"), rs.getString("product_name"),
                            rs.getDouble("price"), rs.getString("description"), rs.getString("image"),
                            rs.getInt("category_id"), rs.getString("gender"), rs.getBoolean("is_sale"),
                            rs.getBoolean("is_active"), rs.getInt("discount"), date);
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error at product DAO");
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> runQuery(String sql) {
        List<Product> list = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm vào ProductDAO.java
    // Thêm vào ProductDAO.java
    public List<ProductImage> getImagesByProductId(int productId) {
        List<ProductImage> images = new ArrayList<>();
        String sql = "SELECT image_id, image_path, image_order, product_id "
                + "FROM product_images WHERE product_id = ? ORDER BY image_order ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductImage img = new ProductImage();
                img.setImageId(rs.getInt("image_id"));
                // Bỏ prefix "product/" để khớp với mainImage
                String path = rs.getString("image_path");
                if (path != null) {
                    path = path.replace("product/", "").replace("/product/", "");
                }
                img.setImagePath(path);
                img.setImageOrder(rs.getInt("image_order"));
                img.setProductId(rs.getInt("product_id"));
                images.add(img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

// ===================== QUẢN LÝ SIZE =====================
// Thêm Size mới
    public boolean addSize(String sizeName) {
        String sql = "INSERT INTO sizes (size_name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, sizeName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Cập nhật Size
    public boolean updateSize(int sizeId, String sizeName) {
        String sql = "UPDATE sizes SET size_name = ? WHERE size_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, sizeName);
            ps.setInt(2, sizeId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Xóa Size 
    public boolean deleteSize(int sizeId) {
        String sql = "DELETE FROM sizes WHERE size_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, sizeId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

// Kiểm tra trùng tên Size 
    public boolean isSizeNameExists(String name, int id) {
        String sql = "SELECT * FROM sizes WHERE size_name = ? AND size_id != ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// ===================== QUẢN LÝ MÀU SẮC =====================
// Thêm Màu mới
    public boolean addColor(String colorName) {
        String sql = "INSERT INTO colors (color_name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, colorName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Cập nhật Màu
    public boolean updateColor(int colorId, String colorName) {
        String sql = "UPDATE colors SET color_name = ? WHERE color_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, colorName);
            ps.setInt(2, colorId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Xóa Màu
    public boolean deleteColor(int colorId) {
        String sql = "DELETE FROM colors WHERE color_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, colorId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

// Kiểm tra trùng tên Màu
    public boolean isColorNameExists(String name, int id) {
        String sql = "SELECT * FROM colors WHERE color_name = ? AND color_id != ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
