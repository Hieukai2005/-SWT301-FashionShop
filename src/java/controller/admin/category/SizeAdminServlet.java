package controller.admin.category;

import dal.ProductDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SizeAdminServlet", urlPatterns = {"/size"})
public class SizeAdminServlet extends HttpServlet {

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
        String sizeName = request.getParameter("sizeName");
        if (sizeName == null || sizeName.trim().isEmpty()) {
            setSessionMessage(request, "error", "Tên size không được để trống!");
        } else if (productDAO.isSizeNameExists(sizeName.trim(), 0)) {
            setSessionMessage(request, "error", "Size \"" + sizeName.trim() + "\" đã tồn tại!");
        } else if (productDAO.addSize(sizeName.trim())) {
            setSessionMessage(request, "success", "Thêm size thành công!");
        }
        response.sendRedirect(request.getContextPath() + "/category");
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("sizeId");
        String sizeName = request.getParameter("sizeName");
        try {
            int id = Integer.parseInt(idStr);
            if (productDAO.updateSize(id, sizeName.trim())) {
                setSessionMessage(request, "success", "Cập nhật size thành công!");
            }
        } catch (Exception e) {
            setSessionMessage(request, "error", "Lỗi dữ liệu!");
        }
        response.sendRedirect(request.getContextPath() + "/category");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("sizeId"));
            if (productDAO.deleteSize(id)) {
                setSessionMessage(request, "success", "Xóa size thành công!");
            } else {
                setSessionMessage(request, "error", "Không thể xóa size đang được sử dụng!");
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