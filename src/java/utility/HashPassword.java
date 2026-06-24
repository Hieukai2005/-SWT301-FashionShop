package utility;

import java.security.MessageDigest;
import java.util.Base64;

public class HashPassword {
    public static String toSHA1(String str) {
        String salt = "fashionshop@123";
        String result = null;

        str = str + salt;
        try {
            byte[] dataBytes = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            result = Base64.getEncoder().encodeToString(md.digest(dataBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
