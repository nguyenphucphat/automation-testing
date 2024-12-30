import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Utils {
    static public void verifyElement(WebDriver driver, String cssSelector, String expectedMessage, String display) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(1, ChronoUnit.SECONDS));

        if (expectedMessage == null || expectedMessage.isBlank()) {
            boolean isNotVisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(cssSelector)));
            if (!isNotVisible) {
                throw new AssertionError("Phần tử không được xuất hiện, nhưng lại tìm thấy: " + display);
            }
        } else {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
            String actualText = element.getText();
            if (!expectedMessage.equals(actualText)) {
                throw new AssertionError(String.format("Thông báo không khớp! Mong đợi: '%s', Thực tế: '%s'", expectedMessage, actualText));
            }
        }
    }

    public static void login(WebDriver driver) {
        driver.get("http://localhost:4000/login");
        driver.findElement(By.cssSelector(".w-100")).click();
    }
}
