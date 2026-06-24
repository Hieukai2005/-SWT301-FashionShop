/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author dotha
 */
import ai.OllamaService;
import dal.ProductDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Product;

@WebServlet("/chat-ai")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String message = request.getParameter("message");
        String intent = OllamaService.detectIntent(message);
        String aiReply = "Tôi không hiểu câu hỏi, hãy hỏi lại!";
        boolean isProduct = false;
        StringBuilder productLinks = new StringBuilder();
        System.out.println(" Ai answer " + intent);
        if (intent.contains("SEARCH_PRODUCT")) {
            // tạo link sản phẩm
            String sql = OllamaService.generateSQL(message);
            sql = sql.replace("```sql", "").replace("```", "").replace("\n", " ").trim();
            System.out.println(sql);
            if (isSafeSQL(sql)) {
                List<Product> products = new ProductDAO().runQuery(sql);
                for (Product product : products) {
                    System.out.println(product);
                }
                if (products.isEmpty()) {
                     productLinks.append("<p>Tôi không tìm thấy sản phẩm bạn yêu cầu!</p>");
                     response.getWriter().write(productLinks.toString());
                     return;
                }
                isProduct = true;
                productLinks.append("<p>Tôi tìm thấy các sản phẩm sau</p>");
                for (Product product : products) {
                    productLinks.append(
                            "<a href=\"/fashionShop/product-detail?id=" + product.getProductId() + "\" class=\"chat-product-link\">"
                            + "<div class=\"chat-product-name\">"
                            + product.getProductName()
                            + "</div>"
                            + "<div class=\"chat-product-price\">"
                            + product.getPrice() + " đ</div>"
                            + "</a>"
                    );
                }
            }
        } else {
            // chat bình thường
            aiReply = OllamaService.getIntroduction(message);
        }
        if (isProduct) {
            response.getWriter().write(productLinks.toString());
            return;
        }
        response.getWriter().write(aiReply);
    }

    public boolean isSafeSQL(String sql) {
        String s = sql.toLowerCase();
        if (s.contains("delete")
                || s.contains("update")
                || s.contains("insert")
                || s.contains("drop")) {
            return false;
        }
        return true;
    }
}
