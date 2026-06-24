package controller.admin.category;

import dal.CategoryDAO;
import dal.ProductDAO; // BẮT BUỘC PHẢI THÊM IMPORT NÀY
import model.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = {"/category"})
public class CategoryAdminServlet extends HttpServlet {

    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final ProductDAO productDAO = new ProductDAO(); // THÊM KHAI BÁO NÀY

    // ===================== GET: Hiển thị danh sách =====================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                handleList(request, response);
                break;
            case "search":
                handleSearch(request, response);
                break;
            case "getById":
                handleGetById(request, response);
                break;
            default:
                handleList(request, response);
                break;
        }
    }

    // ===================== POST: Xử lý thêm, sửa, xóa (của category) =====================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "add":
                handleAdd(request, response);
                break;
            case "edit":
                handleEdit(request, response);
                break;
            case "delete":
                handleDelete(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/category");
                break;
        }
    }

    // ===================== HANDLER: Danh sách =====================
    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String toastType = (String) session.getAttribute("toastType");
        String toastMessage = (String) session.getAttribute("toastMessage");
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");

        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);

        // 1. Lấy danh sách Category
        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("categories", categories);

        // 2. LẤY DANH SÁCH SIZE VÀ MÀU ĐẨY SANG JSP (CODE MỚI THÊM)
        request.setAttribute("sizes", productDAO.getAllSize());
        request.setAttribute("colors", productDAO.getAllColor());

        request.setAttribute("searchKeyword", "");
        request.getRequestDispatcher("/admin/category.jsp").forward(request, response);
    }

    // ===================== HANDLER: Tìm kiếm =====================
    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String toastType = (String) session.getAttribute("toastType");
        String toastMessage = (String) session.getAttribute("toastMessage");
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");

        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);

        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        
        // Vẫn phải lấy đủ Category, Size, Color khi search
        List<Category> categories = categoryDAO.searchCategories(keyword.trim());
        request.setAttribute("categories", categories);
        
        request.setAttribute("sizes", productDAO.getAllSize()); // Bổ sung
        request.setAttribute("colors", productDAO.getAllColor()); // Bổ sung

        request.setAttribute("searchKeyword", keyword);
        request.getRequestDispatcher("/admin/category.jsp").forward(request, response);
    }

    // ... (Giữ nguyên các hàm handleGetById, handleAdd, handleEdit, handleDelete, setSessionMessage, escapeJson ở dưới y như cũ của bạn) ...
    // ===================== HANDLER: Lấy danh mục theo ID =====================
    private void handleGetById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            Category cat = categoryDAO.getCategoryById(id);
            if (cat != null) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"categoryId\":" + cat.getCategoryId() + ",\"categoryName\":\"" + escapeJson(cat.getCategoryName()) + "\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Không tìm thấy danh mục\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"ID không hợp lệ\"}");
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");
        if (categoryName == null || categoryName.trim().isEmpty()) {
            setSessionMessage(request, "error", "Tên danh mục không được để trống!");
            response.sendRedirect(request.getContextPath() + "/category");
            return;
        }
        if (categoryName.trim().length() > 100) {
            setSessionMessage(request, "error", "Tên danh mục không được quá 100 ký tự!");
            response.sendRedirect(request.getContextPath() + "/category");
            return;
        }
        if (categoryDAO.isCategoryNameExists(categoryName, 0)) {
            setSessionMessage(request, "error", "Tên danh mục \"" + categoryName.trim() + "\" đã tồn tại!");
            response.sendRedirect(request.getContextPath() + "/category");
            return;
        }
        boolean success = categoryDAO.addCategory(categoryName);
        if (success) {
            setSessionMessage(request, "success", "Thêm danh mục \"" + categoryName.trim() + "\" thành công!");
        } else {
            setSessionMessage(request, "error", "Thêm danh mục thất bại. Vui lòng thử lại!");
        }
        response.sendRedirect(request.getContextPath() + "/category");
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("categoryId");
        String categoryName = request.getParameter("categoryName");
        if (idStr == null || categoryName == null || categoryName.trim().isEmpty()) {
            setSessionMessage(request, "error", "Dữ liệu không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/category");
            return;
        }
        int categoryId;
        try {
            categoryId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            setSessionMessage(request, "error", "ID danh mục không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/category");
            return;
        }
        if (categoryName.trim().length() > 100) {
            setSessionMessage(request, "error", "Tên danh mục không được quá 100 ký tự!");
            response.sendRedirect(request.getContextPath() + "/category");
            return;
        }
        if (categoryDAO.isCategoryNameExists(categoryName, categoryId)) {
            setSessionMessage(request, "error", "Tên danh mục \"" + categoryName.trim() + "\" đã tồn tại!");
            response.sendRedirect(request.getContextPath() + "/category");
            return;
        }
        boolean success = categoryDAO.updateCategory(categoryId, categoryName);
        if (success) {
            setSessionMessage(request, "success", "Cập nhật danh mục thành công!");
        } else {
            setSessionMessage(request, "error", "Cập nhật danh mục thất bại. Vui lòng thử lại!");
        }
        response.sendRedirect(request.getContextPath() + "/category");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("categoryId");
        if (idStr == null) {
            setSessionMessage(request, "error", "ID danh mục không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/category");
            return;
        }
        int categoryId;
        try {
            categoryId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            setSessionMessage(request, "error", "ID danh mục không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/category");
            return;
        }
        if (categoryDAO.categoryHasProducts(categoryId)) {
            setSessionMessage(request, "error", "Không thể xóa danh mục này vì vẫn còn sản phẩm liên kết!");
            response.sendRedirect(request.getContextPath() + "/category");
            return;
        }
        boolean success = categoryDAO.deleteCategory(categoryId);
        if (success) {
            setSessionMessage(request, "success", "Xóa danh mục thành công!");
        } else {
            setSessionMessage(request, "error", "Xóa danh mục thất bại. Vui lòng thử lại!");
        }
        response.sendRedirect(request.getContextPath() + "/category");
    }

    private void setSessionMessage(HttpServletRequest request, String type, String message) {
        HttpSession session = request.getSession();
        session.setAttribute("toastType", type);
        session.setAttribute("toastMessage", message);
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
}