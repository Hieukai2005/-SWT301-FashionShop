/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.product;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import java.util.HashMap;
import java.util.Map;
import model.ProductImage;
import model.Color;
import model.Size;
import model.ProductVariant;

/**
 *
 * @author Tien Hoang
 */
@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/product-detail"})
public class ProductDetailServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // 1. Lấy id từ URL
        String idRaw = request.getParameter("id");
        if (idRaw == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idRaw);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // 2. Lấy sản phẩm theo id
        ProductDAO dao = new ProductDAO();
        Product product = dao.getProductById(id);

        if (product == null) {
            response.sendError(404, "Không tìm thấy sản phẩm");
            return;
        }

        // ====== lấy màu/size/variants ======
        ProductDAO variantDao = new ProductDAO();
        List<Color> colors = variantDao.getColorsByProductId(id);
        List<Size> sizes = variantDao.getSizesByProductId(id);
        List<ProductVariant> variants = variantDao.getVariantsByProductId(id);
        // dùng biến var để tự suy ra kiểu dữ liệu thật của đối tượng được lấy ra

        // ====== lấy color đang chọn ======
        Integer selectedColorId = null;
        String colorRaw = request.getParameter("color");
        if (colorRaw != null && !colorRaw.trim().isEmpty()) {
            try {
                selectedColorId = Integer.parseInt(colorRaw);
            } catch (Exception e) {
                selectedColorId = null;
            }
        }
        // ====== MAP sizeId -> stock theo selectedColorId ======
        Map<Integer, Integer> sizeStockMap = new HashMap<>();

        if (variants != null && selectedColorId != null && selectedColorId > 0) {
            for (ProductVariant v : variants) {
                if (v.getColorId() == selectedColorId) {
                    int sizeId = v.getSizeId();
                    int qty = v.getQuantity(); // hoặc getQuantity()
                    sizeStockMap.put(sizeId, sizeStockMap.getOrDefault(sizeId, 0) + qty);
                }
            }
        }

        // ====== lấy size đang chọn ======
        Integer selectedSizeId = null;
        String sizeRaw = request.getParameter("size");
        if (sizeRaw != null && !sizeRaw.trim().isEmpty()) {
            try {
                selectedSizeId = Integer.parseInt(sizeRaw);
            } catch (Exception e) {
                selectedSizeId = null;
            }
        }

        // ====== lấy stock theo color+size (nếu đã chọn size) ======
        Integer stockQty = null;
        if (selectedColorId != null && selectedColorId > 0
                && selectedSizeId != null && selectedSizeId > 0) {

            //query DB trực tiếp 
            stockQty = variantDao.getQtyByProductColorSize(id, selectedColorId, selectedSizeId);
        }

        // ====== PHẦN ẢNH GIỮ NGUYÊN CỦA BẠN ======
        // ====== LẤY ẢNH TỪ DATABASE ======
        // ====== LẤY ẢNH TỪ DATABASE ======
        String mainImage = product.getImage();
        if (mainImage != null) {
            mainImage = mainImage.replace("product/", "").replace("/product/", "");
        }

        List<ProductImage> thumbs = new ArrayList<>();

        boolean isColorSelected = (colorRaw != null && !colorRaw.trim().isEmpty()
                && selectedColorId != null && selectedColorId > 0);

// Lấy toàn bộ ảnh của product từ DB theo đối tượng ProductImage
        List<ProductImage> dbImages = variantDao.getImagesByProductId(id);

        if (!isColorSelected) {
            // Chưa chọn màu: lấy ảnh details từ DB
            for (ProductImage img : dbImages) {
                String path = img.getImagePath();
                if (path != null && path.contains("details")) {
                    // Chuẩn hóa path
                    img.setImagePath(path.replace("product/", "").replace("/product/", ""));
                    thumbs.add(img);
                }
            }
            // Fallback nếu DB không có ảnh details
            if (thumbs.isEmpty() && !dbImages.isEmpty()) {
                ProductImage first = dbImages.get(0);
                first.setImagePath(
                        first.getImagePath().replace("product/", "").replace("/product/", "")
                );
                thumbs.add(first);
            }
        } else {
            // Đã chọn màu: ưu tiên ảnh color từ variant
            String colorImage = null;
            for (ProductVariant v : variants) {
                if (v.getColorId() == selectedColorId
                        && v.getImage() != null && !v.getImage().trim().isEmpty()) {
                    colorImage = v.getImage()
                            .replace("product/", "").replace("/product/", "");
                    break;
                }
            }

            if (colorImage != null) {
                mainImage = colorImage;

                // Tạo ProductImage đại diện cho ảnh color (không có trong bảng product_images)
                ProductImage colorImg = new ProductImage();
                colorImg.setImagePath(colorImage);
                colorImg.setProductId(id);
                colorImg.setImageOrder(0);
                thumbs.add(colorImg);

                // Thêm ảnh details từ DB làm thumb phụ
                for (ProductImage img : dbImages) {
                    String path = img.getImagePath();
                    if (path != null && path.contains("details")) {
                        img.setImagePath(path.replace("product/", "").replace("/product/", ""));
                        thumbs.add(img);
                    }
                }
            } else {
                // Fallback: dùng toàn bộ DB images
                for (ProductImage img : dbImages) {
                    img.setImagePath(
                            img.getImagePath().replace("product/", "").replace("/product/", "")
                    );
                    thumbs.add(img);
                }
                if (!thumbs.isEmpty()) {
                    mainImage = thumbs.get(0).getImagePath();
                }
            }
        }

// Đảm bảo mainImage luôn là thumb đầu tiên
        if (!thumbs.isEmpty() && !thumbs.get(0).getImagePath().equals(mainImage)) {
            ProductImage mainImg = new ProductImage();
            mainImg.setImagePath(mainImage);
            mainImg.setProductId(id);
            mainImg.setImageOrder(0);
            thumbs.add(0, mainImg);
        }

        List<Product> listProductRelated = dao.getRelatedProducts(product);

        // ====== setAttribute sang JSP ======
        request.setAttribute("sizeStockMap", sizeStockMap);
        request.setAttribute("relatedProducts", listProductRelated);
        request.setAttribute("product", product);
        request.setAttribute("colors", colors);
        request.setAttribute("sizes", sizes);
        request.setAttribute("variants", variants);
        request.setAttribute("selectedColorId", selectedColorId);
        request.setAttribute("selectedSizeId", selectedSizeId);
        request.setAttribute("stockQty", stockQty); // null => JSP hiện "--"
        request.setAttribute("mainImage", mainImage);
        request.setAttribute("thumbs", thumbs);
        request.getRequestDispatcher("/product-details.jsp").forward(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
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
