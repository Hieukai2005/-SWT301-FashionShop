/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai;

/**
 *
 * @author dotha
 */
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class OllamaService {

    private static final String OLLAMA_URL = "http://localhost:11434/api/chat";

    public static String askAI(String prompt) {
        try {
            prompt = prompt.replace("\"", "'").replace("\n", " ");

            URL url = new URL(OLLAMA_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            String json = String.format("{\n" +
"          \"model\": \"deepseek-r1:8b\",\n" +
"          \"messages\": [\n" +
"            {\n" +
"              \"role\": \"user\",\n" +
"              \"content\": \"%s\"\n" +
"            }\n" +
"          ],\n" +
"          \"stream\": false\n" +
"        }\n", prompt);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.flush();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            JSONObject obj = new JSONObject(response.toString());

            return obj.getJSONObject("message").getString("content");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "AI đang bận, vui lòng thử lại.";
    }

    public static String generateSQL(String question) {
        String systemPrompt = String.format("Bạn là AI tạo SQL cho website bán quần áo, các câu lệnh chạy trên SQL Server nên bạn phải tạo câu truy vấn dùng cho SQL Server.\n" +
"        Database schema: \n" +
"        products(product_id, product_name, price, description, category_id, gender, is_sale, is_active, discount) \n" +
"        categories(category_id, category_name) \n" +
"        product_variants(variant_id, product_id, size_id, color_id, quantity, sold_quantity) \n" +
"        sizes(size_id, size_name) \n" +
"        colors(color_id, color_name)\n" +
"        Quan hệ giữa các bảng:\n" +
"        products.category_id = categories.category_id\n" +
"        product_variants.product_id = products.product_id\n" +
"        product_variants.size_id = sizes.size_id\n" +
"        product_variants.color_id = colors.color_id\n" +
"        Quy tắc:\n" +
"        - Chỉ tạo câu lệnh SELECT\n" +
"        - Không dùng backticks (`).\n" +
"        - Không dùng markdown.\n" +
"        - Chỉ trả về SQL thuần.\n" +
"        - Chỉ lấy sản phẩm có products.is_active = 1\n" +
"        - Nếu người dùng yêu cầu lấy top các sản phẩm, thì hãy dùng TOP số lượng người dùng yêu cầu như trong sql server, không dùng cú pháp của MySql \n" +
"        - Nếu người dùng hỏi size phải JOIN sizes\n" +
"        - Khi query chuỗi tiếng Việt phải dùng N'' , ví dụ N'quần dài'\n" +
"        - Nếu người dùng yêu cầu lọc các sản phẩm mà có liên quan tới giới tính, thì giới tính trong database có 3 loại là \"Nam\", \"Nữ\" và \"Unisex\" thuộc cột gender của bảng products\n" +
"        - Khi người dùng yêu cầu tìm sản phẩm như áo hoặc quần thì phải dùng lệnh LIKE N'%%'\n" +
"        - Luôn phải có cột product_id, product_name, price trong câu truy vấn\n" +
"        - Nếu hỏi màu phải JOIN colors\n" +
"        - Nếu hỏi tồn kho phải dùng product_variants.quantity\n" +
"        - Nếu cần size hoặc màu phải JOIN product_variants trước\n" +
"        Câu hỏi người dùng:\n" +
"        %s\n", question);
        return askAI(systemPrompt);
    }

    public static String detectIntent(String question) {
        String prompt = String.format("Bạn là AI phân loại câu hỏi cho chatbot bán quần áo.\n" +
"        Nếu câu hỏi liên quan đến:\n" +
"        - tìm sản phẩm\n" +
"        - giá sản phẩm\n" +
"        - size\n" +
"        - màu\n" +
"        - quần áo\n" +
"        hãy trả về đúng chữ:\n" +
"        SEARCH_PRODUCT\n" +
"        Nếu không hãy trả về:\n" +
"        NORMAL_QUESTION\n" +
"        Câu hỏi: %s\n", question);
        return askAI(prompt);
    }

    public static String getIntroduction(String question) {
        String prompt = String.format("Hãy đóng vai 1 nhân viên bán quần áo, giới thiệu bạn là Nhân viên bán quần áo của CANIFA, sau đó trả lời câu hỏi của người dùng.\n" +
"                        Câu hỏi: %s\n", question);
        return askAI(prompt);
    }

}
