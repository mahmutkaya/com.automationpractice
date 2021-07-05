package utilities;

import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Helpers {

    public static void sendKeysIfNotNull(WebElement el, String keys) {
        el.clear();
        if (keys != null) el.sendKeys(keys);
    }

    public static String generateDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("ddMMHHmmss");
        return now.format(formatDate);
    }

}
