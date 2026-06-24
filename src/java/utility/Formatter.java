
package utility;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author HoaNK
 * @date: 15/2/2026
 * @description: chuan hoa dinh dang du lieu
 */
public class Formatter {   
    // Dinh dang hien thi thoi gian: nam-thang-ngay
    public String getDateFormat(LocalDateTime date) {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formater);       
    }
}
