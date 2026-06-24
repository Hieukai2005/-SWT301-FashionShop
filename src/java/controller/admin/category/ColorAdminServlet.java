package controller.admin.category;

import dal.ProductDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ColorAdminServlet", urlPatterns = {"/color"})
public class ColorAdminServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        switch (action == null ? "" : action) {
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

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String colorName = request.getParameter("colorName");
        if (colorName == null || colorName.trim().isEmpty()) {
            setSessionMessage(request, "error", "Tên màu không được trống!");
        } else if (productDAO.isColorNameExists(colorName.trim(), 0)) {
            setSessionMessage(request, "error", "Màu \"" + colorName.trim() + "\" đã tồn tại!");
        } else if (productDAO.addColor(colorName.trim())) {
            setSessionMessage(request, "success", "Thêm màu thành công!");
        }
        response.sendRedirect(request.getContextPath() + "/category");
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("colorId");
        String colorName = request.getParameter("colorName");
        try {
            int id = Integer.parseInt(idStr);
            if (productDAO.updateColor(id, colorName.trim())) {
                setSessionMessage(request, "success", "Cập nhật màu thành công!");
            }
        } catch (Exception e) {
            setSessionMessage(request, "error", "Lỗi dữ liệu!");
        }
        response.sendRedirect(request.getContextPath() + "/category");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("colorId"));
            if (productDAO.deleteColor(id)) {
                setSessionMessage(request, "success", "Xóa màu thành công!");
            } else {
                setSessionMessage(request, "error", "Không thể xóa màu đang được sử dụng!");
            }
        } catch (Exception e) {
            setSessionMessage(request, "error", "Lỗi ID!");
        }
        response.sendRedirect(request.getContextPath() + "/category");
    }

    private void setSessionMessage(HttpServletRequest request, String type, String msg) {
        HttpSession session = request.getSession();
        session.setAttribute("toastType", type);
        session.setAttribute("toastMessage", msg);
    }
}